package com.example.chatchit.screen


//import com.example.chatchit.component.data.chatList
import Avatar
import android.content.Context
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.Absolute.SpaceBetween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.chatchit.R
import com.example.chatchit.component.SpacerHeight
import com.example.chatchit.component.SpacerWidth
import com.example.chatchit.models.Conversation
import com.example.chatchit.models.MessageTranslate
import com.example.chatchit.models.Room
import com.example.chatchit.models.User
import com.example.chatchit.navigation.ChatSetting
import com.example.chatchit.services.APIService
import com.example.chatchit.services.WebSocketCallback
import com.example.chatchit.services.WebSocketService
import com.example.chatchit.services.api.MessageAPI
import com.example.chatchit.services.api.await
import com.example.chatchit.services.api.form.MessageForm
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatterBuilder
import java.util.Calendar

class ChatViewModel() : ViewModel() {
    private var loaded = false
    private val chatList = mutableStateOf<List<MessageTranslate>>(emptyList())
    private val stateTranslate = mutableStateOf<Boolean>(false)

    fun init(context: Context, roomId: Int) {
        MainScope().launch {
            try {
                val mesService: MessageAPI =
                    APIService.getApiClient(context).create(
                        MessageAPI::class.java
                    )
                val messAPIResponse =
                    mesService.getMessage(roomId?: 0, 1, 100).await()
                val json = Gson().toJson(messAPIResponse.data)
                val itemType = object : TypeToken<Conversation>() {}.type
                val conversation = Gson().fromJson<Conversation>(json, itemType)
                setChatList(conversation.rows?: emptyList())
            } catch (e: Exception) {
                Log.e("moveChat", e.toString())
            }
        }
    }

    public fun getChatList(): State<List<MessageTranslate>> = chatList

    private fun setChatList(list: List<MessageTranslate>) {
        chatList.value = list
    }
    public fun addMessage(message: MessageTranslate) {
        chatList.value = listOf(message) + chatList.value
    }
    fun clear(){
        chatList.value = emptyList()
    }

    fun setStateTranslate(state: Boolean){
        stateTranslate.value = state
    }
    fun getStateTranslate() : Boolean = stateTranslate.value

    companion object {
        private var instance: ChatViewModel? = null
        fun getInstance(): ChatViewModel {
            if (instance == null) {
                instance = ChatViewModel()
            }
            return instance!!
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    navHostController: NavHostController,
    context: Context
){
    val person = navHostController.previousBackStackEntry?.savedStateHandle?.get<Room>("data") ?: Room()
    val user = navHostController.previousBackStackEntry?.savedStateHandle?.get<User>("user") ?: User()
    val roomId = navHostController.previousBackStackEntry?.savedStateHandle?.get<Int>("roomId") ?: -1
    ChatViewModel.getInstance().init(context, roomId)
    val lazyColumnListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    WebSocketService.getInstance().setCallback(object : WebSocketCallback {
        override fun onReceiveMessage(message: MessageTranslate) {
            if (message.message!!.roomId == roomId) {
                ChatViewModel.getInstance().addMessage(message)
                if (message.message.senderId == user.id) {
                    coroutineScope.launch {
                        lazyColumnListState.animateScrollToItem(0)
                    }
                }
            }
        }
    })

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
//                .padding(innerPadding)
    ){

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            inforBar(
                ChatViewModel.getInstance(),
                person,
                modifier = Modifier.padding(top = 30.dp, start = 10.dp, end = 20.dp),
                navHostController
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 15.dp, start = 20.dp, end = 20.dp, bottom = 20.dp)
                    .background(Color.White)


            ){
                LazyColumn(modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp, bottom = 75.dp),
                    reverseLayout = true,
                    state = lazyColumnListState
                ){
                    items(ChatViewModel.getInstance().getChatList().value, key = {it.id?:Int}){
                        chatRow(ChatViewModel.getInstance(), user, chat = it, person)
                    }
                }
                chatInput(roomId = roomId, modifier = Modifier.align(BottomCenter), modifierText = Modifier.align(Center))
            }
        }
    }
}

@Composable
fun IconButtonBackChat(viewModel: ChatViewModel, modifier: Modifier = Modifier, navHostController: NavHostController) {
    IconButton(onClick = {
        viewModel.clear()
        navHostController.popBackStack()}) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_arrow_back_24),
            contentDescription = "",
            modifier = modifier.size(size = 25.dp),
            tint = Color.Unspecified
        )
    }
}

@Composable
fun IconButtonBack(modifier: Modifier = Modifier, navHostController: NavHostController) {
    IconButton(onClick = {
        navHostController.popBackStack()}) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_arrow_back_24),
            contentDescription = "",
            modifier = modifier.size(size = 25.dp),
            tint = Color.Unspecified
        )
    }
}

@Composable
fun IconButtonCall(modifier: Modifier = Modifier, navHostController: NavHostController) {
    IconButton(onClick = { /* do something */ }) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_call_24),
            contentDescription = "",
            modifier = modifier.size(size = 25.dp),
            tint = Color.Unspecified
        )
    }
}

@Composable
fun IconButtonTranslate(viewModel: ChatViewModel,modifier: Modifier = Modifier, navHostController: NavHostController) {
    IconButton(onClick = {
        if (viewModel.getStateTranslate()){
            viewModel.setStateTranslate(false)
        }
        else{
            viewModel.setStateTranslate(true)
        }
    }) {
        if (viewModel.getStateTranslate()) {
            Icon(
                painter = painterResource(id = R.drawable.translate),
                contentDescription = "",
                modifier = modifier.size(size = 30.dp),
                tint = Color.Unspecified
            )
        }
        else{
            Icon(
                painter = painterResource(id = R.drawable.notranslate),
                contentDescription = "",
                modifier = modifier.size(size = 30.dp),
                tint = Color.Unspecified
            )
        }
    }
}
@Composable
fun IconButtonVideoCall(modifier: Modifier = Modifier, navHostController: NavHostController) {
    IconButton(onClick = { /* do something */ }) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_videocam_24),
            contentDescription = "",
            modifier = modifier.size(size = 25.dp),
            tint = Color.Unspecified
        )
    }
}

@Composable
fun inforBar(
    viewModel: ChatViewModel,
    person: Room,
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
){
    Row (
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = SpaceBetween
    ){
        Row {
//            IconComponentDrawable(icon = R.drawable.baseline_arrow_back_24, modifier = Modifier.align(CenterVertically), size = 25.dp)
            IconButtonBackChat(viewModel, modifier = Modifier.align(CenterVertically), navHostController)
            SpacerWidth()

            IconButton(onClick = {
                navHostController.currentBackStackEntry?.savedStateHandle?.set(
                    "data",
                    person
                )
                navHostController.navigate(ChatSetting)}) {
                Avatar(b64Image = person.avatar, modifier = Modifier.clip(RoundedCornerShape(21.dp)), size = 42.dp)
            }
            SpacerWidth()
            Column {
                Text(
                    text = person.name?:String(),
                    style = TextStyle(
                        color = Color.Black,
                        fontWeight = Bold,
                        fontSize = 20.sp
                    )
                )
                Text(
                    text = "Active now",
                    style = TextStyle(
                        color = Color.Gray,
                        fontSize = 15.sp
                    )
                )
            }
        }

        Row {
            IconButtonTranslate(viewModel, modifier = Modifier.align(CenterVertically), navHostController)
        }
    }
}
//--------------------------

@Composable
fun chatRow(
    viewModel: ChatViewModel,
    user: User,
    chat: MessageTranslate,
    person: Room
){
    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (chat.message?.senderId == user.id) Alignment.End else Alignment.Start
    ) {
        Row {
            if (chat.message?.senderId != user.id) {
                if (true) {
                    Avatar(
                        b64Image = person.avatar,
                        size = 42.dp,
                        modifier = Modifier
                            .align(CenterVertically)
                            .clip(RoundedCornerShape(21.dp))
                    )
                }
                else{
                    SpacerWidth(42.dp)
                }
                SpacerWidth()
            }
            Box(
                modifier = Modifier.background(
                    if (chat.message?.senderId == user.id) Color.Green else Color.LightGray,
                    RoundedCornerShape(20.dp)
                )
            ) {
                if (!viewModel.getStateTranslate()) {
                    Text(
                        text = chat.message?.content ?: String(), style = TextStyle(
                            fontSize = 15.sp,
                            color = Color.Black,
                        ),
                        modifier = Modifier.padding(horizontal = 15.dp, vertical = 8.dp)
                    )
                }
                else{
                    Text(
                        text = chat.translatedContent ?: String(), style = TextStyle(
                            fontSize = 15.sp,
                            color = Color.Black,
                        ),
                        modifier = Modifier.padding(horizontal = 15.dp, vertical = 8.dp)
                    )
                }
            }
        }
        if (true) {
            var time = ""
            time = if (Calendar.getInstance().time.date == chat.createdAt?.date!!) {
                val formatter: SimpleDateFormat = SimpleDateFormat("HH:mm")
                formatter.format(chat.createdAt)
            } else if (Calendar.getInstance().time.date - chat.createdAt.date == 1) {
                val formatter: SimpleDateFormat = SimpleDateFormat("HH:mm")
                "Yesterday at " + formatter.format(chat.createdAt)
            } else {
                val formatter: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm")
                formatter.format(chat.createdAt)
            }
            if (chat.message?.senderId != user.id) {
                Text(
                    text = time, style = TextStyle(
                        fontSize = 12.sp,
                        color = Color.Black,
                    ),
                    textAlign = TextAlign.End,
                    modifier = Modifier.padding(horizontal = 55.dp, vertical = 8.dp)

                )
            }
            else{
                Text(
                    text = time, style = TextStyle(
                        fontSize = 12.sp,
                        color = Color.Black,
                    ),
                    textAlign = TextAlign.End,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)

                )
            }
        }
        else{
            SpacerHeight(5.dp)
        }
    }
}
//--------------------------
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun chatInput(
    roomId: Int,
    modifier: Modifier = Modifier,
    modifierText: Modifier = Modifier,
){
    var message by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    Row(modifier = modifier.fillMaxWidth()) {
        TextField(
            value = message,
            onValueChange = {message = it},
            modifier = modifier.width(220.dp),
            shape = RoundedCornerShape(160.dp),
            placeholder = {
                Text(
                    text = "Write your message",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                )
            },
            trailingIcon = { IconButtonEmoji(modifier = modifierText) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Send
            ),
            keyboardActions = KeyboardActions(
                onSend = {
                    keyboardController?.hide()
                    val content = message.trim()
                    message = ""
                    WebSocketService.getInstance().sendMessage(
                        MessageForm(
                            content = content,
                            roomId = roomId
                        )
                    )
                }
            )
        )
//        SpacerWidth()
        IconButtonRecord(modifier = modifierText)
//        SpacerWidth()
        IconButtonImageSend(modifier = modifierText)
//        SpacerWidth()
        IconButtonCam(modifier = modifierText)
    }
}
@Composable
fun IconButtonEmoji(modifier: Modifier = Modifier) {

    IconButton(onClick = {}) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_emoji_emotions_24),
            contentDescription = "",
            modifier = modifier.size(size = 25.dp),
            tint = Color.Unspecified
        )
    }
}

@Composable
fun IconButtonRecord(modifier: Modifier = Modifier) {

    IconButton(onClick = {}) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_mic_24),
            contentDescription = "",
            modifier = modifier.size(size = 25.dp),
            tint = Color.Unspecified
        )
    }
}

@Composable
fun IconButtonCam(modifier: Modifier = Modifier) {

    IconButton(onClick = {}) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_camera_alt_24),
            contentDescription = "",
            modifier = modifier.size(size = 25.dp),
            tint = Color.Unspecified
        )
    }
}

@Composable
fun IconButtonImageSend(modifier: Modifier = Modifier) {

    IconButton(onClick = {}) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_image_24),
            contentDescription = "",
            modifier = modifier.size(size = 25.dp),
            tint = Color.Unspecified
        )
    }
}
