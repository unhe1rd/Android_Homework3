package com.example.homework3.screens.profileScreen.ui

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.homework3.layout.Elevations
import com.example.homework3.layout.Spacers
import com.example.homework3.screens.profileScreen.models.ProfileAction
import com.example.homework3.screens.profileScreen.models.ProfileState
import com.example.homework3.screens.profileScreen.models.UserProfile
import com.example.homework3.screens.profileScreen.viewModel.ProfileViewModel
import com.example.homework3.ui.layout.Paddings
import com.example.homework3.ui.layout.Size

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
                    }
                    ProfileAction.Logout -> {
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

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { profileImageUri = it }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = Paddings.medium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.padding(Paddings.extraLarge)
            )
        } else if (state.errorMessage != null) {
            Text(
                text = state.errorMessage,
                color = MaterialTheme.colorScheme.error
            )
        } else {
            ProfileImage(
                imageUri = profileImageUri,
                onClick = {
                    imagePickerLauncher.launch("image/*")
                }
            )

            Spacer(modifier = Modifier.height(Spacers.large))

            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(Size.medium),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFBAD4FF),
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
                elevation = CardDefaults.cardElevation(Elevations.extraSmall)
            ) {
                Column(
                    modifier = Modifier.padding(Paddings.large),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Добро пожаловать,",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = Paddings.extraSmall)
                    )

                    Text(
                        text = "${state.userProfile?.name}!",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = Paddings.large)
                    )

                    ProfileInfoCard(state.userProfile)

                    Spacer(modifier = Modifier.height(Spacers.extraLarge))

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
            .size(Size.profileImageSize)
            .clip(CircleShape)
            .border(
                width = Size.profileBorderSize,
                color = Color(0xFFBAD4FF),
                shape = CircleShape
            )
            .background(Color.White, CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (imageUri != null) {
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
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Добавить фото",
                modifier = Modifier.size(Size.profileIconSize),
                tint = Color(0xFFBAD4FF)
            )
        }
    }
}

@Composable
private fun ProfileInfoCard(userProfile: UserProfile?) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(Size.base),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        elevation = CardDefaults.cardElevation(Size.extraSmall)
    ) {
        Column(
            modifier = Modifier.padding(Paddings.medium),
            horizontalAlignment = Alignment.Start
        ) {
            InfoRow(
                title = "Разряд:",
                value = userProfile?.rank ?: ""
            )

            Spacer(modifier = Modifier.height(Spacers.medium))

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
        verticalArrangement = Arrangement.spacedBy(Spacers.medium)
    ) {
        ProfileActionButton(
            text = "Моё авто",
            icon = Icons.Default.DirectionsCar,
            onClick = { onAction(ProfileAction.OpenMyCar) }
        )

        ProfileActionButton(
            text = "Сообщить о баге",
            icon = Icons.Default.BugReport,
            onClick = { onAction(ProfileAction.ReportBug) }
        )

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
        shape = RoundedCornerShape(Size.buttonRoundedCornerSize),
        border = BorderStroke(1.dp, Color.LightGray),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 2.dp,
            pressedElevation = 4.dp
        )
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.padding(end = Paddings.small)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}