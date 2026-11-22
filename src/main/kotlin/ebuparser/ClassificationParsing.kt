package ebuparser

import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.jvmErasure

internal data class LlmFieldMeta(
    val propertyName: String,
    val type: KType,
    val llmField: LlmField,
)

internal fun llmFields(kClass: KClass<*>): List<LlmFieldMeta> =
    kClass.primaryConstructor!!.parameters.mapNotNull { parameter ->
        val llmField = parameter.findAnnotation<LlmField>() ?: return@mapNotNull null
        LlmFieldMeta(parameter.name!!, parameter.type, llmField)
    }

internal fun isLabeledEnum(kClass: KClass<*>): Boolean = kClass.isSubclassOf(Enum::class) && kClass.isSubclassOf(Labeled::class)

internal fun labeledEntries(kClass: KClass<*>): List<Labeled> = kClass.java.enumConstants.map { it as Labeled }

const val DM_PER_EUR = 1.95583

data class ParsedClassification(
    val classification: Classification,
    // Values the model got wrong are null in the classification and described here, instead of becoming a default.
    val issues: List<String>,
)

fun parseClassification(response: Map<String, Any?>): ParsedClassification {
    val parser = ResponseParser()
    val classification = parser.parseObject(Classification::class, response, "") as Classification
    val normalized =
        classification.copy(
            signalBoxes = classification.signalBoxes.map { it.copy(type = normalizeSignalBoxType(it.type)) },
            railVehicles = classification.railVehicles.map { it.copy(series = normalizeVehicleSeries(it.series)) },
        )
    return ParsedClassification(normalized, parser.issues + consistencyIssues(normalized))
}

private class ResponseParser {
    val issues = mutableListOf<String>()

    fun parseObject(
        kClass: KClass<*>,
        value: Any?,
        path: String,
    ): Any? {
        val map =
            value as? Map<*, *> ?: run {
                issues += "$path: kein Objekt, sondern '$value'"
                return null
            }
        val fields = llmFields(kClass)
        val knownKeys = fields.map { it.propertyName }.toSet() + REASONING_PROPERTY
        (map.keys - knownKeys).forEach { issues += "$path: unbekanntes Feld '$it'" }

        val constructor = kClass.primaryConstructor!!
        val arguments =
            constructor.parameters.associateWith { parameter ->
                val field = fields.single { it.propertyName == parameter.name }
                parseValue(field.type, map[parameter.name], path + field.llmField.name)
            }
        return constructor.callBy(arguments)
    }

    private fun parseValue(
        type: KType,
        value: Any?,
        path: String,
    ): Any? {
        val kClass = type.jvmErasure
        return when {
            kClass == Int::class -> parseInt(value, path)
            kClass == Double::class -> parseDouble(value, path)
            kClass == String::class -> parseString(value, path)
            kClass == List::class -> parseList(type, value, path)
            isLabeledEnum(kClass) -> parseLabel(kClass, value, path)
            else -> parseObject(kClass, value, "$path.")
        }
    }

    private fun parseInt(
        value: Any?,
        path: String,
    ): Int? =
        when {
            value == null -> null
            value is Number && value.toDouble() % 1.0 == 0.0 -> value.toInt()
            value is String && value.trim().matches(Regex("""\d+""")) -> value.trim().toInt()
            else -> invalid(path, value)
        }

    private fun parseDouble(
        value: Any?,
        path: String,
    ): Double? =
        when (value) {
            null -> null
            is Number -> value.toDouble()
            else -> invalid(path, value)
        }

    private fun parseString(
        value: Any?,
        path: String,
    ): String =
        when (value) {
            null -> ""
            is String -> value.trim()
            else -> {
                invalid(path, value)
                ""
            }
        }

    private fun parseList(
        type: KType,
        value: Any?,
        path: String,
    ): List<Any> {
        val itemType = type.arguments.single().type!!
        val list =
            value as? List<*> ?: run {
                invalid(path, value)
                return emptyList()
            }
        return list.mapIndexedNotNull { index, item -> parseValue(itemType, item, "$path[${index + 1}]") }
    }

    private fun parseLabel(
        kClass: KClass<*>,
        value: Any?,
        path: String,
    ): Labeled? {
        val text = (value as? String)?.trim() ?: return invalid(path, value)
        val entries = labeledEntries(kClass)
        return entries.firstOrNull { it.label == text }
            ?: entries.firstOrNull { it.label.equals(text, ignoreCase = true) }
            ?: invalid(path, value)
    }

    private fun invalid(
        path: String,
        value: Any?,
    ): Nothing? {
        issues += if (value == null) "$path fehlt" else "$path: ungültiger Wert '$value'"
        return null
    }
}

// The summaries spell the same signal box type in many ways, e.g. "Sp Dr S 60", "SpDrS60" and "SpDr S60".
private val RELAY_SIGNAL_BOX_TYPE = Regex("""\b(Sp\s*)?Dr\s*([SL])\s*(\d+)""", RegexOption.IGNORE_CASE)
private val SPELLED_OUT_SIEMENS_TYPE = Regex("""Spurplan-?\s*Drucktasten\w*.*?Siemens\s*(\d+)""", RegexOption.IGNORE_CASE)

fun normalizeSignalBoxType(type: String): String {
    RELAY_SIGNAL_BOX_TYPE.find(type)?.let { match ->
        val (spurplan, letter, number) = match.destructured
        return "${if (spurplan.isBlank()) "" else "Sp"}Dr${letter.uppercase()} $number"
    }
    SPELLED_OUT_SIEMENS_TYPE.find(type)?.let { return "SpDrS ${it.groupValues[1]}" }
    return type
}

// The class is the first three digits of the vehicle number, after the country code in the twelve-digit form.
private val SERIES_PATTERNS =
    listOf(
        Regex("""\b(?:BR|Baureihe)\s*(\d{3})\b"""),
        Regex("""\b9\d\s?80\s?\d(\d{3})\s?\d{3}-\d\b"""),
        Regex("""\b(\d{3})\s?\d{3}-\d\b"""),
    )

fun normalizeVehicleSeries(series: String): String {
    SERIES_PATTERNS.forEach { pattern -> pattern.find(series)?.let { return "BR ${it.groupValues[1]}" } }
    return series
}

fun consistencyIssues(classification: Classification): List<String> =
    buildList {
        with(classification) {
            val levelCrossingFields = listOf("BÜ-Sicherung" to levelCrossingProtection, "Straßenfahrzeug" to roadVehicle)
            levelCrossingFields.forEach { (name, value) ->
                val saysNoLevelCrossing = value?.label == NO_LEVEL_CROSSING
                if (levelCrossing == Tristate.YES && saysNoLevelCrossing) add("Bahnübergang ja, aber $name '$NO_LEVEL_CROSSING'")
                if (levelCrossing == Tristate.NO && value != null && !saysNoLevelCrossing) {
                    add("kein Bahnübergang, aber $name '${value.label}'")
                }
            }

            if (injured == null && (seriouslyInjured != null || slightlyInjured != null)) {
                add("Schwer- oder Leichtverletzte genannt, aber keine Gesamtzahl der Verletzten")
            }
            if (injured != null && (seriouslyInjured ?: 0) + (slightlyInjured ?: 0) > injured) {
                add("mehr Schwer- und Leichtverletzte als Verletzte insgesamt")
            }

            if ((propertyDamageAmount == null) != (propertyDamageCurrency == Currency.NONE)) {
                add("Sachschaden: Betrag und Währung passen nicht zusammen")
            }

            if (factors.isEmpty()) add("keine Unfallfaktoren")
            if (factors.isNotEmpty() && factors.none { it.role == FactorRole.CAUSAL }) add("kein ursächlicher Faktor")
            factors.forEachIndexed { index, factor ->
                if (factor.category?.coarse == HUMAN_ERROR && factor.actor == Actor.NOBODY) {
                    add("Unfallfaktoren[${index + 1}]: Fehlhandlung ohne Akteur")
                }
            }
        }
    }

fun propertyDamageEur(classification: Classification): Long? {
    val amount = classification.propertyDamageAmount ?: return null
    return when (classification.propertyDamageCurrency) {
        Currency.EUR -> Math.round(amount)
        Currency.DM -> Math.round(amount / DM_PER_EUR)
        else -> null
    }
}

// Three grades instead of yes/no, in the same number of columns. Systemic factors count as contributing.
enum class CauseGrade(
    override val label: String,
) : Labeled {
    NO("nein"),
    CONTRIBUTING("beitragend"),
    CAUSAL("ursächlich"),
}

class CauseColumn(
    val name: String,
    val matches: (Factor) -> Boolean,
)

// Summary columns of the factors, so that a spreadsheet can filter by cause without reading factors.csv.
val causeColumns: List<CauseColumn> =
    listOf(
        CauseColumn("$HUMAN_ERROR Tf") { it.category?.coarse == HUMAN_ERROR && it.actor == Actor.DRIVER },
        CauseColumn("$HUMAN_ERROR Fdl") { it.category?.coarse == HUMAN_ERROR && it.actor == Actor.SIGNALLER },
        CauseColumn("$HUMAN_ERROR Straßenverkehr") { it.category?.coarse == HUMAN_ERROR && it.actor == Actor.ROAD_USER },
    ) +
        CauseCategory.entries.map { it.coarse }.distinct().map { coarse ->
            CauseColumn("Ursache $coarse") { it.category?.coarse == coarse }
        } +
        CauseColumn("Ursache Bremse") { it.category == CauseCategory.VEHICLE_BRAKE }

// null when the model listed no factors: then nothing is known about the causes.
fun causeGrade(
    factors: List<Factor>,
    column: CauseColumn,
): CauseGrade? {
    if (factors.isEmpty()) return null
    val roles = factors.filter(column.matches).map { it.role }
    return when {
        FactorRole.CAUSAL in roles -> CauseGrade.CAUSAL
        roles.isNotEmpty() -> CauseGrade.CONTRIBUTING
        else -> CauseGrade.NO
    }
}
