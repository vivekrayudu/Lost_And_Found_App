package com.example.trial.ui.theme

import com.example.trial.text_validation


class foundcalidation: text_validation(
    valodator = :: isValid,
    erreormessage = ::errorMessage
) {

}

private fun isValid(text : String): Boolean{
    return text.isNotEmpty()
}

private fun errorMessage(text: String)="Enter the place where you found it."