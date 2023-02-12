package com.fernandomarquezrodriguez.contactplusfmr.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.fernandomarquezrodriguez.contactplusfmr.R
import com.fernandomarquezrodriguez.contactplusfmr.databinding.FragmentAddcontactBinding
import com.fernandomarquezrodriguez.contactplusfmr.databinding.FragmentEditBinding
import com.fernandomarquezrodriguez.contactplusfmr.model.bd.CloudStorage
import com.fernandomarquezrodriguez.contactplusfmr.model.bd.Contact
import com.fernandomarquezrodriguez.contactplusfmr.model.bd.ContactDao
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class EditFragment : Fragment(R.layout.fragment_edit) {

    companion object {

        const val ACTIVE_CONTACT = "contactoactivo"

    }

    private lateinit var binding: FragmentEditBinding
    var uriImagenLocal: Uri? = null
    lateinit var uriImagen: Uri
    private val viewModel: EditViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding = FragmentEditBinding.bind(view)
        var contact = arguments?.getParcelable<Contact>(ACTIVE_CONTACT)!!
        val user = FirebaseAuth.getInstance().currentUser?.email
        val context = this.requireContext()

        FragmentEditBinding.bind(view).apply {

            nombreContact.setText(contact.name.toCharArray(), 0, contact.name.length)
            apellido1Contact.setText(
                contact.firstSurname.toCharArray(), 0, contact.firstSurname.length
            )
            apellido2Contact.setText(
                contact.secondSurname.toCharArray(), 0, contact.secondSurname.length
            )
            emailContact.setText(contact.email.toCharArray(), 0, contact.email.length)
            telefonoContact.setText(contact.phone.toCharArray(), 0, contact.phone.length)
            instagramContact.setText(
                contact.instagramUser.toCharArray(), 0, contact.instagramUser.length
            )
            githubContact.setText(contact.gitHubUser.toCharArray(), 0, contact.gitHubUser.length)
            twitterContact.setText(contact.twitterUser.toCharArray(), 0, contact.twitterUser.length)
            tiktokContact.setText(contact.tikTokUser.toCharArray(), 0, contact.tikTokUser.length)
            Glide.with(imageView).load(contact.imageRef).into(imageView)

            btnImg.setOnClickListener { requestPermission() }

            btnEditar.setOnClickListener {

                viewModel.viewModelScope.launch {
                    if (nombreContact.text.isNotEmpty() && apellido1Contact.text.isNotEmpty() && apellido2Contact.text.isNotEmpty() && emailContact.text.isNotEmpty() && telefonoContact.text.isNotEmpty() && instagramContact.text.isNotEmpty() && githubContact.text.isNotEmpty() && twitterContact.text.isNotEmpty() && tiktokContact.text.isNotEmpty()) {


                        if (contact.phone != telefonoContact.text.toString()) {

                            ContactDao.deleteContact(contact, user!!)

                            if (uriImagenLocal != null) {

                                if (user != null) {
                                    if (user.isNotEmpty())

                                        uriImagen =
                                            CloudStorage.uploadFile(user, uriImagenLocal!!, context)!!
                                    contact = Contact(
                                        nombreContact.text.toString(),
                                        apellido1Contact.text.toString(),
                                        apellido2Contact.text.toString(),
                                        emailContact.text.toString(),
                                        telefonoContact.text.toString(),
                                        instagramContact.text.toString(),
                                        githubContact.text.toString(),
                                        twitterContact.text.toString(),
                                        tiktokContact.text.toString(),
                                        uriImagen.toString()
                                    )
                                }

                            } else {

                                contact = Contact(
                                    nombreContact.text.toString(),
                                    apellido1Contact.text.toString(),
                                    apellido2Contact.text.toString(),
                                    emailContact.text.toString(),
                                    telefonoContact.text.toString(),
                                    instagramContact.text.toString(),
                                    githubContact.text.toString(),
                                    twitterContact.text.toString(),
                                    tiktokContact.text.toString(),
                                    contact.imageRef
                                )

                            }

                            ContactDao.addContact(contact, user)

                        } else {

                            if (uriImagenLocal != null) {

                                if (user != null) {
                                    if (user.isNotEmpty())

                                        uriImagen =
                                            CloudStorage.uploadFile(user, uriImagenLocal!!, context)!!
                                    contact = Contact(
                                        nombreContact.text.toString(),
                                        apellido1Contact.text.toString(),
                                        apellido2Contact.text.toString(),
                                        emailContact.text.toString(),
                                        telefonoContact.text.toString(),
                                        instagramContact.text.toString(),
                                        githubContact.text.toString(),
                                        twitterContact.text.toString(),
                                        tiktokContact.text.toString(),
                                        uriImagen.toString()
                                    )
                                }

                            } else {

                                contact = Contact(
                                    nombreContact.text.toString(),
                                    apellido1Contact.text.toString(),
                                    apellido2Contact.text.toString(),
                                    emailContact.text.toString(),
                                    telefonoContact.text.toString(),
                                    instagramContact.text.toString(),
                                    githubContact.text.toString(),
                                    twitterContact.text.toString(),
                                    tiktokContact.text.toString(),
                                    contact.imageRef
                                )

                            }

                            ContactDao.updateContact(contact, user!!)
                        }
                    }

                    findNavController().navigate(
                        R.id.action_editFragment_to_mainFragment,
                        bundleOf(MainFragment.ACTIVE_USER to user)
                    )

                }
            }
        }

        //TODO Realizar logica boton editar añadir boton de borrado al menu
    }

    private fun requestPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            when {

                ContextCompat.checkSelfPermission(
                    this.requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED -> {

                    pickPhotoFromGallery()

                }
                else -> requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }

        } else {

            pickPhotoFromGallery()

        }

    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->

        if (isGranted) {
            pickPhotoFromGallery()
        } else {

            Toast.makeText(
                this.requireContext(), "Necesitas aceptar los permisos ", Toast.LENGTH_LONG
            ).show()

        }
    }

    private val startForActivityGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->

        if (result.resultCode == Activity.RESULT_OK) {

            val data = result.data?.data
            if (data != null) {
                uriImagenLocal = data
            }
            Glide.with(binding.imageView).load(data).into(binding.imageView)
        }
    }

    private fun pickPhotoFromGallery() {

        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startForActivityGallery.launch(intent)
    }
}