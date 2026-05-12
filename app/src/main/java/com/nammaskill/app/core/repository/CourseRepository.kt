package com.nammaskill.app.core.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.nammaskill.app.core.model.Application
import com.nammaskill.app.core.model.Course

class CourseRepository {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    fun getCourses(
        onSuccess: (List<Course>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {

        db.collection("courses")
            .get()
            .addOnSuccessListener { result ->

                val courseList = result.documents.map { doc ->

                    Course(
                        name = doc.getString("name") ?: "",
                        duration = doc.getString("duration") ?: "",
                        center = doc.getString("center") ?: "",
                        jobGuarantee = doc.getBoolean("jobGuarantee") ?: false,
                        category = doc.getString("category") ?: ""
                    )
                }

                onSuccess(courseList)
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    fun applyForCourse(
        fullName: String,
        phoneNumber: String,
        qualification: String,
        age: String,
        gender: String,
        district: String,
        skills: String,
        courseName: String,
        category: String,
        duration: String,
        center: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {

        val user = auth.currentUser

        if (user == null) {
            onFailure(Exception("User not logged in"))
            return
        }

        val application = hashMapOf(

            "userId" to user.uid,
            "userEmail" to (user.email ?: ""),

            "fullName" to fullName,
            "phoneNumber" to phoneNumber,
            "qualification" to qualification,
            "age" to age,
            "gender" to gender,
            "district" to district,
            "skills" to skills,

            "courseName" to courseName,
            "category" to category,
            "duration" to duration,
            "center" to center,

            "status" to "pending",

            "timestamp" to System.currentTimeMillis()
        )

        db.collection("applications")
            .add(application)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onFailure(it)
            }
    }

    fun getApplications(
        onSuccess: (List<Application>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {

        db.collection("applications")
            .get()
            .addOnSuccessListener { result ->

                val list = result.documents.map { doc ->

                    Application(

                        id = doc.id,

                        userId = doc.getString("userId") ?: "",
                        userEmail = doc.getString("userEmail") ?: "",

                        fullName = doc.getString("fullName") ?: "",
                        phoneNumber = doc.getString("phoneNumber") ?: "",
                        qualification = doc.getString("qualification") ?: "",
                        age = doc.getString("age") ?: "",
                        gender = doc.getString("gender") ?: "",
                        district = doc.getString("district") ?: "",
                        skills = doc.getString("skills") ?: "",

                        courseName = doc.getString("courseName") ?: "",
                        category = doc.getString("category") ?: "",
                        duration = doc.getString("duration") ?: "",
                        center = doc.getString("center") ?: "",

                        status = doc.getString("status") ?: "pending",

                        timestamp = doc.getLong("timestamp") ?: 0L
                    )
                }

                onSuccess(list)
            }
            .addOnFailureListener {
                onFailure(it)
            }
    }

    fun updateApplicationStatus(
        documentId: String,
        newStatus: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {

        db.collection("applications")
            .document(documentId)
            .update("status", newStatus)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onFailure(it)
            }
    }
}