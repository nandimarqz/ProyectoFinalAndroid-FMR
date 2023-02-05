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
import okhttp3.internal.wait
import java.io.File

object CloudStorage {
    val storage = Firebase.storage("gs://contactplus-333e1.appspot.com")
     fun uploadFile(user:String, imageUri:Uri, context:Context) {



        val storageRef = storage.reference
        val userRef : StorageReference? = storageRef.child(user)

        val imagesRef :StorageReference? = storageRef.child("$user/images")

        val spaceRef = storageRef.child("$user/images/${imageUri.lastPathSegment}")

        val uploadTask = spaceRef.putFile(imageUri)

        uploadTask.addOnFailureListener{

            Toast.makeText(context, "La imagen no se ha subido correctamente", Toast.LENGTH_LONG).show()

        } .addOnSuccessListener {

            Toast.makeText(context, "La imagen se ha subido correactamente", Toast.LENGTH_LONG).show()
        }
    }

    fun getUrlFile(user:String, imageUri:String):Uri{

        val uri = Uri.fromFile(File(imageUri))

        val storageRef = storage.reference
        val userRef : StorageReference? = storageRef.child(user)

        val imagesRef :StorageReference? = storageRef.child("$user/images")

        val spaceRef = storageRef.child("$user/images/${uri.lastPathSegment}")

        var uriStorage: Uri? = null

        val downloadUrlTask = spaceRef.downloadUrl;

        downloadUrlTask.addOnSuccessListener{result ->

            uriStorage = result

        }
        .addOnFailureListener{e ->
            uriStorage = Uri.fromFile(File(""))
        }
        return uriStorage!!
    }
}