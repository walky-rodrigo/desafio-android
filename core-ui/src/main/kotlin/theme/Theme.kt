package theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

private val darkColors = AppColor(
    primary = ColorPrimaryDark,
    primaryAccent = ColorAccentDark,
    primaryText = ColorTextDark,
    primaryDetail = ColorDetailDark
)

private val lightColors = AppColor(
    primary = ColorPrimaryLight,
    primaryAccent = ColorAccentLight,
    primaryText = ColorTextLight,
    primaryDetail = ColorDetailLight
)

private val appItemSize = CreatingItemSize.factory()

val appPadding = CreatingPadding.factory()

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colors = if (darkTheme) darkColors else lightColors
    val appTypography = CreateTypography.factory(
        colorDefault = colors.primaryText,
        colorDetail = colors.primaryDetail
    )

    CompositionLocalProvider(
        LocalColorScheme provides colors,
        LocalPaddingScheme provides appPadding,
        LocalAppTypography provides appTypography,
        LocalItemSizeScheme provides appItemSize,
        content = content
    )
}

object AppTheme {

    val colorScheme: AppColor
        @Composable get() = LocalColorScheme.current

    val paddingScheme: AppPadding
        @Composable get() = LocalPaddingScheme.current

    val typographyScheme: AppTypography
        @Composable get() = LocalAppTypography.current

    val itemSizeScheme: AppItemSize
        @Composable get() = LocalItemSizeScheme.current
}