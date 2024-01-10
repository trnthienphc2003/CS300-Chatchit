package com.example.chatchit.screen

import Avatar
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.chatchit.R
import com.example.chatchit.component.IconComponentDrawable
import com.example.chatchit.component.SpacerHeight
import com.example.chatchit.component.SpacerWidth
import com.example.chatchit.models.Room
import com.example.chatchit.navigation.Group
import com.example.chatchit.navigation.ManageMember

@Composable
fun ChatSettingScreen(
    navHostController: NavHostController
){
    val person = navHostController.previousBackStackEntry?.savedStateHandle?.get<Room>("data") ?: Room()
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
            info(person = person, modifier = Modifier.padding(top = 6.dp, start = 10.dp, end = 20.dp))
            setting(person,navHostController, modifier = Modifier.padding(top = 6.dp))
        }
    }
}

@Composable
fun Bar(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
){
    Row (
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Absolute.SpaceBetween
    ){
        IconButtonBack(modifier = Modifier.align(Alignment.CenterVertically), navHostController = navHostController)
    }
}

@Composable
fun info(
    person: Room,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier
            .clip(CircleShape)
            .size(150.dp),
            contentAlignment = Alignment.Center
        ){
            Avatar(b64Image = person.avatar, Modifier.clip(RoundedCornerShape(75.dp)), size = 150.dp)
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
    }
}

@Composable
fun setting(
    person: Room,
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
){
    Column (
        modifier = modifier.fillMaxWidth(),
    ){
        if(person.type == "group") {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp, start = 20.dp, end = 20.dp)
                    .clickable {
                        // Your click logic goes here
                        // For example, you can navigate to another screen or perform some action.
                    }
            ) {
                Row {
                    IconComponentDrawable(icon = R.drawable.group_403, size = 40.dp)
                    SpacerWidth()

                    Text(
                        text = "Change group photo", style = TextStyle(
                            color = Color.Black,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.align((Alignment.CenterVertically))

                    )

                }
            }

            SpacerHeight()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp, start = 20.dp, end = 20.dp)
                    .clickable {
                        // Your click logic goes here
                        // For example, you can navigate to another screen or perform some action.
                    }
            ) {
                Row {
                    IconComponentDrawable(icon = R.drawable.group_404, size = 40.dp)
                    SpacerWidth()

                    Text(
                        text = "Change group name", style = TextStyle(
                            color = Color.Black,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.align((Alignment.CenterVertically))

                    )

                }
            }
        }
        else{
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp, start = 20.dp, end = 20.dp)
                    .clickable {
                        // Your click logic goes here
                        // For example, you can navigate to another screen or perform some action.
                    }
            ) {
                Row {
                    IconComponentDrawable(icon = R.drawable.group_403, size = 40.dp)
                    SpacerWidth()

                    Text(
                        text = "Change photo", style = TextStyle(
                            color = Color.Black,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.align((Alignment.CenterVertically))

                    )

                }
            }

            SpacerHeight()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp, start = 20.dp, end = 20.dp)
                    .clickable {
                        // Your click logic goes here
                        // For example, you can navigate to another screen or perform some action.
                    }
            ) {
                Row {
                    IconComponentDrawable(icon = R.drawable.group_404, size = 40.dp)
                    SpacerWidth()

                    Text(
                        text = "Change name", style = TextStyle(
                            color = Color.Black,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.align((Alignment.CenterVertically))

                    )

                }
            }
        }
        if(person.type == "group") {
            SpacerHeight()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp, start = 20.dp, end = 20.dp)
                    .clickable {
                        navHostController.currentBackStackEntry?.savedStateHandle?.set(
                            "roomID",
                            person.id
                        )
                        navHostController.navigate(Group)
                        // For example, you can navigate to another screen or perform some action.
                    }
            ) {
                Row {
                    IconComponentDrawable(icon = R.drawable.group_407, size = 40.dp)
                    SpacerWidth()

                    Text(
                        text = "Invite a friend", style = TextStyle(
                            color = Color.Black,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.align((Alignment.CenterVertically))

                    )

                }
            }
            SpacerHeight()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp, start = 20.dp, end = 20.dp)
                    .clickable {
                        navHostController.currentBackStackEntry?.savedStateHandle?.set(
                            "roomID",
                            person.id
                        )
                        navHostController.navigate(ManageMember)
                    }
            ) {
                Row {
                    IconComponentDrawable(icon = R.drawable.group_408, size = 40.dp)
                    SpacerWidth()

                    Text(
                        text = "Manage members", style = TextStyle(
                            color = Color.Black,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.align((Alignment.CenterVertically))

                    )

                }
            }
        }
        SpacerHeight()
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp, start = 20.dp, end = 20.dp)
                .clickable {
                    // Your click logic goes here
                    // For example, you can navigate to another screen or perform some action.
                }
        ) {
            Row {
                IconComponentDrawable(icon = R.drawable.group_409, size = 40.dp)
                SpacerWidth()

                Text(
                    text = "Notification", style = TextStyle(
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.align((Alignment.CenterVertically))

                )

            }
        }
        SpacerHeight()
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp, start = 20.dp, end = 20.dp)
                .clickable {
                    // Your click logic goes here
                    // For example, you can navigate to another screen or perform some action.
                }
        ) {
            Row {
                IconComponentDrawable(icon = R.drawable.group_410, size = 40.dp)
                SpacerWidth()

                Text(
                    text = "Chat summary", style = TextStyle(
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.align((Alignment.CenterVertically))

                )
            }
        }
        if (person.type == "group") {
            SpacerHeight()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp, start = 20.dp, end = 20.dp)
                    .clickable {
                        // Your click logic goes here
                        // For example, you can navigate to another screen or perform some action.
                    }
            ) {
                Row {
                    IconComponentDrawable(icon = R.drawable.group_411, size = 40.dp)
                    SpacerWidth()

                    Text(
                        text = "Leave group", style = TextStyle(
                            color = Color.Black,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.align((Alignment.CenterVertically))

                    )

                }
            }
        }
        SpacerHeight()
    }

}

@Composable
fun IconGeneral(modifier: Modifier = Modifier, navHostController: NavHostController) {
    IconButton(onClick = { /* do something */ }) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_call_24),
            contentDescription = "",
            modifier = modifier.size(size = 25.dp),
            tint = Color.Unspecified
        )
    }
}