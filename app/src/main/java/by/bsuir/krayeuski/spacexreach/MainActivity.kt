package by.bsuir.krayeuski.spacexreach

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import by.bsuir.krayeuski.spacexreach.ui.theme.SpaceXReachTheme
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions


import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AboutScreen()
        }
    }
}

@Composable
fun AboutScreen() {

    val developerName = stringResource(id = R.string.developer_name)
    val appFacts = stringResource(id = R.string.app_facts)
    val fact1 = stringResource(id = R.string.fact_1)
    val fact2 = stringResource(id = R.string.fact_2)
    val fact3 = stringResource(id = R.string.fact_3, developerName)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.adminphoto),
                contentDescription = "Изображение разработчика",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
            )
        }



        Text(
            text = "Kraevsky V.Yu.",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(R.color.cosmic_color) ,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = "Developer SpaceXReach",
            fontSize = 16.sp,
            color = Color(R.color.cosmic_color),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))


        Text(
            text = "About the app:",
            fontSize = 20.sp,
            color = Color(R.color.cosmic_color),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))


        FactItem(text = fact1)
        FactItem(text = fact2)
        FactItem(text = fact3)
    }
}


@Composable
fun FactItem(text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Icon(
            painter = painterResource(id = R.mipmap.ic_launcher_monochrome),
            contentDescription = null,
            tint =  Color.Black,
            modifier = Modifier.size(17.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = text, fontSize = 18.sp,  color = Color(R.color.cosmic_color))
    }
}



class AplicationSpaceXReach : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@AplicationSpaceXReach)
            modules()
        }
    }
}




@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
            text = "Привет $name!",
            modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SpaceXReachTheme {
        Greeting("Android")
    }
}