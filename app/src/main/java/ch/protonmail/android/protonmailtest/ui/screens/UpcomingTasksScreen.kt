package ch.protonmail.android.protonmailtest.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import ch.protonmail.android.protonmailtest.ui.common.DefaultButton
import ch.protonmail.android.protonmailtest.ui.theme.TasksComposeAppTheme

@Composable
fun UpcomingTasksScreen(
    navigateToDetail: () -> Unit
) {
    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Upcoming tasks Screen", fontSize = 40.sp)

        DefaultButton(
            text = "Detail",
            onClick = navigateToDetail
        )
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
                navigateToDetail = {}
            )
        }
    }
}