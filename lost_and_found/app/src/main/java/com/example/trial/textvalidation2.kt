package com.example.trial

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

open class textvalidation2(
    private val valodator: (String) -> Boolean={true},
    private val erreormessage: (String) -> String
) {
    var displayname=""
    val id= Firebase.auth.currentUser?.uid

    var value by mutableStateOf(value = displayname)
    var error by mutableStateOf<String?>(null)
    fun validate(){
        error=if(isValid()){
            null
        }
        else {
            erreormessage(value)
        }
    }

    fun isValid()=valodator(value)
}