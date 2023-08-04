package com.kirillmesh.carsdbwithroom.data

import androidx.room.*
import com.kirillmesh.carsdbwithroom.domain.Car
import kotlinx.coroutines.flow.Flow

@Dao
interface CarDAO {

    @Insert
    suspend fun addCar(car: Car): Long

    @Update
    suspend fun updateCar(car: Car)

    @Delete
    suspend fun deleteCar(car: Car)

    @Query(
        "select * from cars  order by " +
            "case when :isAsc = 1 then price end asc, " +
            "case when :isAsc = 2 then price end desc"
    )
    fun getAllCarsSortedByPrice(isAsc: Int): Flow<List<Car>>

    @Query(
        "select exists (select * from cars)"
    )
    fun checkTable(): Boolean

    @Query("select * from cars where id ==:carId")
    suspend fun getCar(carId: Long): Car

    @Query(
        "select * from cars where brand LIKE '%' || :brandName || '%' order by " +
            "case when :isAsc = 1 then price end asc, " +
            "case when :isAsc = 2 then price end desc"
    )
    fun getCarsBrandSortedByPrice(brandName: String, isAsc: Int): Flow<List<Car>>

    @Query(
        "select * from cars where model LIKE '%' || :modelName || '%' order by " +
            "case when :isAsc = 1 then price end asc, " +
            "case when :isAsc = 2 then price end desc"
    )
    fun getCarsModelSortedByPrice(modelName: String, isAsc: Int): Flow<List<Car>>

    @Query(
        "select * from cars where model LIKE '%' || :modelName || '%' and " +
            "brand LIKE '%' || :brandName || '%' order by " +
            "case when :isAsc = 1 then price end asc, " +
            "case when :isAsc = 2 then price end desc"
    )
    fun getCarsBrandModelSortedByPrice(
        brandName: String,
        modelName: String,
        isAsc: Int
    ): Flow<List<Car>>
}