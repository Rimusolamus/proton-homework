package ch.protonmail.android.protonmailtest.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val selectedCategory = MutableStateFlow(HomeCategory.All)
    private val categories = MutableStateFlow(HomeCategory.values().asList())
    private val refreshing = MutableStateFlow(false)

    // Holds our view state which the UI collects via [state]
    private val _state = MutableStateFlow(MainViewState())

    val state: StateFlow<MainViewState>
        get() = _state

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