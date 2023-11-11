package by.bsuir.krayeuski.spacexreach.fragment


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.text.buildSpannedString
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.bsuir.krayeuski.spacexreach.R
import by.bsuir.krayeuski.spacexreach.api.SpaceXApiClient
import by.bsuir.krayeuski.spacexreach.model.Rocket
import by.bsuir.krayeuski.spacexreach.model.SpaceXEventStorage
import by.bsuir.krayeuski.spacexreach.model.SpaceXObject
import by.bsuir.krayeuski.spacexreach.model.SpaceXViewModel
import by.bsuir.krayeuski.spacexreach.Navigation.HomeScreen
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoveFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var spaceXViewModel: SpaceXViewModel
    private lateinit var adapter: LaunchAdapter
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        spaceXViewModel = SpaceXViewModel(SpaceXEventStorage(requireContext()))
        adapter = LaunchAdapter(spaceXViewModel)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if (!isNetworkAvailable()) {
            view?.let {
                Snackbar.make(it, "Отсутствует интернет-соединение", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        return capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_love, container, false)
        recyclerView = view.findViewById(R.id.launchRecyclerView)
        progressBar = view.findViewById(R.id.progressBar)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        loadData()

        return view
    }

    private fun loadData() {
        progressBar.visibility = View.VISIBLE
        if (isNetworkAvailable()) {

        val spaceXApiService = SpaceXApiClient.spaceXApiService
        val call = spaceXApiService.getAllLaunches()

        call.enqueue(object : Callback<List<SpaceXObject>> {
            override fun onResponse(call: Call<List<SpaceXObject>>, response: Response<List<SpaceXObject>>) {
                progressBar.visibility = View.GONE
                if (response.isSuccessful) {
                    val spaceXLaunches = response.body()
                    adapter.updateData(spaceXLaunches)

                } else {

                }
            }

            override fun onFailure(call: Call<List<SpaceXObject>>, t: Throwable) {

            }
        })}else{
            view?.let {
                Snackbar.make(it, "Отсутствует интернет-соединение", Snackbar.LENGTH_LONG).show()
            }
        }
    }

}






class LaunchAdapter(private val spaceXViewModel: SpaceXViewModel) : RecyclerView.Adapter<LaunchAdapter.LaunchViewHolder>() {
    private val launches: MutableList<SpaceXObject> = mutableListOf()

    inner class LaunchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        private val textViewDate: TextView = itemView.findViewById(R.id.textViewDate)
        private val textViewDescription: TextView = itemView.findViewById(R.id.textViewDescription)
        private val textViewRocketName: TextView = itemView.findViewById(R.id.textViewRocketName)
        private val textViewMissionName: TextView = itemView.findViewById(R.id.textViewMissionName)
        private val imageButtonHeart: ImageButton = itemView.findViewById(R.id.imageButtonHeart)


        fun bind(launch: SpaceXObject) {
            textViewName.text = "Name: ${launch.name}"
            textViewDate.text = "Date: ${launch.date_utc}"
            textViewDescription.text = "Description: ${launch.details}"
            textViewRocketName.text = "Rocket Name: ${launch.rocket}"
            textViewMissionName.text = "Mission Name: ${launch.name}"
            if (spaceXViewModel.isEventInList(launch.name)) {
                imageButtonHeart.setImageResource(R.drawable.ic_hear_full)
            }
            imageButtonHeart.setOnClickListener {
                spaceXViewModel.removeSpaceXEventByName(launch.name)
                if (launch.isFavorite) {
                    imageButtonHeart.setImageResource(R.drawable.ic_heart)


                } else {
                    imageButtonHeart.setImageResource(R.drawable.ic_hear_full)
                    spaceXViewModel.addSpaceXEvent(
                        launch.name,
                        launch.date_utc,
                        launch.details,
                        rocket = Rocket(
                            name = launch.rocket,
                            power = launch.links.article.toString(),
                            destination = launch.links.webcast.toString()
                        )
                    )
                    spaceXViewModel.spaceXEvents
                }
                launch.isFavorite = !launch.isFavorite
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_launch, parent, false)
        return LaunchViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LaunchViewHolder, position: Int) {
        holder.bind(launches[position])
    }

    override fun getItemCount(): Int {
        return launches.size
    }

    fun updateData(newData: List<SpaceXObject>?) {
        launches.clear()
        if (newData != null) {
            launches.addAll(newData)
        }
        notifyDataSetChanged()
    }
}