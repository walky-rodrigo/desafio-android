package viewModel

import app.cash.turbine.test
import common.ErrorType
import common.OnResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import model.Contact
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import presentation.contacts.ContactsViewModel
import use_case.GetContactUseCase
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class ContactsViewModelTest {

    private lateinit var getContactsUseCase: GetContactUseCase
    private lateinit var viewModel: ContactsViewModel

    private val contact = Contact(id = 1, name = "John", username = "JohnUsername", img = "img")

    @Before
    fun setup() {
        getContactsUseCase = mock()
    }

    @Test
    fun `initial state is Loading then Success`() = runTest {
        whenever(getContactsUseCase.fetchContact())
            .thenReturn(OnResult.Success(data = listOf(contact)))

        viewModel = ContactsViewModel(getContactsUseCase)

        assertTrue(viewModel.state.value is OnResult.Loading)

        viewModel.state.test {
            assertTrue(awaitItem() is OnResult.Success)
            cancelAndIgnoreRemainingEvents()
        }
        verify(getContactsUseCase, times(1)).fetchContact()
    }

    @Test
    fun `initial state Loading then Error`() = runTest {
        whenever(getContactsUseCase.fetchContact())
            .thenReturn(OnResult.Error(type = ErrorType.SERVER, "Server Error"))

        viewModel = ContactsViewModel(getContactsUseCase)

        assertTrue(viewModel.state.value is OnResult.Loading)

        viewModel.state.test {
            assertTrue(awaitItem() is OnResult.Error)
            cancelAndIgnoreRemainingEvents()
        }
        verify(getContactsUseCase, times(1)).fetchContact()
    }

    @Test
    fun `reloadContacts triggers another fetch`() = runTest {
        whenever(getContactsUseCase.fetchContact())
            .thenReturn(OnResult.Success(data = listOf(contact)))

        viewModel = ContactsViewModel(getContactsUseCase)

        assertTrue(viewModel.state.value is OnResult.Loading)

        viewModel.state.test {
            assertTrue(awaitItem() is OnResult.Success)

            viewModel.reloadContacts()

            assertTrue(awaitItem() is OnResult.Loading)
            assertTrue(awaitItem() is OnResult.Success)

            cancelAndIgnoreRemainingEvents()
        }

        verify(getContactsUseCase, times(2)).fetchContact()
    }


}