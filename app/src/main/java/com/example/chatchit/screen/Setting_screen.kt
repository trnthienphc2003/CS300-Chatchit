package com.example.chatchit.screen

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.provider.FontsContractCompat.Columns
import androidx.navigation.NavHostController
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatchit.R
import com.example.chatchit.component.IconComponentDrawable
import com.example.chatchit.component.data.Person
import com.example.chatchit.models.User

@Composable
fun SettingScreen(
    navHostController: NavHostController,
    context: Context
) {
    val user = navHostController.previousBackStackEntry?.savedStateHandle?.get<User>("user") ?: User()

    Row (
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.Absolute.SpaceBetween
    ){
        IconButtonBack(modifier = Modifier.align(Alignment.CenterVertically), navHostController)

    }
    Column (
        modifier = Modifier.fillMaxSize()
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
                    .fillMaxWidth(0.5f)
//                    .fillMaxSize()
            ) {
                IconComponentDrawable(
                    icon = R.drawable.person_2,
                    modifier = Modifier
//                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                    size = 60.dp
                )

                Text(
                    text = user.name?: String(),
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    modifier = Modifier
//                        .align(Alignment.CenterHorizontally)
                        .padding(top = 20.dp),
                )
            }

            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.qr_code),
                    contentDescription = "",
                    modifier = Modifier.size(size = 36.dp)
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
                    modifier = Modifier.size(48.dp)
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
                    modifier = Modifier.size(48.dp)
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
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Absolute.Left
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.group_505),
                    contentDescription = "",
                    modifier = Modifier.size(48.dp)
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
                    modifier = Modifier.size(48.dp)
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
                    modifier = Modifier.size(48.dp)
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