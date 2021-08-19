package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.api.NasaApi
import com.udacity.asteroidradar.api.getLastDayFormatted
import com.udacity.asteroidradar.api.getTodayFormatted
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.await

class AsteroidRepository(private val database: AsteroidDatabase) {

    val asteroids: LiveData<List<Asteroid>> = database.asteroidDatabaseDao.getAllAsteroids()

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            val response = NasaApi.asteroidsService.getAsteroids(getTodayFormatted(), getLastDayFormatted()).await()
            val responseJSONObject = JSONObject(response)
            val asteroidsArray = parseAsteroidsJsonResult(responseJSONObject)
            database.asteroidDatabaseDao.insertAll(*asteroidsArray.toTypedArray())
        }
    }
}

