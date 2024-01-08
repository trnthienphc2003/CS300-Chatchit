package com.example.chatchit.screen

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.chatchit.R
import com.example.chatchit.models.Room
import com.example.chatchit.models.User
import com.example.chatchit.navigation.Home
import com.example.chatchit.navigation.SignUp
import com.example.chatchit.services.APIService
import com.example.chatchit.services.WebSocketService
import com.example.chatchit.services.api.APIResponse
import com.example.chatchit.services.api.AuthAPI
import com.example.chatchit.services.api.FriendAPI
import com.example.chatchit.services.api.MessageAPI
import com.example.chatchit.services.api.RoomAPI
import com.example.chatchit.services.api.await
import com.example.chatchit.services.api.form.LoginForm
import com.example.chatchit.ui.theme.Gray
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.util.Date


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserInput(
    @StringRes label: Int,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    visualTransformation: PasswordVisualTransformation? = null
) {
    if (visualTransformation != null) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(
                stringResource(label),
                color = Color(0xFF24786D)
            ) },
            modifier = modifier,
            maxLines = 1,
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = visualTransformation,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF24786D),
                unfocusedBorderColor = Gray,
                errorBorderColor = Color.Red,
                errorLabelColor = Color.Red,
                errorLeadingIconColor = Color.Red
            )
        )
    }
    
    else {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(
                stringResource(label),
                color = Color(0xFF24786D)
            ) },
            maxLines = 1,
            modifier = modifier,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF24786D),
                unfocusedBorderColor = Gray,
                errorBorderColor = Color.Red,
                errorLabelColor = Color.Red,
                errorLeadingIconColor = Color.Red
            )
        )
    }
}

@Composable
//@Preview(showBackground = true)
fun LoginScreen(
    navHostController: NavHostController,
    context: Context
){
    var emailInput by remember { mutableStateOf("") }
    var passwordInput by remember { mutableStateOf("") }
    val email = emailInput.trim()
    val password = passwordInput.trim()

    var emailValid by remember { mutableStateOf(true) }
    var passwordValid by remember { mutableStateOf(true) }
//    var buttonEnable by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Log in to Chatchit",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Welcome back! Sign in using your social account or email to continue with us.",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 32.dp, start = 30.dp, end = 30.dp),
            fontStyle = FontStyle.Italic,
            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Center
        )

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp),
//            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.Center
        ) {
            OutlinedButton(onClick = { /*TODO*/ },
                modifier= Modifier.size(50.dp),  //avoid the oval shape
                shape = CircleShape,
                border= BorderStroke(1.dp, Color.Blue),
                contentPadding = PaddingValues(0.dp),  //avoid the little icon
                colors = ButtonDefaults.outlinedButtonColors(contentColor =  Color.Blue)
            ) {
                Icon (
                    imageVector = Icons.Default.Add,
                    contentDescription = "content description"
                )
            }

            Spacer(modifier = Modifier.width(20.dp))

            OutlinedButton(onClick = { /*TODO*/ },
                modifier= Modifier.size(50.dp),  //avoid the oval shape
                shape = CircleShape,
                border= BorderStroke(1.dp, Color.Blue),
                contentPadding = PaddingValues(0.dp),  //avoid the little icon
                colors = ButtonDefaults.outlinedButtonColors(contentColor =  Color.Blue)
            ) {
                Icon (
                    imageVector = Icons.Default.Add,
                    contentDescription = "content description"
                )
            }

        }

        Spacer(modifier = Modifier.height(30.dp))

        UserInput(
            label = R.string.email,
            value = emailInput,
            onValueChange = {
                emailInput = it
                emailValid = it.isValidEmail()
                            },
            modifier = Modifier.fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp,
                    bottom = 10.dp),
        )

        Spacer(modifier = Modifier.height(15.dp))

        UserInput(
            value = passwordInput,
            onValueChange = {
                passwordInput = it
                passwordValid = !it.isEmpty()
                            },
            label = R.string.password,
            modifier = Modifier.fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp,
                    bottom = 10.dp),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {

                val authService: AuthAPI = APIService.getApiClient(context).create(AuthAPI::class.java)
                MainScope().launch {
                    try {
                        val loginAPIResponse = authService.login(LoginForm(email, password)).await()
                        val roomService: RoomAPI = APIService.getApiClient(context).create(RoomAPI::class.java)
                        val roomAPIResponse = roomService.listRoom().await()
                        val json = Gson().toJson(roomAPIResponse.data)
                        val itemType = object : TypeToken<List<Room>>() {}.type
                        val listRoom = Gson().fromJson<List<Room>>(json, itemType)

                        val userResponse = authService.getUser().await()
                        val jsonUser = Gson().toJson(userResponse.data)
                        val itemUserType = object : TypeToken<User>() {}.type
                        val user = Gson().fromJson<User>(jsonUser, itemUserType)

                        WebSocketService.getInstance().setup(context, user.id!!)

                        navHostController.currentBackStackEntry?.savedStateHandle?.set(
                            "listRoom",
                            listRoom
                        )
                        navHostController.currentBackStackEntry?.savedStateHandle?.set(
                            "user",
                            user
                        )
                        navHostController.navigate(Home)
                    } catch (e: Exception) {
                        Log.e("LoginScreen", e.toString())
                        Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show()
                    } catch (e: ConnectException) {
                        Toast.makeText(context, "Connection error", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            enabled = emailValid && passwordValid,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF24786D),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(25)
        ) {
            Text(
                "Log in",
                fontWeight = FontWeight.Bold,
            )
        }

        Button(
            onClick = {
                navHostController.navigate(SignUp)
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
//                contentColor = Color(0xFF24786D),
            )
        ) {
            Text(
                "Forgot password?",
                fontWeight = FontWeight.Bold,
                color = Color(0xFF24786D)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun previewLogin() {
    LoginScreen(
        navHostController = NavHostController(androidx.compose.ui.platform.LocalContext.current),
        context = androidx.compose.ui.platform.LocalContext.current
    )
}