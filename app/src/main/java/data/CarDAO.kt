package data

import model.Car
import androidx.room.*

@Dao
interface CarDAO {

    @Insert
    suspend fun addCar(car: Car): Long

    @Update
    suspend fun updateCar(car: Car)

    @Delete
    suspend fun deleteCar(car: Car)

    @Query("select * from cars")
    suspend fun getAllCars(): List<Car>

    @Query("select * from cars where car_id ==:carId")
    suspend fun getCar(carId: Long): Car
}