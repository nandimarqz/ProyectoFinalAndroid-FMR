package com.fernandomarquezrodriguez.contactplusfmr.model.bd

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object ContactDao {
    const val COLLECTION_CONTACS = "contacts"
    fun addContact(contact: Contact, email: String) {

        val contactMap = hashMapOf(
            "name" to contact.name,
            "firstSurname" to contact.firstSurname,
            "secondSurname" to contact.secondSurname,
            "email" to contact.email,
            "phone" to contact.phone,
            "instagramUser" to contact.instagramUser,
            "gitHubUser" to contact.gitHubUser,
            "twitterUser" to contact.twitterUser,
            "tikTokUser" to contact.tikTokUser,
            "imageRef" to contact.imageRef
        )

        FirebaseFirestore.getInstance()
            .collection(UserDao.COLLECTION_USER)
            .document(email)
            .collection(COLLECTION_CONTACS)
            .document(contact.phone)
            .set(contactMap)


    }

    fun updateContact(contact: Contact, email: String){

        val contactMap = hashMapOf(
            "name" to contact.name,
            "firstSurname" to contact.firstSurname,
            "secondSurname" to contact.secondSurname,
            "email" to contact.email,
            "phone" to contact.phone,
            "instagramUser" to contact.instagramUser,
            "gitHubUser" to contact.gitHubUser,
            "twitterUser" to contact.twitterUser,
            "tikTokUser" to contact.tikTokUser,
            "imageRef" to contact.imageRef
        )

        FirebaseFirestore.getInstance()
            .collection(UserDao.COLLECTION_USER)
            .document(email)
            .collection(COLLECTION_CONTACS)
            .document(contact.phone)
            .update(contactMap as Map<String, Any>)
            .addOnCompleteListener {
                if(it.isSuccessful){

                    Log.d(COLLECTION_CONTACS, contact.phone)

                }
            }.addOnFailureListener {

                Log.d(COLLECTION_CONTACS, it.toString())

            }


    }

    fun deleteContact(contact: Contact, email: String){

        FirebaseFirestore.getInstance()
            .collection(UserDao.COLLECTION_USER)
            .document(email)
            .collection(COLLECTION_CONTACS)
            .document(contact.phone)
            .delete()
            .addOnCompleteListener {
                if(it.isSuccessful){

                    Log.d(COLLECTION_CONTACS, contact.phone)

                }
            }.addOnFailureListener {

                Log.d(COLLECTION_CONTACS, it.toString())

            }

    }

    suspend fun getAllContacts(email:String): List<Contact> {
        val snapshot = FirebaseFirestore.getInstance().collection(UserDao.COLLECTION_USER).document(email)
            .collection(COLLECTION_CONTACS)
            .get()
            .await()
        val contacts = mutableListOf<Contact>()
        for (documentSnapshot in snapshot){
            val contact = documentSnapshot.toObject(Contact::class.java)
            contacts.add(contact)
        }
        return contacts
    }
}