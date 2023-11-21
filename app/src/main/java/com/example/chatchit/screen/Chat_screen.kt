package com.example.chatchit.screen


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.Absolute.SpaceBetween
import androidx.compose.foundation.layout.Box


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.chatchit.R
import com.example.chatchit.component.IconComponentDrawable
import com.example.chatchit.component.IconComponentImageVector
import com.example.chatchit.component.SpacerWidth
import com.example.chatchit.component.data.Person

@Composable
fun ChatScreen(
    navHostController: NavHostController
){
    val person = navHostController.previousBackStackEntry?.savedStateHandle?.get<Person>("data") ?: Person()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ){
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            inforBar (person, modifier = Modifier.padding(top = 30.dp, start = 10.dp, end = 20.dp), navHostController)
        }
    }

}

@Composable
fun IconButtonBack(modifier: Modifier = Modifier, navHostController: NavHostController) {
    IconButton(onClick = {navHostController.popBackStack()}) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_arrow_back_24),
            contentDescription = "",
            modifier = modifier.size(size = 25.dp),
            tint = Color.Unspecified
        )
    }
}

@Composable
fun IconButtonCall(modifier: Modifier = Modifier, navHostController: NavHostController) {
    IconButton(onClick = { /* do something */ }) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_call_24),
            contentDescription = "",
            modifier = modifier.size(size = 25.dp),
            tint = Color.Unspecified
        )
    }
}

@Composable
fun IconButtonVideoCall(modifier: Modifier = Modifier, navHostController: NavHostController) {
    IconButton(onClick = { /* do something */ }) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_videocam_24),
            contentDescription = "",
            modifier = modifier.size(size = 25.dp),
            tint = Color.Unspecified
        )
    }
}

@Composable
fun inforBar(
    person: Person,
    modifier: Modifier = Modifier,
    navHostController: NavHostController
){
    Row (
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = SpaceBetween
    ){
        Row {
//            IconComponentDrawable(icon = R.drawable.baseline_arrow_back_24, modifier = Modifier.align(CenterVertically), size = 25.dp)
            IconButtonBack(modifier = Modifier.align(CenterVertically), navHostController)
            SpacerWidth()
            IconComponentDrawable(icon = person.icon, size = 42.dp)
            SpacerWidth()
            Column {
                Text(
                    text = person.name,
                    style = TextStyle(
                        color = Color.Black,
                        fontWeight = Bold,
                        fontSize = 20.sp
                    )
                )
                Text(
                    text = "Active now",
                    style = TextStyle(
                        color = Color.Gray,
                        fontSize = 15.sp
                    )
                )
            }
        }

        Row {
            IconButtonCall(modifier = Modifier.align(CenterVertically), navHostController)
            SpacerWidth()
            IconButtonVideoCall(modifier = Modifier.align(CenterVertically), navHostController)
        }
    }
}