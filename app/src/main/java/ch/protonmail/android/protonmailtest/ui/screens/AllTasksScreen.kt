package ch.protonmail.android.protonmailtest.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ch.protonmail.android.protonmailtest.model.Task
import ch.protonmail.android.protonmailtest.ui.common.LazyList
import ch.protonmail.android.protonmailtest.ui.theme.TasksComposeAppTheme

@Composable
fun AllTasksScreen(
    navigateToDetail: (String) -> Unit,
    tasks: List<Task>
) {
    LazyList(navigateToDetail = navigateToDetail, tasks = tasks)
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    TasksComposeAppTheme(useSystemUiController = false) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            AllTasksScreen(
                navigateToDetail = {},
                tasks = listOf()
            )
        }
    }
}