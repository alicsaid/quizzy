package com.example.quizzy.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.quizzy.ui.screens.AboutQuizzyDestination
import com.example.quizzy.ui.screens.AboutQuizzyScreen
import com.example.quizzy.ui.screens.HomeDestination
import com.example.quizzy.ui.screens.HomeScreen
import com.example.quizzy.ui.screens.HowToPlayDestination
import com.example.quizzy.ui.screens.HowToPlayScreen
import com.example.quizzy.ui.screens.QuizzyDestination
import com.example.quizzy.ui.screens.QuizzyScreen

@Composable
fun QuizzyNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToQuizzy = { navController.navigate(QuizzyDestination.route) },
                navigateToHowToPlay = { navController.navigate(HowToPlayDestination.route) },
                navigateToAboutQuizzy = { navController.navigate(AboutQuizzyDestination.route) }
            )
        }
        composable(route = QuizzyDestination.route) {
            QuizzyScreen(
                navigateBack = { navController.popBackStack() }
            )
        }
        composable(route = HowToPlayDestination.route) {
            HowToPlayScreen(
                navigateBack = { navController.popBackStack() },
            )
        }
        composable(route = AboutQuizzyDestination.route) {
            AboutQuizzyScreen(
                navigateBack = { navController.popBackStack() },
            )
        }
    }
}