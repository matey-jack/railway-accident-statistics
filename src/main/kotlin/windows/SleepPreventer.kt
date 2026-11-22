package windows

import com.sun.jna.platform.win32.Kernel32
import com.sun.jna.platform.win32.WinBase

/*
    Use 'powercfg /requests' in an admin console to check that this works.
    It will only show java.exe, but at least with the full path, so you can see it's the JDK of your app.
 */
object SleepPreventer {
    private val isWindows = System.getProperty("os.name").lowercase().contains("win")

    fun <T> keepAwake(block: () -> T): T {
        if (!isWindows) return block()
        Kernel32.INSTANCE.SetThreadExecutionState(WinBase.ES_CONTINUOUS or WinBase.ES_SYSTEM_REQUIRED)
        try {
            return block()
        } finally {
            Kernel32.INSTANCE.SetThreadExecutionState(WinBase.ES_CONTINUOUS)
        }
    }
}
