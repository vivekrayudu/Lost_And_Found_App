package com.example.trial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.trial.ui.theme.TrialTheme
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class userposts : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrialTheme {
                mypost()
            }
        }
    }
}

@Composable
private fun mypost() {
    val user = Firebase.auth.currentUser
    val feed = mutableListOf<DocumentSnapshot>()
    runBlocking {
        val db = Firebase.firestore
        val result = db.collection("lostfeed").get().await()
        for (document in result.documents) {
            if (user != null) {
                if(user.email== document.get("EmailAddress"))
                    feed.add(document)
            }
        }
    }
    runBlocking {
        val db = Firebase.firestore
        val resultfound = db.collection("foundfeed").get().await()
        for (document in resultfound.documents) {
            if (user != null) {
                if(user.email== document.get("EmailAddress"))
                    feed.add(document)
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black, RectangleShape)
            .wrapContentHeight(Alignment.CenterVertically)
            .wrapContentWidth(Alignment.CenterHorizontally)
    ){
        Text(
            color = Color.White,
            text = "My Posts",
            fontStyle = FontStyle.Normal,
            fontSize = 40.sp,
            modifier = Modifier.padding(12.dp),
        )
        Spacer(modifier = Modifier.padding(10.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black, RectangleShape)
                .wrapContentHeight(Alignment.CenterVertically)
                .wrapContentWidth(Alignment.CenterHorizontally)
        ){
            items(feed){doc->
                UserCard(doc)
            }
        }
    }
}

@Composable
private fun UserCard(doc:DocumentSnapshot) {
    val mContext = LocalContext.current
    val imageurl = rememberSaveable { mutableStateOf(" ") }
    var storageRef = Firebase.storage.reference
    val type=doc.get("Type") as String
    if(type=="Lost Post")
    runBlocking {
        val result=storageRef.child("lost/"+"images/${doc.get("Imageuri") as String}").downloadUrl.await()
        imageurl.value=result.toString()

    }
    else if (type == "Found Post"){
        runBlocking {
            val result=storageRef.child("found/"+"images/${doc.get("Imageuri") as String}").downloadUrl.await()
            imageurl.value=result.toString()

        }
    }
    Card(
        elevation = 4.dp,
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(
                model = imageurl.value,
                placeholder = painterResource(R.drawable.iitp_logo),
                contentDescription = null,
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = doc.get("Type").toString(),
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.padding(12.dp)
                )
                Text(
                    text = doc.get("Name").toString(),
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.padding(12.dp)
                )
                Text(
                    text = doc.get("Number").toString(),
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.padding(12.dp)
                )
                Text(
                    text = doc.get("Lost_Place").toString(),
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.padding(12.dp)
                )
                Text(
                    text = doc.get("Description").toString(),
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }

    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview6() {
    TrialTheme {
        mypost()
    }
}