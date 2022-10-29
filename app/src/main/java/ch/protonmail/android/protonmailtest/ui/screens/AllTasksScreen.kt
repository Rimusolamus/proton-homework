package ch.protonmail.android.protonmailtest.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ch.protonmail.android.protonmailtest.model.Task
import ch.protonmail.android.protonmailtest.ui.common.TaskItem
import ch.protonmail.android.protonmailtest.ui.theme.TasksComposeAppTheme

@Composable
fun AllTasksScreen(
    navigateToDetail: (String) -> Unit,
    tasks: List<Task>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // here we are using LazyColumn to display the list of tasks
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            items(tasks.size) {
                TaskItem(task = tasks[it], navigateToDetail = navigateToDetail)
                Divider()
            }
        }
    }
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