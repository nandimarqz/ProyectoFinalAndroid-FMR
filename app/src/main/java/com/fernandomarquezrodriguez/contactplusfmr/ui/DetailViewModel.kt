package com.fernandomarquezrodriguez.contactplusfmr.ui

import androidx.lifecycle.*
import com.fernandomarquezrodriguez.contactplusfmr.model.bd.Contact
import com.fernandomarquezrodriguez.contactplusfmr.model.bd.ContactDao
import com.fernandomarquezrodriguez.trabajo1trimestre.ui.main.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(contact: Contact): ViewModel() {

    private val _state = MutableLiveData(ContactUiState())
    val state : LiveData<ContactUiState> get() = _state

    init {

        //La ejecucion se va realizar por el hilo principal
        viewModelScope.launch(Dispatchers.Main) {
            //Se iguala los contactos
            _state.value = _state.value?.copy(contact = contact)
        }
    }


    //Clase que contiene las variables que se usan
    data class ContactUiState(

        val contact: Contact? = null

    )

    //La factoria del mainViewModel
    class DetailViewModelFactory(private val contact: Contact) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DetailViewModel(contact) as T
        }

    }
}

