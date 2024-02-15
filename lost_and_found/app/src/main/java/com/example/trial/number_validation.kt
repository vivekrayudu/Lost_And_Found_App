package com.example.trial

class number_validation:text_validation(
    valodator = :: isValid,
    erreormessage = ::errorMessage
) {
}
private fun isValid(num : String):Boolean{
    return num.length==10
}

private fun errorMessage(num : String) = "Enter a valid number"