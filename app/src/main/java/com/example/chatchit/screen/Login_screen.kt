package com.example.chatchit.screen

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.chatchit.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserInput(
    @StringRes label: Int,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    visualTransformation: PasswordVisualTransformation? = null
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(stringResource(label)) },
        modifier = modifier
    )
}

@Composable
@Preview(showBackground = true)
fun LoginScreen(){
    var usernameInput by remember { mutableStateOf("") }
    var passwordInput by remember { mutableStateOf("") }
    val username = usernameInput.trim()
    val password = passwordInput.trim()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 30.dp)
    ) {
        Text(
            text = "Login to Chatchit",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Welcome back! Sign in using your social account or email to continue us",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 32.dp),
            fontStyle = FontStyle.Italic
        )

        Spacer(modifier = Modifier.height(30.dp))

        UserInput(
            label = R.string.username,
            value = usernameInput,
            onValueChange = { usernameInput = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        )

        Spacer(modifier = Modifier.height(15.dp))

        UserInput(
            value = passwordInput,
            onValueChange = { passwordInput = it },
            label = R.string.password,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(Color.Cyan)
        ) {
            Text("Login")
        }
    }
}

