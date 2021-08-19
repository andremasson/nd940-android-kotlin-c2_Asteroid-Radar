package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.api.*
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.AsteroidDatabaseDao
import com.udacity.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.*
import retrofit2.await
import java.lang.Exception

class AsteroidListViewModel(private val database: AsteroidDatabaseDao, application: Application) : AndroidViewModel(application) {

    private val asteroidDatabase = AsteroidDatabase.getInstance(application)
    private val asteroidRepository = AsteroidRepository(asteroidDatabase)

    val asteroids = asteroidRepository.asteroids

    init {
        getImageOfTheDay()
    }

    private fun getImageOfTheDay() {
        viewModelScope.launch {
            try {
                val response = NasaApi.pictureService.getImageOfTheDay().await()
                _imageTitle.value = response.title
                _imageUrl.value = response.url
                _imageDescription.value = response.explanation
            } catch (e: Exception) {
                Log.i("Asteroids", "Fail getting image of the day: $e")
            }
        }
    }

    private val _imageTitle = MutableLiveData<String>()
    val imageTitle
        get() = _imageTitle

    private val _imageDescription = MutableLiveData<String>()
    val imageDescription
        get() = _imageDescription

    private val _imageUrl = MutableLiveData<String>()
    val imageUrl
        get() = _imageUrl

    private val _navigateToAsteroidDetails = MutableLiveData<Asteroid>()
    val navigateToAsteroidDetails
        get() = _navigateToAsteroidDetails

    fun onAsteroidClicked(id: Asteroid) {
        _navigateToAsteroidDetails.value = id
    }

    fun onAsteroidDetailsNavigated() {
        _navigateToAsteroidDetails.value = null
    }
}