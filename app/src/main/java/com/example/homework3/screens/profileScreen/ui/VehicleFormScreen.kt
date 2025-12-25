//package com.example.homework2.ui.screen
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material.icons.filled.Check
//import androidx.compose.material.icons.filled.Close
//import androidx.compose.material.icons.filled.Home
//import androidx.compose.material.icons.filled.ListAlt
//import androidx.compose.material.icons.filled.Person
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.NavigationBar
//import androidx.compose.material3.NavigationBarItem
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextFieldDefaults
//import androidx.compose.material3.TopAppBar
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.compose.collectAsStateWithLifecycle
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavHostController
//import com.example.homework2.models.VehicleFormAction
//import com.example.homework2.models.VehicleFormState
//import com.example.homework2.viewModel.VehicleFormViewModel
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun VehicleFormScreen(
//    navController: NavHostController,
//    viewModel: VehicleFormViewModel = viewModel()
//) {
//    val state by viewModel.state.collectAsStateWithLifecycle()
//
//    // Отслеживаем состояние сохранения
//    var isSaveSuccessful by remember { mutableStateOf(false) }
//
//    // Если сохранение успешно, возвращаемся назад
//    LaunchedEffect(isSaveSuccessful) {
//        if (isSaveSuccessful && !state.isLoading && state.errorMessage == null) {
//            navController.navigateUp()
//        }
//    }
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = {
//                    Box(
//                        modifier = Modifier.fillMaxWidth(),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Text(
//                            text = "Данные автомобиля",
//                            style = MaterialTheme.typography.titleLarge,
//                            fontWeight = FontWeight.Bold,
//                            textAlign = TextAlign.Center
//                        )
//                    }
//                },
//                navigationIcon = {
//                    IconButton(onClick = { navController.navigateUp() }) {
//                        Icon(
//                            imageVector = Icons.Default.ArrowBack,
//                            contentDescription = "Назад"
//                        )
//                    }
//                }
//            )
//        },
//        bottomBar = {
//            BottomNavigationBar(navController)
//        }
//    ) { paddingValues ->
//        VehicleFormContent(
//            state = state,
//            onAction = viewModel::onAction,
//            onSaveClick = {
//                onAction(VehicleFormAction.SaveVehicle)
//                // Запускаем таймер для проверки успешного сохранения
//                // В реальном приложении это должно быть через ViewModel
//                LaunchedEffect(Unit) {
//                    delay(1000) // Имитация задержки сохранения
//                    if (!state.isLoading && state.errorMessage == null) {
//                        isSaveSuccessful = true
//                    }
//                }
//            },
//            onCancelClick = { navController.navigateUp() },
//            modifier = Modifier.padding(paddingValues)
//        )
//    }
//}
//
//@Composable
//private fun VehicleFormContent(
//    state: VehicleFormState,
//    onAction: (VehicleFormAction) -> Unit,
//    onSaveClick: () -> Unit,
//    onCancelClick: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    Column(
//        modifier = modifier
//            .fillMaxSize()
//            .padding(horizontal = 16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        // Форма ввода данных
//        Card(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 16.dp),
//            shape = RoundedCornerShape(16.dp),
//            colors = CardDefaults.cardColors(
//                containerColor = Color(0xFFBAD4FF),
//                contentColor = Color.Black
//            ),
//            elevation = CardDefaults.cardElevation(4.dp)
//        ) {
//            Column(
//                modifier = Modifier.padding(24.dp),
//                verticalArrangement = Arrangement.spacedBy(16.dp)
//            ) {
//                // Марка машины
//                OutlinedTextField(
//                    value = state.vehicle.brand,
//                    onValueChange = { onAction(VehicleFormAction.UpdateBrand(it)) },
//                    label = { Text("Марка машины") },
//                    modifier = Modifier.fillMaxWidth(),
//                    singleLine = true
//                )
//
//                // Номер машины
//                OutlinedTextField(
//                    value = state.vehicle.licensePlate,
//                    onValueChange = { onAction(VehicleFormAction.UpdateLicensePlate(it)) },
//                    label = { Text("Номер машины") },
//                    modifier = Modifier.fillMaxWidth(),
//                    singleLine = true
//                )
//
//                // Номер ПТС
//                OutlinedTextField(
//                    value = state.vehicle.ptsNumber,
//                    onValueChange = { onAction(VehicleFormAction.UpdatePtsNumber(it)) },
//                    label = { Text("Номер ПТС") },
//                    modifier = Modifier.fillMaxWidth(),
//                    singleLine = true
//                )
//
//                // Номер СТС
//                OutlinedTextField(
//                    value = state.vehicle.stsNumber,
//                    onValueChange = { onAction(VehicleFormAction.UpdateStsNumber(it)) },
//                    label = { Text("Номер СТС") },
//                    modifier = Modifier.fillMaxWidth(),
//                    singleLine = true
//                )
//
//                // Тип кузова
//                OutlinedTextField(
//                    value = state.vehicle.bodyType,
//                    onValueChange = { onAction(VehicleFormAction.UpdateBodyType(it)) },
//                    label = { Text("Тип кузова") },
//                    modifier = Modifier.fillMaxWidth(),
//                    singleLine = true
//                )
//            }
//        }
//
//        Spacer(modifier = Modifier.weight(1f))
//
//        if (state.isLoading) {
//            CircularProgressIndicator(
//                modifier = Modifier.padding(16.dp)
//            )
//        } else if (state.errorMessage != null) {
//            Text(
//                text = state.errorMessage,
//                color = MaterialTheme.colorScheme.error,
//                modifier = Modifier.padding(16.dp)
//            )
//        }
//
//        // Кнопки действий
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 16.dp),
//            horizontalArrangement = Arrangement.spacedBy(16.dp)
//        ) {
//            // Кнопка отмены
//            Button(
//                onClick = onCancelClick,
//                modifier = Modifier.weight(1f),
//                shape = RoundedCornerShape(12.dp),
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = Color.LightGray,
//                    contentColor = Color.Black
//                ),
//                elevation = ButtonDefaults.buttonElevation(4.dp)
//            ) {
//                Icon(
//                    imageVector = Icons.Default.Close,
//                    contentDescription = "Отмена",
//                    modifier = Modifier.padding(end = 8.dp)
//                )
//                Text("Отмена")
//            }
//
//            // Кнопка сохранения
//            Button(
//                onClick = onSaveClick,
//                modifier = Modifier.weight(1f),
//                shape = RoundedCornerShape(12.dp),
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = Color(0xFFBAD4FF),
//                    contentColor = Color.Black
//                ),
//                elevation = ButtonDefaults.buttonElevation(4.dp)
//            ) {
//                Icon(
//                    imageVector = Icons.Default.Check,
//                    contentDescription = "Сохранить",
//                    modifier = Modifier.padding(end = 8.dp)
//                )
//                Text("Сохранить")
//            }
//        }
//    }
//}
