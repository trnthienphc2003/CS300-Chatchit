package com.example.chatchit.screen

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.navigation.NavHostController
import java.lang.reflect.Modifier

@Composable
fun CallScreen(
    navHostController: NavHostController,
    context: Context
) {
    Column (
        modifier = androidx.compose.ui.Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(
            text = "Call Screen",
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
//            modifier = Modifier.fillMaxSize(),
        )
    }
}