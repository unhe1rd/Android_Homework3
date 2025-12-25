package com.example.homework2.models

import androidx.compose.runtime.Stable

interface Action
interface State

@Stable
data class UserProfile(
    val id: String,
    val name: String,
    val rank: String,
    val studiedTopics: Int,
    val email: String? = null
)

// Состояние экрана
@Stable
data class ProfileState(
    val userProfile: UserProfile? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
) : State

// Действия
sealed class ProfileAction : Action {
    object LoadProfile : ProfileAction()
    object ReportBug : ProfileAction()
    object OpenMyCar : ProfileAction()
    object Logout : ProfileAction()
    data class NavigateTo(val destination: String) : ProfileAction()
}