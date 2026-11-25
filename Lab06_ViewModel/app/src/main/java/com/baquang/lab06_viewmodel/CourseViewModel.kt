package com.baquang.lab06_viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class Course(
    val id: Int,
    val title: String,
    val author: String
)

sealed interface CourseUiState {
    data object Loading : CourseUiState
    data class Success(val list: List<Course>) : CourseUiState
    data class Error(val message: String) : CourseUiState
}

class CourseViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<CourseUiState>(CourseUiState.Loading)
    val uiState: StateFlow<CourseUiState> = _uiState.asStateFlow()

    private var loadCount = 0

    init {
        load()
    }

    fun load() {
        val attempt = loadCount
        loadCount++

        viewModelScope.launch {
            _uiState.value = CourseUiState.Loading
            delay(1200)
            _uiState.value = if (attempt % 2 == 0) {
                CourseUiState.Success(
                    listOf(
                        Course(1, "Android Kotlin cơ bản", "LTDD"),
                        Course(2, "Jetpack Compose", "LTDD"),
                        Course(3, "Material Design 3", "LTDD")
                    )
                )
            } else {
                CourseUiState.Error("Không tải được dữ liệu.\nThử lại!")
            }
        }
    }
}