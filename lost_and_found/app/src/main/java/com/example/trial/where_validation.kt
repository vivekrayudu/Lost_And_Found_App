package com.example.trial

class where_validation:text_validation(
    valodator = :: isValid,
    erreormessage = ::errorMessage
) {
}

private fun isValid(name : String): Boolean{
    return name.isNotEmpty()
}

private fun errorMessage(name: String)="Enter Valid Description."
