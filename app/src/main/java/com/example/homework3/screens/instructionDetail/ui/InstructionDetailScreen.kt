package com.example.homework3.screens.instructionDetail.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.homework3.data.model.ApiResponse
import com.example.homework3.presentation.viewmodel.InstructionDetailViewModel
import com.example.homework3.ui.layout.Paddings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstructionDetailScreen(
    navController: NavHostController,
    instructionId: String? = "1"
) {
    val viewModel = InstructionDetailViewModel()

    val instructionState by viewModel.instruction.collectAsStateWithLifecycle()

    LaunchedEffect(instructionId) {
        if (instructionId != null) {
            viewModel.loadInstructionById(instructionId)
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.clearInstruction()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    when (instructionState) {
                        is ApiResponse.Loading -> {
                            Text(
                                text = "Загрузка...",
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                        is ApiResponse.Error -> {
                            Text(
                                text = "Ошибка",
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                        is ApiResponse.Success -> {
                            Text(
                                text = "Детальная инструкция",
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        when (val state = instructionState) {
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

            is ApiResponse.Error -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(Paddings.large),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Ошибка загрузки",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.height(Paddings.medium))
                    Text(
                        text = state.message,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(Paddings.large))
                    Button(onClick = {
                        if (instructionId != null) {
                            viewModel.loadInstructionById(instructionId)
                        }
                    }) {
                        Text("Повторить попытку")
                    }
                }
            }

            is ApiResponse.Success -> {
                state.data?.let { instruction ->
                    InstructionDetailContent(
                        instruction = instruction,
                        paddingValues = paddingValues,
                        onStartClick = {
                            // Начать выполнение инструкции
                        }
                    )
                } ?: run {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Инструкция не найдена")
                    }
                }
            }
        }
    }
}

@Composable
fun InstructionDetailContent(
    instruction: com.example.homework3.data.model.Instruction,
    paddingValues: PaddingValues,
    onStartClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
    ) {
        // Изображение инструкции
        Image(
            painter = painterResource(id = instruction.imageResId),
            contentDescription = "Изображение инструкции",
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.padding(Paddings.large),
            verticalArrangement = Arrangement.spacedBy(Paddings.medium)
        ) {
            // Заголовок
            Text(
                text = instruction.title,
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary
            )

            // Подзаголовок
            Text(
                text = instruction.subtitle,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // Разделитель
            Divider(
                modifier = Modifier.padding(vertical = Paddings.medium),
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
            )

            // Блок сложности
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.1f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(Paddings.medium),
                    verticalArrangement = Arrangement.spacedBy(Paddings.small)
                ) {
                    Text(
                        text = "Сложность выполнения:",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Medium
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        repeat(instruction.difficulty) {
                            Icon(
                                Icons.Default.Star,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp),
                                tint = Color(0xFFFFA000)
                            )
                        }
                        repeat(5 - instruction.difficulty) {
                            Icon(
                                Icons.Default.Star,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp),
                                tint = MaterialTheme.colorScheme.outline
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = "${instruction.difficulty}/5 ${getDifficultyText(instruction.difficulty)}",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // Описание инструкции
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier.padding(Paddings.medium),
                    verticalArrangement = Arrangement.spacedBy(Paddings.medium)
                ) {
                    Text(
                        text = "Подробное описание:",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary
                    )

                    Text(
                        text = instruction.description,
                        style = MaterialTheme.typography.bodyLarge,
                        lineHeight = 24.sp
                    )

                    // Показываем шаги инструкции
                    instruction.steps.forEach { step ->
                        Spacer(modifier = Modifier.height(Paddings.medium))
                        Text(
                            text = "${step.stepNumber}. ${step.title}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = step.description,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            // Блок с временем и инструментами
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Paddings.medium)
            ) {
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(Paddings.medium),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "⏱️",
                            fontSize = 24.sp,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        Text(
                            text = "Время",
                            style = MaterialTheme.typography.labelMedium
                        )
                        Text(
                            text = instruction.timeRequired,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(Paddings.medium),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "🔧",
                            fontSize = 24.sp,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        Text(
                            text = "Инструменты",
                            style = MaterialTheme.typography.labelMedium
                        )
                        Text(
                            text = "${instruction.toolsRequired.size} видов",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Кнопка начала работы
            Button(
                onClick = onStartClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = Paddings.large),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = "Начать выполнение",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(vertical = 12.dp)
                )
            }
        }
    }
}

@Composable
fun getDifficultyText(difficulty: Int): String {
    return when (difficulty) {
        1 -> "(Очень легко)"
        2 -> "(Легко)"
        3 -> "(Средняя сложность)"
        4 -> "(Сложно)"
        5 -> "(Очень сложно)"
        else -> ""
    }
}