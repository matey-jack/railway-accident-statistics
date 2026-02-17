# BEU Investigation Reports Downloader, written by Claude and Ampcode.
# Downloads all 241 reports from all 5 pages automatically
# No manual URL list needed - it fetches them from the website

$DownloadPath = "C:\\Users\\beaut\\Bahnwesen\\EBUs"
$BaseUrl = "https://www.eisenbahn-unfalluntersuchung.de"

# Create folder if it doesn't exist
if (-not (Test-Path $DownloadPath)) {
    New-Item -ItemType Directory -Path $DownloadPath -Force | Out-Null
    Write-Host "Created directory: $DownloadPath" -ForegroundColor Green
}

Write-Host "Starting download of BEU investigation reports..." -ForegroundColor Green
Write-Host "This will download all 241 files from 5 pages" -ForegroundColor Cyan
Write-Host ""

$DownloadedCount = 0
$FailedCount = 0
$SkippedCount = 0
$AllUrls = @()

# Function to extract PDF URLs from a page
function Get-PDFUrlsFromPage {
    param([int]$PageNumber)
    
    try {
        # Build the page URL
        if ($PageNumber -eq 1) {
            $PageUrl = "$BaseUrl/SiteGlobals/Forms/Suche/Untersuchungsberichtesuche/Untersuchungsberichtesuche_Formular.html?cl2Categories_Suchpfad=1817214&documentType_=Publication&resultsPerPage=50&sortOrder=dateOfIssue_dt+desc"
        } else {
            $PageUrl = "$BaseUrl/SiteGlobals/Forms/Suche/Untersuchungsberichtesuche/Untersuchungsberichtesuche_Formular.html?cl2Categories_Suchpfad=1817214&gtp=1863846_list%253D$PageNumber&documentType_=Publication&resultsPerPage=50&sortOrder=dateOfIssue_dt+desc"
        }
        
        Write-Host "Fetching URLs from page $PageNumber..." -ForegroundColor Cyan
        $Response = Invoke-WebRequest -Uri $PageUrl -UseBasicParsing
        
        # Extract all PDF links from the page - pattern: href="/SharedDocs/Downloads/BEU/Untersuchungsberichte/.../###_Name.pdf?__blob=publicationFile&amp;v=2"
        # The regex captures the full path and query parameter
        $Links = [regex]::Matches($Response.Content, 'href="(/SharedDocs/Downloads/BEU/Untersuchungsberichte/[^"]*\.pdf[^"]*)"') | ForEach-Object { $_.Groups[1].Value }
        
        $UniqueLinks = @()
        foreach ($Link in $Links) {
            # Decode HTML entities (&amp; to &)
            $Link = $Link -replace '&amp;', '&'
            
            # Build absolute URL
            if (-not $Link.StartsWith('http')) {
                $Link = $BaseUrl + $Link
            }
            
            if ($Link -notin $UniqueLinks) {
                $UniqueLinks += $Link
            }
        }
        
        Write-Host "Found $($UniqueLinks.Count) unique PDF links on page $PageNumber" -ForegroundColor Yellow
        return $UniqueLinks
    }
    catch {
        Write-Host "Error fetching page $PageNumber : $_" -ForegroundColor Red
        return @()
    }
}

# Fetch URLs from all 5 pages
for ($page = 1; $page -le 5; $page++) {
    $PageUrls = Get-PDFUrlsFromPage -PageNumber $page
    $AllUrls += $PageUrls
    Start-Sleep -Milliseconds 500  # Be respectful to the server
}

Write-Host ""
Write-Host "Total URLs found: $($AllUrls.Count)" -ForegroundColor Green
Write-Host "Starting downloads..." -ForegroundColor Green
Write-Host ""

# Download all files
for ($i = 0; $i -lt $AllUrls.Count; $i++) {
    $Url = $AllUrls[$i]
    # Extract filename from URL (remove query parameters)
    $UrlWithoutQuery = $Url -split '\?' | Select-Object -First 1
    $Filename = Split-Path -Leaf $UrlWithoutQuery
    $FilePath = Join-Path $DownloadPath $Filename
    
    $Progress = "[$($i+1)/$($AllUrls.Count)]"
    Write-Host $Progress -NoNewline -ForegroundColor Cyan
    
    # Skip if already exists
    if (Test-Path $FilePath) {
        Write-Host " SKIPPED (exists): $Filename" -ForegroundColor Yellow
        $SkippedCount++
        continue
    }
    
    try {
        Write-Host " Downloading: $Filename" -ForegroundColor White
        $ProgressPreference = 'SilentlyContinue'
        Invoke-WebRequest -Uri $Url -OutFile $FilePath -ErrorAction Stop
        Write-Host " [OK] Success" -ForegroundColor Green
        $DownloadedCount++
    }
    catch {
        Write-Host " [FAIL] FAILED: $($_.Exception.Message.Substring(0,50))" -ForegroundColor Red
        $FailedCount++
    }
    
    # Brief pause between downloads (be respectful)
    Start-Sleep -Milliseconds 100
}

# Summary
Write-Host ""
Write-Host "=========================================" -ForegroundColor Cyan
Write-Host "DOWNLOAD COMPLETE" -ForegroundColor Cyan
Write-Host "=========================================" -ForegroundColor Cyan
Write-Host "Total files:    $($AllUrls.Count)" -ForegroundColor White
Write-Host "Downloaded:     $DownloadedCount" -ForegroundColor Green
Write-Host "Skipped:        $SkippedCount" -ForegroundColor Yellow
Write-Host "Failed:         $FailedCount" -ForegroundColor Red
Write-Host "Location:       $DownloadPath" -ForegroundColor White
Write-Host "=========================================" -ForegroundColor Cyan

if ($FailedCount -eq 0) {
    Write-Host "[OK] All files downloaded successfully!" -ForegroundColor Green
} else {
    Write-Host "[WARNING] Some files failed. Check your internet connection." -ForegroundColor Yellow
}
