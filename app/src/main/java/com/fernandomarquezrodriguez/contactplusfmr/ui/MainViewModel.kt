package com.fernandomarquezrodriguez.trabajo1trimestre.ui.main

import androidx.lifecycle.*
import com.fernandomarquezrodriguez.contactplusfmr.model.bd.Contact
import com.fernandomarquezrodriguez.contactplusfmr.model.bd.ContactDao
import com.fernandomarquezrodriguez.contactplusfmr.model.bd.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class MainViewModel(activeUser : String) : ViewModel() {



    val _state = MutableLiveData(UiState())
    val state: LiveData<UiState> get() = _state

    //Cuando se inicie realizara lo siguiente
    init {

        //La ejecucion se va realizar por el hilo principal
        viewModelScope.launch(Dispatchers.Main) {

            val contacts =  ContactDao.getAllContacts(activeUser)

            //Se iguala los contactos
            _state.value = _state.value?.copy(contacts = contacts, activeUser = activeUser)
        }
    }

    //Realiza la navegacion con una receta pasada por parametro
    fun navigateTo(contact: Contact) {
        _state.value = _state.value?.copy(navigateTo = contact)
    }

    //Finaliza la navegacion
    fun onNavigateDone() {
        _state.value = _state.value?.copy(navigateTo = null)
    }
    //Clase que contiene las variables que se usan
    data class UiState(

        val activeUser: String?=null,
        val contacts: List<Contact>? = null,
        val navigateTo: Contact? = null
    )

    //La factoria del mainViewModel
    class MainViewModelFactory(private val activeUser: String) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(activeUser) as T
        }

    }
}
