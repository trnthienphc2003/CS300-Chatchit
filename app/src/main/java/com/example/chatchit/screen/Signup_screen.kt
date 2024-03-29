package com.example.chatchit.screen

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.chatchit.navigation.Login
import com.example.chatchit.services.APIService
import com.example.chatchit.services.api.AuthAPI
import com.example.chatchit.services.api.await
import com.example.chatchit.services.api.form.SignupForm
import com.example.chatchit.ui.theme.Gray
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.net.ConnectException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    navHostController: NavHostController,
    context: Context
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var isEmailValid by remember { mutableStateOf(false) }
    var isPasswordMatching by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier.fillMaxSize().background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Sign up with Email",
            style = androidx.compose.ui.text.TextStyle(
                color = Color.Black,
                fontSize = 24.sp
            ),
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Get chatting with friends and family today\n by signing up for our chat app!",
            style = androidx.compose.ui.text.TextStyle(
                color = Color(0xFF797C7B)
            ),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 32.dp, start = 30.dp, end = 30.dp),
            fontStyle = FontStyle.Italic,
            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(
                "Name",
                color = Color(0xFF24786D),
            ) },
            textStyle = androidx.compose.ui.text.TextStyle(
                color = Color.Black,
                fontWeight = FontWeight.Bold
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF24786D),
                unfocusedBorderColor = Gray,
                errorBorderColor = Color.Red,
                errorLabelColor = Color.Red,
                errorLeadingIconColor = Color.Red,
                cursorColor = Color.Black,
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                isEmailValid = it.isValidEmail()  // Validate email
            },
            label = { Text(
                "Email",
                color = Color(0xFF24786D),
            ) },
            textStyle = androidx.compose.ui.text.TextStyle(
                color = Color.Black,
                fontWeight = FontWeight.Bold
            ),
            singleLine = true,
            isError = !isEmailValid,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            // Highlight invalid email field with red color
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF24786D),
                unfocusedBorderColor = Gray,
                errorBorderColor = Color.Red,
                errorLabelColor = Color.Red,
                errorLeadingIconColor = Color.Red,
                cursorColor = Color.Black,
            ),
            modifier = Modifier.fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(
                "Password",
                color = Color(0xFF24786D),
                ) },
            textStyle = androidx.compose.ui.text.TextStyle(
                color = Color.Black,
                fontWeight = FontWeight.Bold
            ),
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF24786D),
                unfocusedBorderColor = Gray,
                errorBorderColor = Color.Red,
                errorLabelColor = Color.Red,
                errorLeadingIconColor = Color.Red,
                cursorColor = Color.Black,
            ),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
                isPasswordMatching = (it == password)  // Check password match
            },
            label = { Text(
                "Confirm Password",
                color = Color(0xFF24786D),
            ) },
            textStyle = androidx.compose.ui.text.TextStyle(
                color = Color.Black,
                fontWeight = FontWeight.Bold
            ),
            singleLine = true,
            isError = !isPasswordMatching,
            // Highlight non-matching password field with red color
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF24786D),
                unfocusedBorderColor = Gray,
                errorBorderColor = Color.Red,
                errorLabelColor = Color.Red,
                errorLeadingIconColor = Color.Red,
                cursorColor = Color.Black,
            ),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
        )
        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
//                // Handle sign up logic here, checking for valid email and password match
//                if (isEmailValid && isPasswordMatching) {
//                    // Proceed with sign up
//                } else {
//                    // Display error message or visual cue for invalid input
//                }
                val authService: AuthAPI = APIService.getApiClient(context).create(AuthAPI::class.java)
                MainScope().launch {
                    try {
                        val signupAPIResponse = authService.signup(SignupForm(name, email, password)).await()
//                        val roomService: RoomAPI = APIService.getApiClient(context).create(RoomAPI::class.java)
//                        val roomAPIResponse = roomService.listRoom().await()
//                        val json = Gson().toJson(roomAPIResponse.data)
//                        val itemType = object : TypeToken<List<Room>>() {}.type
//                        val listRoom = Gson().fromJson<List<Room>>(json, itemType)
//
//                        val userResponse = authService.getUser().await()
//                        val jsonUser = Gson().toJson(userResponse.data)
//                        val itemUserType = object : TypeToken<User>() {}.type
//                        val user = Gson().fromJson<User>(jsonUser, itemUserType)
//
//                        WebSocketService.getInstance().setup(context, user.id!!)
//
//                        navHostController.currentBackStackEntry?.savedStateHandle?.set(
//                            "listRoom",
//                            listRoom
//                        )
//                        navHostController.currentBackStackEntry?.savedStateHandle?.set(
//                            "user",
//                            user
//                        )
                        Toast.makeText(context, "Sign Up successfully, please login!", Toast.LENGTH_SHORT).show()

                        navHostController.navigate(Login)
                    } catch (e: Exception) {
                        Log.e("SignupScreen", e.toString())
                        Toast.makeText(context, "Sign Up failed", Toast.LENGTH_SHORT).show()
                    } catch (e: ConnectException) {
                        Toast.makeText(context, "Connection error", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            enabled = isEmailValid && isPasswordMatching,
            modifier = Modifier.fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            shape = RoundedCornerShape(25),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF24786D),
                contentColor = Color.White
            )
        ) {
            Text(
                "Create an account",
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

fun String.isValidEmail(): Boolean {
    // Simplified email validation for demonstration
    val emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()
    return matches(emailRegex)
//    return contains("@") && contains(".") && !isDigitsOnly()
}

@Preview(showBackground = true)
@Composable
fun PreviewSignUpScreen() {
    SignUpScreen(
        navHostController = NavHostController(androidx.compose.ui.platform.LocalContext.current),
        context = androidx.compose.ui.platform.LocalContext.current
    )
}
