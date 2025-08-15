package components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import theme.AppTheme

@Composable
fun TitleComponent(
    modifier: Modifier = Modifier,
    title: String,
    style: TextStyle = AppTheme.typographyScheme.titleLarge
) {
    Text(
        modifier = modifier,
        text = title,
        style = style
    )
}