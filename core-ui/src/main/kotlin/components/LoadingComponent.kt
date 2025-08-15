package components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp
import theme.AppTheme
import utils.TEST_FLAG_LOADING_SCREEN

@Composable
fun LoadingComponent(
    size: Dp = AppTheme.itemSizeScheme.defaultLoading,
    color: Color = AppTheme.colorScheme.primaryAccent
) {
    CircularProgressIndicator(
        modifier = Modifier.size(size = size),
        color = color
    )
}

@Composable
fun LoadingScreenComp(modifier: Modifier, title: String) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag(TEST_FLAG_LOADING_SCREEN),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SpacingVertLargeComp()

        TitleComponent(
            title = title,
            style = AppTheme.typographyScheme.titleLarge.copy(AppTheme.colorScheme.primaryAccent)
        )

        SpacingVertExtraLargeComp()

        LoadingComponent()

    }
}