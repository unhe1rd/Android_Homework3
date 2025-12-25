package com.example.homework3.screens.profileScreen.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.homework3.R
import com.example.homework3.layout.Elevations
import com.example.homework3.layout.Spacers
import com.example.homework3.screens.profileScreen.models.Vehicle
import com.example.homework3.screens.profileScreen.viewModel.MyCarsState
import com.example.homework3.screens.profileScreen.viewModel.MyCarsViewModel
import com.example.homework3.ui.layout.Paddings
import com.example.homework3.ui.layout.Size

@Composable
fun MyCarsScreen(
    navController: NavController,
    viewModel: MyCarsViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            MyCarsTopBar(
                title = stringResource(R.string.my_cars),
                onBackClick = { navController.popBackStack() }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { },
                containerColor = Color(0xFFBAD4FF)
            ) {
                Icon(Icons.Default.Add, contentDescription = stringResource(R.string.add_car))
            }
        }
    ) { paddingValues ->
        MyCarsContent(
            state = state,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MyCarsTopBar(
    title: String,
    onBackClick: () -> Unit
) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Default.ArrowBack, contentDescription = stringResource(R.string.back))
            }
        }
    )
}

@Composable
private fun MyCarsContent(
    state: MyCarsState,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        when {
            state.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            state.errorMessage != null -> {
                Text(
                    text = state.errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            state.cars.isEmpty() -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.DirectionsCar,
                        contentDescription = stringResource(R.string.no_car),
                        modifier = Modifier.size(Size.carIconSize),
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(Spacers.large))
                    Text(
                        text = "У вас пока нет автомобилей",
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(Spacers.small))
                    Text(
                        text = "Нажмите + чтобы добавить",
                        color = Color.Gray
                    )
                }
            }

            else -> {
                LazyColumn(
                    contentPadding = PaddingValues(Paddings.medium),
                    verticalArrangement = Arrangement.spacedBy(Spacers.medium)
                ) {
                    items(state.cars) { vehicle ->
                        CarCard(vehicle = vehicle)
                    }
                }
            }
        }
    }
}

@Composable
private fun CarCard(vehicle: Vehicle) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(Elevations.extraSmall)
    ) {
        Column(
            modifier = Modifier.padding(Paddings.medium)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = vehicle.brand,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = vehicle.year.toString(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(Spacers.medium))

            CardInfoRow(
                label = "Гос. номер:",
                value = vehicle.licensePlate
            )

            CardInfoRow(
                label = "СТС:",
                value = vehicle.stsNumber
            )

            CardInfoRow(
                label = "ПТС:",
                value = vehicle.ptsNumber
            )

            CardInfoRow(
                label = "Тип кузова:",
                value = vehicle.bodyType
            )
        }
    }
}

@Composable
private fun CardInfoRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Paddings.extraSmall)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black,
            fontWeight = FontWeight.Medium
        )
    }
}