package com.example.homework2.ui.screen

import androidx.compose.foundation.BorderStroke
import com.example.homework2.viewModel.*
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.homework2.models.ProfileAction
import com.example.homework2.models.ProfileState
import com.example.homework2.models.UserProfile

@Composable
@Preview
fun PreviewScreen(){
    ProfileScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController? = null,
    viewModel: ProfileViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(

    ) { paddingValues ->
        ProfileContent(
            state = state,
            onAction = { action ->
                when (action) {
                    is ProfileAction.OpenMyCar -> {
                        navController?.navigate("my_cars")
                    }
                    ProfileAction.ReportBug -> {
                        // Обработка сообщения о баге
                    }
                    ProfileAction.Logout -> {
                        // Обработка выхода
                    }

                    else -> {}
                }
            },
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
private fun ProfileContent(
    state: ProfileState,
    onAction: (ProfileAction) -> Unit,
    modifier: Modifier = Modifier
) {
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }

    // Лончер для выбора изображения из галереи
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { profileImageUri = it }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.padding(32.dp)
            )
        } else if (state.errorMessage != null) {
            Text(
                text = state.errorMessage,
                color = MaterialTheme.colorScheme.error
            )
        } else {
            // Круглое изображение профиля
            ProfileImage(
                imageUri = profileImageUri,
                onClick = {
                    // Открываем галерею для выбора фото
                    imagePickerLauncher.launch("image/*")
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Основной контейнер с закругленными краями и цветом #BAD4FF
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFBAD4FF), // #BAD4FF
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Приветствие
                    Text(
                        text = "Добро пожаловать,",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    Text(
                        text = "${state.userProfile?.name}!",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    // Информация о пользователе в отдельном карточке
                    ProfileInfoCard(state.userProfile)

                    Spacer(modifier = Modifier.height(32.dp))

                    // Действия профиля
                    ProfileActions(onAction)
                }
            }
        }
    }
}

@Composable
private fun ProfileImage(
    imageUri: Uri?,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(120.dp)
            .clip(CircleShape)
            .border(
                width = 3.dp,
                color = Color(0xFFBAD4FF),
                shape = CircleShape
            )
            .background(Color.White, CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (imageUri != null) {
            // Показываем выбранное изображение
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(imageUri)
                        .build()
                ),
                contentDescription = "Фото профиля",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        } else {
            // Иконка профиля по умолчанию
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Добавить фото",
                modifier = Modifier.size(60.dp),
                tint = Color(0xFFBAD4FF)
            )
        }
    }
}

@Composable
private fun ProfileInfoCard(userProfile: UserProfile?) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 0.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            InfoRow(
                title = "Разряд:",
                value = userProfile?.rank ?: ""
            )

            Spacer(modifier = Modifier.height(12.dp))

            InfoRow(
                title = "Изучено:",
                value = "${userProfile?.studiedTopics ?: 0} тем"
            )
        }
    }
}

@Composable
private fun InfoRow(
    title: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black,
            fontWeight = FontWeight.Normal
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}

@Composable
private fun ProfileActions(
    onAction: (ProfileAction) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Моё авто
        ProfileActionButton(
            text = "Моё авто",
            icon = Icons.Default.DirectionsCar,
            onClick = { onAction(ProfileAction.OpenMyCar) }
        )

        // Сообщить о баге
        ProfileActionButton(
            text = "Сообщить о баге",
            icon = Icons.Default.BugReport,
            onClick = { onAction(ProfileAction.ReportBug) }
        )

        // Выйти из аккаунта
        ProfileActionButton(
            text = "Выйти из аккаунта",
            icon = Icons.Default.ExitToApp,
            onClick = { onAction(ProfileAction.Logout) }
        )
    }
}

@Composable
private fun ProfileActionButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 2.dp,
            pressedElevation = 4.dp
        )
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.padding(end = 12.dp)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}