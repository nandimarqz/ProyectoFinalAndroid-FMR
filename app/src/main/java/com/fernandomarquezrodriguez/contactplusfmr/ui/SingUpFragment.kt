package com.fernandomarquezrodriguez.contactplusfmr.ui

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fernandomarquezrodriguez.contactplusfmr.R
import com.fernandomarquezrodriguez.contactplusfmr.databinding.FragmentSingupBinding
import com.fernandomarquezrodriguez.contactplusfmr.model.bd.User
import com.fernandomarquezrodriguez.contactplusfmr.model.bd.UserDao
import com.google.firebase.auth.FirebaseAuth


class SingUpFragment : Fragment(R.layout.fragment_singup) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FragmentSingupBinding.bind(view).apply {

            btnRegistarse.setOnClickListener {

                if (emailUsr.text.isNotEmpty() && inputContrasenaReg.text.isNotEmpty()) {

                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                        emailUsr.text.toString(),
                        inputContrasenaReg.text.toString()
                    )
                        .addOnCompleteListener {
                            if (it.isSuccessful) {

                                val user = User(
                                    nombreUsr.text.toString(),
                                    apellido1Usr.text.toString(),
                                    apellido2Usr.text.toString(),
                                    usuario.text.toString(),
                                    inputContrasenaReg.text.toString(),
                                    emailUsr.text.toString(),
                                    telefonoUsr.text.toString(),
                                    instagramUsr.text.toString(),
                                    githubUsr.text.toString(),
                                    twitterUsr.text.toString(),
                                    tiktokUsr.text.toString()
                                )

                                UserDao.addUser(user)

                                findNavController().navigate(
                                    R.id.action_signUpFragment_to_mainFragmnet,
                                    bundleOf(MainFragment.ACTIVE_USER to emailUsr.text.toString())
                                )
                            } else {

                                showError()
                                Log.d(UserDao.COLLECTION_USER, it.exception.toString())

                            }



                        }
                }

            }

        }


    }

    private fun showError() {

        val alert = AlertDialog.Builder(requireView().context)
        alert.setTitle("Error")
        alert.setMessage("Ha ocurrido un error registrando al usuario")
        alert.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = alert.create()
        dialog.show()
    }
}