package com.fernandomarquezrodriguez.contactplusfmr.model.bd

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await
import okhttp3.internal.wait
import java.io.File

object CloudStorage {
    val storage = Firebase.storage("gs://contactplus-333e1.appspot.com")
    suspend fun uploadFile(user: String, imageUri: Uri, context: Context): Uri? {

        var uri: Uri? = Uri.fromFile(File(""))

        val storageRef = storage.reference
        val userRef: StorageReference? = storageRef.child(user)

        val imagesRef: StorageReference? = storageRef.child("$user/images")

        val spaceRef = storageRef.child("$user/images/${imageUri.lastPathSegment}")

        val uploadTask = spaceRef.putFile(imageUri)

        uri = uploadTask.continueWithTask { task ->

            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            spaceRef.downloadUrl
        }.await()
        return uri
    }

//    fun getUrlFile(user: String, imageUri: String): Uri {
//
//        val uri = Uri.fromFile(File(imageUri))
//
//        val storageRef = storage.reference
//        val userRef: StorageReference? = storageRef.child(user)
//
//        val imagesRef: StorageReference? = storageRef.child("$user/images")
//
//        val spaceRef = storageRef.child("$user/images/${uri.lastPathSegment}")
//
//        var uriStorage: Uri? = null
//
//        val downloadUrlTask = spaceRef.downloadUrl;
//
//        downloadUrlTask.addOnSuccessListener { result ->
//
//            uriStorage = result
//
//        }
//            .addOnFailureListener { e ->
//                uriStorage = Uri.fromFile(File(""))
//            }
//        return uriStorage!!
//    }
}