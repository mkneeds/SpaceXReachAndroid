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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.widget.ConstraintLayout
import by.bsuir.krayeuski.spacexreach.model.Rocket
import by.bsuir.krayeuski.spacexreach.model.SpaceXEvent
import by.bsuir.krayeuski.spacexreach.model.SpaceXViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: SpaceXViewModel
) {
    var items by remember { mutableStateOf(viewModel.spaceXEvents) }


    val showDialog = remember { mutableStateOf(false) }
    val isEditDialog = remember { mutableStateOf(false) }
    val selectedEvent = remember { mutableStateOf<SpaceXEvent?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "SpaceX Events") },
                actions = {
                    IconButton(onClick = { showDialog.value = true; isEditDialog.value = false }) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    }
                },

                )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog.value = true; isEditDialog.value = false },
                modifier = Modifier
                    .padding(end = 0.dp, bottom = 50.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                SpaceXEventList(
                    events = items,
                    onRemove = { event ->
                        viewModel.removeSpaceXEvent(event)
                        items = items.filterNot { it == event }

                    },


                            onEdit = { event ->
                        selectedEvent.value = event
                        showDialog.value = true
                        isEditDialog.value = true
                    }
                )
            }
        }
    )
    
    if (showDialog.value) {
        val eventToEdit = selectedEvent.value
        if (isEditDialog.value && eventToEdit != null) {
            EditSpaceXEventDialog(
                onDismiss = { showDialog.value = false },
                onSave = { title, date, description, rocket ->

                    val editedEvent = SpaceXEvent(
                        id = eventToEdit.id,
                        title = title,
                        date = date,
                        description = description,
                        rocket = rocket
                    )
                    viewModel.editSpaceXEvent(
                        editedEvent.id,
                        editedEvent.title,
                        editedEvent.date,
                        editedEvent.description,
                        editedEvent.rocket
                    )
                    showDialog.value = false
                    items = items.map { if (it.id == editedEvent.id) editedEvent else it }
                },
                eventToEdit = eventToEdit
            )

        } else {
            AddSpaceXEventDialog(
                onDismiss = { showDialog.value = false },
                onSave = { title, date, description, rocket ->
                    viewModel.addSpaceXEvent(title, date, description, rocket)

                    showDialog.value = false
                    items = viewModel.spaceXEvents
                }
            )
        }
    }
}









@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddSpaceXEventDialog(
    onDismiss: () -> Unit,
    onSave: (String, String, String, Rocket) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var rocketName by remember { mutableStateOf("") }
    var rocketPower by remember { mutableStateOf("") }
    var rocketDestination by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = {
            onDismiss()
        },
        title = {
            Text(text = "Add Event")
        },
        confirmButton = {
            Button(
                onClick = {
                    val rocket = Rocket(rocketName, rocketPower, rocketDestination)
                    onSave(title, date, description, rocket)
                    onDismiss()
                }
            ) {
                Text(text = "Save")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismiss()
                }
            ) {
                Text(text = "Cancel")
            }
        },
        text = {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") }
                )
                TextField(
                    value = date,
                    onValueChange = { date = it },
                    label = { Text("Date") }
                )
                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") }
                )

                TextField(
                    value = rocketName,
                    onValueChange = { rocketName = it },
                    label = { Text("Rocket Name") }
                )
                TextField(
                    value = rocketPower,
                    onValueChange = { rocketPower = it },
                    label = { Text("Rocket Power") }
                )
                TextField(
                    value = rocketDestination,
                    onValueChange = { rocketDestination = it },
                    label = { Text("Rocket Destination") }
                )
            }
        }
    )
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditSpaceXEventDialog(
    onDismiss: () -> Unit,
    onSave: (String, String, String, Rocket) -> Unit,
    eventToEdit: SpaceXEvent? //
) {

    val isEditing = eventToEdit != null


    var title by remember { mutableStateOf(eventToEdit?.title.orEmpty()) }
    var date by remember { mutableStateOf(eventToEdit?.date.orEmpty()) }
    var description by remember { mutableStateOf(eventToEdit?.description.orEmpty()) }
    var rocketName by remember { mutableStateOf(eventToEdit?.rocket?.name.orEmpty()) }
    var rocketPower by remember { mutableStateOf(eventToEdit?.rocket?.power.orEmpty()) }
    var rocketDestination by remember { mutableStateOf(eventToEdit?.rocket?.destination.orEmpty()) }

    AlertDialog(
        onDismissRequest = {
            onDismiss()
        },
        title = {
            Text(text = if (isEditing) "Edit Event" else "Add Event")
        },
        confirmButton = {
            Button(
                onClick = {
                    val rocket = Rocket(rocketName, rocketPower, rocketDestination)
                    onSave(title, date, description, rocket)
                    onDismiss()
                }
            ) {
                Text(text = "Save")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismiss()
                }
            ) {
                Text(text = "Cancel")
            }
        },
        text = {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") }
                )
                TextField(
                    value = date,
                    onValueChange = { date = it },
                    label = { Text("Date") }
                )
                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") }
                )
                // Поля для информации о ракете
                TextField(
                    value = rocketName,
                    onValueChange = { rocketName = it },
                    label = { Text("Rocket Name") }
                )
                TextField(
                    value = rocketPower,
                    onValueChange = { rocketPower = it },
                    label = { Text("Rocket Power") }
                )
                TextField(
                    value = rocketDestination,
                    onValueChange = { rocketDestination = it },
                    label = { Text("Rocket Destination") }
                )
            }
        }
    )
}

@Composable
private fun SpaceXEventList(
    events: List<SpaceXEvent>,
    onRemove: (SpaceXEvent) -> Unit,
    onEdit: (SpaceXEvent) -> Unit
) {
    LazyColumn {
        item {
            Spacer(modifier = Modifier.height(45.dp))
        }
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




