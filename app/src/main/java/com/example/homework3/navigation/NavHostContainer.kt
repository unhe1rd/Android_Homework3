package com.example.homework3.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.homework3.presentation.viewmodel.MainViewModel
import com.example.homework3.screens.detailsScreen.ui.DetailsScreen
import com.example.homework3.screens.detailsScreen.ui.ToolsRequiredScreen
import com.example.homework3.screens.instructionDetail.ui.InstructionDetailScreen
import com.example.homework3.screens.mainScreen.MainScreen
import com.example.homework3.screens.profileScreen.ui.MyCarsScreen
import com.example.homework3.screens.profileScreen.ui.ProfileScreen
import com.example.homework3.screens.tabbarScreen.Screen

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
            MainScreen(navController = navController, viewModel = MainViewModel())
        }
        composable(Screen.Details.route) {
            DetailsScreen()
        }
        composable(Screen.Profile.route) {
            ProfileScreen(navController = navController)
        }
        composable("my_cars") {
            MyCarsScreen(navController = navController)
        }
        composable(
            route = "toolsRequired/{instructionId}",
            arguments = listOf(
                navArgument("instructionId") {
                    type = NavType.StringType
                    defaultValue = "1"
                }
            )
        ) { backStackEntry ->
            ToolsRequiredScreen(
                navController = navController,
                instructionId = backStackEntry.arguments?.getString("instructionId")
            )
        }
        composable(
            route = "instruction/{instructionId}",
            arguments = listOf(
                navArgument("instructionId") {
                    type = NavType.StringType
                    defaultValue = "1"
                }
            )
        ) { backStackEntry ->
            InstructionDetailScreen(
                navController = navController,
                instructionId = backStackEntry.arguments?.getString("instructionId")
            )
        }
    }
}