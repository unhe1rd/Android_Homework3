package com.example.homework3.app

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.homework3.screens.tabbarScreen.AppTabBar
import com.example.homework3.screens.tabbarScreen.NavHostContainer
import com.example.homework3.screens.tabbarScreen.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val navController = rememberNavController()

    MaterialTheme {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
                        val currentTitle = Screen.values().find { it.route == currentRoute }?.title ?: "Главная"
                        Text(currentTitle)
                    }
                )
            },
            bottomBar = {
                AppTabBar(navController)
            }
        ) { paddingValues ->
            NavHostContainer(
                navController = navController,
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}