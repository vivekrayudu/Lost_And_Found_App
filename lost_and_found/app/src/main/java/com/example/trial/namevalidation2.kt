package com.example.trial


class namevalidation2:textvalidation2(
    valodator = :: isValid,
    erreormessage = ::errorMessage
) {

}

private fun isValid(name : String): Boolean{
    return name.isNotEmpty()
}

private fun errorMessage(name: String)="Enter your name."