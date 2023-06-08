package com.example.quizzy.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quizzy.R
import com.example.quizzy.ui.navigation.NavigationDestination

object QuizzyDestination : NavigationDestination {
    override val route = "quizzy"
    override val titleRes = R.string.app_name
}

@Composable
fun QuizzyScreen(navigateBack: () -> Unit) {
    val currentQuestionIndex = remember { mutableStateOf(0) }
    val selectedAnswerIndex = remember { mutableStateOf(-1) }
    val isAnswerCorrect = remember { mutableStateOf(false) }
    val isAnswerSelected = selectedAnswerIndex.value != -1
    val isLastQuestion = currentQuestionIndex.value == questions.size - 1
    val isAllAnswersCorrect = remember { mutableStateOf(false) }
    val correctAnswersCount = remember { mutableStateOf(0) }
    val selectedAnswerOptions = answerOptions[currentQuestionIndex.value]
    val correctAnswer = correctAnswers[currentQuestionIndex.value]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val bluish = Color(4, 53, 85, 255)

        ProgressBar(currentQuestionIndex = currentQuestionIndex.value)

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Correct Answers: ${correctAnswersCount.value}",
            modifier = Modifier.padding(bottom = 16.dp),
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = questions[currentQuestionIndex.value],
            modifier = Modifier.padding(bottom = 16.dp),
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(64.dp))

        selectedAnswerOptions.forEachIndexed { index, answer ->
            Button(
                onClick = {
                    selectedAnswerIndex.value = index
                    isAnswerCorrect.value = index == correctAnswer
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .height(48.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = bluish,
                    contentColor = Color.White
                ),
                contentPadding = PaddingValues(12.dp)
            ) {
                Text(
                    text = answer,
                    modifier = Modifier.fillMaxSize(),
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 14.sp
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(64.dp))

        if (isAnswerSelected) {
            if (isAnswerCorrect.value) {
                if (isLastQuestion) {
                    isAllAnswersCorrect.value = true
                } else {
                    currentQuestionIndex.value++
                    selectedAnswerIndex.value = -1
                    correctAnswersCount.value++
                }
            } else {
                AlertDialog(
                    onDismissRequest = { },
                    title = { Text(text = "Incorrect!") },
                    text = {
                        Column {
                            Text(text = "Your answer is incorrect. Try again!")
                            Text(text = "Correct answers: ${correctAnswersCount.value}.")
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                currentQuestionIndex.value = 0
                                selectedAnswerIndex.value = -1
                                correctAnswersCount.value = 0
                            },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = bluish,
                                contentColor = Color.White
                            )
                        ) {
                            Text(text = "Retry")
                        }
                    }
                )
            }
        }

        if (isAllAnswersCorrect.value) {
            AlertDialog(
                onDismissRequest = { },
                title = { Text(text = "Congratulations!") },
                text = { Text(text = "You have answered all questions correctly!") },
                confirmButton = {
                    Button(
                        onClick = {
                            currentQuestionIndex.value = 0
                            selectedAnswerIndex.value = -1
                            isAllAnswersCorrect.value = false
                        },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = bluish,
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = "Play Again",)
                    }
                },
                dismissButton = {
                    Button(
                        onClick = { navigateBack() },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = bluish,
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = "Go Home")
                    }
                }
            )
        }

        ActionButton(
            navigateBack = { navigateBack() }
        )
    }
}

@Composable
fun ActionButton(
    navigateBack: () -> Unit
) {
    val showDialog = remember { mutableStateOf(false) }
    val bluish = Color(4, 53, 85, 255)

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text("Confirmation") },
            text = { Text("Are you sure you want to give up?") },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog.value = false
                        navigateBack()
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = bluish,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Yes")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDialog.value = false },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = bluish,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "No")
                }
            }
        )
    }

    Row(modifier = Modifier.padding(top = 16.dp)) {

        val darkRed = Color(128, 0, 0)

        Button(
            onClick = { showDialog.value = true },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = darkRed,
                contentColor = Color.White
            ),
            contentPadding = PaddingValues(12.dp)
        ) {
            Text(text = "Give Up",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}


@Composable
fun ProgressBar(
    currentQuestionIndex: Int
) {
    LinearProgressIndicator(
        progress = (currentQuestionIndex + 1).toFloat() / questions.size,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    )
}