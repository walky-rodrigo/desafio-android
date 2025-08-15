package components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import theme.AppTheme
import utils.TEST_FLAG_ERROR_COMPONENT

@Composable
fun ErrorComponent(
    modifier: Modifier,
    title: String,
    description: String,
    showReloadOption: Boolean = false,
    onReload: () -> Unit = {}
) {

    Column(modifier = modifier.testTag(TEST_FLAG_ERROR_COMPONENT), horizontalAlignment = Alignment.CenterHorizontally) {

        SpacingVertExtraLargeComp()

        TitleComponent(
            modifier = Modifier.padding(all = AppTheme.paddingScheme.extraLarge),
            title = title
        )

        TextDefaultComp(
            modifier = Modifier.padding(horizontal = AppTheme.paddingScheme.extraLarge),
            text = description,
            textAlign = TextAlign.Center
        )

        if (showReloadOption) {
            SpacingVertSmallComp()
            ReloadButtonComponent(onReload = onReload)
        }
    }
}