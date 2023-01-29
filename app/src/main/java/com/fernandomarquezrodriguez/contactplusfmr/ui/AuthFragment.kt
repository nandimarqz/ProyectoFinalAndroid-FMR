package com.fernandomarquezrodriguez.contactplusfmr.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.fernandomarquezrodriguez.contactplusfmr.R
import com.fernandomarquezrodriguez.contactplusfmr.databinding.FragmentAuthBinding
import com.fernandomarquezrodriguez.contactplusfmr.model.bd.User
import com.fernandomarquezrodriguez.contactplusfmr.model.bd.UserDao
import com.fernandomarquezrodriguez.contactplusfmr.model.bd.UserDao.getUser
import com.fernandomarquezrodriguez.trabajo1trimestre.ui.main.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthFragment : Fragment(R.layout.fragment_auth) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentAuthBinding.bind(view)


        binding.btnRegis.setOnClickListener {

            findNavController().navigate(
                R.id.action_authFragment_to_singupFragmnet
            )

        }

        binding.btnInicio.setOnClickListener {

            if (binding.inputUsr.text.isNotEmpty() && binding.inputContrasena.text.isNotEmpty()) {

                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    binding.inputUsr.text.toString(),
                    binding.inputContrasena.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {

                            findNavController().navigate(
                                R.id.action_authFragment_to_mainFragmnet,
                                bundleOf(MainFragment.ACTIVE_USER to binding.inputUsr.text.toString())
                            )


                    } else {

                        showError()

                    }


                }


            }
        }



    }

    private fun showError() {

        val alert = AlertDialog.Builder(requireView().context)
        alert.setTitle("Error")
        alert.setMessage("Ha ocurrido un error autenticando al usuario")
        alert.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = alert.create()
        dialog.show()
    }

}