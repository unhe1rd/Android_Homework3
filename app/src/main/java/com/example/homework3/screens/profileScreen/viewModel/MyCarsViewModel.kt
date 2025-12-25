package com.example.homework3.screens.profileScreen.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework3.screens.profileScreen.models.Vehicle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class MyCarsState(
    val cars: List<Vehicle> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class MyCarsViewModel : ViewModel() {

    private val _state = MutableStateFlow(MyCarsState())
    val state: StateFlow<MyCarsState> = _state

    init {
        loadCars()
    }

    private fun loadCars() {
        _state.value = _state.value.copy(isLoading = true)

        viewModelScope.launch {
            try {

                val cars = listOf(
                    Vehicle(
                        id = "1",
                        brand = "Toyota",
                        licensePlate = "А123ВС777",
                        stsNumber = "77УТ123456",
                        ptsNumber = "77ТР987654",
                        year = 2020,
                        bodyType = "Седан",
                    ),
                    Vehicle(
                        id = "2",
                        brand = "Kia",
                        licensePlate = "В456ОР777",
                        stsNumber = "77УТ654321",
                        ptsNumber = "77ТР123456",
                        year = 2019,
                        bodyType = "Хэтчбек",
                    )
                )

                _state.value = _state.value.copy(
                    cars = cars,
                    isLoading = false,
                    errorMessage = null
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    errorMessage = "Ошибка загрузки: ${e.message}"
                )
            }
        }
    }
}