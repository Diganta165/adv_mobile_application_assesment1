package com.example.booktracker.ui.screens.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.booktracker.R
import com.example.booktracker.data.model.User
import com.example.booktracker.viewmodel.AuthViewModel

@Composable
fun AuthScreen(
    viewModel: AuthViewModel,
    onLoginSuccess: (User) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val scrollState = rememberScrollState()
    val keyboardOpened by remember { derivedStateOf { scrollState.value > 0 } }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isSignUp by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    val currentUser by viewModel.currentUser.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(currentUser, error) {
        if (currentUser != null) {
            isLoading = false
            onLoginSuccess(currentUser!!)
        } else if (error != null) {
            isLoading = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .imePadding() // Adds padding when keyboard appears
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(150.dp))

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(150.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .padding(bottom = 32.dp),
                contentScale = ContentScale.Fit
            )

            Text(
                text = if (isSignUp) "Sign Up" else "Login",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    if (email.isNotBlank() && password.isNotBlank()) {
                        keyboardController?.hide()
                        isLoading = true
                        errorMessage = null
                        if (isSignUp) {
                            viewModel.signUp(User(email = email, password = password))
                        } else {
                            viewModel.login(email, password)
                        }
                    } else {
                        errorMessage = "Please fill in both fields"
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(20.dp)
                            .padding(2.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(text = if (isSignUp) "Sign Up" else "Login")
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(onClick = {
                isSignUp = !isSignUp
                errorMessage = null
            }) {
                Text(
                    text = if (isSignUp)
                        "Already have an account? Login"
                    else
                        "Don't have an account? Sign Up"
                )
            }

            (errorMessage ?: error)?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = it, color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(40.dp)) // Bottom spacer for better scrolling
        }

        // Auto-scroll when keyboard appears
        LaunchedEffect(keyboardOpened) {
            if (keyboardOpened) {
                scrollState.animateScrollTo(scrollState.maxValue)
            }
        }
    }
}