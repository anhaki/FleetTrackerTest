package com.haki.fleettrackertest.feature.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.haki.fleettrackertest.core.navigation.Screen
import com.haki.fleettrackertest.feature.login.components.CustomButton
import com.haki.fleettrackertest.feature.login.components.CustomPasswordField
import com.haki.fleettrackertest.feature.login.components.CustomTextField

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val username by viewModel.username.collectAsState()
    val password by viewModel.password.collectAsState()
    val isError by viewModel.isError.collectAsState()

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
            .fillMaxSize()
            .padding(top = 38.dp)
            .padding(horizontal = 34.dp)
    ) {
        GreetingText(
            stringResource(R.string.greeting),
            stringResource(R.string.greeting_desc),
        )
        Spacer(modifier = Modifier.height(16.dp))
        LoginForm(
            navController = navController,
            value = username,
            onValueChange = { viewModel.updateValue(it) },
            password = password,
            onPasswordChange = { viewModel.updatePassword(it) },
            isError = isError,
            onisErrorChange = { viewModel.updateisError(it) },
            onButtonClicked = {
                viewModel.login(username, password) {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            },
        )
    }
}

@Composable
fun GreetingText(
    greeting: String,
    greetingDesc: String,
) {
    Text(
        text = greeting,
        fontSize = 24.sp,
        fontWeight = FontWeight.Black,
        color = MaterialTheme.colorScheme.onPrimary
    )
    Text(
        text = greetingDesc,
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        color = MaterialTheme.colorScheme.onPrimary
    )
}

@Composable
fun LoginForm(
    navController: NavController,
    value: String,
    onValueChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    isError: Boolean,
    onisErrorChange: (Boolean) -> Unit,
    onButtonClicked: () -> Unit,
) {
    CustomTextField(
        value = value,
        onValueChange = {
            onisErrorChange(false)
            onValueChange(it)
        },
        placeHolder = stringResource(R.string.username))
    Spacer(modifier = Modifier.height(16.dp))
    CustomPasswordField(
        value = password,
        onValueChange = {
            onisErrorChange(false)
            onPasswordChange(it)
        },
        placeHolder = stringResource(R.string.password))
    Spacer(modifier = Modifier.height(16.dp))
    if(isError){
        Text(
            text = "Invalid credentials",
            color = MaterialTheme.colorScheme.error,
            fontSize = 16.sp
        )
    }
    Spacer(modifier = Modifier.height(16.dp))
    CustomButton(
        onClick = {
            onButtonClicked()
        }
    ) {
        Text(modifier = Modifier.padding(vertical = 6.dp), text = stringResource(R.string.login))
    }
}