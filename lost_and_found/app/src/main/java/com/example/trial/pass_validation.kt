package com.example.trial

import java.util.regex.Pattern

class pass_validation : text_validation(
    valodator = :: isPasswordValid,
    erreormessage = { passwordErrorMessage() }
) {
}
private const val regex= "^" +
        "(?=.*[0-9])" +         //at least 1 digit
        "(?=.*[a-z])" +         //at least 1 lower case letter
        "(?=.*[A-Z])" +         //at least 1 upper case letter
        "(?=.*[a-zA-Z])" +      //any letter
        "(?=.*[@#$%^&+=])" +    //at least 1 special character
        "(?=\\S+$)" +           //no white spaces
        ".{8,}" +               //at least 8 characters
        "$"
private fun isPasswordValid(pass: String) :Boolean{
return Pattern.matches(regex,pass)
}
private fun passwordErrorMessage()= "Password should contain atleast One capital letter\n" +

        "alteast One number" +
        "\n" +
        "atleast One symbol"+
        "\n"+"No Spaces"+"\n"+"Atleat 8 characters"