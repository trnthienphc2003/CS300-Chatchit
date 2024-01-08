package com.example.chatchit.screen

import android.util.Log
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.chatchit.R
import com.example.chatchit.component.IconComponentDrawable
import com.example.chatchit.component.SpacerHeight
import com.example.chatchit.component.SpacerWidth
import com.example.chatchit.models.User
import com.example.chatchit.navigation.AddFriend



class SearchViewModel : ViewModel() {
    private val searchList = mutableStateOf<List<User>>(emptyList())

    fun getsearchList(): State<List<User>> = searchList

    fun setsearchList(list: List<User>) {
        searchList.value = list
    }
    fun addSearch(friend: User) {
        searchList.value = listOf(friend) + searchList.value
    }
    fun getsize(): Int = searchList.value.size
}


@Composable
fun SearchScreen(
    navHostController: NavHostController
){
    val viewModel = SearchViewModel()
    viewModel.setsearchList(listOf())
    val lazyColumnListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp)
        ){
            searchInput(viewModel, modifier = Modifier.align(alignment = Alignment.CenterHorizontally), modifierText = Modifier.align(
                alignment = Alignment.CenterHorizontally
            ))
            LazyColumn(modifier = Modifier.padding(top = 30.dp, bottom = 15.dp) ){
                items(viewModel.getsearchList().value, key = { it.id?:Int }) {
                    FriendEachRow(person = it){
                        navHostController.currentBackStackEntry?.savedStateHandle?.set(
                            "addfriend",
                            it
                        )
                        navHostController.navigate(AddFriend)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun searchInput(
    viewModel: SearchViewModel,
    modifier: Modifier = Modifier,
    modifierText: Modifier = Modifier,
){
    var friend by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        TextField(
            value = friend,
            onValueChange = { friend = it },
            modifier = modifier
                .width(340.dp)
                .height(50.dp),
            shape = RoundedCornerShape(10.dp),
            placeholder = {
                Text(
                    text = "Search friend",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                )
            },
            leadingIcon = { IconComponentDrawable(icon = R.drawable.baseline_search_24, size = 35.dp)},
//            trailingIcon = { IconButtonEmoji(modifier = modifierText) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Send
            ),
            keyboardActions = KeyboardActions(
                onSend = {
                    keyboardController?.hide()
                    val content = friend.trim()
                    friend = ""
                    viewModel.addSearch(User(id = viewModel.getsize(), name = "nghiem"))
                }
            )
        )
    }
}

@Composable
fun FriendEachRow(
    modifier: Modifier = Modifier,
    person: User,
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
                    Text(
                        text = person.name?:String(), style = TextStyle(
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.align(
                            alignment = Alignment.CenterVertically
                        )
                    )
                }
            }
            SpacerHeight(15.dp)
//            Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp,color = Gray)
        }
    }
}