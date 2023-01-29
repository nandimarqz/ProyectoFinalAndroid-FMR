package com.fernandomarquezrodriguez.contactplusfmr.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fernandomarquezrodriguez.contactplusfmr.R
import com.fernandomarquezrodriguez.contactplusfmr.databinding.FragmentDetailBinding
import com.fernandomarquezrodriguez.contactplusfmr.model.bd.Contact

    class DetailFragment : Fragment(R.layout.fragment_detail) {

    //Crea la constante para recoger del intent a el contacto
    companion object {

        const val SELECTED_CONTACT = "ContactoSeleccionada"

    }


        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            val contact = arguments?.getParcelable<Contact>(SELECTED_CONTACT)!!

            FragmentDetailBinding.bind(view).apply {

                nombreContacto.text = contact.name
                apellido1Contacto.text = contact.firstSurname
                Apellido2Contacto.text = contact.secondSurname
                emailContacto.text = contact.email
                instagramContacto.text = contact.instagramUser
                gitHubContacto.text = contact.gitHubUser
                twitterContacto.text = contact.twitterUser
                tiktokContacto.text = contact.tikTokUser
            }
        }

}

