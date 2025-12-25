package com.example.homework3.screens.instructionDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
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
import androidx.navigation.NavHostController
import com.example.homework3.R
import com.example.homework3.ui.layout.Paddings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstructionDetailScreen(
    navController: NavHostController,
    instructionId: String? = "1"
) {
    var isFavorite by remember { mutableStateOf(false) }
    val difficulty = 2 // Можно передавать как параметр

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Детальная инструкция",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { isFavorite = !isFavorite }) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Избранное",
                            tint = if (isFavorite) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            // Изображение инструкции
            Image(
                painter = painterResource(id = R.drawable.oil_change),
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
                    text = "Замена масла",
                    style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                )

                // Подзаголовок
                Text(
                    text = "Замена масла в моторе М43620\nДиагностика и замена масляного фильтра",
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
                            repeat(difficulty) {
                                Icon(
                                    Icons.Default.Star,
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp),
                                    tint = Color(0xFFFFA000)
                                )
                            }
                            repeat(5 - difficulty) {
                                Icon(
                                    Icons.Default.Star,
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp),
                                    tint = MaterialTheme.colorScheme.outline
                                )
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = "$difficulty/5 (Средняя сложность)",
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
                            text = """
                                1. Подготовка автомобиля
                                - Установите автомобиль на ровную поверхность
                                - Дайте двигателю остыть 15-20 минут
                                - Подготовьте необходимые инструменты
                                
                                2. Слив старого масла
                                - Найдите сливную пробку на поддоне картера
                                - Подставьте емкость для слива масла
                                - Аккуратно открутите пробку
                                - Дождитесь полного стекания масла
                                
                                3. Замена масляного фильтра
                                - Открутите старый масляный фильтр
                                - Смажьте резиновую прокладку нового фильтра
                                - Затяните фильтр рукой без инструментов
                                
                                4. Заливка нового масла
                                - Затяните сливную пробку
                                - Залейте новое масло через заливную горловину
                                - Проверьте уровень масла щупом
                                - Заведите двигатель на 1-2 минуты
                                - Проверьте герметичность и уровень масла
                            """.trimIndent(),
                            style = MaterialTheme.typography.bodyLarge,
                            lineHeight = 24.sp
                        )
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
                                text = "45-60 мин",
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
                                text = "5 видов",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                // Кнопка начала работы
                Button(
                    onClick = {
                        // Начать выполнение инструкции
                    },
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
}