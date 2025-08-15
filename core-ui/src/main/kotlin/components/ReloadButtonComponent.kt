package components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.picpay.desafio.android.core.ui.R
import theme.AppTheme
import utils.TEST_FLAG_BTN_RELOAD

@Composable
fun ReloadButtonComponent(
    onReload: () -> Unit,
    modifier: Modifier = Modifier,
    text: String = stringResource(R.string.reload_button)
) {
    Button(
        onClick = onReload,
        modifier = modifier
            .padding(AppTheme.paddingScheme.small)
            .height(AppTheme.itemSizeScheme.buttonNormalHeight)
            .testTag(TEST_FLAG_BTN_RELOAD),
        colors = ButtonDefaults.buttonColors(containerColor = AppTheme.colorScheme.primaryAccent)
    ) {
        Icon(
            imageVector = Icons.Default.Refresh,
            contentDescription = text,
            modifier = Modifier.size(AppTheme.itemSizeScheme.iconSize)
        )
        TextDefaultComp(
            modifier = Modifier.padding(start = AppTheme.paddingScheme.thin),
            text = text
        )
    }
}