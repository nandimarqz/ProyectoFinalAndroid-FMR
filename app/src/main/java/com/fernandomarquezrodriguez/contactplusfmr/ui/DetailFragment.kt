package com.fernandomarquezrodriguez.contactplusfmr.ui


import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.fernandomarquezrodriguez.contactplusfmr.R
import com.fernandomarquezrodriguez.contactplusfmr.databinding.FragmentDetailBinding
import com.fernandomarquezrodriguez.contactplusfmr.model.bd.Contact
import com.fernandomarquezrodriguez.contactplusfmr.model.bd.ContactDao
import com.fernandomarquezrodriguez.contactplusfmr.ui.EditFragment.Companion.ACTIVE_CONTACT
import com.fernandomarquezrodriguez.contactplusfmr.ui.MainFragment.Companion.ACTIVE_USER
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth


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
        val user = FirebaseAuth.getInstance().currentUser?.email
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

                    if(contact.imageRef.isNotEmpty() && !contact.imageRef.equals("")){

                        Glide.with(imgContacto).load(contact.imageRef).into(imgContacto)

                    }

                    toolbar3.setOnMenuItemClickListener {

                        when (it.itemId) {

                            R.id.editContact -> {

                                findNavController().navigate(
                                    R.id.action_detailFragment_to_editFragment,
                                    bundleOf(ACTIVE_CONTACT to contact)
                                )
                                true

                            }
                            R.id.deleteContact->{

                                ContactDao.deleteContact(contact, user!!)
                                findNavController().navigate(
                                    R.id.action_detailFragment_to_mainFragmnet,
                                    bundleOf(ACTIVE_USER to user)
                                )
                                true
                            }
                            else -> false

                        }


                    }

                    instagramContacto.setOnClickListener {

                        val uri = Uri.parse("https://instagram.com/${contact.instagramUser}")
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        intent.setPackage("com.instagram.android")

                        try {
                            startActivity(intent)
                        }catch (e: ActivityNotFoundException){

                            startActivity(Intent(Intent.ACTION_VIEW,Uri.parse("https://instagram.com/${contact.instagramUser}")))

                        }

                    }

                    emailContacto.setOnClickListener {

                        val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"+contact.email))

                        startActivity(intent)

                    }

                    gitHubContacto.setOnClickListener {

                        val uri = Uri.parse("https://github.com/${contact.gitHubUser}")
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        intent.setPackage("com.github.android")

                        try {
                            startActivity(intent)
                        }catch (e: ActivityNotFoundException){

                            startActivity(Intent(Intent.ACTION_VIEW,Uri.parse("https://github.com/${contact.gitHubUser}")))

                        }

                    }

                    tiktokContacto.setOnClickListener {

                        val uri = Uri.parse("https://tiktok.com/@${contact.tikTokUser}")
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        intent.setPackage("com.tiktok.android")

                        try {
                            startActivity(intent)
                        }catch (e: ActivityNotFoundException){

                            startActivity(Intent(Intent.ACTION_VIEW,Uri.parse("https://tiktok.com/@${contact.tikTokUser}")))

                        }

                    }

                    twitterContacto.setOnClickListener {

                        val uri = Uri.parse("https://twitter.com/${contact.twitterUser}")
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        intent.setPackage("com.twitter.android")

                        try {
                            startActivity(intent)
                        }catch (e: ActivityNotFoundException){

                            startActivity(Intent(Intent.ACTION_VIEW,Uri.parse("https://twitter.com/${contact.twitterUser}")))

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

