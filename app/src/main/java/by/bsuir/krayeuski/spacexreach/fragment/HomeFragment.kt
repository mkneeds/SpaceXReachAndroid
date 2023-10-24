import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.*
import androidx.compose.ui.platform.*
import androidx.lifecycle.ViewModel
import by.bsuir.krayeuski.spacexreach.Navigation.HomeScreen
import by.bsuir.krayeuski.spacexreach.model.SpaceXEventStorage
import by.bsuir.krayeuski.spacexreach.model.SpaceXViewModel

import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val context = requireContext()


        val storage = SpaceXEventStorage(context)


        val viewModelFactory = SpaceXViewModelFactory(storage)

        return ComposeView(requireContext()).apply {
            setContent {

                val viewModel: SpaceXViewModel = viewModel(
                    factory = viewModelFactory,
                    modelClass = SpaceXViewModel::class.java
                )

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

