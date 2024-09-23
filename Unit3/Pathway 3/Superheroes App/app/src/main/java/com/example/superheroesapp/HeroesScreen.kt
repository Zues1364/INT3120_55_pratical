package com.example.superheroesapp

import SuperheroesTheme
import android.os.Bundle
import android.provider.ContactsContract.Data
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.superheroesapp.data.Hero
import com.example.superheroesapp.data.HeroesRepository.heroes


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SuperheroesTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    HeroApp()
                }
            }
        }
    }
}

@Composable
fun HeroApp() {
    Scaffold(
        topBar = { HeroTopAppBar() }
    )
    { it ->
        LazyColumn(
            contentPadding = it,
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
        ) {
            items(heroes) {
                HeroItem(
                    hero = it,
                    modifier = Modifier
                        .padding(dimensionResource(R.dimen.padding_small))
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeroTopAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.displayLarge
            )
        },
        modifier = modifier
    )
}

@Composable
fun HeroItem(hero: Hero, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .shadow(2.dp, shape = MaterialTheme.shapes.medium)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
        ) {
            HeroInformation(
                heroName = hero.nameRes,
                description = hero.descriptionRes,
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(16.dp))
            HeroIcon(
                heroIcon = hero.imageRes
            )
        }
    }
}

@Composable
fun HeroIcon(@DrawableRes heroIcon: Int, modifier: Modifier = Modifier) {
    Box(modifier = Modifier.wrapContentSize()) {
        Image(
            modifier = modifier
                .size(dimensionResource(R.dimen.image_size))
                .clip(MaterialTheme.shapes.small),
            painter = painterResource(heroIcon),
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
fun HeroInformation(
    @StringRes heroName: Int,
    @StringRes description: Int,
    modifier: Modifier = Modifier
) {

    Column(modifier = modifier) {
        Text(
            text = stringResource(heroName),
            style = MaterialTheme.typography.displaySmall
        )
        Text(
            text = stringResource(description),
            style = MaterialTheme.typography.bodyLarge,
        )
    }

}

@Preview(showBackground = true)
@Composable
fun HeroItemPreview() {
    SuperheroesTheme {
        HeroItem(
            hero = Hero(
                nameRes = R.string.hero1,
                descriptionRes = R.string.description1,
                imageRes = R.drawable.android_superhero1
            )
        )
    }
}