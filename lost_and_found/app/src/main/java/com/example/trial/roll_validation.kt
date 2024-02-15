package com.example.trial

import java.util.regex.Pattern

class roll_validation :text_validation(
    valodator = :: isValid,
    erreormessage = ::errorMessage
){
}

private fun isValid(roll:String):Boolean{
    return roll.isNotEmpty()
}

private fun errorMessage(roll:String) ="Enter Valid Roll Number"