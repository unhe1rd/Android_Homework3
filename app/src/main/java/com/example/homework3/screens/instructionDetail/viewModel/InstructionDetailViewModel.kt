package com.example.homework3.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework3.data.model.ApiResponse
import com.example.homework3.data.model.Instruction
import com.example.homework3.data.networkService.MockNetworkService
import com.example.homework3.data.repository.InstructionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class InstructionDetailViewModel : ViewModel() {

    private val instructionRepository = InstructionRepository(networkService = MockNetworkService())

    private val _instruction = MutableStateFlow<ApiResponse<Instruction?>>(ApiResponse.Loading(true))
    val instruction: StateFlow<ApiResponse<Instruction?>> = _instruction.asStateFlow()

    fun loadInstructionById(id: String) {
        viewModelScope.launch {
            _instruction.value = ApiResponse.Loading(true)

            // Получаем все инструкции
            val response = instructionRepository.getAllInstructions()

            when (response) {
                is ApiResponse.Success -> {
                    // Находим инструкцию по ID
                    val foundInstruction = response.data.find { it.id == id }
                    if (foundInstruction != null) {
                        _instruction.value = ApiResponse.Success(foundInstruction)
                    } else {
                        _instruction.value = ApiResponse.Error("Инструкция с ID $id не найдена")
                    }
                }
                is ApiResponse.Error -> {
                    _instruction.value = ApiResponse.Error(response.message)
                }
                is ApiResponse.Loading -> {
                    // Обработка состояния загрузки
                }
            }
        }
    }

    fun clearInstruction() {
        _instruction.value = ApiResponse.Loading(true)
    }
}