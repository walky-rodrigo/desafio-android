package theme

import androidx.compose.ui.graphics.Color

data class AppColor(
    val primary: Color,
    val primaryAccent: Color,
    val primaryText: Color,
    val primaryDetail: Color
)

val ColorPrimaryDark = Color(0xFF1D1E20)
val ColorAccentDark = Color(0xFF11C76F)
val ColorDetailDark = Color(0x80FFFFFF)
val ColorTextDark = Color(0xFFFFFFFF)


val ColorPrimaryLight = Color(0xFFFFFFFF)
val ColorAccentLight = Color(0xFF11C76F)
val ColorDetailLight = Color(0xFF1D1E20)
val ColorTextLight = Color(0xFF000000)