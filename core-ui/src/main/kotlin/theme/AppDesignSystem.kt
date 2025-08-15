package theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

val LocalPaddingScheme = staticCompositionLocalOf {
    AppPadding(
        thin = Dp.Unspecified,
        small = Dp.Unspecified,
        normal = Dp.Unspecified,
        medium = Dp.Unspecified,
        large = Dp.Unspecified,
        extraLarge = Dp.Unspecified
    )
}

val LocalColorScheme = staticCompositionLocalOf {
    AppColor(
        primary = Color.Unspecified,
        primaryAccent = Color.Unspecified,
        primaryText = Color.Unspecified,
        primaryDetail = Color.Unspecified
    )
}

val LocalItemSizeScheme = staticCompositionLocalOf {
    AppItemSize(
        defaultLoading = Dp.Unspecified,
        defaultItemLine = Dp.Unspecified,
        iconSize = Dp.Unspecified,
        buttonNormalHeight = Dp.Unspecified
    )
}

val LocalAppTypography = staticCompositionLocalOf {
    AppTypography(
        titleLarge = TextStyle.Default,
        textDefault = TextStyle.Default,
        textDetail = TextStyle.Default
    )
}