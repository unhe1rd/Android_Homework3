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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.homework3.R

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
                icon = { Icon(screen.icon, contentDescription = stringResource(screen.title)) },
                label = { Text(stringResource(screen.title)) },
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

sealed class Screen(val route: String, val title: Int, val icon: ImageVector) {
    object Main : Screen("main", R.string.main_screen_title, Icons.Default.Home)
    object Details : Screen("details", R.string.details_screen_title, Icons.Default.Email)
    object Profile : Screen("profile", R.string.profile_screen_title, Icons.Default.Person)

    companion object {
        fun values(): List<Screen> = listOf(Main, Details, Profile)
    }
}