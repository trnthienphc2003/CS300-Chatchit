import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.chatchit.R

@Composable
fun Avatar(
    b64Image: String?,
    modifier: Modifier = Modifier,
    size: Dp,
    tint:Color = Color.Unspecified
) {
    if (b64Image == null) {
//        Icon(
//            painter = painterResource(id = R.drawable.person_2),
//            contentDescription = "",
//            modifier = modifier.size(size = size),
//            tint = tint
//
        Box(
            modifier = Modifier.size(size).clip(shape = CircleShape).background(Color.Gray)
        )
    } else {
        val imageBytes = Base64.decode(b64Image, Base64.DEFAULT)
        val image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        Icon(
            bitmap = image.asImageBitmap(),
            contentDescription = "",
            modifier = modifier.size(size = size),
            tint = tint
        )
    }
}

@Composable
fun testCircle() {
    Box(
        modifier = Modifier.size(100.dp).clip(shape = CircleShape).background(Color.Gray)
    )
}

@Composable
@Preview(showBackground = true)
fun testCirclePreview() {
    testCircle()
}