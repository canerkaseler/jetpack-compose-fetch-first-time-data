package com.canerkaseler

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val screenState = MutableStateFlow(value = ScreenState())
    val uiState = screenState
        .onStart {
            fetchArticleList(from = "ViewModel")
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
            initialValue = screenState.value,
        )

    suspend fun fetchArticleList(from: String) {
        delay(timeMillis = 2000L)

        Log.i("CANER", "--> fetchArticleList from: $from")

        screenState.update { state ->
            state.copy(
                text = "Fetch Data ${state.counter}",
                counter = state.counter + 1
            )
        }
    }
}

data class ScreenState(
    val text: String = "",
    val counter: Int = 1,
)