package components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import theme.AppTheme

@Composable
fun TextComponent(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        modifier = modifier,
        text = text,
        style = style,
        textAlign = textAlign
    )
}

@Composable
fun TextDefaultComp(modifier: Modifier = Modifier, text: String, textAlign: TextAlign = TextAlign.Start) {
    TextComponent(
        modifier = modifier,
        text = text,
        style = AppTheme.typographyScheme.textDefault,
        textAlign = textAlign
    )
}

@Composable
fun TextDetailComp(modifier: Modifier = Modifier, text: String, textAlign: TextAlign = TextAlign.Start) {
    TextComponent(
        modifier = modifier,
        text = text,
        style = AppTheme.typographyScheme.textDetail,
        textAlign = textAlign
    )
}