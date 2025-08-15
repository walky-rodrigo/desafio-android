package presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.testTag
import coil3.compose.AsyncImage
import components.SpacingVertThinComp
import components.TextDefaultComp
import components.TextDetailComp
import model.Contact
import theme.AppTheme
import utils.TEST_FLAG_CONTACT_IMAGE


@Composable
fun ContactItem(modifier: Modifier = Modifier, contact: Contact) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .wrapContentSize()
                .padding(
                    horizontal = AppTheme.paddingScheme.large,
                    vertical = AppTheme.paddingScheme.large
                ),
            contentAlignment = Alignment.Center
        ) {

            AsyncImage(
                modifier = Modifier.testTag(TEST_FLAG_CONTACT_IMAGE)
                    .size(size = AppTheme.itemSizeScheme.defaultItemLine)
                    .clip(CircleShape),
                model = if (LocalInspectionMode.current) Icons.Default.Refresh else contact.img,
                contentDescription = contact.name
            )
        }

        Column(modifier = Modifier.wrapContentSize()) {

            TextDefaultComp(text = "@${contact.username}")

            SpacingVertThinComp()

            TextDetailComp(text = contact.name)

        }
    }
}