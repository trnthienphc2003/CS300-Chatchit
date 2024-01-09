package com.example.chatchit.screen

import Avatar
import android.util.Log
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.chatchit.R
import androidx.compose.material3.Card
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import com.example.chatchit.component.IconComponentDrawable
import com.example.chatchit.component.SpacerHeight
import com.example.chatchit.component.SpacerWidth
import com.example.chatchit.models.Conversation
import com.example.chatchit.models.Room
import com.example.chatchit.models.User
import com.example.chatchit.navigation.Chat
import com.example.chatchit.navigation.Search
import com.example.chatchit.services.APIService
import com.example.chatchit.services.api.MessageAPI
import com.example.chatchit.services.api.RoomAPI
import com.example.chatchit.services.api.await
import com.example.chatchit.services.api.form.NameField
import com.example.chatchit.ui.theme.Gray400
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


@Composable
fun HomeScreen(
    navHostController: NavHostController,
    context: Context
){
    val listRoom = navHostController.previousBackStackEntry?.savedStateHandle?.get<List<Room>>("listRoom") ?: emptyList<Room>()
    val user = navHostController.previousBackStackEntry?.savedStateHandle?.get<User>("user") ?: User()

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp)
        ){
            HeaderAndStory(context, listRoom, user, navHostController)
            Box(modifier = Modifier
                .fillMaxSize()
                .clip(
                    RoundedCornerShape(
                        topStart = 30.dp, topEnd = 30.dp
                    )
                )
                .background(Color.White)
            ){
//                BottomSheetSwipe(
//                    modifier = Modifier
//                        .align(TopCenter)
//                        .padding(top = 15.dp)
//
//                )
                LazyColumn(modifier = Modifier.padding(top = 30.dp, bottom = 15.dp) ){
                    items(listRoom, key = { it.id?:Int }) {
                        UserEachRow(person = it) {
//
                                navHostController.currentBackStackEntry?.savedStateHandle?.set(
                                    "data",
                                    it
                                )
                                navHostController.currentBackStackEntry?.savedStateHandle?.set(
                                    "user",
                                    user
                                )
                                navHostController.currentBackStackEntry?.savedStateHandle?.set(
                                    "roomId",
                                    it.id
                                )
                                navHostController.navigate(Chat)


                        }
                    }
                }
            }
        }
    }
}

@Composable
fun UserEachRow(
    modifier: Modifier = Modifier,
    person: Room,
    onClick: () -> Unit
){
    Box(modifier = modifier
        .fillMaxWidth()
        .background(Color.White)
        .clickable { onClick() }
        .padding(horizontal = 20.dp, vertical = 5.dp)
    ){
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Row{
                    Avatar(b64Image = person.avatar, modifier = Modifier.clip(RoundedCornerShape(30.dp)), size = 60.dp)
                    SpacerWidth()
                    Column{
                        Text(
                            text = person.name?:String(), style = TextStyle(
                                color = Color.Black,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        SpacerHeight(5.dp)
                        Text(
                            text = stringResource(id = R.string.okay),
                            style = TextStyle(
                                color = Color.Gray,
                                fontSize = 16.sp
                            )
                        )
                    }
                }
                Text(
                    text = "12:23 PM", style = TextStyle(
                        color = Gray,
                        fontSize = 12.sp,

                    )
                )
            }
            SpacerHeight(15.dp)
//            Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp,color = Gray)
        }
    }
}
@Composable
fun BottomSheetSwipe(
    modifier: Modifier = Modifier
){
    Box(modifier = modifier
        .clip(
            RoundedCornerShape(90.dp)

        )
        .width(90.dp)
        .height(5.dp)
        .background(Gray400)
    ){

    }
}

@Composable
fun HeaderAndStory(context:Context, listRoom: List<Room>, user: User, navHostController: NavHostController){
    Column (
        modifier = Modifier.padding(start = 20.dp)
    ){
        Header(user, navHostController)
        ViewStoryLayout(context, user, listRoom, navHostController)
    }
}


@Composable
fun ViewStoryLayout(context:Context, user: User, listRoom: List<Room>, navHostController: NavHostController){
    LazyRow(modifier = Modifier.padding(vertical = 20.dp)){
        item {
            AddStoryLayout(context, navHostController)
            SpacerWidth()
        }
        items(listRoom, key = {it.id?: Int}){
            UserStoryLayout(person = it){
                MainScope().launch {
                    try {
                        val mesService: MessageAPI =
                            APIService.getApiClient(context).create(
                                MessageAPI::class.java
                            )
                        val messAPIResponse =
                            mesService.getMessage(it.id ?: 0, 1, 100).await()
                        val json = Gson().toJson(messAPIResponse.data)
                        val itemType = object : TypeToken<Conversation>() {}.type
                        val mess = Gson().fromJson<Conversation>(json, itemType)
                        navHostController.currentBackStackEntry?.savedStateHandle?.set(
                            "data",
                            it
                        )
                        navHostController.currentBackStackEntry?.savedStateHandle?.set(
                            "user",
                            user
                        )
                        navHostController.currentBackStackEntry?.savedStateHandle?.set(
                            "mess",
                            mess
                        )
                        navHostController.navigate(Chat)
                    } catch (e: Exception) {
                        Log.e("moveChat", e.toString())
                    }
                }
            }
        }
    }
}


@Composable
fun Header(user: User, navHostController: NavHostController){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(end = 20.dp), horizontalArrangement = Arrangement.SpaceBetween){
        IconButtonSearch(modifier = Modifier.align(CenterVertically), navHostController = navHostController)
        Text(text = "Home", modifier = Modifier.align(CenterVertically), style = TextStyle(
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        ))
        Avatar(b64Image = user.avatar, modifier = Modifier.align(CenterVertically).clip(
            RoundedCornerShape(25.dp)
        ), size = 50.dp)
    }
}

@Composable
fun IconButtonSearch(modifier: Modifier = Modifier, navHostController: NavHostController) {
    IconButton(onClick = {
        navHostController.navigate(Search)
}) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_search_24),
            contentDescription = "",
            modifier = modifier.size(size = 30.dp),
            tint = Color.Unspecified
        )
    }
}

@Composable
fun UserStoryLayout(
    modifier: Modifier = Modifier,
    person: Room,
    onClick: () -> Unit
){
    Column(
        modifier = modifier.padding(end  = 10.dp)
    ) {
        Box(modifier = Modifier
            .clip(CircleShape)
            .background(Color.Yellow, CircleShape)
            .clickable { onClick() }
            .size(70.dp),
        contentAlignment = Center
        ){
            Avatar(b64Image = person.avatar, modifier = Modifier.clip(RoundedCornerShape(32.dp)), size = 64.dp)
        }
        SpacerHeight(8.dp)
        Text(
            text = person.name?:String(),
            style = TextStyle(
                fontSize = 13.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.align((CenterHorizontally))
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddStoryLayout(context:Context, navHostController: NavHostController){
    var openDialog by remember{ mutableStateOf(false)}
    Column {

        Box(modifier = Modifier
            .clip(CircleShape)
            .background(Color.White)
            .size(70.dp),
            contentAlignment = Alignment.Center
        ){
            IconButton(onClick = {
                openDialog = !openDialog
//                MainScope().launch {
//                    try {
//                        val groupService: RoomAPI =
//                            APIService.getApiClient(context).create(RoomAPI::class.java)
//
//                        val name = "SC203"
//                        val removeAPIResponse = groupService.createRoom(NameField(name)).await()
//                    } catch (e: Exception) {
//                        Log.e("Create group", e.toString())
//                    }
//                }
            }) {
                IconComponentDrawable(icon = R.drawable.baseline_add_circle_24, size = 70.dp, tint = Color.Gray)
            }

            if(openDialog) {
                var groupName by remember{ mutableStateOf("")}
                Dialog(onDismissRequest = { openDialog = false }) {
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
                            horizontalAlignment = CenterHorizontally
                        ) {
                            Text(
                                text = "Create group",
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier.align(CenterHorizontally)
                            )
                            SpacerHeight(10.dp)
                            TextField(
                                value = groupName,
                                onValueChange = {groupName = it},
                                maxLines = 1,
                                modifier = Modifier.align(CenterHorizontally)
                            )
                            SpacerHeight(10.dp)
                            Button(onClick = {
                                openDialog = false
                                MainScope().launch {
                                    try {
                                        val groupService: RoomAPI =
                                            APIService.getApiClient(context).create(RoomAPI::class.java)

                                        val name = groupName
                                        val removeAPIResponse = groupService.createRoom(NameField(name)).await()
                                    } catch (e: Exception) {
                                        Log.e("Create group", e.toString())
                                    }
                                }
                            }) {
                                Text(
                                    text = "Create",
                                    style = TextStyle(
                                        fontSize = 20.sp,
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    textAlign = TextAlign.Center,
//                                    modifier = Modifier.align(CenterHorizontally)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
