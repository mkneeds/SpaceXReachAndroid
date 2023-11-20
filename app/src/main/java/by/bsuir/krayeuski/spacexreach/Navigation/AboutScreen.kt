package by.bsuir.krayeuski.spacexreach.Navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import by.bsuir.krayeuski.spacexreach.R
import com.ismaeldivita.chipnavigation.ChipNavigationBar


@Composable
internal fun AboutScreen() {

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

