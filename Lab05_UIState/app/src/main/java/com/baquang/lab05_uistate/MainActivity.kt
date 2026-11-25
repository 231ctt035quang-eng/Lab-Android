package com.baquang.lab05_uistate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                CourseUiStateScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseUiStateScreen() {
    var uiState by remember { mutableStateOf<CourseUiState>(CourseUiState.Loading) }
    var reloadKey by remember { mutableStateOf(0) }

    LaunchedEffect(reloadKey) {
        uiState = CourseUiState.Loading
        delay(1200)
        uiState = if (reloadKey % 2 == 0) {
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

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("UI State - Khóa học") }
            )
        }
    ) { paddingValues ->
        when (val state = uiState) {
            is CourseUiState.Loading -> LoadingContent(paddingValues)
            is CourseUiState.Success -> SuccessContent(paddingValues, state.list) {
                reloadKey++
            }
            is CourseUiState.Error -> ErrorContent(paddingValues, state.message) {
                reloadKey++
            }
        }
    }
}

@Composable
private fun LoadingContent(paddingValues: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Đang tải...",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun SuccessContent(
    paddingValues: PaddingValues,
    courses: List<Course>,
    onReload: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(courses, key = { it.id }) { course ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = course.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = course.author,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onReload,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Tải lại (để xem Error)")
        }
    }
}

@Composable
private fun ErrorContent(
    paddingValues: PaddingValues,
    message: String,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = message,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onRetry,
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Thử lại")
            }
        }
    }
}