package com.example.trial

class password_validation:text_validation(
    valodator = :: isPasswordValid,
    erreormessage = { passwordErrorMessage() }
) {
}

 private fun isPasswordValid(password : String) = password.length>=4
private fun passwordErrorMessage()= "Password is invalid"