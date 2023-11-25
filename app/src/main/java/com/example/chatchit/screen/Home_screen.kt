package com.example.chatchit.screen

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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
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
import com.example.chatchit.component.data.Person
import com.example.chatchit.component.data.personList
import com.example.chatchit.navigation.Chat
import com.example.chatchit.ui.theme.Gray400


@Composable
fun HomeScreen(
    navHostController: NavHostController
){
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp)
        ){
            val user = Person(
                1,
            "Pranav",
                R.drawable.person_1
            )
            HeaderAndStory(user, navHostController)
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
                    items(personList, key = { it.id }) {
                        UserEachRow(person = it) {
                            navHostController.currentBackStackEntry?.savedStateHandle?.set(
                                "data",
                                it
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
    person: Person,
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
                    IconComponentDrawable(icon = person.icon, size = 60.dp)
                    SpacerWidth()
                    Column{
                        Text(
                            text = person.name, style = TextStyle(
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
fun HeaderAndStory(user: Person, navHostController: NavHostController){
    Column (
        modifier = Modifier.padding(start = 20.dp)
    ){
        Header(user, navHostController)
        ViewStoryLayout(navHostController)
    }
}


@Composable
fun ViewStoryLayout(navHostController: NavHostController){
    LazyRow(modifier = Modifier.padding(vertical = 20.dp)){
//        item {
//            AddStoryLayout()
//            SpacerWidth()
//        }
        items(personList, key = {it.id}){
            UserStoryLayout(person = it){
                navHostController.currentBackStackEntry?.savedStateHandle?.set(
                    "data",
                    it
                )
                navHostController.navigate(Chat)
            }
        }
    }
}


@Composable
fun Header(user: Person, navHostController: NavHostController){
    Row(modifier = Modifier.fillMaxWidth().padding(end=20.dp), horizontalArrangement = Arrangement.SpaceBetween){
        IconButtonSearch(modifier = Modifier.align(CenterVertically), navHostController = navHostController)
        Text(text = "Home", modifier = Modifier.align(CenterVertically), style = TextStyle(
            color = Gray,
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp
        ))
        IconComponentDrawable(icon = user.icon, modifier = Modifier.align(CenterVertically), size = 50.dp)
    }
}

@Composable
fun IconButtonSearch(modifier: Modifier = Modifier, navHostController: NavHostController) {
    IconButton(onClick = { /* do something */ }) {
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
    person: Person,
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
            IconComponentDrawable(icon = person.icon, size = 65.dp)
        }
        SpacerHeight(8.dp)
        Text(
            text = person.name,
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

