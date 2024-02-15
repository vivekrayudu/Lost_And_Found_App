package com.example.trial

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

open class cpass_validation(
    val key:String
) {
    var value by mutableStateOf("")
    var error by mutableStateOf<String?>(null)

    fun validate(){
        error=if(isValid()){
            null
        }
        else {
            "Doesn't match with password"
        }
    }

    fun isValid():Boolean{
        return true
    }
}