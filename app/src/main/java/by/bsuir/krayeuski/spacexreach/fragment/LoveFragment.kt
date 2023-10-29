package by.bsuir.krayeuski.spacexreach.fragment


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoveFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var spaceXViewModel: SpaceXViewModel
    private lateinit var adapter: LaunchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        spaceXViewModel = SpaceXViewModel(SpaceXEventStorage(requireContext()))
        adapter = LaunchAdapter(spaceXViewModel)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_love, container, false)
        recyclerView = view.findViewById(R.id.launchRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        loadData()

        return view
    }

    private fun loadData() {
        val spaceXApiService = SpaceXApiClient.spaceXApiService
        val call = spaceXApiService.getAllLaunches()

        call.enqueue(object : Callback<List<SpaceXObject>> {
            override fun onResponse(call: Call<List<SpaceXObject>>, response: Response<List<SpaceXObject>>) {
                if (response.isSuccessful) {
                    val spaceXLaunches = response.body()
                    adapter.updateData(spaceXLaunches)

                } else {
                    // Обработка ошибки
                }
            }

            override fun onFailure(call: Call<List<SpaceXObject>>, t: Throwable) {
                // Обработка ошибки
            }
        })
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
            imageButtonHeart.setOnClickListener {
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
                            power = launch.rocket,
                            destination = launch.rocket
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