package by.bsuir.krayeuski.spacexreach.model

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID


data class SpaceXEvent(
    val id: String,
    var title: String,
    var date: String,
    var description: String,
    var rocket: Rocket
)

data class Rocket(
    val name: String,
    val power: String,
    val destination: String
)

class SpaceXEventStorage(context: Context) {

    private val fileName = "spacex_events.json"

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveSpaceXEvents(events: List<SpaceXEvent>) {
        val json = gson.toJson(events)
        sharedPreferences.edit {
            putString(fileName, json) // Use the constant file name
        }
    }

    fun loadSpaceXEvents(): List<SpaceXEvent> {
        val json = sharedPreferences.getString(fileName, null)
        return if (json != null) {
            gson.fromJson(json, Array<SpaceXEvent>::class.java).toList()
        } else {
            emptyList()
        }
    }
}


class SpaceXViewModel(private val storage: SpaceXEventStorage) : ViewModel() {

    private val _spaceXEvents = mutableListOf<SpaceXEvent>()
    val spaceXEvents: List<SpaceXEvent> get() = _spaceXEvents.toList()

    init {

        _spaceXEvents.addAll(storage.loadSpaceXEvents())
    }

    fun addSpaceXEvent(title: String, date: String, description: String, rocket: Rocket) {
        val id = UUID.randomUUID().toString()
        val event = SpaceXEvent(id, title, date, description, rocket)
        _spaceXEvents.add(event)

        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                storage.saveSpaceXEvents(_spaceXEvents)
            }
        }
    }


    fun editSpaceXEvent(eventId: String, title: String, date: String, description: String, rocket: Rocket) {
        val existingEvent = _spaceXEvents.find { it.id == eventId }
        existingEvent?.let { event ->
            event.title = title
            event.date = date
            event.description = description
            event.rocket = rocket

            CoroutineScope(Dispatchers.IO).launch {
                withContext(Dispatchers.IO) {

                    storage.saveSpaceXEvents(_spaceXEvents)
                }
            }
        }
    }

    fun removeSpaceXEvent(event: SpaceXEvent) {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                _spaceXEvents.remove(event)
                storage.saveSpaceXEvents(_spaceXEvents)
            }

        }
    }

    fun isEventInList(eventName: String): Boolean {
        return _spaceXEvents.any { it.title == eventName }
    }
    fun removeSpaceXEventByName(eventName: String) {
        val eventToRemove = _spaceXEvents.find { it.title == eventName }
        eventToRemove?.let { event ->
            CoroutineScope(Dispatchers.IO).launch {
                withContext(Dispatchers.IO) {
                    _spaceXEvents.remove(event)
                    storage.saveSpaceXEvents(_spaceXEvents)
                }
            }
        }
    }


}
