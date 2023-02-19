package com.fernandomarquezrodriguez.contactplusfmr.ui

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
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
import com.fernandomarquezrodriguez.contactplusfmr.model.bd.CloudStorage
import com.fernandomarquezrodriguez.contactplusfmr.model.bd.Contact
import com.fernandomarquezrodriguez.contactplusfmr.model.bd.ContactDao
import kotlinx.coroutines.launch
import java.io.File

class AddContactFragment : Fragment(R.layout.fragment_addcontact) {

    private lateinit var binding: FragmentAddcontactBinding
    var uriImagenLocal: Uri? = null
    var uriImagen: Uri? = null
    private val viewModel: AddContactViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = arguments?.getString(MainFragment.ACTIVE_USER)!!
        binding = FragmentAddcontactBinding.bind(view)


        val context = this.requireContext()

        FragmentAddcontactBinding.bind(view).apply {

            btnImg.setOnClickListener { requestPermission() }

            btnCrear.setOnClickListener {


                viewModel.viewModelScope.launch {
                    if (nombreContact.text.isNotEmpty()
                        && apellido1Contact.text.isNotEmpty()
                        && apellido2Contact.text.isNotEmpty()
                        && emailContact.text.isNotEmpty()
                        && telefonoContact.text.isNotEmpty()
                        && instagramContact.text.isNotEmpty()
                        && githubContact.text.isNotEmpty()
                        && twitterContact.text.isNotEmpty()
                        && tiktokContact.text.isNotEmpty()
                    ) {
                        val contact: Contact
                        if (uriImagenLocal != null) {


                            uriImagen = CloudStorage.uploadFile(user, uriImagenLocal!!, context)!!
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
                                ""
                            )

                        }



                        ContactDao.addContact(contact, user)

                    } else {

                        showError()

                    }

                findNavController().navigate(
                    R.id.action_addContactFragment_to_mainFragmnet,
                    bundleOf(MainFragment.ACTIVE_USER to user)
                )
                }
            }

        }

    }


    private fun requestPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            when {

                ContextCompat.checkSelfPermission(
                    this.requireContext(),
                    READ_EXTERNAL_STORAGE
                ) == PERMISSION_GRANTED -> {

                    pickPhotoFromGallery()

                }
                else -> requestPermissionLauncher.launch(READ_EXTERNAL_STORAGE)
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
                this.requireContext(),
                "Necesitas aceptar los permisos ",
                Toast.LENGTH_LONG
            ).show()

        }
    }

    private val startForActivityGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->

        if (result.resultCode == RESULT_OK) {

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

    private fun showError() {

        val alert = AlertDialog.Builder(requireView().context)
        alert.setTitle("Error")
        alert.setMessage("Ha ocurrido un error, todos los campos deben de estar rellenos")
        alert.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = alert.create()
        dialog.show()
    }
}