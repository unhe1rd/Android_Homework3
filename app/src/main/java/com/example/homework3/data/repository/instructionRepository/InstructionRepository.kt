// data/repository/InstructionRepository.kt
package com.example.homework3.data.repository

import com.example.homework3.data.networkService.MockNetworkService
import com.example.homework3.data.model.ApiResponse
import com.example.homework3.data.model.Instruction

class InstructionRepository (
    private val networkService: MockNetworkService
) {
    suspend fun getAllInstructions(): ApiResponse<List<Instruction>> {
        return networkService.getAllInstructions()
    }
}