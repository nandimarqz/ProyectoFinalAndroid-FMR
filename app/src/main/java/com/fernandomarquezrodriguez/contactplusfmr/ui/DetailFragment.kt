package com.fernandomarquezrodriguez.contactplusfmr.ui


import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fernandomarquezrodriguez.contactplusfmr.R
import com.fernandomarquezrodriguez.contactplusfmr.databinding.FragmentDetailBinding
import com.fernandomarquezrodriguez.contactplusfmr.model.bd.Contact
import com.fernandomarquezrodriguez.contactplusfmr.ui.EditFragment.Companion.ACTIVE_CONTACT
import com.google.android.material.navigation.NavigationView


class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val viewModel: DetailViewModel by viewModels {
        val contact = arguments?.getParcelable<Contact>(SELECTED_CONTACT)!!
        DetailViewModel.DetailViewModelFactory(contact!!)
    }
    private var binding: FragmentDetailBinding? = null

    //Crea la constante para recoger del intent a el contacto
    companion object {

        const val SELECTED_CONTACT = "ContactoSeleccionada"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//            val contact = arguments?.getParcelable<Contact>(SELECTED_CONTACT)!!

        FragmentDetailBinding.bind(view).apply {

            (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar3)
//                nombreContacto.text = contact.name
//                apellido1Contacto.text = contact.firstSurname
//                Apellido2Contacto.text = contact.secondSurname
//                emailContacto.text = contact.email
//                instagramContacto.text = contact.instagramUser
//                gitHubContacto.text = contact.gitHubUser
//                twitterContacto.text = contact.twitterUser
//                tiktokContacto.text = contact.tikTokUser
            viewModel.state.observe(viewLifecycleOwner) { state ->

                state.contact?.let { contact ->

                    nombreContacto.text = contact.name
                    apellido1Contacto.text = contact.firstSurname
                    Apellido2Contacto.text = contact.secondSurname
                    emailContacto.text = contact.email
                    instagramContacto.text = contact.instagramUser
                    gitHubContacto.text = contact.gitHubUser
                    twitterContacto.text = contact.twitterUser
                    tiktokContacto.text = contact.tikTokUser

                    toolbar3.setOnMenuItemClickListener {

                        when (it.itemId) {

                            R.id.editContact -> {

                                findNavController().navigate(
                                    R.id.action_detailFragment_to_editFragment,
                                    bundleOf(ACTIVE_CONTACT to contact)
                                )
                                true

                            }
                            else -> false
                        }


                    }
                }


            }

            //TODO funcionalidad de las redes sociales telefono email

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.edit_contact, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

}

