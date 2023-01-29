package com.fernandomarquezrodriguez.contactplusfmr.model.bd

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(

    var name: String = "",
    var firstSurname: String = "",
    var secondSurname: String = "",
    var userName: String = "",
    var password: String = "",
    var email: String = "",
    var phone: String = "",
    var instagramUser: String = "",
    var gitHubUser: String = "",
    var twitterUser: String = "",
    var tikTokUser: String = "",
    var list: MutableList<Contact> = arrayListOf()
    ) : Parcelable {
    }