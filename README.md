# Viceri - Android (Compose + MVVM + Hilt + Room + Retrofit)

Aplicativo de exemplo que consome a API pública `https://jsonplaceholder.typicode.com/users` e exibe uma lista paginada (4 por página), pull-to-refresh, busca e tela de detalhes. Persistência local com Room e injeção com Hilt.

---

## Conteúdo deste README

- Requisitos
- Como abrir o projeto (Android Studio)
- Como rodar (emulador / dispositivo)
- Comandos Git úteis
- Estrutura técnica do projeto (mapa das pastas)
- Troubleshooting (erros comuns e soluções)
- Como contribuir

---

## Requisitos

- Android Studio (recomendo Arctic Fox / Chipmunk / Electric Eel / Flamingo — use a versão compatível com o plugin Android Gradle indicado no projeto).
- JDK 17 (conforme `compileOptions` do projeto ou o que o Gradle usar).
- Internet para baixar dependências e a API de exemplo.
- Dispositivo/emulador Android API 23+.

---

## Abrir o projeto

1. Abra o Android Studio.
2. `File` → `Open` → selecione a pasta do projeto.
3. Aguarde o Gradle sync. Se houver erro de versão do Kotlin / Compose, ajuste o `build.gradle.kts` (veja seção troubleshooting).

---

## Executar no Emulador / Dispositivo

1. Conecte device ou abra AVD.
2. Selecione o módulo `app` e clique em Run ▶️.
3. O app fará fetch da API e popular o banco local (Room). Use pull-to-refresh (arrastar para baixo) para recarregar.

---


## Explicação técnica 

**MVVM + Hilt + Repository + Room + Retrofit (fluxo de dados)**

1. **UI (Compose)**  
   - `UserListScreen` observa `UserListViewModel.users` (StateFlow).  
   - `UserListViewModel` expõe `isLoading`, `users` e `setQuery()`; chama `repo.refresh()` para sincronizar.

2. **ViewModel**  
   - Gera UI State a partir de flows do `Repository`.  
   - Chama `repo.refreshUsers()` (suspend) em coroutines (viewModelScope), cuidando do `isLoading` e tratativas de erro.

3. **Repository**  
   - `fetchAndSaveUsers()` — chama `api.getUsers()` (suspend), mapeia DTO → Entity, chama `dao.insertAll(...)`.  
   - `observeUsers()` — retorna `dao.getAll()` (Flow<List<UserEntity>>) para observação reativa.  
   - `search(q)` — pode ser `dao.search("%$q%")` retornando Flow.

4. **Room**  
   - `UserDao` contém queries: `getAll()`, `search(query)`, `getById(id)`, `insertAll(users)`.

5. **Retrofit**  
   - `ApiService` com `@GET("users") suspend fun getUsers(): List<UserDto>`  
   - **Importante:** `BASE_URL` termina com `/`. Endpoint final será `BASE_URL + "users"`.

6. **Hilt**  
   - Fornece singleton `Retrofit`, `ApiService`, `AppDatabase`, `UserDao`, `UserRepository`.  
   - Apenas um `@Module` deve prover `UserDao` (evite duplicidade).
