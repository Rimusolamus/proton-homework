package ch.protonmail.android.protonmailtest.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ch.protonmail.android.protonmailtest.R
import ch.protonmail.android.protonmailtest.ui.HomeCategory
import ch.protonmail.android.protonmailtest.ui.MainViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun MasterScreen(
    goToDetail: (String) -> Unit,
    viewModel: MainViewModel
) {

    val viewState by viewModel.state.collectAsStateWithLifecycle()
    val tasks by viewModel.tasks.collectAsStateWithLifecycle()

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
            viewModel::onCategorySelected
        )
        when (viewState.selectedCategory) {
            HomeCategory.All -> {
                AllTasksScreen(goToDetail, tasks)
            }
            HomeCategory.Upcoming -> {
                UpcomingTasksScreen(goToDetail)
            }
        }
    }
}

@Composable
fun MakeTabs(
    innerPadding: PaddingValues,
    categories: List<HomeCategory>,
    selectedCategory: HomeCategory,
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
                            HomeCategory.All -> stringResource(R.string.all_tasks)
                            HomeCategory.Upcoming -> stringResource(R.string.upcoming_tasks)
                        }
                    )
                }
            )
        }
    }
}