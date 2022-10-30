package ch.protonmail.android.protonmailtest

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ch.protonmail.android.protonmailtest.ui.MainViewModel
import ch.protonmail.android.protonmailtest.ui.navigation.NavRoute
import ch.protonmail.android.protonmailtest.ui.screens.DetailScreen
import ch.protonmail.android.protonmailtest.ui.screens.MasterScreen
import ch.protonmail.android.protonmailtest.ui.theme.TasksComposeAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HomeScreen()
        }
    }

    @Composable
    private fun HomeScreen() {
        TasksComposeAppTheme {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = NavRoute.Master.path
            ) {
                composable(NavRoute.Master.path) {
                    MasterScreen(goToDetail = { id ->
                        val uriWithId = Uri.encode(id)
                        navController.navigate(NavRoute.Detail.createRoute(uriWithId))
                    }, viewModel = viewModel())
                }
                composable(NavRoute.Detail.path) { backStackEntry ->
                    val id = backStackEntry.arguments?.getString("id") ?: ""
                    DetailScreen(backToMain = { navController.popBackStack() }, viewModel = viewModel(), id = id)
                }
            }
        }
    }

    @Composable
    private fun viewModel() = hiltViewModel<MainViewModel>()
}