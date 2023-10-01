package by.bsuir.krayeuski.spacexreach.model
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID // Добавьте импорт UUID для генерации уникальных идентификаторов
data class SpaceXEvent(
    val id: String, // Уникальный идентификатор события
    val title: String, // Название события
    val date: String, // Дата события
    val description: String, // Описание события
    val rocket: Rocket // Информация о ракете
)

data class Rocket(
    val name: String, // Название ракеты
    val power: String, // Мощность ракеты
    val destination: String // Точка назначения
)




class SpaceXViewModel : ViewModel() {

    private val _spaceXEvents = mutableListOf<SpaceXEvent>()
    val spaceXEvents: List<SpaceXEvent> get() = _spaceXEvents.toList()

    fun addSpaceXEvent(title: String, date: String, description: String, rocket: Rocket) {
        val id = UUID.randomUUID().toString() // Генерируем уникальный идентификатор
        val event = SpaceXEvent(id, title, date, description, rocket)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _spaceXEvents.add(event)
            }
        }
    }


    fun removeSpaceXEvent(event: SpaceXEvent) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _spaceXEvents.remove(event)
            }
        }
    }
}

