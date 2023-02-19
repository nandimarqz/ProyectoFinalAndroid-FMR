package com.fernandomarquezrodriguez.contactplusfmr.ui

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.fernandomarquezrodriguez.contactplusfmr.R
import com.fernandomarquezrodriguez.contactplusfmr.databinding.FragmentMainBinding
import com.fernandomarquezrodriguez.contactplusfmr.model.bd.User
import com.fernandomarquezrodriguez.contactplusfmr.ui.DetailFragment.Companion.SELECTED_CONTACT
import com.fernandomarquezrodriguez.trabajo1trimestre.ui.main.MainViewModel
import com.fernandomarquezrodriguez.trabajo1trimestre.ui.main.MainViewModel.MainViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class MainFragment : Fragment(R.layout.fragment_main) {
    private val viewModel: MainViewModel by viewModels{
        val user = arguments?.getString(ACTIVE_USER)
        MainViewModelFactory(user!!)
    }
    private val adapter = ContactAdapter(){ contact-> viewModel.navigateTo(contact)}

    companion object{

        const val ACTIVE_USER = "usuarioactivo"

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentMainBinding.bind(view).apply {

            recycler.adapter = adapter

        }

        viewModel.state.observe(viewLifecycleOwner){ state ->

           lifecycleScope.launch {
               repeatOnLifecycle(Lifecycle.State.STARTED){

                   state.contacts?.collect(){

                       adapter.contacts = it
                       adapter.originalContacts = it
                       adapter.notifyDataSetChanged()
                   }

               }
           }

            state.navigateTo?.let {

                findNavController().navigate(
                    R.id.action_mainFragment_to_detailFragmnet,
                    bundleOf(SELECTED_CONTACT to it)
                )

                viewModel.onNavigateDone()

            }

            //Pone un escuchador de eventos para cuando se haga click en el buscador y busque
            binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{

                //Cuando se envia el texto
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    if (p0 != null) {
                        adapter.filtrado(p0)
                    }
                    return false
                }

                //Cuando el texto esta cambiando
                override fun onQueryTextChange(p0: String?): Boolean {
                    if (p0 != null) {
                        adapter.filtrado(p0)
                    }
                    return false
                }

            })

            binding.addButton.setOnClickListener{

                findNavController().navigate(
                    R.id.action_mainFragment_to_addContactFragmnet,
                    bundleOf(ACTIVE_USER to state.activeUser)
                )

            }
        }
    }

}