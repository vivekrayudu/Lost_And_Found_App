package com.example.trial

import android.R.attr.data
import android.R.attr.password
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage
import android.widget.Button
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity


class Lost_feed : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrialTheme {
                lost_feed()
            }
        }
    }
}





@Composable
fun lost_feed(){
    val feed = mutableListOf<DocumentSnapshot>()
    runBlocking {
        val db = Firebase.firestore
        val result = db.collection("lostfeed").get().await()
        for (document in result.documents) {
            feed.add(document)
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
            text = "Lost Feed",
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
    //val imageuri=
    var storageRef = Firebase.storage.reference
    runBlocking {
        val result=storageRef.child("lost/"+"images/${doc.get("Imageuri") as String}").downloadUrl.await()
        imageurl.value=result.toString()

    }
    Card(
        elevation = 4.dp,
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Text(
            text = doc.get("Type").toString(),
            fontSize = 20.sp,
            color = Color.White,
            modifier = Modifier.padding(12.dp)
        )

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
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color.Black,
                elevation = 8.dp
            ) {
            Button(
                onClick = {
                    val user: FirebaseUser? = Firebase.auth.currentUser
                    val from= user?.email
                    val to=doc.get("EmailAddress") as String
                    val subject = "YOUR ITEM HAS BEEN FOUND!!"
                    val message = "Your item as been found"
                    val intent =Intent(Intent.ACTION_SEND)
                        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
                        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
                        intent.putExtra(Intent.EXTRA_TEXT, message)
                    intent.setType("message/rfc822");
                    mContext.startActivity(intent)
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.DarkGray,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp)
            )
            {
                Text(text = "I Found It")
            }
        }
        }

    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview3() {
    TrialTheme {
        lost_feed()
    }
}