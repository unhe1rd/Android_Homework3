// presentation/viewmodel/MainViewModel.kt (упрощенная версия)
package com.example.homework3.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework3.data.model.ApiResponse
import com.example.homework3.data.model.Instruction
import com.example.homework3.data.networkService.MockNetworkService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val networkService = MockNetworkService()

    private val _instructions = MutableStateFlow<ApiResponse<List<Instruction>>>(ApiResponse.Loading(true))
    val instructions: StateFlow<ApiResponse<List<Instruction>>> = _instructions.asStateFlow()

    private val _selectedInstruction = MutableStateFlow<Instruction?>(null)
    val selectedInstruction: StateFlow<Instruction?> = _selectedInstruction.asStateFlow()

    init {
        loadInstructions()
    }

    fun loadInstructions() {
        viewModelScope.launch {
            _instructions.value = ApiResponse.Loading(true)
            _instructions.value = networkService.getAllInstructions()
        }
    }

    fun getInstructionById(id: String) {
        viewModelScope.launch {
            _selectedInstruction.value = null
            when (val response = networkService.getInstructionById(id)) {
                is ApiResponse.Success -> {
                    _selectedInstruction.value = response.data
                }
                is ApiResponse.Error -> {
                    // Обработка ошибки
                }
                else -> {}
            }
        }
    }

    fun toggleFavorite(instructionId: String, currentState: Boolean) {
        viewModelScope.launch {
            networkService.toggleFavorite(instructionId, !currentState)
            // Обновляем локальное состояние
            if (_instructions.value is ApiResponse.Success) {
                val currentList = (_instructions.value as ApiResponse.Success).data
                val updatedList = currentList.map { instruction ->
                    if (instruction.id == instructionId) {
                        instruction.copy(isFavorite = !currentState)
                    } else {
                        instruction
                    }
                }
                _instructions.value = ApiResponse.Success(updatedList)
            }
        }
    }
}