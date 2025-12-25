package com.example.homework2.viewModel

import androidx.lifecycle.viewModelScope
import com.example.homework2.models.Vehicle
import com.example.homework2.models.VehicleFormAction
import com.example.homework2.models.VehicleFormState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class VehicleFormViewModel : BaseViewModel<VehicleFormAction, VehicleFormState>() {

    private val _state = MutableStateFlow(
        VehicleFormState(
            vehicle = Vehicle.createEmpty()
        )
    )

    override val state: StateFlow<VehicleFormState> = _state.asStateFlow()

    override fun onAction(action: VehicleFormAction) {
        when (action) {
            is VehicleFormAction.UpdateBrand -> updateBrand(action.brand)
            is VehicleFormAction.UpdateLicensePlate -> updateLicensePlate(action.licensePlate)
            is VehicleFormAction.UpdatePtsNumber -> updatePtsNumber(action.ptsNumber)
            is VehicleFormAction.UpdateStsNumber -> updateStsNumber(action.stsNumber)
            is VehicleFormAction.UpdateBodyType -> updateBodyType(action.bodyType)
            is VehicleFormAction.SaveVehicle -> saveVehicle()
            is VehicleFormAction.Cancel -> cancel()
        }
    }

    private fun updateBrand(brand: String) {
        _state.update { it.copy(vehicle = it.vehicle.copy(brand = brand)) }
    }

    private fun updateLicensePlate(licensePlate: String) {
        _state.update { it.copy(vehicle = it.vehicle.copy(licensePlate = licensePlate)) }
    }

    private fun updatePtsNumber(ptsNumber: String) {
        _state.update { it.copy(vehicle = it.vehicle.copy(ptsNumber = ptsNumber)) }
    }

    private fun updateStsNumber(stsNumber: String) {
        _state.update { it.copy(vehicle = it.vehicle.copy(stsNumber = stsNumber)) }
    }

    private fun updateBodyType(bodyType: String) {
        _state.update { it.copy(vehicle = it.vehicle.copy(bodyType = bodyType)) }
    }

    private fun saveVehicle() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            // Валидация полей
            val vehicle = _state.value.vehicle
            if (vehicle.brand.isBlank() || vehicle.licensePlate.isBlank()) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Заполните обязательные поля"
                    )
                }
                return@launch
            }

            delay(500) // Имитация сохранения

            // Здесь должен быть вызов репозитория для сохранения
            _state.update {
                it.copy(
                    isLoading = false,
                    errorMessage = null
                )
            }

            // После сохранения закрываем экран
            // Навигация будет в UI
        }
    }

    private fun cancel() {
        // Навигация будет обработана в UI
    }
}