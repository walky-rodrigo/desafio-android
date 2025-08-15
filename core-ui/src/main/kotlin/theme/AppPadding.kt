package theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class AppPadding(
    val thin: Dp,
    val small: Dp,
    val normal: Dp,
    val medium: Dp,
    val large: Dp,
    val extraLarge: Dp
)

object CreatingPadding {
    fun factory() = AppPadding(
        thin = 4.dp,
        small = 8.dp,
        normal = 12.dp,
        medium = 16.dp,
        large = 24.dp,
        extraLarge = 48.dp
    )
}

