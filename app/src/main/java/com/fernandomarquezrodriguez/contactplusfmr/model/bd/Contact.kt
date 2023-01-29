package com.fernandomarquezrodriguez.contactplusfmr.model.bd

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Contact(
    val name: String = "",
    val firstSurname: String = "",
    val secondSurname: String = "",
    val email: String = "",
    val phone: String = "",
    val instagramUser: String = "",
    val gitHubUser: String = "",
    val twitterUser: String = "",
    val tikTokUser: String = "",
) : Parcelable
