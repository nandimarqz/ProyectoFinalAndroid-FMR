package com.fernandomarquezrodriguez.contactplusfmr.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.fernandomarquezrodriguez.contactplusfmr.R
import com.fernandomarquezrodriguez.contactplusfmr.databinding.FragmentEditBinding
import com.fernandomarquezrodriguez.contactplusfmr.model.bd.Contact

class EditFragment  : Fragment(R.layout.fragment_edit) {

    companion object{

        const val ACTIVE_CONTACT = "contactoactivo"

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val contact = arguments?.getParcelable<Contact>(ACTIVE_CONTACT)!!
        FragmentEditBinding.bind(view).apply {

            nombreContact.setText(contact.name.toCharArray(),0,contact.name.length)
            apellido1Contact.setText(contact.firstSurname.toCharArray(),0,contact.firstSurname.length)
            apellido2Contact.setText(contact.secondSurname.toCharArray(),0,contact.secondSurname.length)
            emailContact.setText(contact.email.toCharArray(),0,contact.email.length)
            telefonoContact.setText(contact.phone.toCharArray(),0,contact.phone.length)
            instagramContact.setText(contact.instagramUser.toCharArray(),0,contact.instagramUser.length)
            githubContact.setText(contact.gitHubUser.toCharArray(),0,contact.gitHubUser.length)
            twitterContact.setText(contact.twitterUser.toCharArray(),0,contact.twitterUser.length)
            tiktokContact.setText(contact.tikTokUser.toCharArray(),0,contact.tikTokUser.length)

        }
    }
}