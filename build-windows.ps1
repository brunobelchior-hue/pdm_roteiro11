# Script de compilacao do Roteiro 11 (Windows / PowerShell)
# Executa Gradle 8.13 com JDK 17 ou 21, evitando o erro "25.0.2".
$ErrorActionPreference = 'Stop'
Set-Location $PSScriptRoot

$candidates = New-Object 'System.Collections.Generic.List[string]'
function Add-Jdk([string]$path) {
    if ($path -and (Test-Path (Join-Path $path 'bin\java.exe'))) {
        $script:candidates.Add($path)
    }
}
if ($env:JAVA_HOME) { Add-Jdk $env:JAVA_HOME }
$envRoot = "C:\Program Files"
$patterns = @(
    'Eclipse Adoptium\jdk-17*',
    'Microsoft\jdk-17*',
    'Java\jdk-17*',
    'Amazon Corretto\jdk17*',
    'Eclipse Adoptium\jdk-21*',
    'Microsoft\jdk-21*',
    'Java\jdk-21*',
    'Amazon Corretto\jdk21*'
)
foreach ($pattern in $patterns) {
    Get-ChildItem -Path (Join-Path $envRoot $pattern) -Directory -ErrorAction SilentlyContinue |
        ForEach-Object { Add-Jdk $_.FullName }
}
Add-Jdk 'C:\Program Files\Android\Android Studio\jbr'
if ($env:LOCALAPPDATA) {
    Get-ChildItem -Path (Join-Path $env:LOCALAPPDATA 'Programs\Eclipse Adoptium\jdk-*') -Directory -ErrorAction SilentlyContinue |
        ForEach-Object { Add-Jdk $_.FullName }
}
function Get-JavaVersionText([string]$javaExecutable) {
    $info = New-Object System.Diagnostics.ProcessStartInfo
    $info.FileName = $javaExecutable
    $info.Arguments = '-version'
    $info.UseShellExecute = $false
    $info.RedirectStandardError = $true
    $info.RedirectStandardOutput = $true
    $info.CreateNoWindow = $true
    $process = [System.Diagnostics.Process]::Start($info)
    $message = $process.StandardError.ReadToEnd() + $process.StandardOutput.ReadToEnd()
    $process.WaitForExit()
    return $message
}
$selected = $null
foreach ($candidate in ($candidates | Select-Object -Unique)) {
    try {
        $result = Get-JavaVersionText (Join-Path $candidate 'bin\java.exe')
    } catch {
        continue
    }
    $m = [regex]::Match($result, '"(\d+)(?:\.|")')
    if ($m.Success -and @('17','21') -contains $m.Groups[1].Value) {
        $selected = $candidate
        break
    }
}
if (-not $selected) {
    Write-Host 'Nenhum JDK 17 ou 21 encontrado no computador.' -ForegroundColor Red
    Write-Host 'Instale JDK 17 (Temurin): https://adoptium.net/temurin/releases/?version=17'
    Write-Host 'Depois escolha JDK 17 em Settings > Build Tools > Gradle > Gradle JDK.'
    exit 1
}
$env:JAVA_HOME = $selected
$env:Path = (Join-Path $selected 'bin') + ';' + $env:Path
Write-Host "Usando JAVA_HOME: $selected" -ForegroundColor Green
Write-Host (Get-JavaVersionText (Join-Path $selected 'bin\java.exe'))
& .\gradlew.bat --stop
& .\gradlew.bat clean :app:assembleDebug --no-daemon
$buildExit = $LASTEXITCODE
if ($buildExit -ne 0) {
    Write-Host "Falha no build. Veja as linhas de erro acima." -ForegroundColor Red
    exit $buildExit
}
Write-Host 'BUILD SUCCESSFUL. APK: app\build\outputs\apk\debug\app-debug.apk' -ForegroundColor Green
