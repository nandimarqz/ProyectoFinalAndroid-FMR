package com.fernandomarquezrodriguez.contactplusfmr.model.bd

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object UserDao {
    const val COLLECTION_USER = "user"

    fun addUser(user: User) {

        val userMap = hashMapOf(
            "name" to user.name,
            "firstSurname" to user.firstSurname,
            "secondSurname" to user.secondSurname,
            "userName" to user.userName,
            "password" to user.password,
            "email" to user.email,
            "phone" to user.phone,
            "instagramUser" to user.instagramUser,
            "gitHubUser" to user.gitHubUser,
            "twitterUser" to user.twitterUser,
            "tikTokUser" to user.tikTokUser
        )
//
        FirebaseFirestore.getInstance().collection(COLLECTION_USER)
            .document(user.email)
            .set(userMap)


        ContactDao.addContact(
            Contact(
                user.name,
                user.firstSurname,
                user.secondSurname,
                user.email,
                user.phone,
                user.instagramUser,
                user.gitHubUser,
                user.twitterUser,
                user.tikTokUser
            ), user.email
        )


    }

    suspend fun getUser(email: String): User {
      val user = User()
      FirebaseFirestore.getInstance().collection(UserDao.COLLECTION_USER).document(email)
            .get().addOnSuccessListener {

                user.name = it.get("name") as String
                user.firstSurname = it.get("firstSurname") as String
                user.secondSurname = it.get("secondSurname") as String
                user.userName = it.get("userName") as String
                user.password = it.get("password") as String
                user.email = it.get("email") as String
                user.phone = it.get("phone") as String
                user.instagramUser = it.get("instagramUser") as String
                user.gitHubUser = it.get("gitHubUser") as String
                user.twitterUser = it.get("twitterUser") as String
                user.tikTokUser = it.get("tikTokUser") as String


          }

        return user
    }

}