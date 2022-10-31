package ch.protonmail.android.protonmailtest.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.protonmail.android.protonmailtest.data.model.Task
import ch.protonmail.android.protonmailtest.data.repo.TasksRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val tasksRepo: TasksRepo
) : ViewModel() {
    private val selectedCategory = MutableStateFlow(HomeCategory.All)
    private val categories = MutableStateFlow(HomeCategory.values().asList())
    private val refreshing = MutableStateFlow(true)
    private val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)

    val tasks: StateFlow<List<Task>>
        get() = _tasks

    private val _tasks = MutableStateFlow<List<Task>>(value = emptyList())

    private val _state = MutableStateFlow(MainViewState())

    val state: StateFlow<MainViewState>
        get() = _state

    private val _upcomingTasks = MutableStateFlow<List<Task>>(value = emptyList())

    val upcomingTasks: StateFlow<List<Task>>
        get() = _upcomingTasks

    init {
        viewModelScope.launch {
            combine(
                categories, selectedCategory, refreshing
            ) { categories, selectedCategory, refreshing ->
                MainViewState(
                    categories = categories,
                    selectedCategory = selectedCategory,
                    refreshing = refreshing,
                    errorMessage = null,
                )
            }.catch { throwable -> // TODO: emit a UI error here. For now we'll just rethrow
                throw throwable
            }.collect {
                _state.value = it
            }
        }
        getTasks()
    }

    private fun getTasks() = viewModelScope.launch(Dispatchers.IO) {
        tasksRepo.getTasks().collect { tasks ->
            if (tasks != null) {
                _tasks.emit(tasks)
                refreshing(isRefreshing = false)
            }
            tasks?.filter { task ->
                try {
                    val mDate = formatter.parse(task.dueDate)?.time ?: 0
                    mDate > System.currentTimeMillis()
                } catch (e: Exception) {
                    Log.e("MainViewModel", "Error parsing date", e)
                    false
                }
            }?.let { _upcomingTasks.emit(it) }
        }
    }

    private fun refreshing(isRefreshing: Boolean) {
        viewModelScope.launch {
            refreshing.value = isRefreshing
        }
    }

    fun onCategorySelected(category: HomeCategory) {
        selectedCategory.value = category
    }
}

enum class HomeCategory {
    All, Upcoming
}

data class MainViewState(
    val selectedCategory: HomeCategory = HomeCategory.All,
    val categories: List<HomeCategory> = emptyList(),
    val errorMessage: String? = null,
    val refreshing: Boolean = false,
)