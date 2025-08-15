package components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import theme.AppTheme

@Composable
fun SpacingVerticalComponent(size: Dp) {
    Spacer(modifier = Modifier.height(size).width(1.dp))
}

@Composable
fun SpacingVertExtraLargeComp() {
    SpacingVerticalComponent(AppTheme.paddingScheme.extraLarge)
}

@Composable
fun SpacingVertLargeComp() {
    SpacingVerticalComponent(AppTheme.paddingScheme.large)
}

@Composable
fun SpacingVertThinComp() {
    SpacingVerticalComponent(AppTheme.paddingScheme.thin)
}

@Composable
fun SpacingVertSmallComp() {
    SpacingVerticalComponent(AppTheme.paddingScheme.small)
}