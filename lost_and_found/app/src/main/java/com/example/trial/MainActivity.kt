package com.example.trial

import android.content.Intent
import android.graphics.drawable.Icon
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.Visibility
import com.example.trial.MainActivity.Companion.TAG
import com.example.trial.ui.theme.TrialTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app


class MainActivity : ComponentActivity() {
    companion object{
        val TAG : String = MainActivity::class.java.simpleName
    }
    private val auth by lazy {
        Firebase.auth
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrialTheme {
                loginscreen(auth)
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun loginscreen(auth : FirebaseAuth) {
    val image = painterResource(id = R.drawable.laf)
    Column(
            modifier = Modifier
                .fillMaxSize()
                .background(androidx.compose.ui.graphics.Color.Black, RectangleShape)
                .wrapContentHeight(Alignment.CenterVertically)
                .wrapContentWidth(Alignment.CenterHorizontally)
                .verticalScroll(rememberScrollState())
        ) {
            Image(
                painter = image,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .size(200.dp)
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.padding(16.dp))
            val username= remember { email_validation() }
            enter(username.value,username.error){
                username.value = it
                username.validate()
            }
            val password= remember { password_validation() }
            password(password.value,password.error){
                password.value=it
                password.validate()
            }
            sign_in(enable = username.isValid() && password.isValid(),username.value,password.value)
            forgot()
            Spacer(modifier = Modifier.padding(10.dp))
            sign_up()


        }

}
@Composable
private fun enter(email: String, error:String?,onChange: (String)->Unit){
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
                        value = email,
                        onValueChange = { onChange(it) },
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(8.dp),
                        label = {
                            Text(text = "Username")
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        isError = error != null

                    )
                }
            }

        }
        error?.let { Errorfield(it) }
    }
}
@Composable
fun Errorfield(error:String){
    Text(text = error, modifier = Modifier.fillMaxWidth(), style = TextStyle(color = MaterialTheme.colors.error))
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun password(password:String,error:String?,onPassword:(String)->Unit){
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
                        value = password,
                        onValueChange = {onPassword(it)},
                        label = {
                            Text(text = "Password")
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
                        keyboardActions = KeyboardActions(
                            onDone =
                            { focusManager.clearFocus() }),
                        isError = error != null
                    )
                }

            }

        }
        error?.let { Errorfield(it) }
    }
}
@Composable
fun sign_in(enable:Boolean,username:String,password:String){
    val mContext = LocalContext.current
    Surface(
        modifier =Modifier.fillMaxWidth(),
        color= Color.Black,
        elevation=8.dp
    ){
        Button(onClick = {
            Firebase.auth.currentUser!!.reload()
            if(Firebase.auth.currentUser?.isEmailVerified == true){
                         Firebase.auth.signInWithEmailAndPassword(username,password)
                             .addOnCompleteListener() {
                                 if(it.isSuccessful){
                                     Toast.makeText(mContext, "Successfully Logged In", Toast.LENGTH_SHORT).show()
                                     mContext.startActivity(Intent(mContext,Home_Screen::class.java))
                                 }
                                 else{
                                     Toast.makeText(mContext, "Login In Failed!", Toast.LENGTH_SHORT).show()
                                 }
                             }
            }
            else{
                Toast.makeText(mContext, "Email not verified", Toast.LENGTH_SHORT).show()

            }
        }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray, contentColor = Color.White), shape = RoundedCornerShape(8.dp), enabled = enable) {
            Text(stringResource(R.string.Sign_in),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )
        }
    }

}

@Composable
fun sign_up(){
    val s1 = LocalContext.current
    Button(onClick = {
                     s1.startActivity(Intent(s1,Sign_up::class.java))
    },colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray, contentColor = Color.White),shape = RoundedCornerShape(8.dp)) {
        Text(stringResource(R.string.Sign_up),modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally))
    }
}
@Composable
fun forgot(){
    val mContext = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.End)
    ) {
        TextButton(onClick = {
            mContext.startActivity(Intent(mContext,forgotpass::class.java))
        }) {
            Text(
                color=Color.White,
                fontStyle = FontStyle.Normal,
                text = "Forgotten Password?",
                modifier = Modifier.padding(end=8.dp)
            )
        }

    }

}
@Preview(showBackground = true)
@Composable
fun loginPreview(){
    TrialTheme {
        loginscreen(Firebase.auth)
    }
}
