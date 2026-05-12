package com.nammaskill.app.core.model

data class Course(
    val id: String = "",
    val name: String = "",
    val duration: String = "",
    val center: String = "",
    val jobGuarantee: Boolean = false,
    val category: String = "",
    val description: String = "",
    val eligibility: String = "",
    val imageUrl: String = ""
)