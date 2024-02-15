package com.example.trial

import android.content.Intent
import android.graphics.Paint.Style
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trial.ui.theme.TrialTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class forgotpass : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrialTheme {
                forgotpassword()
            }
        }
    }
}

@Composable
fun forgotpassword() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black, RectangleShape)
            .wrapContentHeight(Alignment.CenterVertically)
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
    {
        Text(
            color = Color.White,
            text = "Forgot Password",
            fontStyle = FontStyle.Normal,
            fontSize = 24.sp,
            modifier = Modifier.padding(12.dp),
        )
        Text(
            color = Color.White,
            text = "Enter your Email Address. A Password reset mail will be sent to your Email Address",
            fontStyle = FontStyle.Normal,
            fontSize = 18.sp,
            modifier = Modifier.padding(12.dp),
        )
        val username= remember { email_validation() }
        enter(username.value,username.error){
            username.value = it
            username.validate()
        }
        submit(enable = username.isValid(), email = username.value)


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
                            Text(text = "Email Address")
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
private fun submit(enable:Boolean,email:String){
    val mContext1 = LocalContext.current
    Surface(
        modifier =Modifier.fillMaxWidth(),
        color= Color.Black,
        elevation=8.dp
    ){
        Button(onClick = {
            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener{task->
                    if(task.isSuccessful){
                        Toast.makeText(mContext1, "Email has been sent to your mail address", Toast.LENGTH_LONG).show()
                        mContext1.startActivity(Intent(mContext1,MainActivity::class.java))
                    }
                    else{
                        Toast.makeText(mContext1, task.exception?.message.toString(), Toast.LENGTH_LONG).show()
                    }
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
fun DefaultPreview5() {
    TrialTheme {
        forgotpassword()
    }
}