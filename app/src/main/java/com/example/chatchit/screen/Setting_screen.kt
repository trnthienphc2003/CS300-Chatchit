package com.example.chatchit.screen

import Avatar
import android.content.Context
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
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.chatchit.R
import com.example.chatchit.component.IconComponentDrawable
import com.example.chatchit.component.SpacerHeight
import com.example.chatchit.models.Language
import com.example.chatchit.models.User
import com.example.chatchit.services.APIService
import com.example.chatchit.services.api.LanguageAPI
import com.example.chatchit.services.api.await
import com.example.chatchit.services.api.form.LanguageIdField
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    navHostController: NavHostController,
    context: Context
) {
    var openLanguageLog by rememberSaveable { mutableStateOf(false) }
    val user = navHostController.previousBackStackEntry?.savedStateHandle?.get<User>("user") ?: User()
//    Row (
//        modifier = Modifier.fillMaxWidth()
//            .padding(vertical = 12.dp, horizontal = 8.dp),
//        horizontalArrangement = Arrangement.Absolute.SpaceBetween
//    ){
//        IconButtonBack(modifier = Modifier.align(Alignment.CenterVertically), navHostController)
//
//    }
    val listOfLanguages = listOf<Language>(
        Language(
            24,
            "Vietnamese",
            "vi"
        ),
        Language(
            25,
            "English",
            "en"
        ),
        Language(
            26,
            "Chinese (simplified)",
            "zh-CN"
        ),
        Language(
            27,
            "Spanish",
            "es"
        ),
        Language(
            28,
            "French",
            "fr"
        ),
        Language(
            29,
            "German",
            "de"
        ),
        Language(
            30,
            "Arabic",
            "ar"
        ),
        Language(
            31,
            "Russian",
            "ru"
        ),
        Language(
            32,
            "Japanese",
            "ja"
        ),
        Language(
            33,
            "Korean",
            "ko"
        ),
        Language(
            34,
            "Hindi",
            "hi"
        ),
        Language(
            35,
            "Esperanto",
            "eo"
        ),
    )
    val decodeLanguage = emptyMap<String, Language>().toMutableMap()
    for (language in listOfLanguages) {
        decodeLanguage[language.name?: String()] = language
    }

//    println(decodeLanguage[""])

    val languageNames = listOfLanguages.map { it.name?: String() }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp, horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
    ) {
        Text (
            text = "Settings",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
//                modifier = modifier.align(Alignment.CenterHorizontally),

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
                    fontSize = 20.sp,
                    maxLines = 2,
                    modifier = Modifier
//                        .align(Alignment.CenterHorizontally)
                        .padding(top = 20.dp),
                )
            }

            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.qr_code),
                    contentDescription = "",
                    modifier = Modifier
                        .size(size = 36.dp)
                        .padding(top = 10.dp),
                    tint = Color.Unspecified
                )
            }
        }

        Spacer(modifier = Modifier.padding(vertical = 24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Absolute.Left
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.group_503),
                    contentDescription = "",
                    modifier = Modifier
                        .size(48.dp)
                        .padding(top = 10.dp, start = 16.dp),
                    tint = Color.Unspecified
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = "Account",
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    modifier = Modifier
//                        .align(Alignment.CenterVertically)
                        .padding(top = 10.dp),
                )
                Text(
                    text = "Security, change password",
//                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 10.sp,
                    modifier = Modifier
//                        .align(Alignment.CenterVertically)
//                        .padding(top = 5.dp),
                )
            }


        }

        Spacer(modifier = Modifier.padding(vertical = 10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Absolute.Left
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.group_504),
                    contentDescription = "",
                    modifier = Modifier
                        .size(48.dp)
                        .padding(top = 10.dp, start = 16.dp),
                    tint = Color.Unspecified
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = "Notifications",
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    modifier = Modifier
//                        .align(Alignment.CenterVertically)
                        .padding(top = 10.dp),
                )
                Text(
                    text = "Messages, group and others",
//                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 10.sp,
                    modifier = Modifier
//                        .align(Alignment.CenterVertically)
//                        .padding(top = 5.dp),
                )
            }

        }

        Spacer(modifier = Modifier.padding(vertical = 10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Absolute.Left
        ) {
            IconButton(onClick = { openLanguageLog = !openLanguageLog }) {
                Icon(
                    painter = painterResource(id = R.drawable.group_505),
                    contentDescription = "",
                    modifier = Modifier
                        .size(48.dp)
                        .padding(top = 10.dp, start = 16.dp),
                    tint = Color.Unspecified
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = "Primary language",
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    modifier = Modifier
//                        .align(Alignment.CenterVertically)
                        .padding(top = 10.dp),
                )
                Text(
                    text = "Translated message language",
//                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 10.sp,
                    modifier = Modifier
//                        .align(Alignment.CenterVertically)
//                        .padding(top = 5.dp),
                )
            }

        }

        Spacer(modifier = Modifier.padding(vertical = 10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Absolute.Left
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.group_506),
                    contentDescription = "",
                    modifier = Modifier
                        .size(48.dp)
                        .padding(top = 10.dp, start = 16.dp),
                    tint = Color.Unspecified
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = "Help",
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    modifier = Modifier
//                        .align(Alignment.CenterVertically)
                        .padding(top = 10.dp),
                )
                Text(
                    text = "Help center, contact us, privacy policy",
//                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 10.sp,
                    modifier = Modifier
//                        .align(Alignment.CenterVertically)
//                        .padding(top = 5.dp),
                )
            }

        }

        Spacer(modifier = Modifier.padding(vertical = 10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Absolute.Left
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.group_507),
                    contentDescription = "",
                    modifier = Modifier
                        .size(48.dp)
                        .padding(top = 10.dp, start = 16.dp),
                    tint = Color.Unspecified
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = "Invite a friend",
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    modifier = Modifier
//                        .align(Alignment.CenterVertically)
                        .padding(top = 15.dp),
                )
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