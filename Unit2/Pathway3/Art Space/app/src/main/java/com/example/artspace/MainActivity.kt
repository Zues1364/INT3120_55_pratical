package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtSpaceTheme {

                ArtSpaceLayout()

            }
        }
    }
}

@Composable

fun ArtSpaceLayout() {
    var res by remember { mutableStateOf(0) }
    val result = res % 3
    val imageResource = when (result) {
        0 -> R.drawable.mona_lisa
        1 -> R.drawable.starry_night_over_the_rhone
        2 -> R.drawable.van_gogh___starry_night___google_art_project
        else -> R.drawable.mona_lisa
    }
    val nameResource = when (result) {
        0 -> "Mona Lisa"
        1 -> "Starry Night Over the Rhône"
        2 -> "The Starry Night"
        else -> "Mona Lisa"
    }
    val artistResource = when (result) {
        0 -> "Leonardo Da Vinci (1503–1506)"
        1 -> "Vincent van Gogh (1888)"
        2 -> "Vincent van Gogh (1889)"
        else -> "Leonardo Da Vinci (1503–1506)"
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Box(
            modifier = Modifier
                .weight(1f)
                .wrapContentSize()
                .padding(16.dp)
                .background(color = Color(0xFFF5FAFA))
                .shadow(elevation = 8.dp, shape = RectangleShape)


        ) {

            Box(

                modifier = Modifier
                    .size(400.dp)
                    .padding(36.dp)
                    .align(Alignment.Center)
            ) {

                Image(
                    painter = painterResource(imageResource),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxSize()

                )

            }

        }

        Box(
            modifier = Modifier
                .wrapContentSize().background(color = Color(0xFFC1DAD6))
        ) {
            Spacer(modifier = Modifier.height(16.dp))


            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = nameResource,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Black.copy(alpha = 0.7f),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = artistResource,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black.copy(alpha = 0.7f),
                    fontSize = 16.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { res-- },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6D929B),
                )
            ) {
                Text("Previous")
            }
            Button(
                onClick = { res++ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6D929B)
                )
            ) {
                Text("Next")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArtSpacePreview() {
    ArtSpaceTheme {
        ArtSpaceLayout()
    }
}