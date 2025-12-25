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

class MainViewModel : ViewModel() {

    private val instructionRepository = InstructionRepository(networkService = MockNetworkService())

    private val _instructions = MutableStateFlow<ApiResponse<List<Instruction>>>(ApiResponse.Loading(true))
    val instructions: StateFlow<ApiResponse<List<Instruction>>> = _instructions.asStateFlow()

    init {
        loadInstructions()
    }

    fun loadInstructions() {
        viewModelScope.launch {
            _instructions.value = ApiResponse.Loading(true)
            _instructions.value = instructionRepository.getAllInstructions()
        }
    }
}