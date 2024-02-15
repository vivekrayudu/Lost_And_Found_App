package com.example.trial

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.trial.ui.theme.TrialTheme
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class Home_Screen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrialTheme {
                    homescreen()

            }
        }
    }
}


@Composable
fun homescreen() {
    val image= painterResource(id = R.drawable.laf2)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black, RectangleShape)
            .wrapContentHeight(Alignment.CenterVertically)
            .wrapContentWidth(Alignment.CenterHorizontally)
    ){
        Image(
            painter = image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .size(200.dp)
                .wrapContentWidth(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.padding(10.dp))
        lost()
        Spacer(modifier = Modifier.padding(10.dp))
        found()
        Spacer(modifier = Modifier.padding(10.dp))
        feedlost()
        Spacer(modifier = Modifier.padding(10.dp))
        feedfound()
        Spacer(modifier = Modifier.padding(10.dp))
        myposts()
        Spacer(modifier = Modifier.padding(10.dp))
        changepassword()
    }

}

@Composable
fun lost(){
    val mContext = LocalContext.current
    Surface(
        modifier =Modifier.fillMaxWidth(),
        color= Color.Black,
        elevation=8.dp
    ){
        Button(onClick = {
                         mContext.startActivity(Intent(mContext,lostpost::class.java))
        }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black, contentColor = Color.White), shape = RoundedCornerShape(8.dp)) {
            Text(
                text = "Post for lost item",
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )
        }
    }
}
@Composable
fun found(){
    val mContext = LocalContext.current
    Surface(
        modifier =Modifier.fillMaxWidth(),
        color= Color.Black,
        elevation=8.dp
    ){
        Button(onClick = {
            mContext.startActivity(Intent(mContext,foundpost::class.java))
        }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black, contentColor = Color.White), shape = RoundedCornerShape(20.dp)) {
            Text(
                text = "Post for found item",
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )
        }
    }

}
@Composable
fun feedlost(){
    val mContext = LocalContext.current
    Surface(
        modifier =Modifier.fillMaxWidth(),
        color= Color.Black,
        elevation=8.dp
    ){
        Button(onClick = {
            mContext.startActivity(Intent(mContext, Lost_feed::class.java))
        }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black, contentColor = Color.White), shape = RoundedCornerShape(8.dp)) {
            Text(
                text = "Feed for lost items",
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )
        }
    }
}
@Composable
fun feedfound(){
    val mContext = LocalContext.current
    Surface(
        modifier =Modifier.fillMaxWidth(),
        color= Color.Black,
        elevation=8.dp
    ){
        Button(onClick = {
            mContext.startActivity(Intent(mContext, Found_Feed::class.java))
        }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black, contentColor = Color.White), shape = RoundedCornerShape(8.dp)) {
            Text(
                text = "Feed for found items",
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )
        }
    }
}
@Composable
fun myposts(){
    val mContext = LocalContext.current
    Surface(
        modifier =Modifier.fillMaxWidth(),
        color= Color.Black,
        elevation=8.dp
    ){
        Button(onClick = {
            mContext.startActivity(Intent(mContext, userposts::class.java))
        }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black, contentColor = Color.White), shape = RoundedCornerShape(8.dp)) {
            Text(
                text = "My Posts",
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )
        }
    }
}
@Composable
fun changepassword(){
    val mContext = LocalContext.current
    Surface(
        modifier =Modifier.fillMaxWidth(),
        color= Color.Black,
        elevation=8.dp
    ){
        Button(onClick = {
            mContext.startActivity(Intent(mContext, changepass::class.java))
        }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black, contentColor = Color.White), shape = RoundedCornerShape(8.dp)) {
            Text(
                text = "Change Password",
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun homescreenPreview() {
    TrialTheme {
        homescreen()
    }
}