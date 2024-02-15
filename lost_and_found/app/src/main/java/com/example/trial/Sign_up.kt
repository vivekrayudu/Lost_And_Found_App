package com.example.trial

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
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
import coil.compose.rememberImagePainter
import com.example.trial.ui.theme.TrialTheme
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.UserInfo
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.core.UserData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class Sign_up : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrialTheme {
               Screen2()
            }
        }
    }
}



@Composable
fun Screen2(){
    val image= painterResource(id = R.drawable.user)
    val db = Firebase.firestore
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black, RectangleShape)
            .wrapContentHeight(Alignment.CenterVertically)
            .wrapContentWidth(Alignment.CenterHorizontally)
            .verticalScroll(rememberScrollState())
    ){

            uploadpic()

        Spacer(modifier = Modifier.padding(16.dp))

        val name= remember { name_validation() }
        name(name.value,name.error){
            name.value = it
            name.validate()
        }
        val roll= remember { roll_validation() }
        roll(roll.value,roll.error){
            roll.value = it
            roll.validate()
        }
        val mail= remember { mail_validation() }
        mail(mail.value,mail.error){
            mail.value = it
            mail.validate()
        }
        val pass= remember { pass_validation() }
        pass(pass.value,pass.error){
            pass.value=it
            pass.final=it
            pass.validate()
        }
       val cpass= remember { cpass_validation(pass.final) }
       cpass(cpass.value,cpass.error){
            cpass.value=it
           cpass.validate()
        }
        val number= remember { number_validation() }
        number(number.value,number.error){
            number.value=it
            number.validate()
        }
        submit(enable = name.isValid() && roll.isValid()&&mail.isValid()&&pass.isValid()&&number.isValid(),name.value,roll.value,mail.value,pass.value,cpass.value,number.value,db)


    }
}

@Composable
private fun name(name: String, error:String?,onChange: (String)->Unit){
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
                        value = name,
                        onValueChange = { onChange(it) },
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(8.dp),
                        label = {
                            Text(text = "Name")
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
fun roll(roll_num: String, error:String?,onChange: (String)->Unit){
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
                        value = roll_num,
                        onValueChange = { onChange(it) },
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(8.dp),
                        label = {
                            Text(text = "Roll Number")
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
fun mail(post: String, error:String?,onChange: (String)->Unit){
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
                        value = post,
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
                            Text(text = "Conform Password")
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
private fun number(num: String, error:String?,onChange: (String)->Unit){
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
                        value = num,
                        onValueChange = { onChange(it) },
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(8.dp),
                        label = {
                            Text(text = "Number")
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Phone,
                            imeAction = ImeAction.Done
                        ),
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
private fun submit(enable:Boolean,name:String,roll:String,email:String,password:String,cpassword:String,number:String,db:FirebaseFirestore){
    val mContext1 = LocalContext.current
    val s2 = LocalContext.current
    Surface(
        modifier =Modifier.fillMaxWidth(),
        color= Color.Black,
        elevation=8.dp
    ){
        Button(onClick = {
            if(cpassword==password) {
                val details = hashMapOf(
                    "Name" to name,
                    "Roll NO." to roll,
                    "Email Address" to email,
                    "Password" to password,
                    "Number" to number
                )
                db.collection("users")
                    .add(details)
                    .addOnSuccessListener {
                        Toast.makeText(mContext1, "Details updated successfully", Toast.LENGTH_SHORT)
                            .show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(mContext1, "Error:"+(it.message), Toast.LENGTH_SHORT)
                            .show()
                    }
                Firebase.auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener() {
                        if (it.isSuccessful) {
                            Firebase.auth.currentUser?.sendEmailVerification()
                                ?.addOnCompleteListener(){
                                    if(it.isSuccessful){
                                        Toast.makeText(mContext1, "A Verification Email has been sent to your mail", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            val user : FirebaseUser? = Firebase.auth.currentUser
                            if (user != null) {
                                Firebase.auth.zzq(user,
                                    UserProfileChangeRequest.Builder().setDisplayName(name).build())

                            }
                            Toast.makeText(mContext1, "Successfully Singed Up", Toast.LENGTH_SHORT)
                                .show()
                            s2.startActivity(Intent(s2, MainActivity::class.java))
                        } else {
                            Toast.makeText(mContext1, "Singed Up Failed!", Toast.LENGTH_SHORT)
                                .show()
                            s2.startActivity(Intent(s2, MainActivity::class.java))
                        }
                    }
            }
            else{
                Toast.makeText(mContext1, "Password doesn't match", Toast.LENGTH_SHORT).show()
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
@Composable
private fun uploadpic(){
    val imageurl = rememberSaveable { mutableStateOf("") }
    val painter = rememberImagePainter(
        if(imageurl.value.isEmpty())
            R.drawable.user
        else
            imageurl.value
    )
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()){
        uri : Uri? ->
        uri?.let{imageurl.value=it.toString()}

    }
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(shape = CircleShape,
        modifier = Modifier
            .padding(8.dp)
            .size(100.dp)) {
            Image(painter = painter, contentDescription = null, modifier = Modifier
                .fillMaxWidth()
                .clickable {launcher.launch("image/*")},
            contentScale = ContentScale.Crop)
        }
        Surface(
            modifier =Modifier.fillMaxWidth(),
            color= Color.Black,
            elevation=8.dp
        ){
            Button(onClick = {launcher.launch("image/*")}, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black, contentColor = Color.White), shape = RoundedCornerShape(8.dp)) {
                Text(text = "Upload Profile Pic",
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
            }
        }
    }

}






@Preview(showBackground = true)
@Composable
fun Screen2Preview() {
    TrialTheme {
        Screen2()
    }
}