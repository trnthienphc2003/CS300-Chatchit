package com.example.chatchit.screen

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.chatchit.component.IconComponentDrawable
import com.example.chatchit.component.data.Person
import com.example.chatchit.component.data.personList
import com.example.chatchit.models.Room
import com.example.chatchit.models.User
import kotlinx.coroutines.launch

@Composable
fun ContactScreen(
    navHostController: NavHostController,
    context: Context,
) {
    val user = navHostController.previousBackStackEntry?.savedStateHandle?.get<User>("user") ?: User()
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Box {
        SearchContact(grouped = grouped)
        Row(modifier = Modifier.fillMaxSize().padding(top = 60.dp)) {
            ContactList(grouped = grouped, modifier = Modifier.weight(1f), listState)

            AlphabetBar(grouped = grouped) {pos ->
                coroutineScope.launch{
                    listState.animateScrollToItem(pos)
                }
            }
        }
    }
}

val grouped = personList.sortedBy { it.name } .groupBy { it.name[0] }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchContact(grouped: Map<Char, List<Person>>) {
    var text by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }

    // search bar
    SearchBar(
        modifier = Modifier.fillMaxWidth(),
        query = text,
        onQueryChange = { text = it },
        onSearch = { active = false },
        active = active,
        onActiveChange = {
            active = it
        },
        placeholder = { Text("Hinted search text") },
    ) {
        val flat = grouped.values.flatten().filter { it.name.contains(text, ignoreCase = true) }

        LazyColumn(
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp),
            content = {
                item { Text(text = "${flat.size} contacts found") }

                items(items = flat) { contact ->
                    contactListItem(person = contact)
                }
            })
    }
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContactList (
    grouped: Map<Char, List<Person>>,
    modifier: androidx.compose.ui.Modifier,
    listState: androidx.compose.foundation.lazy.LazyListState
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        state = listState
    ) {
        grouped.forEach { (initial, contactForLetter) ->
            stickyHeader {
//                Header
                contactListHeader(initial = initial)
            }
            items(items = contactForLetter) { person ->
//                Items component
                contactListItem(person = person)
                if(contactForLetter.indexOf(person) != contactForLetter.lastIndex) {
                    Divider(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        color = Color(0xFFE7E7E7)
                    )
                }
            }
        }
    }
}

@Composable
fun AlphabetBar(
    grouped: Map<Char, List<Person>>,
    toHeader: (Int) -> Unit
) {
    val letterPos = mutableMapOf<Char, Int>()
    var count by rememberSaveable{ mutableStateOf(0) }

    grouped.forEach { (letter, contactsForLetter) ->
        letterPos[letter] = count
        count += contactsForLetter.size + 1
    }

    Column (
        modifier = Modifier
            .fillMaxHeight()
            .padding(horizontal = 6.dp, vertical = 36.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(3.dp),

    ) {
        for (alphabet in 'A'..'Z') {
            Box(
                modifier = Modifier
                    .clickable { letterPos[alphabet]?.let { toHeader(it) } }
                    .size(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = alphabet.toString(),
                    textAlign = TextAlign.Center
                )
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
            IconComponentDrawable(
                icon = person.icon,
                size = 36.dp
            )
//            Text(
//                text = person.name.first().toString(),
//                color = Color.White,
//            )
        }

        Text(
            text = person.name,
            fontWeight = FontWeight.Medium,
            color = Color.DarkGray
        )
    }
}

@Composable
@Preview(showBackground = true)
fun ContactScreenPreview() {
    ContactScreen(
        navHostController = NavHostController(androidx.compose.ui.platform.LocalContext.current),
        context = androidx.compose.ui.platform.LocalContext.current
    )
}
