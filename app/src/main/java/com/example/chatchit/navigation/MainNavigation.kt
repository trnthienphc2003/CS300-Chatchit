package com.example.chatchit.navigation

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
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.chatchit.screen.CallScreen
import com.example.chatchit.screen.ChatScreen
import com.example.chatchit.screen.ChatSettingScreen
import com.example.chatchit.screen.ContactScreen
import com.example.chatchit.screen.HomeScreen
import com.example.chatchit.screen.LoginScreen
import com.example.chatchit.screen.SignUpScreen
import com.example.chatchit.screen.StartScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavigation(){
    val navHostController = rememberNavController()

    val onBack: () -> Unit = {
        navHostController.popBackStack()
    }

    val showBottomBar = navHostController
        .currentBackStackEntryAsState().value?.destination?.route in listOfNavItems.map { it.route }

    Scaffold(
        bottomBar = {
            if(showBottomBar) {
                NavigationBar {
                    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination

                    listOfNavItems.forEach { navItem ->
                        NavigationBarItem(
                            selected = currentDestination?.hierarchy?.any { it.route == navItem.route } == true,
                            onClick = {
                                navHostController.navigate(navItem.route) {
                                    popUpTo(navHostController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
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
                    navHostController
                )
            }
            composable(ChatSetting){
                ChatSettingScreen(
                    navHostController
                )
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