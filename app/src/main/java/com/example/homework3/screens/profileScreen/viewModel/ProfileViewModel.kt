package com.example.homework3.screens.profileScreen.viewModel

import androidx.lifecycle.viewModelScope
import com.example.homework3.screens.profileScreen.models.ProfileAction
import com.example.homework3.screens.profileScreen.models.ProfileState
import com.example.homework3.screens.profileScreen.models.UserProfile
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class ProfileViewModel : BaseViewModel<ProfileAction, ProfileState>() {

    private val _state = MutableStateFlow(
        ProfileState()
    )

    override val state: StateFlow<ProfileState> = _state.asStateFlow()

    init {
        loadProfile()
    }

    override fun onAction(action: ProfileAction) {
        when (action) {
            is ProfileAction.LoadProfile -> loadProfile()
            is ProfileAction.ReportBug -> reportBug()
            is ProfileAction.OpenMyCar -> openMyCar()
            is ProfileAction.Logout -> logout()
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
    }

    private fun openMyCar() {
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


}