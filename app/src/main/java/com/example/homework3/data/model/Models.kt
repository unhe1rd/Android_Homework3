package com.example.homework3.data.model

data class Instruction(
    val id: String,
    val title: String,
    val subtitle: String,
    val description: String,
    val difficulty: Int, // от 1 до 5
    val timeRequired: String,
    val toolsRequired: List<String>,
    val steps: List<InstructionStep>,
    val imageResId: Int,
    val category: String = "Авто",
    val isFavorite: Boolean = false
)

data class InstructionStep(
    val stepNumber: Int,
    val title: String,
    val description: String,
    val imageResId: Int? = null
)

sealed class ApiResponse<T> {
    data class Success<T>(val data: T) : ApiResponse<T>()
    data class Error<T>(val message: String) : ApiResponse<T>()
    data class Loading<T>(val isLoading: Boolean) : ApiResponse<T>()
}