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

    suspend fun getInstructionById(id: String): ApiResponse<Instruction> {
        return networkService.getInstructionById(id)
    }

    suspend fun getFavoriteInstructions(): ApiResponse<List<Instruction>> {
        return networkService.getFavoriteInstructions()
    }

    suspend fun searchInstructions(query: String): ApiResponse<List<Instruction>> {
        return networkService.searchInstructions(query)
    }

    suspend fun toggleFavorite(instructionId: String, isFavorite: Boolean): ApiResponse<Boolean> {
        return networkService.toggleFavorite(instructionId, isFavorite)
    }
}