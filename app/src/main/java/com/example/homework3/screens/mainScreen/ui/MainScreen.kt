// screens/mainScreen/MainScreen.kt
package com.example.homework3.screens.mainScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.homework3.data.model.ApiResponse
import com.example.homework3.data.model.Instruction
import com.example.homework3.layout.Spacers
import com.example.homework3.presentation.viewmodel.MainViewModel
import com.example.homework3.ui.MainItemCard
import com.example.homework3.ui.layout.Paddings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    val instructionsState by viewModel.instructions.collectAsStateWithLifecycle()

    Scaffold { paddingValues ->
        when (instructionsState) {
            is ApiResponse.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is ApiResponse.Success -> {
                val instructions = (instructionsState as ApiResponse.Success<List<Instruction>>).data

                if (instructions.isEmpty()) {
                    EmptyScreen()
                } else {
                    InstructionsList(
                        instructions = instructions,
                        navController = navController,
                        viewModel = viewModel,
                        paddingValues = paddingValues
                    )
                }
            }

            is ApiResponse.Error -> {
                ErrorScreen(
                    errorMessage = (instructionsState as ApiResponse.Error<List<Instruction>>).message,
                    onRetry = { viewModel.loadInstructions() },
                    paddingValues = paddingValues
                )
            }
        }
    }
}

@Composable
fun InstructionsList(
    instructions: List<Instruction>,
    navController: NavHostController,
    viewModel: MainViewModel,
    paddingValues: PaddingValues
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        verticalArrangement = Arrangement.spacedBy(Paddings.small),
        contentPadding = PaddingValues(
            horizontal = Paddings.medium,
            vertical = Paddings.medium
        )
    ) {
        items(instructions) { instruction ->
            MainItemCard(
                title = instruction.title,
                subtitle = instruction.subtitle,
                difficulty = instruction.difficulty,
                imageResId = instruction.imageResId,
                onClick = {
                    navController.navigate("instruction/${instruction.id}")
                }
            )
        }
    }
}

@Composable
fun EmptyScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Spacers.large)
        ) {
            Text(
                text = "Инструкции не найдены",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Попробуйте позже",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun ErrorScreen(
    errorMessage: String,
    onRetry: () -> Unit,
    paddingValues: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Ошибка загрузки",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.error
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = errorMessage,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = onRetry) {
            Text("Повторить попытку")
        }
    }
}