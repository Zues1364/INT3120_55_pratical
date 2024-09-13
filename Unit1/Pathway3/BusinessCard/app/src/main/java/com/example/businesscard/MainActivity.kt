package com.example.businesscard

import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.businesscard.ui.theme.BusinessCardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BusinessCardTheme {
                BusinessCardImage(
                    name = stringResource(R.string.name_text),
                    title = stringResource(R.string.title_text),
                    number = stringResource(R.string.phone_text),
                    social = stringResource(R.string.descrip_text),
                    email = stringResource(R.string.email_text)
                )
            }
        }
    }
}

@Composable
fun BusinessCardImage(
    name: String,
    title: String,
    number: String,
    social: String,
    email: String,
) {
    val image = painterResource(R.drawable.android_logo)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Green.copy(alpha = 0.16f)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            Spacer(modifier = Modifier.weight(1f))

            Box(contentAlignment = Alignment.Center) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(modifier = Modifier.background(color = Color.Black)) {
                        Image(
                            painter = image,
                            contentDescription = null,
                            modifier = Modifier
                                .size(116.dp)
                                .padding(bottom = 16.dp),
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Information(name, title)
                }
            }
            Spacer(modifier = Modifier.weight(1f))

            Box(contentAlignment = Alignment.Center) {
                ContactInformation(number, social, email)
            }
        }
    }
}

@Composable
fun Information(name: String, title: String, modifier: Modifier = Modifier) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = name,
            modifier = modifier,
            fontSize = 30.sp,
        )
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF3ddc84),
            fontSize = 18.sp
        )
    }
}

@Composable
fun ContactInformation(
    number: String,
    social: String,
    email: String,
) {
    val phoneImage = painterResource(R.drawable.smartphone)
    val socialImage = painterResource(R.drawable.share)
    val mailImage = painterResource(R.drawable.mail)

    Column {
        Row(modifier = Modifier.padding(8.dp)) {
            Image(
                painter = phoneImage,
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = number,
                fontSize = 18.sp
            )
        }
        Row(modifier = Modifier.padding(8.dp)) {
            Image(
                painter = socialImage,
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = social,
                fontSize = 18.sp
            )
        }

        Row(modifier = Modifier.padding(8.dp)) {
            Image(
                painter = mailImage,
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = email,
                fontSize = 18.sp
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BusinessCardTheme {
        BusinessCardImage(
            name = stringResource(R.string.name_text),
            title = stringResource(R.string.title_text),
            number = stringResource(R.string.phone_text),
            social = stringResource(R.string.descrip_text),
            email = stringResource(R.string.email_text)
        )
    }
}