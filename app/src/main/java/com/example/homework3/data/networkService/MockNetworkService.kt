package com.example.homework3.data.networkService

import com.example.homework3.data.model.ApiResponse
import com.example.homework3.data.model.Instruction
import com.example.homework3.data.model.InstructionStep
import kotlinx.coroutines.delay
import com.example.homework3.R

class MockNetworkService {

    // Имитация сетевой задержки
    private suspend fun simulateNetworkDelay() {
        delay(1000) // Задержка 1 секунда
    }

    // Получить все инструкции
    suspend fun getAllInstructions(): ApiResponse<List<Instruction>> {
        return try {
            simulateNetworkDelay()
            ApiResponse.Success(mockInstructions)
        } catch (e: Exception) {
            ApiResponse.Error("Ошибка загрузки данных: ${e.message}")
        }
    }

    // Получить инструкцию по ID
    suspend fun getInstructionById(id: String): ApiResponse<Instruction> {
        return try {
            simulateNetworkDelay()
            val instruction = mockInstructions.find { it.id == id }
            if (instruction != null) {
                ApiResponse.Success(instruction)
            } else {
                ApiResponse.Error("Инструкция с ID $id не найдена")
            }
        } catch (e: Exception) {
            ApiResponse.Error("Ошибка загрузки данных: ${e.message}")
        }
    }

    // Получить избранные инструкции
    suspend fun getFavoriteInstructions(): ApiResponse<List<Instruction>> {
        return try {
            simulateNetworkDelay()
            val favorites = mockInstructions.filter { it.isFavorite }
            ApiResponse.Success(favorites)
        } catch (e: Exception) {
            ApiResponse.Error("Ошибка загрузки данных: ${e.message}")
        }
    }

    // Поиск инструкций
    suspend fun searchInstructions(query: String): ApiResponse<List<Instruction>> {
        return try {
            simulateNetworkDelay()
            val results = mockInstructions.filter {
                it.title.contains(query, ignoreCase = true) ||
                        it.subtitle.contains(query, ignoreCase = true) ||
                        it.description.contains(query, ignoreCase = true)
            }
            ApiResponse.Success(results)
        } catch (e: Exception) {
            ApiResponse.Error("Ошибка поиска: ${e.message}")
        }
    }

    // Добавить в избранное
    suspend fun toggleFavorite(instructionId: String, isFavorite: Boolean): ApiResponse<Boolean> {
        return try {
            simulateNetworkDelay()
            // В реальном приложении здесь был бы сетевой запрос
            ApiResponse.Success(isFavorite)
        } catch (e: Exception) {
            ApiResponse.Error("Ошибка обновления избранного: ${e.message}")
        }
    }

    // Моковые данные
    private val mockInstructions = listOf(
        Instruction(
            id = "1",
            title = "Замена масла",
            subtitle = "Замена масла в двигателе М43620",
            description = """
                Полная инструкция по замене масла в двигателе. 
                Включает диагностику и замену масляного фильтра.
                Важно использовать правильный тип масла и соблюдать интервалы замены.
            """.trimIndent(),
            difficulty = 2,
            timeRequired = "45-60 минут",
            toolsRequired = listOf("Ключ на 17", "Воронка", "Емкость для слива", "Новый масляный фильтр"),
            steps = listOf(
                InstructionStep(
                    stepNumber = 1,
                    title = "Подготовка",
                    description = "Прогрейте двигатель до рабочей температуры, затем заглушите и дайте остыть 15 минут"
                ),
                InstructionStep(
                    stepNumber = 2,
                    title = "Слив старого масла",
                    description = "Найдите сливную пробку на поддоне картера, подставьте емкость и открутите пробку"
                ),
                InstructionStep(
                    stepNumber = 3,
                    title = "Замена фильтра",
                    description = "Открутите старый масляный фильтр, смажьте уплотнитель нового фильтра маслом и установите его"
                ),
                InstructionStep(
                    stepNumber = 4,
                    title = "Заливка нового масла",
                    description = "Затяните сливную пробку и залейте новое масло через заливную горловину"
                )
            ),
            imageResId = R.drawable.oil_change,
            category = "Двигатель",
            isFavorite = true
        ),
        Instruction(
            id = "2",
            title = "Замена воздушного фильтра",
            subtitle = "Замена воздушного фильтра двигателя",
            description = "Инструкция по замене воздушного фильтра для улучшения работы двигателя.",
            difficulty = 1,
            timeRequired = "15-20 минут",
            toolsRequired = listOf("Отвертка", "Новый воздушный фильтр"),
            steps = listOf(
                InstructionStep(1, "Снятие крышки", "Откройте крышку воздушного фильтра"),
                InstructionStep(2, "Извлечение старого", "Достаньте старый фильтр"),
                InstructionStep(3, "Установка нового", "Установите новый фильтр"),
                InstructionStep(4, "Закрытие крышки", "Закройте крышку воздушного фильтра")
            ),
            imageResId = R.drawable.ic_launcher_foreground, // R.drawable.air_filter
            category = "Двигатель"
        ),
        Instruction(
            id = "3",
            title = "Проверка тормозов",
            subtitle = "Диагностика тормозной системы",
            description = "Полная проверка состояния тормозных колодок, дисков и жидкости.",
            difficulty = 3,
            timeRequired = "60-90 минут",
            toolsRequired = listOf("Домкрат", "Ключ для колес", "Штангенциркуль"),
            steps = listOf(
                InstructionStep(1, "Подъем автомобиля", "Поднимите автомобиль на домкрате"),
                InstructionStep(2, "Снятие колеса", "Снимите колесо"),
                InstructionStep(3, "Осмотр колодок", "Проверьте толщину тормозных колодок"),
                InstructionStep(4, "Проверка дисков", "Проверьте состояние тормозных дисков"),
                InstructionStep(5, "Установка колеса", "Установите колесо обратно")
            ),
            imageResId = R.drawable.ic_launcher_foreground, // R.drawable.brakes
            category = "Тормоза",
            isFavorite = true
        ),
        Instruction(
            id = "4",
            title = "Замена свечей зажигания",
            subtitle = "Замена свечей в 4-цилиндровом двигателе",
            description = "Пошаговая инструкция по замене свечей зажигания.",
            difficulty = 2,
            timeRequired = "30-45 минут",
            toolsRequired = listOf("Свечной ключ", "Динамометрический ключ", "Новые свечи"),
            steps = listOf(
                InstructionStep(1, "Снятие проводов", "Снимите высоковольтные провода"),
                InstructionStep(2, "Выкручивание старых", "Выкрутите старые свечи"),
                InstructionStep(3, "Установка новых", "Установите новые свечи с правильным моментом затяжки"),
                InstructionStep(4, "Подключение проводов", "Подключите высоковольтные провода")
            ),
            imageResId = R.drawable.ic_launcher_foreground, // R.drawable.spark_plugs
            category = "Двигатель"
        ),
        Instruction(
            id = "5",
            title = "Замена лампы фары",
            subtitle = "Замена перегоревшей лампы в фаре",
            description = "Инструкция по замене лампы в передней фаре.",
            difficulty = 1,
            timeRequired = "10-15 минут",
            toolsRequired = listOf("Новая лампа", "Перчатки"),
            steps = listOf(
                InstructionStep(1, "Доступ к фаре", "Откройте капот и найдите заднюю часть фары"),
                InstructionStep(2, "Снятие разъема", "Отсоедините разъем лампы"),
                InstructionStep(3, "Замена лампы", "Замените лампу, не касаясь стеклянной части"),
                InstructionStep(4, "Проверка", "Проверьте работу новой лампы")
            ),
            imageResId = R.drawable.ic_launcher_foreground, // R.drawable.headlight
            category = "Электрика"
        ),
        Instruction(
            id = "6",
            title = "Замена аккумулятора",
            subtitle = "Установка нового автомобильного аккумулятора",
            description = "Правильная замена автомобильного аккумулятора.",
            difficulty = 2,
            timeRequired = "20-30 минут",
            toolsRequired = listOf("Ключ на 10", "Новый аккумулятор"),
            steps = listOf(
                InstructionStep(1, "Отключение клемм", "Сначала отсоедините минусовую клемму, затем плюсовую"),
                InstructionStep(2, "Снятие крепления", "Открутите крепление аккумулятора"),
                InstructionStep(3, "Извлечение старого", "Аккуратно извлеките старый аккумулятор"),
                InstructionStep(4, "Установка нового", "Установите новый аккумулятор"),
                InstructionStep(5, "Подключение клемм", "Подключите сначала плюсовую, затем минусовую клемму")
            ),
            imageResId = R.drawable.ic_launcher_foreground, // R.drawable.battery
            category = "Электрика"
        )
    )
}

// Синоним для удобства
typealias NetworkService = MockNetworkService