package ch.protonmail.android.protonmailtest.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.protonmail.android.protonmailtest.data.model.Task
import ch.protonmail.android.protonmailtest.data.repo.TasksRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val tasksRepo: TasksRepo,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val onlyFromCache = MutableStateFlow(true)
    private val taskId = savedStateHandle.get<String>("id")

    private val taskCached: StateFlow<Task>
        get() = _taskCached

    private val _taskCached = MutableStateFlow(Task())

    private val _state = MutableStateFlow(DetailViewState())

    val state: StateFlow<DetailViewState>
        get() = _state

    init {
        viewModelScope.launch {
            combine(
                taskCached, onlyFromCache
            ) { taskCached, onlyFromCache ->
                DetailViewState(
                    task = taskCached,
                    onlyFromCache = onlyFromCache,
                    errorMessage = null,
                )
            }.catch { throwable -> // TODO: emit a UI error here. For now we'll just rethrow
                throw throwable
            }.collect {
                _state.value = it
            }
        }
        getTask(taskId)
    }

    private fun getTask(taskId: String?) {
        viewModelScope.launch {
            if (taskId != null) {
                _taskCached.value = tasksRepo.fetchTaskCached(taskId)
            }
        }
    }

    fun setOnlyFromCache(doLoad: Boolean) {
        viewModelScope.launch {
            onlyFromCache.value = doLoad
        }
    }
}

data class DetailViewState(
    val task: Task = Task(),
    val onlyFromCache: Boolean = true,
    val errorMessage: String? = null,
)
