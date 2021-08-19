package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val API_KEY = "[API_KEY_HERE]"

private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

private val retrofitAsteroids = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .baseUrl(Constants.BASE_URL)
        .build()

private val retrofitPictureOfTheDay = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(Constants.BASE_URL)
        .build()

interface AsteroidsApiService {
    @GET("neo/rest/v1/feed")
    fun getAsteroids(
            @Query("start_date") startDate: String,
            @Query("end_date") endDate: String,
            @Query("api_key") apiKey: String = API_KEY): Call<String>
}

interface PictureOfTheDayApiService {
    @GET("planetary/apod")
    fun getImageOfTheDay(@Query("api_key") apiKey: String = API_KEY): Call<PictureOfDay>
}

object NasaApi {
    val asteroidsService: AsteroidsApiService by lazy { retrofitAsteroids.create(AsteroidsApiService::class.java) }
    val pictureService: PictureOfTheDayApiService by lazy { retrofitPictureOfTheDay.create(PictureOfTheDayApiService::class.java) }
}
