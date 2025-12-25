package com.example.homework3.screens.detailsScreen.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.homework3.data.model.ToolItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolsRequiredScreen(
    navController: NavController,
    instructionId: String? = "1"
) {
    val tools = remember(instructionId) { getToolsForInstruction(instructionId) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Что понадобится:",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1976D2),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF5F5F5))
        ) {
            // Заголовок
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 24.dp)
                ) {
                    Text(
                        text = "Что понадобится:",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF212121),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Для выполнения работы подготовьте следующие инструменты и материалы",
                        fontSize = 16.sp,
                        color = Color(0xFF757575)
                    )
                }
            }

            // Список инструментов
            itemsIndexed(tools) { index, tool ->
                ToolCard(tool = tool, index = index)
            }

            // Кнопка "Продолжить"
            item {
                Button(
                    onClick = {
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 24.dp)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Продолжить",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            // Отступ
            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

@Composable
fun ToolCard(tool: ToolItem, index: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Номер инструмента
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFE3F2FD)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = (index + 1).toString(),
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1976D2)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Информация об инструменте
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = tool.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF212121),
                        modifier = Modifier.weight(1f)
                    )

                    if (tool.quantity.isNotEmpty()) {
//                        Chip(
//                            onClick = {},
//                        ) {
//                            Text(tool.quantity)
//                        }
                    }
                }

                if (tool.description.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = tool.description,
                        fontSize = 14.sp,
                        color = Color(0xFF757575)
                    )
                }
            }
        }
    }
}

// Функция получения инструментов
private fun getToolsForInstruction(instructionId: String?): List<ToolItem> {
    return when (instructionId) {
        "oil_change" -> listOf(
            ToolItem(
                id = 1,
                name = "Моторное масло",
                description = "5W-30 или по спецификации производителя",
                quantity = "4-5 литров"
            ),
            ToolItem(
                id = 2,
                name = "Масляный фильтр",
                description = "Соответствующий модели двигателя",
                quantity = "1 шт"
            ),
            ToolItem(
                id = 3,
                name = "Уплотнительное кольцо",
                description = "Для сливной пробки",
                quantity = "1 шт"
            ),
            ToolItem(
                id = 4,
                name = "Ключ для масляного фильтра",
                description = "на 76 мм или универсальный \"край\"",
                quantity = ""
            ),
            ToolItem(
                id = 5,
                name = "Ключ/головка для сливной пробки",
                description = "обычно на 13 мм или 14 мм",
                quantity = ""
            ),
            ToolItem(
                id = 6,
                name = "Вороток, трещотка, удлинитель",
                description = "комплект для удобной работы",
                quantity = ""
            ),
            ToolItem(
                id = 7,
                name = "Емкость для отработки",
                description = "для сбора старого масла",
                quantity = "5+ литров"
            ),
            ToolItem(
                id = 8,
                name = "Перчатки защитные",
                description = "резиновые или нитриловые",
                quantity = "пара"
            ),
            ToolItem(
                id = 9,
                name = "Чистая ветошь",
                description = "для протирки и уборки",
                quantity = ""
            )
        )
        else -> emptyList()
    }
}

