# Registro de validação — PDM Roteiro 11

**Data:** 05/11/2026

## Validação

- O aluno confirmou que o aplicativo SGM abriu e **rodou no Android Studio**.
- O build `assembleDebug` também havia sido aprovado no GitHub Actions.
- Os recursos do projeto incluem Java, Room/SQLite, ViewModel, LiveData, Repository e Hilt para injeção de dependências.
- Ambiente de compilação do repositório: Gradle 8.13, Android Gradle Plugin 8.13.1, Hilt 2.58.
- Para o Android Studio no Windows, foi orientado selecionar o Gradle JDK da JetBrains Runtime 21.0.8, evitando a incompatibilidade do Java 25.

## Como salvar alterações feitas no Android Studio

Execute no terminal, na raiz do repositório:

```powershell
git status
git add -A
git commit -m "Salva alteracoes locais do Roteiro 11 funcionando"
git pull --rebase origin main
git push origin main
```

Se `git commit` retornar `nothing to commit`, não existem alterações locais pendentes. Se houver conflitos durante o rebase, resolva-os antes de executar o `git push`.

**Observação:** este registro confirma a execução comunicada pelo aluno e o build automático anterior. Não substitui a conferência das alterações que estejam somente no computador.
