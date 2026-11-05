# PDM — Roteiro 11

SGM — Sistema de Gestão, desenvolvido em Java no Android Studio, com CRUD de clientes e veículos, Room e Hilt.

Arquitetura: Activity/Fragments (@AndroidEntryPoint) → SGMViewModel (@HiltViewModel) → SGMRepository (@Inject) → DAOs → Room.

SGMApplication inicializa Hilt. DatabaseModule fornece o banco SQLite/Room e DAOs. O banco permanece sgm_local.db, versão 1.

Abra no Android Studio, sincronize Gradle e execute Build > Make Project. Teste cadastro, edição, exclusão e persistência ao reiniciar. A compilação ainda precisa ser validada no Android Studio.
