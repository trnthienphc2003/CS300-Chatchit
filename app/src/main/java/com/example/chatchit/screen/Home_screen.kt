package com.example.chatchit.screen

import android.util.Log
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.chatchit.component.IconComponentDrawable
import com.example.chatchit.component.IconComponentImageVector
import com.example.chatchit.component.SpacerHeight
import com.example.chatchit.component.SpacerWidth
import com.example.chatchit.models.Conversation
import com.example.chatchit.models.Room
import com.example.chatchit.models.User
import com.example.chatchit.navigation.Chat
import com.example.chatchit.navigation.ChatSetting
import com.example.chatchit.navigation.Search
import com.example.chatchit.services.APIService
import com.example.chatchit.services.api.MessageAPI
import com.example.chatchit.services.api.await
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
//                            navHostController.currentBackStackEntry?.savedStateHandle?.set(
//                                "data",
//                                it
//                            )
//                            navHostController.currentBackStackEntry?.savedStateHandle?.set(
//                                "user",
//                                user
//                            )
//                            navHostController.navigate(Chat)
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
                                    val conversation = Gson().fromJson<Conversation>(json, itemType)
                                    navHostController.currentBackStackEntry?.savedStateHandle?.set(
                                        "data",
                                        it
                                    )
                                    navHostController.currentBackStackEntry?.savedStateHandle?.set(
                                        "user",
                                        user
                                    )
                                    navHostController.currentBackStackEntry?.savedStateHandle?.set(
                                        "conversation",
                                        conversation
                                    )
                                    navHostController.currentBackStackEntry?.savedStateHandle?.set(
                                        "roomId",
                                        it.id
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
                    IconComponentDrawable(icon = R.drawable.person_2, size = 60.dp)
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
//        item {
//            AddStoryLayout()
//            SpacerWidth()
//        }
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
    Row(modifier = Modifier.fillMaxWidth().padding(end=20.dp), horizontalArrangement = Arrangement.SpaceBetween){
        IconButtonSearch(modifier = Modifier.align(CenterVertically), navHostController = navHostController)
        Text(text = "Home", modifier = Modifier.align(CenterVertically), style = TextStyle(
            color = Gray,
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp
        ))
        IconComponentDrawable(icon = R.drawable.person_3, modifier = Modifier.align(CenterVertically), size = 50.dp)
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
            IconComponentDrawable(icon = R.drawable.person_2, size = 65.dp)
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


@Composable
fun AddStoryLayout(){
    Column {
        Box(modifier = Modifier
            .border(
                2.dp, Color.DarkGray, shape = CircleShape
            )
            .background(
                Color.Yellow, shape = CircleShape
            )
            .size(70.dp),
            contentAlignment = Alignment.Center
        ){
            Box(modifier = Modifier
                .clip(CircleShape)
                .background(Color.Black)
                .size(20.dp),
                contentAlignment = Alignment.Center
            ){
                IconComponentImageVector(icon = Icons.Default.Add, size = 12.dp, tint = Color.Yellow)
            }
        }
        SpacerHeight(8.dp)
        Text(text = stringResource(id = R.string.add_story),
                style = TextStyle(
                    fontSize = 13.sp,
                    color = Color.White,
                ),
                modifier = Modifier.align(CenterHorizontally)
            )
    }
}
