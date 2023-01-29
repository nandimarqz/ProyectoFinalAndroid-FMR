package com.fernandomarquezrodriguez.contactplusfmr.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fernandomarquezrodriguez.contactplusfmr.R
import com.fernandomarquezrodriguez.contactplusfmr.databinding.FragmentAddcontactBinding
import com.fernandomarquezrodriguez.contactplusfmr.databinding.FragmentEditBinding
import com.fernandomarquezrodriguez.contactplusfmr.model.bd.Contact
import com.fernandomarquezrodriguez.contactplusfmr.model.bd.ContactDao

class AddContactFragment : Fragment(R.layout.fragment_addcontact) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = arguments?.getString(MainFragment.ACTIVE_USER)!!

        FragmentAddcontactBinding.bind(view).apply {

            btnCrear.setOnClickListener {

                if (nombreContact.text.isNotEmpty()
                    && apellido1Contact.text.isNotEmpty()
                    && apellido2Contact.text.isNotEmpty()
                    && emailContact.text.isNotEmpty()
                    && telefonoContact.text.isNotEmpty()
                    && instagramContact.text.isNotEmpty()
                    && githubContact.text.isNotEmpty()
                    && twitterContact.text.isNotEmpty()
                    && tiktokContact.text.isNotEmpty()){

                    val contact = Contact(nombreContact.text.toString()
                            , apellido1Contact.text.toString()
                            , apellido2Contact.text.toString()
                            , emailContact.text.toString()
                            , telefonoContact.text.toString()
                            , instagramContact.text.toString()
                            , githubContact.text.toString()
                            , twitterContact.text.toString()
                            , tiktokContact.text.toString())

                    ContactDao.addContact(contact,user)

                }else{

                    showError()

                }

                findNavController().navigate(
                    R.id.action_addContactFragment_to_mainFragmnet,
                    bundleOf(MainFragment.ACTIVE_USER to user)
                )

            }

        }
    }

    private fun showError() {

        val alert = AlertDialog.Builder(requireView().context)
        alert.setTitle("Error")
        alert.setMessage("Ha ocurrido un error, todos los campos deben de estar rellenos")
        alert.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = alert.create()
        dialog.show()
    }
}