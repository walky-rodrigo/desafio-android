package theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class AppItemSize(
    val defaultLoading: Dp,
    val defaultItemLine: Dp,
    val iconSize: Dp,
    val buttonNormalHeight: Dp
)

object CreatingItemSize {
    fun factory() = AppItemSize(
        defaultLoading = 48.dp,
        defaultItemLine = 52.dp,
        iconSize = 20.dp,
        buttonNormalHeight = 48.dp
    )
}