package com.example.chatchit.navigation

import android.content.Context
import android.provider.ContactsContract.Profile
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.chatchit.models.Language
import com.example.chatchit.models.Room
import com.example.chatchit.models.User
import com.example.chatchit.screen.AddFriendScreen
import com.example.chatchit.screen.AddGroupScreen
import com.example.chatchit.screen.CallScreen
import com.example.chatchit.screen.ChatScreen
import com.example.chatchit.screen.ChatSettingScreen
import com.example.chatchit.screen.ContactScreen
import com.example.chatchit.screen.GroupScreen
import com.example.chatchit.screen.HomeScreen
import com.example.chatchit.screen.LoginScreen
import com.example.chatchit.screen.ManageMemberScreen
import com.example.chatchit.screen.ProfileScreen
import com.example.chatchit.screen.SearchScreen
import com.example.chatchit.screen.SettingScreen
import com.example.chatchit.screen.SignUpScreen
import com.example.chatchit.screen.StartScreen
import com.example.chatchit.services.APIService
import com.example.chatchit.services.WebSocketService
import com.example.chatchit.services.api.AuthAPI
import com.example.chatchit.services.api.FriendAPI
import com.example.chatchit.services.api.LanguageAPI
import com.example.chatchit.services.api.RoomAPI
import com.example.chatchit.services.api.await
import com.example.chatchit.services.api.form.LoginForm
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.net.ConnectException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavigation(
    context: Context
){
    val navHostController = rememberNavController()

    val onBack: () -> Unit = {
        navHostController.popBackStack()
    }

    val showBottomBar = navHostController
        .currentBackStackEntryAsState().value?.destination?.route in listOfNavItems.map { it.route }

    Scaffold(
        modifier = Modifier.background(Color(0xFF24786D)),
        bottomBar = {
            if(showBottomBar) {
                NavigationBar(
                    modifier = Modifier.background(
                        color = Color(0xFFEEFAF8)
                    )
                ) {
                    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination

                    listOfNavItems.forEach { navItem ->
                        NavigationBarItem(
                            selected = currentDestination?.hierarchy?.any { it.route == navItem.route } == true,
                            onClick = {
                                if(navItem.route == Home) {
                                    MainScope().launch {
                                        try {
                                            val authService: AuthAPI = APIService.getApiClient(context).create(AuthAPI::class.java)
                                            val userResponse = authService.getUser().await()
                                            val jsonUser = Gson().toJson(userResponse.data)
                                            val user = Gson().fromJson(jsonUser, User::class.java)
                                            navHostController.currentBackStackEntry?.savedStateHandle?.set(
                                                "user",
                                                user
                                            )
                                            navHostController.navigate(Home)
                                        } catch (e: Exception) {
                                            Log.e("Main Navigation", e.toString())
                                            Toast.makeText(context, "Fail to navigate home", Toast.LENGTH_SHORT).show()
                                        } catch (e: ConnectException) {
                                            Toast.makeText(context, "Connection error", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }

                                else if(navItem.route == Contact) {
                                    MainScope().launch {
                                        try {
                                            val friendService: FriendAPI = APIService.getApiClient(context).create(FriendAPI::class.java)
                                            val roomAPIResponse = friendService.getFriends().await()
                                            val json = Gson().toJson(roomAPIResponse.data)
                                            val itemType = object : TypeToken<List<User>>() {}.type
                                            val listFriends = Gson().fromJson<List<User>>(json, itemType)

                                            navHostController.currentBackStackEntry?.savedStateHandle?.set(
                                                "friends",
                                                listFriends
                                            )
                                            navHostController.navigate(Contact)
                                        } catch (e: Exception) {
                                            Log.e("Main Navigation", e.toString())
                                            Toast.makeText(context, "Fail to navigate contact", Toast.LENGTH_SHORT).show()
                                        } catch (e: ConnectException) {
                                            Toast.makeText(context, "Connection error", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }

                                else if(navItem.route == Setting) {
                                    val authService: AuthAPI = APIService.getApiClient(context).create(AuthAPI::class.java)
                                    MainScope().launch {
                                        try {
                                            val authService: AuthAPI = APIService.getApiClient(context).create(AuthAPI::class.java)
                                            val userResponse = authService.getUser().await()
                                            val jsonUser = Gson().toJson(userResponse.data)
                                            val user = Gson().fromJson(jsonUser, User::class.java)
                                            navHostController.currentBackStackEntry?.savedStateHandle?.set(
                                                "user",
                                                user
                                            )

                                            val languageAPI: LanguageAPI = APIService.getApiClient(context).create(
                                                LanguageAPI::class.java)
                                            val languageAPIResponse = languageAPI.getLanguages().await()
                                            val json = Gson().toJson(languageAPIResponse.data)
                                            val itemType = object : TypeToken<List<Language>>() {}.type
                                            val listOfLanguages = Gson().fromJson<List<Language>>(json, itemType)

                                            navHostController.currentBackStackEntry?.savedStateHandle?.set(
                                                "listOfLanguages",
                                                listOfLanguages
                                            )
                                            navHostController.navigate(Setting)

                                        } catch (e: ConnectException) {
                                            Toast.makeText(context, "Connection error", Toast.LENGTH_SHORT).show()
                                        } catch (e: Exception) {
                                            Log.e("Main Navigation", e.toString())
                                            Toast.makeText(context, "Fail to navigate settings", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                                else {
                                    navHostController.navigate(Call)
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = navItem.icon,
                                    contentDescription = null
                                )
                            },
                            label = {
                                Text(text = navItem.label)
                            }
                        )
                    }
                }
            }
        }
    ) {paddingValues ->
        NavHost(
            navController = navHostController,
            startDestination = Start,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Start){
                StartScreen(
                    navHostController
                )
            }
            composable(Login) {
                LoginScreen(
                    navHostController,
                    LocalContext.current
                )
            }
            composable(SignUp) {
                SignUpScreen(
                    navHostController,
                    LocalContext.current
                )
            }
            composable(Home){
                HomeScreen(
                    navHostController,
                    LocalContext.current
                )
            }
            composable(Call){
                CallScreen(
                    navHostController,
                    LocalContext.current
                )
            }
            composable(Contact){
                ContactScreen(
                    navHostController,
                    LocalContext.current
                )
            }
            composable(Chat){
                ChatScreen(
                    navHostController,
                    LocalContext.current
                )
            }
            composable(ChatSetting){
                ChatSettingScreen(
                    navHostController,
                    LocalContext.current
                )
            }
            composable(Setting) {
                SettingScreen(
                    navHostController,
                    LocalContext.current
                )
            }
            composable(Search){
                SearchScreen(navHostController,
                    LocalContext.current
                )
            }
            composable(AddFriend){
                AddFriendScreen(navHostController,
                LocalContext.current)
            }
            composable(Group){
                GroupScreen(navHostController,
                    LocalContext.current)
            }
            composable(AddGroup) {
                AddGroupScreen(
                    navHostController,
                    LocalContext.current
                )
            }
            composable(Profile){
                ProfileScreen(
                    navHostController = navHostController,
                    context = LocalContext.current)
            }
            composable(ManageMember){
                ManageMemberScreen(navHostController, LocalContext.current)
            }
        }
    }
}


const val Start = "start_screen"
const val Home = "home_screen"
const val Chat = "chat_screen"
const val Login = "login_screen"
const val SignUp = "signup_screen"
const val ChatSetting = "chat_setting_screen"
const val Call = "call_screen"
const val Contact = "contact_screen"
const val Search = "search_screen"
const val AddFriend = "add_friend_screen"
const val Setting = "setting_screen"
const val Group = "group_screen"
const val AddGroup = "add_group_screen"
const val Profile = "profile_screen"
const val ManageMember = "manage_member_screen"