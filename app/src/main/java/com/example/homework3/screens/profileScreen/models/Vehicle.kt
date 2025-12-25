package com.example.homework2.models

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
) {
    companion object {
        fun createEmpty(): Vehicle {
            return Vehicle(
                id = "",
                brand = "",
                licensePlate = "",
                ptsNumber = "",
                stsNumber = "",
                year = 0,
                bodyType = ""
            )
        }
    }
}

data class VehicleListState(
    val vehicles: List<Vehicle> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val selectedVehicle: Vehicle? = null
) : State

data class VehicleFormState(
    val vehicle: Vehicle = Vehicle.createEmpty(),
    val isEditing: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
) : State

sealed class VehicleListAction : Action {
    object LoadVehicles : VehicleListAction()
    object AddVehicle : VehicleListAction()
    data class SelectVehicle(val vehicle: Vehicle) : VehicleListAction()
    data class EditVehicle(val vehicle: Vehicle) : VehicleListAction()
    data class DeleteVehicle(val vehicle: Vehicle) : VehicleListAction()
    object NavigateBack : VehicleListAction()
}

sealed class VehicleFormAction : Action {
    data class UpdateBrand(val brand: String) : VehicleFormAction()
    data class UpdateLicensePlate(val licensePlate: String) : VehicleFormAction()
    data class UpdatePtsNumber(val ptsNumber: String) : VehicleFormAction()
    data class UpdateStsNumber(val stsNumber: String) : VehicleFormAction()
    data class UpdateBodyType(val bodyType: String) : VehicleFormAction()
    object SaveVehicle : VehicleFormAction()
    object Cancel : VehicleFormAction()
}