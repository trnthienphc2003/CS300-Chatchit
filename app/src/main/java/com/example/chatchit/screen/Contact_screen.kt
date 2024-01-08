package com.example.chatchit.screen

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.chatchit.component.data.Person
import com.example.chatchit.component.data.personList
import com.example.chatchit.models.Room
import com.example.chatchit.models.User

@Composable
fun ContactScreen(
    navHostController: NavHostController,
    context: Context
) {
    val user = navHostController.previousBackStackEntry?.savedStateHandle?.get<Room>("user") ?: User()
    Column (
        modifier = androidx.compose.ui.Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(
            text = "Contact Screen",
            fontWeight = FontWeight.Bold,
//            modifier = Modifier.fillMaxSize(),
        )

        ContactList(grouped = grouped, modifier = Modifier)
    }
}

val grouped = personList.sortedBy { it.name } .groupBy { it.name[0] }

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContactList (
    grouped: Map<Char, List<Person>>,
    modifier: androidx.compose.ui.Modifier
) {
    LazyColumn(
        modifier = androidx.compose.ui.Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        grouped.forEach { (initial, contactForLetter) ->
            stickyHeader {
//                Header
                contactListHeader(initial = initial)
            }
            items(items = contactForLetter) { person ->
//                Items component
                contactListItem(person = person)
            }
        }
    }
}

@Composable
fun contactListHeader(
    initial: Char
) {
    Box(
        modifier = androidx.compose.ui.Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = initial.toString(),
            color = Color.DarkGray
        )
    }
}

@Composable
fun contactListItem(person: Person) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = androidx.compose.ui.Modifier
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .size(36.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = person.name.first().toString(),
                color = Color.White,
            )
        }

        Text(
            text = person.name,
            fontWeight = FontWeight.Medium,
            color = Color.DarkGray
        )
    }
}
