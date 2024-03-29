package com.example.chatchit.screen

//import com.example.chatchit.component.IconComponentImageVector
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.chatchit.R
import com.example.chatchit.component.SpacerWidth
import com.example.chatchit.navigation.Login
import com.example.chatchit.ui.theme.Aqua

@Composable
@Preview
fun StartScreen(
    navHostController: NavHostController
){
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)){
        Image(
            painter = painterResource(id = R.drawable.splash),
            contentDescription = "",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillHeight,
        )
//            Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 40.dp)) {
//                Text(text = stringResource(R.string.stay_with_your_friends),
//                        style = TextStyle(
//                            color = Color.White,
//                            fontSize = 36.sp,
//                            fontWeight = FontWeight.Bold
//                        )
//                    )
////                SpacerHeight(15.dp)
//                CustomCheckBox()
//            }
//        }
        Button(
            onClick = {navHostController.navigate(Login)},
            modifier = Modifier
                .padding(20.dp)
                .align(Alignment.BottomCenter)
                .height(60.dp)
                .width(300.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF24786D),
                contentColor = Color.White
            ),
//            shape = RoundedCornerShape(25)
        ) {
            Text(text = "Get started",
                color = Color.White,)
        }
    }
}

@Composable
fun CustomCheckBox() {

    Row(
        modifier = Modifier.padding(vertical = 15.dp)
    ) {
        Box(
            modifier = Modifier
                .background(
                    Aqua, RoundedCornerShape(
                        topStart = 10.dp, topEnd = 10.dp, bottomStart = 80.dp, bottomEnd = 80.dp
                    )
                )
                .size(24.dp), contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Check,
                contentDescription = "",
                modifier = Modifier.size(15.dp),
                tint = Color.Black
            )
        }
        SpacerWidth(15.dp)
        Text(
            text = stringResource(R.string.secure_private_messaging), style = TextStyle(
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }

}