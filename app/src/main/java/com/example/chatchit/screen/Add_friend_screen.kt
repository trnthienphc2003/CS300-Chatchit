package com.example.chatchit.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.chatchit.R
import com.example.chatchit.component.IconComponentDrawable
import com.example.chatchit.component.SpacerHeight
import com.example.chatchit.models.Room
import com.example.chatchit.models.User

@Composable
fun AddFriendScreen(
    navHostController: NavHostController
){
    val person = navHostController.previousBackStackEntry?.savedStateHandle?.get<User>("addfriend") ?: User()
    val check = navHostController.previousBackStackEntry?.savedStateHandle?.get<Boolean>("checkfriend") ?: true
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Bar(
                modifier = Modifier.padding(top = 30.dp, start = 10.dp, end = 20.dp),
                navHostController
            )
            avatarAdd(check, person = person, modifier = Modifier.padding(top = 6.dp, start = 10.dp, end = 20.dp))
        }
    }
}

@Composable
fun avatarAdd(
    check:Boolean,
    person: User,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier
            .clip(CircleShape)
            .background(Color.Yellow, CircleShape)
            .size(150.dp),
            contentAlignment = Alignment.Center
        ){
            IconComponentDrawable(icon = R.drawable.person_2, size = 145.dp)
        }
        SpacerHeight(8.dp)
        Text(
            text = person.name?: String(),
            style = TextStyle(
                fontSize = 30.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.align((Alignment.CenterHorizontally))
        )
        SpacerHeight(8.dp)
        if(check){
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Remove contact")
            }
        }
        else{
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Add contact")
            }
        }
    }
}