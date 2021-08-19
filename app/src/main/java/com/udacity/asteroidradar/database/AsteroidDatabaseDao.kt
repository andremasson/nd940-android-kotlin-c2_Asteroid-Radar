package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.udacity.asteroidradar.Asteroid

@Dao
interface AsteroidDatabaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg asteroids: Asteroid)

    @Update
    suspend fun update(asteroid: Asteroid)

    @Query("SELECT * from asteroid_doom_table WHERE id = :key")
    suspend fun get(key: Long): Asteroid?

    @Query("DELETE from asteroid_doom_table")
    suspend fun clear()

    @Query("SELECT * FROM asteroid_doom_table where closeApproachDate >= date() ORDER BY closeApproachDate")
    fun getAllAsteroids(): LiveData<List<Asteroid>>

}