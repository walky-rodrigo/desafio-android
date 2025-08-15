package theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

data class AppTypography(
    val titleLarge: TextStyle,
    val textDefault: TextStyle,
    val textDetail: TextStyle
)

object CreateTypography {
    fun factory(colorDefault: Color, colorDetail: Color): AppTypography {
        return AppTypography(
            titleLarge = TextStyle(
                color = colorDefault,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            ),
            textDefault = TextStyle(
                color = colorDefault,
                fontSize = 15.sp
            ),
            textDetail = TextStyle(
                color = colorDetail,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}