package com.kirillmesh.carsdbwithroom.data

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kirillmesh.carsdbwithroom.domain.Car

@Database(entities = [Car::class], version = 1, exportSchema = false)
abstract class CarsAppDatabase : RoomDatabase() {

    abstract fun getCarDAO(): CarDAO

    companion object {
        private var INSTANCE: CarsAppDatabase? = null
        private val LOCK = Any()
        private const val DB_NAME = "cars.db"

        fun geInstance(application: Application): CarsAppDatabase {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }
                val db = Room.databaseBuilder(
                    application,
                    CarsAppDatabase::class.java,
                    DB_NAME
                )
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = db
                return db
            }
        }
    }
}