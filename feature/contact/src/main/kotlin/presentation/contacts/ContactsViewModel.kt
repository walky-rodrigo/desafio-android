package presentation.contacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import common.OnResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import model.Contact
import use_case.GetContactUseCase
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(private val getContactUseCase: GetContactUseCase) :
    ViewModel() {

    private val _state = MutableStateFlow<OnResult<List<Contact>>>(OnResult.Loading())
    val state = _state.asStateFlow()

    init {
        fetchContacts()
    }

    private fun fetchContacts() {
        _state.value = OnResult.Loading()
        viewModelScope.launch {
            _state.value = getContactUseCase.fetchContact()
        }
    }

    fun reloadContacts() {
        fetchContacts()
    }

}