param(
    [Parameter(Mandatory = $true)]
    [string]$JarPath
)

$ErrorActionPreference = 'Stop'
$resolvedJar = (Resolve-Path -LiteralPath $JarPath).Path
Set-Location -LiteralPath $PSScriptRoot
& java -jar $resolvedJar '--server.port=8081' '--spring.datasource.url=jdbc:h2:mem:aqa_submission;DB_CLOSE_DELAY=-1'
