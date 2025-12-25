package com.example.homework3.screens.profileScreen.models

import androidx.compose.runtime.Stable

@Stable
data class Vehicle(
    val id: String,
    val brand: String,
    val year: Int,
    val licensePlate: String,
    val ptsNumber: String,
    val stsNumber: String,
    val bodyType: String,
    val imageUrl: String? = null,
    val isSelected: Boolean = false
)







