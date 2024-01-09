import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp

@Composable
fun Avatar(
    b64Image: String?,
    modifier: Modifier = Modifier,
    size: Dp,
    tint:Color = Color.Unspecified
) {
    val imageBytes = Base64.decode(b64Image, Base64.DEFAULT)
    val image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    Icon(
        bitmap = image.asImageBitmap(),
        contentDescription = "",
        modifier = modifier.size(size = size),
        tint = tint
    )
}