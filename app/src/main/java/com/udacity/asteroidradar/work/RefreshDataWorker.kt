package com.udacity.asteroidradar.work

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.repository.AsteroidRepository
import java.lang.Exception

class RefreshDataWorker(appContext: Context, params: WorkerParameters): CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val asteroidDatabase = AsteroidDatabase.getInstance(applicationContext)
        val asteroidRepository = AsteroidRepository(asteroidDatabase)
        Log.i("Asteroids", "Worker working")
        return try {
            asteroidRepository.refreshAsteroids()
            Log.i("Asteroids", "Success")
            Result.success()
        } catch (e: Exception) {
            Log.i("Asteroids", "Fail")
            Result.retry()
        }

    }
}