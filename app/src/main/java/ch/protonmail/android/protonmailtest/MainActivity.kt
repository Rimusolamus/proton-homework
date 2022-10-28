package ch.protonmail.android.protonmailtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import ch.protonmail.android.protonmailtest.ui.navigation.NavGraph
import ch.protonmail.android.protonmailtest.ui.theme.TasksComposeAppTheme

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
            NavGraph(navController)
        }
    }
}