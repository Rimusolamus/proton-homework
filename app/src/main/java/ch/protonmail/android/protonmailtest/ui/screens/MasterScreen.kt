package ch.protonmail.android.protonmailtest.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ch.protonmail.android.protonmailtest.R
import ch.protonmail.android.protonmailtest.ui.HomeCategory
import ch.protonmail.android.protonmailtest.ui.MainViewModel

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun MasterScreen(
    goToDetail: (String) -> Unit,
    viewModel: MainViewModel
) {

    val viewState by viewModel.state.collectAsStateWithLifecycle()
    val tasks by viewModel.tasks.collectAsStateWithLifecycle()
    val upcomingTasks by viewModel.upcomingTasks.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(vertical = 8.dp),
                title = { Text(stringResource(R.string.app_name)) }, elevation = 0.dp
            )
        }) { innerPadding ->

        MakeTabs(
            innerPadding,
            categories = viewState.categories,
            viewState.selectedCategory,
            listOf(tasks.size, upcomingTasks.size),
            viewModel::onCategorySelected
        )
        if (viewState.refreshing) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    color = MaterialTheme.colors.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        when (viewState.selectedCategory) {
            HomeCategory.All -> {
                AllTasksScreen(goToDetail, tasks)
            }
            HomeCategory.Upcoming -> {
                UpcomingTasksScreen(goToDetail, upcomingTasks)
            }
        }
    }
}

@Composable
fun MakeTabs(
    innerPadding: PaddingValues,
    categories: List<HomeCategory>,
    selectedCategory: HomeCategory,
    tabsNumbers: List<Number>,
    onCategorySelected: (HomeCategory) -> Unit,
) {
    val selectedIndex = categories.indexOfFirst { it == selectedCategory }

    TabRow(
        selectedTabIndex = selectedIndex
    ) {
        categories.forEachIndexed { index, category ->
            Tab(
                selected = selectedIndex == index,
                onClick = {
                    onCategorySelected(category)
                },
                text = {
                    Text(
                        text = when (category) {
                            HomeCategory.All -> "All tasks (${tabsNumbers[0]})"
                            HomeCategory.Upcoming -> "Upcoming tasks (${tabsNumbers[1]})"
                        }
                    )
                }
            )
        }
    }
}