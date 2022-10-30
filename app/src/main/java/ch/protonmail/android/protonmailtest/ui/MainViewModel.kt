package ch.protonmail.android.protonmailtest.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.protonmail.android.protonmailtest.di.CryptoHelper
import ch.protonmail.android.protonmailtest.model.Task
import ch.protonmail.android.protonmailtest.repo.TasksRepo
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
    private val onlyFromCache = MutableStateFlow(true)
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
            refreshing(isRefreshing = true)
            combine(
                categories, selectedCategory, refreshing, onlyFromCache
            ) { categories, selectedCategory, refreshing, onlyFromCache ->
                MainViewState(
                    categories = categories,
                    selectedCategory = selectedCategory,
                    refreshing = refreshing,
                    onlyFromCache = onlyFromCache,
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

    // this is a heavy operation, so we want to unblock the UI thread
    // todo: ERROR HANDLING YOU LAZY BASTARD
    private fun getTasks() = viewModelScope.launch(Dispatchers.IO) {
        tasksRepo.getTasks().let { tasks ->
            refreshing(isRefreshing = true)

            val decryptedTasks = tasks.map { task ->
                decryptFields(task)
            }

            _tasks.emit(decryptedTasks)
            _upcomingTasks.emit(decryptedTasks.filter { task ->
                    val mDate = formatter.parse(task.dueDate)?.time ?: 0
                    mDate > System.currentTimeMillis()
                })
            refreshing(isRefreshing = false)
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

    private fun refreshing(isRefreshing: Boolean) {
        viewModelScope.launch {
            refreshing.value = isRefreshing
        }
    }

    fun setOnlyFromCache(doLoad: Boolean) {
        viewModelScope.launch {
            onlyFromCache.value = doLoad
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
    val onlyFromCache: Boolean = true,
    val refreshing: Boolean = false,
)