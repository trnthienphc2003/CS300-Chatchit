package com.example.chatchit.screen

import Avatar
import android.content.Context
import androidx.compose.foundation.background
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.chatchit.R
import com.example.chatchit.component.IconComponentDrawable
import com.example.chatchit.models.User

@Composable
fun ProfileScreen(
    navHostController: NavHostController,
    context: Context,
) {
    val user = navHostController.previousBackStackEntry?.savedStateHandle?.get<User>("view_person") ?: User()

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween
        ){
            IconButtonBack(modifier = Modifier.align(Alignment.CenterVertically), navHostController)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.LightGray, shape = MaterialTheme.shapes.medium)
        ) {
            Avatar(
                b64Image = user.avatar,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 8.dp)
                    .clip(RoundedCornerShape(50.dp)),
                size = 100.dp
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text (
                text = user.name?: String(),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontSize = 32.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.LightGray, shape = MaterialTheme.shapes.medium),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(id = R.drawable.group_424),
                        contentDescription = "",
                        modifier = Modifier.size(size = 25.dp),
                        tint = Color.Unspecified
                    )
                }

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.group_423),
                        contentDescription = "",
                        modifier = Modifier.size(size = 25.dp),
                        tint = Color.Unspecified
                    )
                }

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.group_422),
                        contentDescription = "",
                        modifier = Modifier.size(size = 25.dp),
                        tint = Color.Unspecified
                    )
                }

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.group_425),
                        contentDescription = "",
                        modifier = Modifier.size(size = 25.dp),
                        tint = Color.Unspecified
                    )
                }
            }
        }

        Column (
            modifier = Modifier
                .fillMaxWidth()
//                .background(color = Color.LightGray, shape = MaterialTheme.shapes.medium)
        ){
            userDisplayName(name = user.name?: String())
            Spacer(modifier = Modifier.height(10.dp))
            userEmail(email = "a@a.com")
            Spacer(modifier = Modifier.height(10.dp))
            userAddress(address = "33 street west subidbazar,sylhet")
            Spacer(modifier = Modifier.height(10.dp))
            userPhone(phone = "(320) 555-0104")
        }

//        Spacer(modifier = Modifier.height(10.dp))
//        Text(
//            text = "Phone: ${user.phone}",
//            style = MaterialTheme.typography.body1,
//            textAlign = TextAlign.Center
//        )
    }
}

@Composable
fun userPhone(phone: String) {
    Text(
        text = "Phone Number",
        style = MaterialTheme.typography.bodySmall,
        fontStyle = FontStyle.Italic,
        fontSize = 15.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp)
    )

    Text (
        text = phone,
        style = MaterialTheme.typography.bodySmall,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp)
    )
}

@Composable
fun userAddress(address: String) {
    Text(
        text = "Address",
        style = MaterialTheme.typography.bodySmall,
        fontStyle = FontStyle.Italic,
        fontSize = 15.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp)
    )

    Text (
        text = address,
        style = MaterialTheme.typography.bodySmall,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp)
    )
}

@Composable
fun userEmail(
    email: String,
) {
    Text(
        text = "Email Address",
        style = MaterialTheme.typography.bodySmall,
        fontStyle = FontStyle.Italic,
        fontSize = 15.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp)
    )

    Text (
        text = email,
        style = MaterialTheme.typography.bodySmall,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp)
    )
}

@Composable
fun userDisplayName(
    name: String,
) {
    Text(
        text = "Display Name",
        style = MaterialTheme.typography.bodySmall,
        fontStyle = FontStyle.Italic,
        fontSize = 15.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp)
    )

    Text (
        text = name,
        style = MaterialTheme.typography.bodySmall,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp)
    )
}



@Composable
@Preview(showBackground = true)
fun previewProfile() {
    ProfileScreen(
        navHostController = NavHostController(androidx.compose.ui.platform.LocalContext.current),
        context = androidx.compose.ui.platform.LocalContext.current,
    )
}