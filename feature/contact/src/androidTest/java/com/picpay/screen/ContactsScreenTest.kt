package com.picpay.screen

import androidx.activity.ComponentActivity
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import common.ErrorType
import common.OnResult
import model.Contact
import org.junit.Rule
import org.junit.Test
import presentation.contacts.ContactsScreenContent
import theme.AppTheme
import utils.TEST_FLAG_BTN_RELOAD
import utils.TEST_FLAG_CONTACT_IMAGE
import utils.TEST_FLAG_CONTACT_LIST
import utils.TEST_FLAG_ERROR_COMPONENT
import utils.TEST_FLAG_LOADING_SCREEN
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ContactsScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    private fun setContent(state: OnResult<List<Contact>>, onReload: () -> Unit = {}) {
        composeRule.setContent {
            AppTheme {
                ContactsScreenContent(
                    modifier = Modifier,
                    state = state,
                    onReload = onReload
                )
            }
        }
    }


    @Test
    fun showsLoadingState() {
        setContent(OnResult.Loading())
        composeRule.onNodeWithTag(TEST_FLAG_LOADING_SCREEN).assertIsDisplayed()
    }

    @Test
    fun showsErrorAndAllowReload() {
        var reload = false
        setContent(
            OnResult.Error(type = ErrorType.NETWORK, msg = ErrorType.NETWORK.title),
            onReload = { reload = true })

        composeRule.onNodeWithTag(TEST_FLAG_ERROR_COMPONENT).assertIsDisplayed()
        composeRule.onNodeWithTag(TEST_FLAG_BTN_RELOAD).assertIsDisplayed()

        composeRule.onNodeWithTag(TEST_FLAG_BTN_RELOAD).performClick()
        assertTrue(reload)
    }

    @Test
    fun showsErrorAndDoNotAllowReloadButton() {
        var reload = false
        setContent(
            OnResult.Error(type = ErrorType.SERVER, msg = ErrorType.SERVER.title),
            onReload = { reload = true })

        composeRule.onNodeWithTag(TEST_FLAG_ERROR_COMPONENT).assertIsDisplayed()
        composeRule.onNodeWithTag(TEST_FLAG_BTN_RELOAD).assertIsNotDisplayed()
        assertFalse(reload)
    }

    @Test
    fun showsContactsListAndItems() {
        val contacts = listOf(
            Contact(
                id = 1,
                name = "Sandrine Spinka",
                username = "Tod86",
                img = "https://randomuser.me/api/portraits/men/1.jpg"
            ),
            Contact(
                id = 2,
                name = "Carli Carroll",
                username = "onstantin_Sawayn",
                img = "https://randomuser.me/api/portraits/men/2.jpg"
            )
        )

        setContent(OnResult.Success(contacts))

        composeRule.onNodeWithTag(TEST_FLAG_CONTACT_LIST).assertIsDisplayed()
        composeRule.onAllNodesWithTag(TEST_FLAG_CONTACT_IMAGE).assertCountEquals(2)

        composeRule.onNodeWithText(contacts[0].name).assertIsDisplayed()
        composeRule.onNodeWithText("@${contacts[0].username}").assertIsDisplayed()

        composeRule.onNodeWithText(contacts[1].name).assertIsDisplayed()
        composeRule.onNodeWithText("@${contacts[1].username}").assertIsDisplayed()

    }
}