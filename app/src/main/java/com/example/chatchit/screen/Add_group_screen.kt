package com.example.chatchit.screen

import Avatar
import android.content.Context
import android.util.Log
import android.widget.Toast
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
import com.example.chatchit.models.User
import com.example.chatchit.services.APIService
import com.example.chatchit.services.api.RoomAPI
import com.example.chatchit.services.api.await
import com.example.chatchit.services.api.form.UserIdField
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


@Composable
fun AddGroupScreen(
    navHostController: NavHostController,
    context: Context
){
    val roomID = navHostController.previousBackStackEntry?.savedStateHandle?.get<Int>("roomID") ?: -1
    val person = navHostController.previousBackStackEntry?.savedStateHandle?.get<User>("addgroup") ?: User()
    val check1 = true
    val check = CheckViewModel()
    check.setCheck(check1)
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
            groupAdd(roomID, context, check, person = person, modifier = Modifier.padding(top = 6.dp, start = 10.dp, end = 20.dp))
        }
    }
}

@Composable
fun groupAdd(
    roomID: Int,
    context: Context,
    check:CheckViewModel,
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
//            IconComponentDrawable(
//                icon = R.drawable.person_2,
//                size = 145.dp
//            )
            Avatar(
                b64Image = person.avatar,
                size = 145.dp
            )
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
        if(check.getCheck()){
            Button(enabled = true, onClick = {
                MainScope().launch {
                    try {
                        val addService: RoomAPI =
                            APIService.getApiClient(context).create(RoomAPI::class.java)
                        val addAPIResponse = addService.addMember(UserIdField(person.id?:-1), roomID).await()
                        check.setCheck(false)
                    } catch (e: Exception) {
                        Log.e("Add to Group", e.toString())
                        Toast.makeText(context, "This member exist in group", Toast.LENGTH_SHORT).show()
                    }
                }
            }) {
                Text(text = "Add to Group")
            }
        }
        else{
            Button(enabled = false, onClick = {

            }) {
                Text(text = "Add to Group")
            }
        }
    }
}