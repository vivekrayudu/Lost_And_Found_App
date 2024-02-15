package com.example.trial

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

open class text_validation(
    private val valodator: (String) -> Boolean={true},
    private val erreormessage: (String) -> String
) {
    var value by mutableStateOf("")
    var error by mutableStateOf<String?>(null)
    var final by mutableStateOf(value)
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