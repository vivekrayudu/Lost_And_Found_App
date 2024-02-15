package com.example.trial

import java.util.regex.Pattern

class mail_validation:text_validation(
    valodator = :: isValid,
    erreormessage = ::errorMessage
) {
}

private const val regex= "^(.+)@iitp.ac.in\$"

private fun isValid(email: String): Boolean{
    return Pattern.matches(regex,email)
}

private fun errorMessage(email: String)= "Email is invald."