package data

import model.Car
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ Car::class ], version = 1)
abstract class CarsAppDatabase: RoomDatabase(){

    abstract fun getCarDAO(): CarDAO

}