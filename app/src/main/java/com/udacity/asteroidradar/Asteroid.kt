package com.udacity.asteroidradar

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import java.time.LocalDate
import java.util.*

@Parcelize
@Entity(tableName = "asteroid_doom_table", indices = [Index(value = ["codename"], unique = true)])
data class Asteroid(@PrimaryKey(autoGenerate = true) val id: Long = 0L,
                    @Json(name = "name")
                    val codename: String,
                    val closeApproachDate: String,
                    val absoluteMagnitude: Double,
                    val estimatedDiameter: Double,
                    val relativeVelocity: Double,
                    val distanceFromEarth: Double,
                    val isPotentiallyHazardous: Boolean) : Parcelable