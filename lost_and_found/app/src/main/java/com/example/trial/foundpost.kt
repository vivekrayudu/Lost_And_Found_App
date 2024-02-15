package com.example.trial

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.trial.MainActivity.Companion.TAG
import com.example.trial.ui.theme.TrialTheme
import com.example.trial.ui.theme.foundcalidation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.asDeferred
import kotlinx.coroutines.tasks.await
import okhttp3.internal.wait
import validation
import kotlin.concurrent.thread

class foundpost : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrialTheme {
                post()
            }
        }
    }
}

@Composable
private fun post() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black, RectangleShape)
                .wrapContentHeight(Alignment.CenterVertically)
                .wrapContentWidth(Alignment.CenterHorizontally)
        ) {
            Text(
                color = Color.White,
                text = "Found Post",
                fontStyle = FontStyle.Italic,
                fontSize = 40.sp,
                modifier = Modifier.padding(12.dp),
            )
            Spacer(modifier = Modifier.padding(10.dp))
            val name= remember { name_validation() }
            name(name.value,name.error){
                name.value = it
                name.validate()
            }
            val number= remember { number_validation() }
            number(number.value,number.error){
                number.value=it
                number.validate()
            }
            val foundnear= remember { foundcalidation() }
            foundnear(foundnear.value,foundnear.error){
                foundnear.value = it
                foundnear.validate()
            }
            val description= remember { validation() }
            description(description.value,description.error){
                description.value = it
                description.validate()
            }
            uploadpic()
            submit(enable = foundnear.isValid() && description.isValid(),name.value,number.value,foundnear.value,description.value)
        }

}

@Composable
private fun name(name: String, error:String?,onChange: (String)->Unit){

    var nam= " "
    val user : FirebaseUser? = Firebase.auth.currentUser
    runBlocking {
        val db=Firebase.firestore
        val result = db.collection("users").get().await()
        for(document in result.documents){
            if(user!=null){
                if(document.get("Name")==user.displayName){
                    nam = document.get("Name") as String
                }
            }
        }
    }
    val query = remember { mutableStateOf(nam) }
    Column() {
        Surface(
            modifier =Modifier.fillMaxWidth(),
            color = Color.Black,
            elevation=8.dp
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                ProvideTextStyle(value = TextStyle(color = Color.White)) {
                    if (user != null) {
                            TextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                value = query.value,
                                onValueChange = { newvalue->
                                    query.value=newvalue
                                },
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

        }
        error?.let { Errorfield(it) }
    }
}
@Composable
private fun number(num: String, error:String?,onChange: (String)->Unit){
    var num= "999999999"
    val user : FirebaseUser? = Firebase.auth.currentUser
    runBlocking {
        val db=Firebase.firestore
        val result = db.collection("users").get().await()
        for(document in result.documents){
            if(user!=null){
                if(document.get("Name")==user.displayName){
                    num = document.get("Number") as String
                }
            }
        }
    }
        val query = remember { mutableStateOf(num) }
        val focusManager = LocalFocusManager.current
        Column() {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color.Black,
                elevation = 8.dp
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    ProvideTextStyle(value = TextStyle(color = Color.White)) {
                        if (user != null) {
                            TextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                value = query.value,
                                onValueChange = { newvalue ->
                                    query.value = newvalue
                                },
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

            }
            error?.let { Errorfield(it) }
        }
}
@Composable
fun foundnear(text: String, error:String?,onChange: (String)->Unit){
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
                        value = text,
                        onValueChange = { onChange(it) },
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(8.dp),
                        label = {
                            Text(text = "Found near")
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
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
private fun description(text: String, error:String?,onChange: (String)->Unit){
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
                        value = text,
                        onValueChange = { onChange(it) },
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(8.dp),
                        label = {
                            Text(text = "Description")
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
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
private val images= mutableListOf<String>()
@Composable
private fun uploadpic(){
    var storageRef = Firebase.storage.reference
    val imageurl = rememberSaveable { mutableStateOf("") }
    val painter = rememberImagePainter(
            imageurl.value
    )
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),){
            uri : Uri? ->
        uri?.let{
            imageurl.value=it.toString()
            images.add(imageurl.value)
            val uploadTask = storageRef.child("found/"+"images/"+ imageurl.value).putFile(it)
        }
    }
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(shape = RectangleShape,
            modifier = Modifier
                .padding(8.dp)
                .size(100.dp)) {
            Image(painter = painter, contentDescription = null, modifier = Modifier
                .fillMaxWidth()
                .clickable { launcher.launch("image/*") },
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
@Composable
private fun submit(enable:Boolean,name:String,number:String,foundnear:String,description:String){
    val mContext1 = LocalContext.current
    val s2 = LocalContext.current
    val user : FirebaseUser? = Firebase.auth.currentUser
    var num= "999999999"
    var nam =" "
    runBlocking {
        val db=Firebase.firestore
        val result = db.collection("users").get().await()
        for(document in result.documents){
            if(user!=null){
                if(document.get("Name")==user.displayName){
                    num = document.get("Number") as String
                    nam = document.get("Name") as String
                }
            }
        }
    }
    Surface(
        modifier =Modifier.fillMaxWidth(),
        color= Color.Black,
        elevation=8.dp
    ){  val db=Firebase.firestore
        Button(onClick = {
                val details = hashMapOf(
                    "Name" to nam,
                    "Number" to num,
                    "Found_Place" to foundnear,
                    "Description" to description,
                    "EmailAddress" to user?.email,
                    "Imageuri" to images.get(0),
                    "Type" to "Found Post"
                )
                db.collection("foundfeed")
                    .add(details)
                    .addOnSuccessListener {
                        Toast.makeText(mContext1, "Details updated successfully", Toast.LENGTH_SHORT)
                            .show()
                        s2.startActivity(Intent(s2, Home_Screen::class.java))
                    }
                    .addOnFailureListener {
                        Toast.makeText(mContext1, "Error:"+(it.message), Toast.LENGTH_SHORT)
                            .show()
                        s2.startActivity(Intent(s2,Home_Screen::class.java))
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
fun DefaultPreview2() {
    TrialTheme {
       post()
    }
}