package com.example.chatchit.screen

import Avatar
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarColors
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.navigation.compose.rememberNavController
import com.example.chatchit.R
import com.example.chatchit.component.IconComponentDrawable
import com.example.chatchit.component.SpacerHeight
import com.example.chatchit.models.User
import com.example.chatchit.navigation.Profile
import kotlinx.coroutines.launch

@Composable
fun ContactScreen(
    navHostController: NavHostController,
    context: Context,
) {
    val friends = navHostController.previousBackStackEntry?.savedStateHandle?.get<List<User>>("friends") ?: List<User>(0) { User() }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val grouped = friends.sortedBy { it.name } .groupBy { it.name?.get(0) ?: '$' }

    Box (
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        SearchContact(
            navHostController = navHostController,
            grouped = grouped
        )
        SpacerHeight(10.dp)
        Row(modifier = Modifier
            .fillMaxSize()
            .padding(top = 60.dp)
            .background(Color.White),
            ) {
            ContactList(grouped = grouped, modifier = Modifier.weight(1f), listState, navHostController)

            AlphabetBar(grouped = grouped) { pos ->
                coroutineScope.launch{
                    listState.animateScrollToItem(pos)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchContact(
    navHostController: NavHostController,
    grouped: Map<Char, List<User>>
) {
    var text by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }

    // search bar
    DockedSearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 11.dp)
            .background(Color(0xFFFFFFFF)),
        shape = RoundedCornerShape(16.dp),
        query = text,
        colors = SearchBarDefaults.colors(
            containerColor = Color(0xFFF3F6F6),
            dividerColor = Color(0xFFE7E7E7),
        ),
        onQueryChange = { text = it },
        onSearch = { active = false },
        active = active,
        onActiveChange = {
            active = it
        },
        placeholder = { Text(
            "Search a friend",
            color = Color.Black
        ) },
    ) {
        val flat = grouped.values.flatten().filter { it.name?.contains(text, ignoreCase = true) ?: false  }

        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 22.dp, vertical = 14.dp)
                .background(Color(0xFFF3F6F6)),
//                .fillMaxHeight(0.1f),
//                .height(50.dp),
            userScrollEnabled = true,
            verticalArrangement = Arrangement.spacedBy(18.dp),
            content = {
                item { Text(text = "${flat.size} contacts found") }

                items(items = flat) { contact ->
//                    contactListItem(person = contact)
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .background(Color(0xFFF3F6F6))
                            .clickable {
                                navHostController.currentBackStackEntry?.savedStateHandle?.set(
                                    "view_person",
                                    contact
                                )
                                navHostController.navigate(Profile)
                            }
                    ) {
                        Box(
                            modifier = androidx.compose.ui.Modifier
                                .clip(CircleShape)
                                .background(Color(0xFFF3F6F6))
                                .size(36.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Avatar(
                                b64Image = contact.avatar,
                                size = 36.dp
                            )
                        }

                        contact.name?.let {
                            Text(
                                text = it,
                                fontWeight = FontWeight.Medium,
                                color = Color.DarkGray
                            )
                        }
                    }
                }
            })
    }
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContactList (
    grouped: Map<Char, List<User>>,
    modifier: Modifier,
    listState: LazyListState,
    navHostController: NavHostController
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        state = listState,
        userScrollEnabled = true
    ) {
        grouped.forEach { (initial, contactForLetter) ->
            stickyHeader {
//                Header
                contactListHeader(initial = initial)
            }
            items(items = contactForLetter) { person ->
//                Items component
//                contactListItem(person = person)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {
                        navHostController.currentBackStackEntry?.savedStateHandle?.set(
                            "view_person",
                            person
                        )
                        navHostController.navigate(Profile)
                    }
                ) {
                    Box(
                        modifier = androidx.compose.ui.Modifier
                            .clip(CircleShape)
                            .background(Color(0xFFF3F6F6))
                            .size(36.dp),
                        contentAlignment = Alignment.Center
                    ) {
//                        IconComponentDrawable(
//                            icon = R.drawable.person_2,
//                            size = 36.dp
//                        )
                        Avatar(
                            b64Image = person.avatar,
                            size = 36.dp
                        )
                    }

                    person.name?.let {
                        Text(
                            text = it,
                            fontWeight = FontWeight.Medium,
                            color = Color.DarkGray
                        )
                    }
                }
                if(contactForLetter.indexOf(person) != contactForLetter.lastIndex) {
                    SpacerHeight(12.dp)
                    Divider(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 3.dp),
                        color = Color(0xFFE7E7E7)
                    )
                }
            }
        }
    }
}

@Composable
fun AlphabetBar(
    grouped: Map<Char, List<User>>,
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
            .background(Color.White)
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = initial.toString(),
            color = Color.DarkGray
        )
    }
}

@Composable
fun contactListItem(person: User) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.background(Color(0xFFF3F6F6))
    ) {
        Box(
            modifier = androidx.compose.ui.Modifier
                .clip(CircleShape)
                .background(Color(0xFFF3F6F6))
                .size(36.dp),
            contentAlignment = Alignment.Center
        ) {
//            IconComponentDrawable(
//                icon = R.drawable.person_2,
//                size = 36.dp
//            )
            Avatar(
                b64Image = person.avatar,
                size = 36.dp
            )
        }

        person.name?.let {
            Text(
                text = it,
                fontWeight = FontWeight.Medium,
                color = Color.DarkGray
            )
        }
    }
}

//@Composable
//@Preview(showBackground = true)
//fun ContactScreenPreview() {
//    ContactScreen(
//        navHostController = NavHostController(androidx.compose.ui.platform.LocalContext.current),
//        context = androidx.compose.ui.platform.LocalContext.current
//    )
//}

@Composable
@Preview(showBackground = true)
fun SearchBarPreview() {
    val grouped = mapOf<Char, List<User>>('A' to List<User>(0) { User() })
    SearchContact(
        grouped = grouped,
        navHostController = rememberNavController()
    )
}

