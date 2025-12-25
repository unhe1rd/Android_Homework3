package com.example.homework3.screens.tabbarScreen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.homework3.screens.detailsScreen.ProfileScreen
import com.example.homework3.screens.detailsScreen.ui.DetailsScreen
import com.example.homework3.screens.mainScreen.ui.MainScreen

@Composable
fun AppTabBar(navController: NavHostController) {
    val items = listOf(
        Screen.Main,
        Screen.Details,
        Screen.Profile
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        items.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = screen.title) },
                label = { Text(screen.title) },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Main : Screen("main", "Главная", Icons.Default.Home)
    object Details : Screen("details", "Мои инструкции", Icons.Default.Email)
    object Profile : Screen("profile", "Профиль", Icons.Default.Person)

    companion object {
        // Используем values() как в enum
        fun values(): List<Screen> = listOf(Main, Details, Profile)
    }
}

@Composable
fun NavHostContainer(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Main.route,
        modifier = modifier
    ) {
        composable(Screen.Main.route) {
            MainScreen()
        }
        composable(Screen.Details.route) {
            DetailsScreen()
        }
        composable(Screen.Profile.route) {
            ProfileScreen()
        }
    }
}