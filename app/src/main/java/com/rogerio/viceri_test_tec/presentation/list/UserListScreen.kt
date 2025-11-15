package com.rogerio.viceri_test_tec.presentation.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.rogerio.viceri_test_tec.data.local.UserEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(
    viewModel: UserListViewModel,
    onUserClick: (UserEntity) -> Unit
) {
    val users by viewModel.users.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    var query by remember { mutableStateOf("") }
    val page by viewModel._currentPage.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Usuários") }
            )

            OutlinedTextField(
                value = query,
                onValueChange = {
                    query = it
                    viewModel.setQuery(it)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp, 50.dp, 10.dp, 10.dp),
                placeholder = { Text("Busca por nome ou email") }
            )},

        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { viewModel.prevPage() },
                    enabled = page >= 1
                ) {
                    Text("Anterior")
                }
                var pgAtual = page + 1
                Text("Página $pgAtual")
                Button(
                    onClick = { viewModel.nextPage() },
                    enabled = users.size == 4 // só habilita se tiver página cheia
                ) {
                    Text("Próxima")
                }
            }
        }
    ) { padding ->
        // Conteúdo da tela
        val swipeState = rememberSwipeRefreshState(isRefreshing = isLoading)

        SwipeRefresh(
            state = swipeState,
            onRefresh = { viewModel.refresh() },
            indicator = { s, trigger ->
                SwipeRefreshIndicator(state = s, refreshTriggerDistance = trigger)
            }
        ) {

            // ------ EMPTY STATE ------
            if (users.isEmpty() && !isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            "Nenhum usuário encontrado.",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(Modifier.height(12.dp))
                        Button(onClick = { viewModel.refresh() }) {
                            Text("Tentar novamente")
                        }
                    }
                }
            }
            // ------ LISTA NORMAL ------
            else {
                LazyColumn(modifier = Modifier.fillMaxSize().padding(padding)) {
                    itemsIndexed(users) { index, user ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onUserClick(user) }
                                .padding(16.dp)
                        ) {
                            Text(user.name ?: "-", style = MaterialTheme.typography.titleMedium)
                            Text(user.email ?: "-", style = MaterialTheme.typography.bodyMedium)
                            Text(user.city ?: "-", style = MaterialTheme.typography.bodySmall)
                        }
                        Divider()
                    }
                }
            }
        }
    }

}
