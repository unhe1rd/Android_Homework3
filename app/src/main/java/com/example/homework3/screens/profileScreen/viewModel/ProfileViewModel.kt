package com.example.homework2.viewModel

import androidx.lifecycle.viewModelScope
import com.example.homework2.models.ProfileAction
import com.example.homework2.models.ProfileState
import com.example.homework2.models.UserProfile
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class ProfileViewModel : BaseViewModel<ProfileAction, ProfileState>() {

    private val _state = MutableStateFlow(
        ProfileState(
            userProfile = UserProfile(
                id = "user123",
                name = "Даниил",
                rank = "6",
                studiedTopics = 10
            )
        )
    )

    override val state: StateFlow<ProfileState> = _state.asStateFlow()

    override fun onAction(action: ProfileAction) {
        when (action) {
            is ProfileAction.LoadProfile -> loadProfile()
            is ProfileAction.ReportBug -> reportBug()
            is ProfileAction.OpenMyCar -> openMyCar()
            is ProfileAction.Logout -> logout()
            is ProfileAction.NavigateTo -> navigateTo(action.destination)
        }
    }

    private fun loadProfile() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            delay(1000)

            _state.update {
                it.copy(
                    isLoading = false,
                    userProfile = UserProfile(
                        id = "user123",
                        name = "Даниил",
                        rank = "6",
                        studiedTopics = 10
                    )
                )
            }
        }
    }

    private fun reportBug() {
        viewModelScope.launch {
            // Логика для сообщения о баге
        }
    }

    private fun openMyCar() {
        // Логика для открытия раздела "Моё авто"
    }

    private fun logout() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            delay(500)

            _state.update {
                ProfileState(
                    isLoading = false,
                    userProfile = null
                )
            }
        }
    }

    private fun navigateTo(destination: String) {
        // Логика навигации
    }
}