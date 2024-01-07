package com.example.chatchit.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.ui.graphics.vector.ImageVector

data class NavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

val listOfNavItems = listOf(
    NavItem(
        label = "Message",
        icon = Icons.Outlined.MailOutline,
        route = Home
    ),
    NavItem(
        label = "Calls",
        icon = Icons.Outlined.Call,
        route = Call
    ),
    NavItem(
        label = "Contacts",
        icon = Icons.Outlined.AccountCircle,
        route = Contact
    ),
    NavItem(
        label = "Settings",
        icon = Icons.Outlined.AccountCircle,
        route = ChatSetting
    )
)