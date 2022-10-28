package ch.protonmail.android.protonmailtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import ch.protonmail.android.protonmailtest.ui.common.DrawerBody
import ch.protonmail.android.protonmailtest.ui.common.DrawerHeader
import ch.protonmail.android.protonmailtest.ui.common.TopBar
import ch.protonmail.android.protonmailtest.ui.navigation.NavGraph
import ch.protonmail.android.protonmailtest.ui.navigation.NavRoute
import ch.protonmail.android.protonmailtest.ui.theme.TasksComposeAppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }

    @Composable
    private fun MainScreen() {
        TasksComposeAppTheme {
            val navController = rememberNavController()
            val scaffoldState = rememberScaffoldState()

            var selectedIndex by remember { mutableStateOf(0) }

            val tabs = listOf("All tasks", "Upcoming tasks")

            Scaffold(
                scaffoldState = scaffoldState,
                topBar = {
                    TopAppBar(
                        modifier = Modifier.padding(vertical = 8.dp),
                        title = { Text(getString(R.string.app_name)) }, elevation = 0.dp
                    )
                }) { paddingValues ->
                TabRow(
                    selectedTabIndex = selectedIndex
                ) {
                    tabs.forEachIndexed { index, text ->
                        val selected = selectedIndex == index
                        Tab(
                            selected = selected,
                            onClick = {
                                selectedIndex = index
                                when (index) {
                                    0 -> navController.navigate(NavRoute.AllTasks.path)
                                    1 -> navController.navigate(NavRoute.UpcomingTasks.path)
                                }
                            },
                            text = { Text(text = text) }
                        )
                    }
                }
                Column {
                    NavGraph(
                        modifier = Modifier.padding(
                            top = paddingValues.calculateTopPadding() + 48.dp,
                        ),
                        navController = navController
                    )
                }
            }
        }
    }
}