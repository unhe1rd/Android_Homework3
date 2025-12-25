package com.example.homework2.viewModel

import androidx.lifecycle.viewModelScope
import com.example.homework2.models.Vehicle
import com.example.homework2.models.VehicleListAction
import com.example.homework2.models.VehicleListState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class VehicleListViewModel : BaseViewModel<VehicleListAction, VehicleListState>() {

    private val _state = MutableStateFlow(
        VehicleListState(
            // Тестовые данные
            vehicles = listOf(
                Vehicle(
                    id = "1",
                    brand = "Lamborgini Ferrarini",
                    licensePlate = "x228xx 77",
                    ptsNumber = "1234567890",
                    stsNumber = "0987654321",
                    bodyType = "Купе"
                ),
                Vehicle(
                    id = "2",
                    brand = "Toyota Camry",
                    licensePlate = "а123бв 77",
                    ptsNumber = "1122334455",
                    stsNumber = "5566778899",
                    bodyType = "Седан"
                )
            )
        )
    )

    override val state: StateFlow<VehicleListState> = _state.asStateFlow()

    override fun onAction(action: VehicleListAction) {
        when (action) {
            is VehicleListAction.LoadVehicles -> loadVehicles()
            is VehicleListAction.AddVehicle -> addVehicle()
            is VehicleListAction.SelectVehicle -> selectVehicle(action.vehicle)
            is VehicleListAction.EditVehicle -> editVehicle(action.vehicle)
            is VehicleListAction.DeleteVehicle -> deleteVehicle(action.vehicle)
            is VehicleListAction.NavigateBack -> navigateBack()
        }
    }

    private fun loadVehicles() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            delay(1000) // Имитация загрузки

            // В реальном приложении здесь будет загрузка из репозитория
            _state.update {
                it.copy(
                    isLoading = false,
                    vehicles = it.vehicles,
                    errorMessage = null
                )
            }
        }
    }

    private fun addVehicle() {
        // Навигация будет обработана в UI
    }

    private fun selectVehicle(vehicle: Vehicle) {
        _state.update { currentState ->
            val updatedVehicles = currentState.vehicles.map { v ->
                v.copy(isSelected = v.id == vehicle.id)
            }
            currentState.copy(
                vehicles = updatedVehicles,
                selectedVehicle = vehicle
            )
        }
    }

    private fun editVehicle(vehicle: Vehicle) {
        // Навигация будет обработана в UI
    }

    private fun deleteVehicle(vehicle: Vehicle) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            delay(500) // Имитация удаления

            val updatedVehicles = _state.value.vehicles.filter { it.id != vehicle.id }
            _state.update {
                it.copy(
                    isLoading = false,
                    vehicles = updatedVehicles,
                    selectedVehicle = if (it.selectedVehicle?.id == vehicle.id) null else it.selectedVehicle
                )
            }
        }
    }

    private fun navigateBack() {
        // Навигация будет обработана в UI
    }
}