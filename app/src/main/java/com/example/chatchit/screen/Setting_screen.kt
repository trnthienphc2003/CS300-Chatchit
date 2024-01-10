package com.example.chatchit.screen

import Avatar
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.chatchit.R
import com.example.chatchit.component.SpacerHeight
import com.example.chatchit.component.SpacerWidth
import com.example.chatchit.models.Language
import com.example.chatchit.models.Room
import com.example.chatchit.models.User
import com.example.chatchit.services.APIService
import com.example.chatchit.services.api.AuthAPI
import com.example.chatchit.services.api.LanguageAPI
import com.example.chatchit.services.api.await
import com.example.chatchit.services.api.form.LanguageIdField
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    navHostController: NavHostController,
    context: Context
) {
    var openLanguageLog by rememberSaveable { mutableStateOf(false) }
    var openSignoutLog by rememberSaveable { mutableStateOf(false) }
    val user = navHostController.previousBackStackEntry?.savedStateHandle?.get<User>("user") ?: User()
    val listOfLanguages = navHostController.previousBackStackEntry?.savedStateHandle?.get<List<Language>>("listOfLanguages") ?: List<Language>(0) { Language() }

    val decodeLanguage = emptyMap<String, Language>().toMutableMap()
    for (language in listOfLanguages) {
        decodeLanguage[language.name?: String()] = language
    }

//    println(decodeLanguage[""])

    val languageNames = listOfLanguages.map { it.name?: String() }

    Column (
        modifier = Modifier
            .fillMaxSize()
//            .padding(vertical = 16.dp, horizontal = 8.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
    ) {
        Text (
            text = "Settings",
            style = TextStyle(
                fontSize = 20.sp,
                color = Color.Black
            ),
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,

            modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 60.dp),

            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.padding(vertical = 16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
//                    .fillMaxSize()
            ) {
                Avatar(
                    b64Image = user.avatar,
                    modifier = Modifier
//                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                        .clip(RoundedCornerShape(32.dp)),
                    size = 64.dp
                )

                Text(
                    text = user.name?: String(),
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    textAlign = TextAlign.Left,
                    color = Color.Black,
                    fontSize = 20.sp,
                    maxLines = 2,
                    modifier = Modifier
//                        .align(Alignment.CenterHorizontally)
                        .padding(top = 20.dp),
                )
            }

            IconButton(onClick = { /*TODO*/ },
                modifier = Modifier
                    .size(size = 48.dp)
                    .padding(top = 10.dp, end = 16.dp)) {
                Icon(
                    painter = painterResource(id = R.drawable.qr_code),
                    contentDescription = "",
//                    modifier = Modifier
//                        .size(size = 48.dp)
//                        .padding(top = 10.dp),
                    tint = Color.Unspecified
                )
            }
        }

        Spacer(modifier = Modifier.padding(vertical = 24.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp),
            horizontalArrangement = Arrangement.Absolute.Left
        ) {
            IconButton(onClick = { /*TODO*/ },
                modifier = Modifier.size(44.dp)) {
                Icon(
                    painter = painterResource(id = R.drawable.group_503),
                    contentDescription = "",
                    modifier = Modifier
//                        .size(44.dp)
//                        .padding(top = 10.dp, start = 16.dp)
                        .fillMaxSize(),
                    tint = Color.Unspecified
                )
            }

            Column(
                modifier = Modifier
//                    .fillMaxWidth()
                    .padding(start = 16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Account",
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    modifier = Modifier,
//                        .align(Alignment.CenterVertically)
//                        .padding(top = 10.dp),
                    color = Color.Black
                )
                Text(
                    text = "Security, change password",
//                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 10.sp,
                    color = Color(0xFF797C7B),
                    modifier = Modifier
//                        .align(Alignment.CenterVertically)
//                        .padding(top = 5.dp),
                )
            }


        }

        Spacer(modifier = Modifier.padding(vertical = 20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp),
            horizontalArrangement = Arrangement.Absolute.Left
        ) {
            IconButton(onClick = { /*TODO*/ },
                modifier = Modifier.size(44.dp)) {
                Icon(
                    painter = painterResource(id = R.drawable.group_504),
                    contentDescription = "",
                    modifier = Modifier
//                        .size(48.dp)
                        .fillMaxSize(),
//                        .padding(top = 10.dp, start = 16.dp),
                    tint = Color.Unspecified
                )
            }

            Column(
                modifier = Modifier
//                    .fillMaxWidth()
                    .padding(start = 16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Notifications",
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    modifier = Modifier,
//                        .align(Alignment.CenterVertically)
//                        .padding(top = 10.dp),
                    color = Color.Black
                )
                Text(
                    text = "Messages, group and others",
//                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 10.sp,
                    color = Color(0xFF797C7B),
                    modifier = Modifier
//                        .align(Alignment.CenterVertically)
//                        .padding(top = 5.dp),
                )
            }

        }

        Spacer(modifier = Modifier.padding(vertical = 20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp),
            horizontalArrangement = Arrangement.Absolute.Left
        ) {
            IconButton(onClick = { /*TODO*/ },
                modifier = Modifier.size(44.dp)) {
                Icon(
                    painter = painterResource(id = R.drawable.group_505),
                    contentDescription = "",
                    modifier = Modifier
//                        .size(48.dp)
                        .fillMaxSize(),
//                        .padding(top = 10.dp, start = 16.dp),
                    tint = Color.Unspecified
                )
            }

            Column(
                modifier = Modifier
//                    .fillMaxWidth()
                    .padding(start = 16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Primary language",
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
//                    color = Color(0xFF797C7B),
                    modifier = Modifier,
//                        .align(Alignment.CenterVertically)
//                        .padding(top = 10.dp),
                    color = Color.Black
                )
                Text(
                    text = "Translated message language",
//                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 10.sp,
                    color = Color(0xFF797C7B),
                    modifier = Modifier
//                        .align(Alignment.CenterVertically)
//                        .padding(top = 5.dp),
                )
            }

        }

        Spacer(modifier = Modifier.padding(vertical = 20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp),
            horizontalArrangement = Arrangement.Absolute.Left
        ) {
            IconButton(onClick = { /*TODO*/ },
                modifier = Modifier.size(44.dp)){
                Icon(
                    painter = painterResource(id = R.drawable.group_506),
                    contentDescription = "",
                    modifier = Modifier
//                        .size(48.dp)
//                        .padding(top = 10.dp, start = 16.dp)
                        .fillMaxSize(),
                    tint = Color.Unspecified
                )
            }

            Column(
                modifier = Modifier
//                    .fillMaxWidth()
                    .padding(start = 16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Help",
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    modifier = Modifier,
//                        .align(Alignment.CenterVertically)
//                        .padding(top = 10.dp),
                    color = Color.Black
                )
                Text(
                    text = "Help center, contact us, privacy policy",
//                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 10.sp,
                    color = Color(0xFF797C7B),
                    modifier = Modifier
//                        .align(Alignment.CenterVertically)
//                        .padding(top = 5.dp),
                )
            }

        }

        Spacer(modifier = Modifier.padding(vertical = 20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp),
            horizontalArrangement = Arrangement.Absolute.Left
        ) {
            IconButton(onClick = { /*TODO*/ },
                modifier = Modifier.size(44.dp)) {
                Icon(
                    painter = painterResource(id = R.drawable.group_507),
                    contentDescription = "",
                    modifier = Modifier
//                        .size(48.dp)
//                        .padding(top = 10.dp, start = 16.dp)
                        .fillMaxSize(),
                    tint = Color.Unspecified
                )
            }

            Column(
                modifier = Modifier
//                    .fillMaxWidth()
                    .padding(start = 16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Invite a friend",
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier
//                        .align(Alignment.CenterVertically)
                        .padding(top = 10.dp),
                )
            }
        }

        Spacer(modifier = Modifier.padding(vertical = 20.dp))
        Row(
            horizontalArrangement = Arrangement.Absolute.Left,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp)
                .clickable {
                    openSignoutLog = true
                }
        ) {
            IconButton(onClick = { /*TODO*/ },
                modifier = Modifier.size(44.dp)) {
                Icon(
                    painter = painterResource(id = R.drawable.group_510),
                    contentDescription = "",
                    modifier = Modifier
//                        .size(48.dp)
//                        .padding(top = 10.dp, start = 16.dp)
                        .fillMaxSize(),
                    tint = Color.Unspecified
                )
            }

            Column(
                modifier = Modifier
//                    .fillMaxWidth()
                    .padding(start = 16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Sign out",
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier
//                        .align(Alignment.CenterVertically)
                        .padding(top = 10.dp),
                )
            }
        }

        if(openSignoutLog) {
            Dialog(onDismissRequest = { openSignoutLog = false }) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    shape = RoundedCornerShape(10.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalArrangement = Arrangement.Center,
//                horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Sign out",
                            style = TextStyle(
                                fontSize = 40.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold
                            ),
                            textAlign = TextAlign.Left,
                        )
                        SpacerHeight(10.dp)
                        Text(
                            text = "Are you sure you want to sign out?",
                            style = TextStyle(
                                fontSize = 15.sp,
                                color = Color.Gray,
                                fontWeight = FontWeight.Bold,
                            ),
                            textAlign = TextAlign.Left,
                        )
//                SpacerHeight(10.dp)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp)
                                .weight(1f),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.Bottom
                        ) {
                            Text(
                                text = "Cancel",
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    color = Color.Gray,
                                    fontWeight = FontWeight.Bold
                                ),
                                textAlign = TextAlign.Left,
                                modifier = Modifier
                                    .clickable {
                                openSignoutLog = false
                                    }
                            )
                            SpacerWidth(10.dp)
                            Text(
                                text = "Sign out",
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    color = Color.Red,
                                    fontWeight = FontWeight.Bold
                                ),
                                textAlign = TextAlign.Left,
                                modifier = Modifier
                                    .clickable {
                                    openSignoutLog = false
                                            val authService: AuthAPI = APIService.getApiClient(context).create(
                                                AuthAPI::class.java)
                                            MainScope().launch {
                                                try {
                                                    val response = authService.logout().await()
                                                    Toast.makeText(context, "Log out successfully!", Toast.LENGTH_SHORT).show()
                                                    val logoutService: AuthAPI = APIService.getApiClient(context, clearCookie = true).create(
                                                        AuthAPI::class.java)
                                                    val cookiePrefs: SharedPreferences = context.getSharedPreferences("Cookies", Context.MODE_PRIVATE)
                                                    val cookies = ""

                                                    val editor = cookiePrefs.edit()
                                                } catch (e: Exception) {
                                                    e.printStackTrace()
                                                }
                                            }
//                                    navHostController.navigate("login")
                                    }
                            )
                        }
                    }
                }
            }
        }

        var isExpanded by remember { mutableStateOf(false) }
        var selectedItem by remember { mutableStateOf("") }
        var textFiledSize by remember { mutableStateOf(Size.Zero) }
        val icon = if (isExpanded) {
            Icons.Filled.KeyboardArrowUp
        } else {
            Icons.Filled.KeyboardArrowDown
        }
        if(openLanguageLog) {
            Dialog(
                onDismissRequest = { openLanguageLog = false }
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    shape = RoundedCornerShape(10.dp),
                ) {
                    Column (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Primary language",
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 10.dp),
                        )
                        SpacerHeight(10.dp)
                        ExposedDropdownMenuBox(
                            expanded = isExpanded,
                            onExpandedChange = {
                                isExpanded = !isExpanded
                            },
                        ) {
                            TextField(
                                value = selectedItem,
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
                                modifier = Modifier.menuAnchor()
                            )

                            ExposedDropdownMenu(
                                expanded = isExpanded,
                                onDismissRequest = { isExpanded = false },) {
                                languageNames.forEach { item ->
                                    DropdownMenuItem(
                                        text = { Text(text = item) },
                                        onClick = {
                                            selectedItem = item
                                            isExpanded = false

                                            MainScope().launch {
                                                try {
                                                    val id = decodeLanguage[item]?.id
                                                    val languageService: LanguageAPI = APIService.getApiClient(context).create(LanguageAPI::class.java)
                                                    val response = languageService.updateUserLanguage(
                                                        LanguageIdField(
                                                            id?: 0
                                                        )
                                                    ).await()
                                                } catch (e: Exception) {
                                                    e.printStackTrace()
                                                }
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewSettingScreen() {
    SettingScreen(
        navHostController = NavHostController(androidx.compose.ui.platform.LocalContext.current),
        context = androidx.compose.ui.platform.LocalContext.current
    )
}