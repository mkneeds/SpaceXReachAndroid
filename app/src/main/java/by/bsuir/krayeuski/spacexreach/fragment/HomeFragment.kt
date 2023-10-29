import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.compose.ui.platform.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import by.bsuir.krayeuski.spacexreach.Navigation.HomeScreen
import by.bsuir.krayeuski.spacexreach.model.SpaceXEventStorage
import by.bsuir.krayeuski.spacexreach.model.SpaceXViewModel

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val context = requireContext()
        val storage = SpaceXEventStorage(context)
        val viewModelFactory = SpaceXViewModelFactory(storage)
        val viewModel: SpaceXViewModel by viewModels { viewModelFactory }

        return ComposeView(requireContext()).apply {
            setContent {
                HomeScreen(viewModel)
            }
        }
    }
}


class SpaceXViewModelFactory(private val storage: SpaceXEventStorage) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SpaceXViewModel::class.java)) {
            return SpaceXViewModel(storage) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

