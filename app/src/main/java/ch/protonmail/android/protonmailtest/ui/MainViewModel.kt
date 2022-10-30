package ch.protonmail.android.protonmailtest.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.protonmail.android.protonmailtest.di.CryptoHelper
import ch.protonmail.android.protonmailtest.model.Task
import ch.protonmail.android.protonmailtest.repo.TasksRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val tasksRepo: TasksRepo
) : ViewModel() {
    private val selectedCategory = MutableStateFlow(HomeCategory.All)
    private val categories = MutableStateFlow(HomeCategory.values().asList())
    private val refreshing = MutableStateFlow(true)
    private val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)

    // Holds our view state which the UI collects via [state]
    private val _state = MutableStateFlow(MainViewState())

    val state: StateFlow<MainViewState>
        get() = _state

    private val _tasks = MutableStateFlow<List<Task>>(value = emptyList())

    private val _upcomingTasks = MutableStateFlow<List<Task>>(value = emptyList())

    val tasks: StateFlow<List<Task>>
        get() = _tasks

    val upcomingTasks: StateFlow<List<Task>>
        get() = _upcomingTasks

    init {
        viewModelScope.launch {
            combine(
                categories,
                selectedCategory,
                refreshing
            ) { categories, selectedCategory, refreshing ->
                MainViewState(
                    categories = categories,
                    selectedCategory = selectedCategory,
                    refreshing = refreshing,
                    errorMessage = null,
                )
            }
                .catch { throwable ->
                    // TODO: emit a UI error here. For now we'll just rethrow
                    throw throwable
                }.collect {
                    _state.value = it
                }

            refresh(force = false)
        }
        getTasks()
    }

    // this is a heavy operation, so we want to unblock the UI thread
    // todo: ERROR HANDLING YOU LAZY BASTARD
    private fun getTasks() = viewModelScope.launch(Dispatchers.IO) {
        tasksRepo.getTasks().let {
            _tasks.emit(it.map { task ->
                decryptFields(task)
            })
            _upcomingTasks.emit(it
                .map { task ->
                    decryptFields(task)
                }
                .filter { task ->
                    val mDate = formatter.parse(task.dueDate)?.time ?: 0
                    mDate > System.currentTimeMillis()
                })
            _state.value = _state.value.copy(refreshing = false)
        }
    }

    private fun decryptFields(task: Task): Task {
        return task.copy(
            encryptedTitle = CryptoHelper().instance.decrypt(task.encryptedTitle).getOrNull()
                ?: task.encryptedTitle,
            encryptedDescription = CryptoHelper().instance.decrypt(task.encryptedDescription)
                .getOrNull() ?: task.encryptedDescription,
        )
    }

    private fun refresh(force: Boolean) {
        viewModelScope.launch {
            runCatching {
                refreshing.value = true
            }
            // TODO: look at result of runCatching and show any errors

            refreshing.value = false
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