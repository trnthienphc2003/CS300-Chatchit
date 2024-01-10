package com.example.chatchit.screen

import Avatar
import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.chatchit.component.SpacerHeight
import com.example.chatchit.component.SpacerWidth
import com.example.chatchit.models.User
import com.example.chatchit.services.APIService
import com.example.chatchit.services.api.RoomAPI
import com.example.chatchit.services.api.form.UserIdField
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import retrofit2.await

class MangeViewModel : ViewModel() {
    private val manageList = mutableStateOf<List<User>>(emptyList())

    fun getManageList(): State<List<User>> = manageList

    fun setManageList(list: List<User>) {
        manageList.value = list
    }
    fun addManage(member: User) {
        manageList.value = listOf(member) + manageList.value
    }


    fun init(roomID: Int, context:Context){
        MainScope().launch {
            try {
                val memberService: RoomAPI =
                    APIService.getApiClient(context).create(RoomAPI::class.java)
                val memberAPIResponse =
                    memberService.listMember(roomID).await()
                val json = Gson().toJson(memberAPIResponse.data)
                val itemType = object : TypeToken<List<User>>() {}.type
                val listmember1 = Gson().fromJson<List<User>>(json, itemType)
                setManageList(listmember1)
            } catch (e: Exception) {
                Log.e("manageMember", e.toString())
            }
        }
    }
}
@Composable
fun ManageMemberScreen(
    navHostController: NavHostController,
    context: Context
){

    val roomID = navHostController.previousBackStackEntry?.savedStateHandle?.get<Int>("roomID") ?: -1
    val listMember = MangeViewModel()

    listMember.init(roomID, context)
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp, horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Members",
            style = TextStyle(
                color = Color.Black,
                fontSize = 20.sp,
            ),
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
//                modifier = modifier.align(Alignment.CenterHorizontally),

            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Center
        )
        SpacerHeight()
        LazyColumn(modifier = Modifier.padding(top = 30.dp, bottom = 15.dp) ) {
            items(listMember.getManageList().value, key = { it.id ?: Int }) {
                MemberEachRow(member = it) {
                    MainScope().launch {
                        try {
                            val removeService: RoomAPI =
                                APIService.getApiClient(context).create(RoomAPI::class.java)
                            val removeAPIResponse = removeService.deleteMember(roomId = roomID, UserIdField(it.id?:-1))
                            listMember.init(roomID, context)
                        } catch (e: Exception) {
                            Log.e("remove Member", e.toString())
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun MemberEachRow(
    modifier: Modifier = Modifier,
    member: User,
    onClick: () -> Unit
) {
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
//                    IconComponentDrawable(icon = member.avatar, size = 60.dp)
                    Avatar(b64Image = member.avatar, size = 60.dp)
                    SpacerWidth()
                    Text(
                        text = member.name?:String(), style = TextStyle(
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.align(
                            alignment = Alignment.CenterVertically
                        )
                    )

                }
            }
            SpacerHeight(15.dp)
//            Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp,color = Gray)
        }
    }
}
