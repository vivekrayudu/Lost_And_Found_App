

import com.example.trial.text_validation


class validation: text_validation(
    valodator = :: isValid,
    erreormessage = ::errorMessage
) {

}

private fun isValid(text : String): Boolean{
    return text.isNotEmpty()
}

private fun errorMessage(name: String)="Enter description"