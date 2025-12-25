package com.example.homework3.screens.detailsScreen.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import com.example.homework3.ui.layout.Paddings
import com.example.homework3.ui.layout.Size

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolsRequiredScreen(
    navController: NavController,
    instructionId: String?
) {
    val tools = remember(instructionId) { getMaterialsForOilChange() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Что понадобится",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF212121)
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Назад",
                            tint = Color(0xFF212121)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
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
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Paddings.medlarge, vertical = Paddings.large)
                ) {
                    Text(
                        text = "Для выполнения работы подготовьте следующие инструменты и материалы:",
                        fontSize = 16.sp,
                        color = Color(0xFF757575),
                        lineHeight = 24.sp
                    )
                }
            }

            itemsIndexed(tools) { index, item ->
                MaterialToolCard(item = item, index = index)
            }

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Paddings.medium, vertical = Paddings.medium),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFE3F2FD)
                    ),
                    shape = RoundedCornerShape(size = 8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(Paddings.medium)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(bottom = Paddings.small)
                        ) {
                            Icon(
                                Icons.Default.Info,
                                contentDescription = "Информация",
                                tint = Color(0xFF1976D2),
                                modifier = Modifier.size(Size.large)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Рекомендации:",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1976D2)
                            )
                        }
                        Text(
                            text = "Все материалы должны соответствовать спецификации BMW Longlife-01 или Longlife-04. Проверьте артикулы перед покупкой.",
                            fontSize = 14.sp,
                            color = Color(0xFF424242),
                            lineHeight = 20.sp
                        )
                    }
                }
            }
            item {
                Button(
                    onClick = {
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Paddings.medium, vertical = Paddings.medium)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50)
                    ),
                    shape = RoundedCornerShape(size = 8.dp)
                ) {
                    Text(
                        text = "Продолжить",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

@Composable
fun MaterialToolCard(item: MaterialToolItem, index: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Paddings.medium, vertical = Paddings.small),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        shape = RoundedCornerShape(size= 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Paddings.medium)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
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
                        color = Color(0xFF1976D2),
                        fontSize = 16.sp
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = item.name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF212121),
                        lineHeight = 24.sp
                    )

                    if (item.quantity.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        AssistChip(
                            onClick = {},
                            label = {
                                Text(
                                    text = item.quantity,
                                    fontWeight = FontWeight.Medium
                                )
                            },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = Color(0xFFE8F5E9),
                                labelColor = Color(0xFF2E7D32)
                            )
                        )
                    }
                }
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.name,
                    tint = getCategoryColor(item.category),
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AssistChip(
                    onClick = {},
                    label = {
                        Text(
                            text = item.category,
                            fontSize = 12.sp
                        )
                    },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = getCategoryColor(item.category).copy(alpha = 0.1f),
                        labelColor = getCategoryColor(item.category)
                    )
                )
                if (item.isRequired) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.CheckCircle,
                            contentDescription = "Обязательно",
                            tint = Color(0xFF4CAF50),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Обязательно",
                            fontSize = 12.sp,
                            color = Color(0xFF4CAF50),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = item.description,
                fontSize = 15.sp,
                color = Color(0xFF424242),
                lineHeight = 22.sp
            )
            if (item.details.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFF5F5F5)
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(Paddings.medium)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(bottom = Paddings.extraSmall)
                        ) {
                            Icon(
                                Icons.Default.Lightbulb,
                                contentDescription = "Детали",
                                tint = Color(0xFF757575),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Детали:",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF757575)
                            )
                        }
                        Text(
                            text = item.details,
                            fontSize = 14.sp,
                            color = Color(0xFF424242),
                            lineHeight = 20.sp
                        )
                    }
                }
            }
            if (item.examples.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFE8F5E9).copy(alpha = 0.5f)
                    ),
                    shape = RoundedCornerShape(Size.small)
                ) {
                    Column(
                        modifier = Modifier.padding(Paddings.medium)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(bottom = Paddings.extraSmall)
                        ) {
                            Icon(
                                Icons.Default.ShoppingCart,
                                contentDescription = "Примеры",
                                tint = Color(0xFF388E3C),
                                modifier = Modifier.size(Size.medium)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Примеры:",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF388E3C)
                            )
                        }
                        Text(
                            text = item.examples,
                            fontSize = 14.sp,
                            color = Color(0xFF424242),
                            lineHeight = 20.sp
                        )
                    }
                }
            }
        }
    }
}
data class MaterialToolItem(
    val id: Int,
    val name: String,
    val quantity: String,
    val description: String,
    val details: String = "",
    val examples: String = "",
    val category: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val isRequired: Boolean = true
)
private fun getCategoryColor(category: String): Color {
    return when (category) {
        "Масло" -> Color(0xFF1976D2)
        "Фильтр" -> Color(0xFFD32F2F)
        "Прокладка" -> Color(0xFF7B1FA2)
        "Инструмент" -> Color(0xFFF57C00)
        "Расходный" -> Color(0xFF388E3C)
        else -> Color(0xFF757575)
    }
}

private fun getMaterialsForOilChange(): List<MaterialToolItem> {
    return listOf(
        MaterialToolItem(
            id = 1,
            name = "Моторное масло",
            quantity = "~4.2 литра",
            description = "Рекомендуется лить 4.0 литра, потом проверить щуп и долить при необходимости",
            details = "Требуемая спецификация: BMW Longlife-01 или Longlife-04. Вязкость 5W-30 или 0W-30/40",
            examples = "Shell Helix Ultra, Mobil 1, Castrol Edge, Liqui Moly",
            category = "Масло",
            icon = Icons.Default.OilBarrel,
            isRequired = true
        ),
        MaterialToolItem(
            id = 2,
            name = "Масляный фильтр",
            quantity = "1 комплект",
            description = "Комплект включает корпус фильтра и уплотнительное кольцо на крышку",
            details = "Фильтр для вашей модели двигателя BMW",
            examples = "Например: Mahle OC 306, Mann HU 816/2 x, Bosch, BMW оригинал",
            category = "Фильтр",
            icon = Icons.Default.FilterAlt,
            isRequired = true
        ),
        MaterialToolItem(
            id = 3,
            name = "Уплотнительное кольцо для сливной пробки",
            quantity = "1 штука",
            description = "Новое уплотнительное кольцо для сливной пробки. Обычно идет в комплекте с фильтром",
            details = "Если не идет в комплекте, необходимо купить отдельно",
            examples = "Артикул: 07 11 9 952 099 (для BMW)",
            category = "Прокладка",
            icon = Icons.Default.Circle,
            isRequired = true
        ),
        MaterialToolItem(
            id = 4,
            name = "Ключ для масляного фильтра",
            quantity = "76 мм",
            description = "Ключ для откручивания крышки масляного фильтра на 76 мм",
            details = "Или универсальный съёмник для масляных фильтров",
            examples = "",
            category = "Инструмент",
            icon = Icons.Default.Build,
            isRequired = true
        ),
        MaterialToolItem(
            id = 5,
            name = "Ключ/головка для сливной пробки",
            quantity = "13-14 мм",
            description = "Ключ или головка для откручивания сливной пробки поддона картера",
            details = "Обычно на 13 мм или 14 мм, в зависимости от модели",
            examples = "",
            category = "Инструмент",
            icon = Icons.Default.Handyman,
            isRequired = true
        ),
        MaterialToolItem(
            id = 6,
            name = "Вороток, трещотка, удлинитель",
            quantity = "Комплект",
            description = "Для удобного откручивания сливной пробки и фильтра",
            details = "Рекомендуется набор инструментов 1/2 дюйма",
            examples = "",
            category = "Инструмент",
            icon = Icons.Default.Settings,
            isRequired = true
        ),
        MaterialToolItem(
            id = 7,
            name = "Емкость для отработки",
            quantity = "5+ литров",
            description = "Емкость для сбора старого масла. Лучше использовать канистру с широкой горловиной",
            details = "Минимум 5 литров, лучше 6-7 литров для запаса",
            examples = "",
            category = "Инструмент",
            icon = Icons.Default.Inventory,
            isRequired = true
        ),
        MaterialToolItem(
            id = 8,
            name = "Перчатки защитные",
            quantity = "1 пара",
            description = "Резиновые или нитриловые перчатки для защиты рук от масла",
            details = "Рекомендуются нитриловые перчатки, они более стойкие к маслам",
            examples = "",
            category = "Расходный",
            icon = Icons.Default.HealthAndSafety,
            isRequired = false
        ),
        MaterialToolItem(
            id = 9,
            name = "Ветошь для очистки",
            quantity = "2-3 штуки",
            description = "Чистая ветошь для протирки маслозаливной горловины и других поверхностей",
            details = "Лучше использовать безворсовые тряпки или микрофибру",
            examples = "",
            category = "Расходный",
            icon = Icons.Default.CleaningServices,
            isRequired = false
        ),
        MaterialToolItem(
            id = 10,
            name = "Средство для очистки рук",
            quantity = "1 флакон",
            description = "Специальное средство для удаления моторного масла с кожи",
            details = "Рекомендуется иметь под рукой на случай попадания масла на кожу",
            examples = "Permatex Fast Orange, Gojo, Liqui Moly Hand Cleaner",
            category = "Расходный",
            icon = Icons.Default.Spa,
            isRequired = false
        )
    )
}