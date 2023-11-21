package com.example.chatchit.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chatchit.screen.ChatScreen
import com.example.chatchit.screen.HomeScreen
import com.example.chatchit.screen.StartScreen

@Composable
fun MainNavigation(){
    val navHostController = rememberNavController()
    NavHost(navController = navHostController, startDestination = Start){
        composable(Start){
            StartScreen(
                navHostController
            )
        }
        composable(Home){
            HomeScreen(
                navHostController
            )
        }
        composable(Chat){
            ChatScreen(
                navHostController
            )
        }
    }
}



const val Start = "start_screen"
const val Home = "home_screen"
const val Chat = "chat_screen"