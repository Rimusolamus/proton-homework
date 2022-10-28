package ch.protonmail.android.protonmailtest.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ch.protonmail.android.protonmailtest.ui.screens.AllTasksScreen
import ch.protonmail.android.protonmailtest.ui.screens.DetailScreen
import ch.protonmail.android.protonmailtest.ui.screens.UpcomingTasksScreen

@Composable
fun NavGraph(modifier: Modifier = Modifier, navController: NavHostController) {

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = NavRoute.AllTasks.path
    ) {
        addAllTasksScreen(navController, this)

        addDetailScreen(navController, this)

        addUpcomingScreen(navController, this)
    }
}

private fun addAllTasksScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = NavRoute.AllTasks.path) {
        AllTasksScreen(
            navigateToDetail = {
                navController.navigate(NavRoute.Detail.path)
            }
        )
    }
}

private fun addUpcomingScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = NavRoute.UpcomingTasks.path) {

        UpcomingTasksScreen(
            navigateToDetail = {
                navController.navigate(NavRoute.Detail.path)
            }
        )
    }
}

private fun addDetailScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = NavRoute.Detail.path) {

        DetailScreen(
            popUpToLogin = { popUpToAllTasks(navController) },
        )
    }
}

private fun popUpToAllTasks(navController: NavHostController) {
    navController.popBackStack(NavRoute.AllTasks.path, inclusive = false)
}
