package com.example.quizzy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.quizzy.ui.navigation.QuizzyNavHost
import com.example.quizzy.ui.screens.AboutQuizzyDestination
import com.example.quizzy.ui.screens.HomeScreen
import com.example.quizzy.ui.screens.HowToPlayDestination
import com.example.quizzy.ui.screens.QuizzyDestination
import com.example.quizzy.ui.theme.QuizzyTheme

class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuizzyTheme {
                navController = rememberNavController()

                QuizzyNavHost(navController = navController)
            }
        }
    }
}