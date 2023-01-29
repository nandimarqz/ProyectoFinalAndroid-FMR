package com.fernandomarquezrodriguez.contactplusfmr.ui

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fernandomarquezrodriguez.contactplusfmr.R
import com.fernandomarquezrodriguez.contactplusfmr.databinding.ContactViewBinding
import com.fernandomarquezrodriguez.contactplusfmr.model.bd.Contact
import java.util.*
import kotlin.streams.toList

class ContactAdapter(val listener : (Contact) ->Unit): RecyclerView.Adapter<ContactAdapter.ViewHolder>() {

    var contacts= emptyList<Contact>()
    var originalContacts= emptyList<Contact>()
    lateinit var coleccion : MutableList<Contact>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        //Guarda la vista inflada en una variable y la devolve pasandola como parametro a viewHolder para que controle la vista
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_view, parent, false)
        return ViewHolder(view)
    }

    /**
     * Se encarga de asignar un componente en el momento en el que la vista entra en pantalla
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //llama a la funcion bind creada abajo con la persona
        holder.bind(contacts[position])

        //Guarda a la persona en una variable
        val person = contacts[position]

        //Pone un escuchador de eventos para cuando se haga un click
        // en contacto llama a la funcion que se pasa por parametro a esta misma clase
        holder.itemView.setOnClickListener {
            listener(person)

        }


    }

    /**
     * Filtra las recetas con la cadena de caracteres pasado por parametro y devuelve
     * una lista con las recetas que contengan esa cadena de caracteres en su titulo
     */
    fun filtrado(txtSearchView : String){


        val longitud = txtSearchView.length

        //Si la longitud de la cadena de caracteres es 0 devolvemos todas los contactos
        if(longitud == 0) {

            contacts = emptyList<Contact>()
            contacts = originalContacts

        }else{
            //Depende de la version del SDK hay que realizarlo de una manera u otra
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                coleccion =
                    contacts.stream().filter{ i-> i.firstSurname.lowercase(Locale.getDefault()).contains(txtSearchView.lowercase(Locale.getDefault()))}.toList() as MutableList<Contact>
                contacts = coleccion

            } else {
                contacts.forEach {contact ->

                    if(contact.name.lowercase(Locale.getDefault()).contains(txtSearchView.lowercase(Locale.getDefault()))){

                        coleccion.add(contact)

                    }

                }
                contacts = coleccion
            }

        }
        notifyDataSetChanged()
    }

    /**
     * Devuelve el tamaño de la lista pasada por parametro
     */
    override fun getItemCount(): Int = contacts.size

    /**
     * Gestiona la vista y controla lo que hay en pantalla, los datos, los reutiliza, etc
     */
    class ViewHolder(view: View): RecyclerView.ViewHolder(view){

        //Obtiene los items de la vista en la variable
        private val binding = ContactViewBinding.bind(view)

        /**
         * Esta funcion cambia todos los valores de texto de las
         * etiquetas recogidas y cambia tambien la imagen con Glide
         */
        fun bind(contact: Contact ){

            val fullName = " " + contact.name + " " + contact.firstSurname + " " + contact.secondSurname

            binding.nombre.text = fullName


//            Glide.with(binding.imageView).load(recipe.image).into(binding.imageView)

        }


    }

}