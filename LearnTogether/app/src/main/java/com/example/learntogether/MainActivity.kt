package com.example.learntogether

import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.learntogether.ui.theme.LearnTogetherTheme
import androidx.compose.material3.Surface


import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LearnTogetherTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LearnTogetherImage(
                        title = stringResource(R.string.title_text),
                        description1 = stringResource(R.string.description1_text),
                        description2 = stringResource(R.string.description2_text)
                    )
                }
            }
        }
    }
}

@Composable
fun LearnTogetherText(
    title: String,
    description1: String,
    description2: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,

        ) {
        Text(
            text = title,
            fontSize = 24.sp,
            modifier = Modifier
                .padding(16.dp)

        )

        Text(
            text = description1,
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp),
            textAlign = TextAlign.Justify
        )
        Text(
            text = description2,
            modifier = Modifier
                .padding(16.dp),
            textAlign = TextAlign.Justify
        )
    }
}

@Composable
fun LearnTogetherImage(
    title: String,
    description1: String,
    description2: String,

    ) {
    val image = painterResource(R.drawable.bg_compose_background)
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Column {
            Image(
                painter = image,
                contentDescription = null,
            )
            LearnTogetherText(title, description1, description2)
        }


    }
}

@Preview(showBackground = true)
@Composable
fun BirthdayCardPreview() {
    LearnTogetherTheme {
        LearnTogetherImage(
            title = stringResource(R.string.title_text),
            description1 = stringResource(R.string.description1_text),
            description2 = stringResource(R.string.description2_text)
        )
    }
}