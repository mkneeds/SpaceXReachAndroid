package by.bsuir.krayeuski.spacexreach.api

import by.bsuir.krayeuski.spacexreach.model.SpaceXObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.http.GET

object SpaceXApiClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.spacexdata.com/v5/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val spaceXApiService: SpaceXApiService = retrofit.create(SpaceXApiService::class.java)
}





interface SpaceXApiService {
    @GET("launches/latest")
    fun getLatestLaunch(): Call<SpaceXObject>
    @GET("launches/")
    fun getAllLaunches(): Call<List<SpaceXObject>>
}

