package com.nammaskill.app.core.model

data class Application(

    val id: String = "",

    val userId: String = "",
    val userEmail: String = "",

    val fullName: String = "",
    val phoneNumber: String = "",
    val qualification: String = "",
    val age: String = "",
    val gender: String = "",
    val district: String = "",
    val skills: String = "",

    val courseName: String = "",
    val category: String = "",
    val duration: String = "",
    val center: String = "",

    val status: String = "pending",

    val timestamp: Long = 0L
)