param([string]$Destination = "dist")
$ErrorActionPreference = "Stop"
mvn -B package -DskipTests
New-Item -ItemType Directory -Force $Destination | Out-Null
$app = Join-Path $Destination "Calculette"
if (Test-Path $app) { Remove-Item -Recurse -Force $app }
jpackage --type app-image --name Calculette --app-version 2.0.0 --vendor sofoste93 `
    --description "A small learner-friendly vintage calculator" --input target `
    --main-jar Calculette.jar --main-class com.sofoste.calculette.CalculetteApp `
    --dest $Destination

