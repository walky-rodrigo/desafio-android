package presentation.contacts

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.picpay.desafio.android.feature.contact.R
import common.OnResult
import common.isNetwork
import components.ErrorComponent
import components.LoadingScreenComp
import components.SpacingVertExtraLargeComp
import components.SpacingVertLargeComp
import components.TitleComponent
import model.Contact
import presentation.components.ContactItem
import theme.AppTheme
import utils.TEST_FLAG_CONTACT_LIST

@Composable
fun ContactsScreen(
    modifier: Modifier,
    viewModel: ContactsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ContactsScreenContent(
        modifier = modifier,
        state = state,
        onReload = {
            viewModel.reloadContacts()
        }
    )

}

@Composable
fun ContactsScreenContent(
    modifier: Modifier,
    state: OnResult<List<Contact>>,
    onReload: () -> Unit
) {
    when (state) {
        is OnResult.Success -> {
            val contacts = state.data
            LazyColumn(modifier = modifier.testTag(TEST_FLAG_CONTACT_LIST)) {

                item {
                    SpacingVertExtraLargeComp()

                    TitleComponent(
                        modifier = Modifier.padding(start = AppTheme.paddingScheme.large),
                        title = stringResource(R.string.contact_title)
                    )
                    SpacingVertLargeComp()
                }

                items(contacts) { contact ->
                    ContactItem(contact = contact)
                }
            }
        }

        is OnResult.Loading -> {
            LoadingScreenComp(modifier, stringResource(R.string.loading_contacts))
        }

        is OnResult.Error -> {
            val error = state
            ErrorComponent(
                modifier = modifier.fillMaxSize(),
                title = error.type.title,
                description = error.msg,
                showReloadOption = error.isNetwork(),
                onReload = onReload
            )
        }
    }
}