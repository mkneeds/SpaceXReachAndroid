package by.bsuir.krayeuski.spacexreach.Navigation


import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import by.bsuir.krayeuski.spacexreach.model.Rocket
import by.bsuir.krayeuski.spacexreach.model.SpaceXEvent
import by.bsuir.krayeuski.spacexreach.model.SpaceXViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: SpaceXViewModel,

) {
    val items = viewModel.spaceXEvents


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "SpaceX Events") },
                actions = {
                    IconButton(onClick = { /* Добавление события */ }) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Добавление события */ },
                modifier = Modifier
                    .padding(end = 0.dp, bottom = 50.dp)

            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ) {
        SpaceXEventList(
            events = items,
            onRemove = { event -> viewModel.removeSpaceXEvent(event) },
            onEdit = { event -> /* Навигация на экран редактирования события */ }
        )
    }
}

@Composable
private fun SpaceXEventList(
    events: List<SpaceXEvent>,
    onRemove: (SpaceXEvent) -> Unit,
    onEdit: (SpaceXEvent) -> Unit
) {
    LazyColumn {
        items(events) { event ->
            SpaceXEventItem(
                event = event,
                onRemove = { onRemove(event) },
                onEdit = { onEdit(event) }
            )
        }
    }
}

@Composable
private fun SpaceXEventItem(
    event: SpaceXEvent,
    onRemove: () -> Unit,
    onEdit: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable { /* Обработка нажатия на карточку события */ }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(text = event.title, fontWeight = FontWeight.Bold)
            Text(text = event.date)
            Text(text = event.description)
            Text(text = "Rocket: ${event.rocket.name}, Power: ${event.rocket.power}, Destination: ${event.rocket.destination}")

            Spacer(modifier = Modifier.height(16.dp))

            Row {
                IconButton(
                    onClick = onRemove,
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                }
                IconButton(
                    onClick = onEdit
                ) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = null)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEventScreen() {
    var title by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var rocketName by remember { mutableStateOf("") }
    var rocketPower by remember { mutableStateOf("") }
    var rocketDestination by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Добавление события", modifier = Modifier.padding(8.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Заголовок") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        OutlinedTextField(
            value = date,
            onValueChange = { date = it },
            label = { Text("Дата") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Описание") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        Text(text = "Информация о ракете", modifier = Modifier.padding(8.dp))

        OutlinedTextField(
            value = rocketName,
            onValueChange = { rocketName = it },
            label = { Text("Название ракеты") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        OutlinedTextField(
            value = rocketPower,
            onValueChange = { rocketPower = it },
            label = { Text("Мощность ракеты") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        OutlinedTextField(
            value = rocketDestination,
            onValueChange = { rocketDestination = it },
            label = { Text("Назначение ракеты") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )





    }
}




