package com.rogerio.viceri_test_tec.presentation.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailScreen(
    userId: Int,
    viewModel: UserDetailViewModel,
    onBack: () -> Unit
) {
    val user by viewModel.user.collectAsState()
    LaunchedEffect(key1 = userId) { viewModel.load(userId) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(user?.name ?: "Detalhe") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = androidx.compose.material.icons.Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            Text("Nome: ${user?.name ?: "-"}", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            Text("Email: ${user?.email ?: "-"}")
            Spacer(Modifier.height(8.dp))
            Text("Username: ${user?.username ?: "-"}")
            Spacer(Modifier.height(8.dp))
            Text("City: ${user?.city ?: "-"}")
            Spacer(Modifier.height(8.dp))
            Text("Phone: ${user?.phone ?: "-"}")
            Spacer(Modifier.height(8.dp))
            Text("Website: ${user?.website ?: "-"}")
            Spacer(Modifier.height(8.dp))
            Text("Company: ${user?.companyName ?: "-"}")
        }
    }
}
