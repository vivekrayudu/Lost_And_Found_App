package com.example.trial


import com.example.trial.text_validation


class lostvalidation: text_validation(
    valodator = :: isValid,
    erreormessage = ::errorMessage
) {

}

private fun isValid(text : String): Boolean{
    return  text.isNotEmpty()
}

private fun errorMessage(text: String)="Enter the place where you lost it."