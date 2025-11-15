package com.rogerio.viceri_test_tec

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.*
import com.rogerio.viceri_test_tec.presentation.detail.UserDetailScreen
import com.rogerio.viceri_test_tec.presentation.list.UserListScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "list") {
        composable("list") {
            val vm = hiltViewModel<com.rogerio.viceri_test_tec.presentation.list.UserListViewModel>()
            UserListScreen(
                viewModel = vm,
                onUserClick = { user ->
                    navController.navigate("detail/${user.id}")
                }
            )
        }
        composable("detail/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toInt() ?: 0
            val vm = hiltViewModel<com.rogerio.viceri_test_tec.presentation.detail.UserDetailViewModel>()
            UserDetailScreen(userId = id, viewModel = vm, onBack = { navController.popBackStack() })
        }
    }
}
