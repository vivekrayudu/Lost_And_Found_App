package com.example.trial

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.trial.ui.theme.TrialTheme
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class changepass : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrialTheme {
                updatepass()
            }
        }
    }
}

@Composable
fun updatepass() {
    val user=Firebase.auth.currentUser
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black, RectangleShape)
            .wrapContentHeight(Alignment.CenterVertically)
            .wrapContentWidth(Alignment.CenterHorizontally)
            .verticalScroll(rememberScrollState())
    ) {
        val pass = remember { pass_validation() }
        pass(pass.value, pass.error) {
            pass.value = it
            pass.final = it
            pass.validate()
        }
        val newpass = remember { pass_validation() }
        newpass(newpass.value, newpass.error) {
            newpass.value = it
            newpass.final = it
            newpass.validate()
        }
        val cpass= remember { cpass_validation(newpass.final) }
        cpass(cpass.value,cpass.error){
            cpass.value=it
            cpass.validate()
        }
        update(enable=newpass.isValid(),pass.value ,newpass.value,cpass.value)
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun pass(word:String,error:String?,onPassword:(String)->Unit){
    val passwordVisible = remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    Column() {
        Surface(
            modifier =Modifier.fillMaxWidth(),
            color = Color.Black,
            elevation=8.dp
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                ProvideTextStyle(value = TextStyle(color = Color.White)) {
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        value = word,
                        onValueChange = {onPassword(it)},
                        label = {
                            Text(text = "Current Password")
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(8.dp),
                        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Next
                        ),
                        trailingIcon = {
                            val image1 = if (passwordVisible.value)
                                painterResource(id = R.drawable.ic_baseline_visibility_24)
                            else painterResource(id = R.drawable.ic_baseline_visibility_off_24)

                            // Please provide localized description for accessibility services
                            val description =
                                if (passwordVisible.value) "Hide password" else "Show password"

                            IconButton(onClick = { passwordVisible.value = !(passwordVisible.value) }) {
                                Icon(
                                    painter = image1,
                                    contentDescription = null
                                )
                            }
                        },
                        isError = error != null
                    )
                }

            }

        }
        error?.let { Errorfield(it) }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun newpass(word:String,error:String?,onPassword:(String)->Unit){
    val passwordVisible = remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    Column() {
        Surface(
            modifier =Modifier.fillMaxWidth(),
            color = Color.Black,
            elevation=8.dp
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                ProvideTextStyle(value = TextStyle(color = Color.White)) {
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        value = word,
                        onValueChange = {onPassword(it)},
                        label = {
                            Text(text = "New Password")
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(8.dp),
                        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Next
                        ),
                        trailingIcon = {
                            val image1 = if (passwordVisible.value)
                                painterResource(id = R.drawable.ic_baseline_visibility_24)
                            else painterResource(id = R.drawable.ic_baseline_visibility_off_24)

                            // Please provide localized description for accessibility services
                            val description =
                                if (passwordVisible.value) "Hide password" else "Show password"

                            IconButton(onClick = { passwordVisible.value = !(passwordVisible.value) }) {
                                Icon(
                                    painter = image1,
                                    contentDescription = null
                                )
                            }
                        },
                        isError = error != null
                    )
                }

            }

        }
        error?.let { Errorfield(it) }
    }
}

@Composable
private fun cpass(word:String,error:String?,onPassword:(String)->Unit){
    val passwordVisible = remember { mutableStateOf(false) }
    Column() {
        Surface(
            modifier =Modifier.fillMaxWidth(),
            color = Color.Black,
            elevation=8.dp
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                ProvideTextStyle(value = TextStyle(color = Color.White)) {
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        value = word,
                        onValueChange = {onPassword(it)},
                        label = {
                            Text(text = "Conform New Password")
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(8.dp),
                        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        trailingIcon = {
                            val image1 = if (passwordVisible.value)
                                painterResource(id = R.drawable.ic_baseline_visibility_24)
                            else painterResource(id = R.drawable.ic_baseline_visibility_off_24)

                            // Please provide localized description for accessibility services
                            val description =
                                if (passwordVisible.value) "Hide password" else "Show password"

                            IconButton(onClick = { passwordVisible.value = !(passwordVisible.value) }) {
                                androidx.compose.material.Icon(
                                    painter = image1,
                                    contentDescription = null
                                )
                            }
                        },
                        isError = error != null
                    )
                }

            }

        }
        error?.let { Errorfield(it) }
    }
}

@Composable
private fun update(enable:Boolean,password : String,newpassword:String,cpassword:String){
    val mContext = LocalContext.current
    val mContext1 = LocalContext.current
    val s2 = LocalContext.current
    Surface(
        modifier =Modifier.fillMaxWidth(),
        color= Color.Black,
        elevation=8.dp
    ){
        Button(onClick = {
            val user = Firebase.auth.currentUser
            var temp =" "
            runBlocking {
                val db=Firebase.firestore
                val result = db.collection("users").get().await()
                for(document in result.documents){
                    if(user!=null){
                        if(document.get("Name")==user.displayName){
                            temp = document.get("Password") as String
                        }
                    }
                }
            }
            if(temp == password){
                if(cpassword==newpassword) {
                    user?.updatePassword(newpassword)?.addOnSuccessListener {
                        Toast.makeText(mContext1, "Password has been updated successfully", Toast.LENGTH_SHORT).show()
                        mContext.startActivity(Intent(mContext, Home_Screen::class.java))
                    }
                }
                else{
                    Toast.makeText(mContext1, "Password doesn't match", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(mContext1, "Current Password is not correct", Toast.LENGTH_SHORT).show()
            }

        }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray, contentColor = Color.White), shape = RoundedCornerShape(8.dp), enabled = enable) {
            Text(
                stringResource(R.string.Submit),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview7() {
    TrialTheme {
        updatepass()
    }
}