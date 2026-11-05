# Compilar no Windows (Android Studio)

A compilação do projeto foi aprovada no GitHub Actions usando **Java 17**, Gradle 8.13, AGP 8.13.1 e Hilt 2.58.

## Se o terminal informar "25.0.2"

O Java 25 não é suportado para executar o Gradle 8.13. Não é erro do Hilt.

1. Instale um JDK 17 caso não tenha: https://adoptium.net/temurin/releases/?version=17
2. No Android Studio: **File > Settings > Build, Execution, Deployment > Build Tools > Gradle > Gradle JDK**. Selecione **JDK 17** (não JDK 25).
3. Sincronize o Gradle pelo menu **File > Sync Project with Gradle Files**.
4. No terminal PowerShell, na pasta raiz, execute:

```powershell
git pull origin main
powershell -ExecutionPolicy Bypass -File .\build-windows.ps1
```

O script busca JDK 17 ou 21 instalado em diretórios comuns do Windows, configura `JAVA_HOME` apenas dentro do processo de compilação e executa `clean :app:assembleDebug`.

Ao concluir, o APK fica em `app\build\outputs\apk\debug\app-debug.apk`.

**Importante:** escolher Gradle JDK no Android Studio resolve os comandos internos do IDE; o terminal usa `JAVA_HOME` ou `PATH`, de modo que pode continuar usando Java 25 sem o script. Se aparecer outro erro, leia a primeira mensagem em `What went wrong`.
