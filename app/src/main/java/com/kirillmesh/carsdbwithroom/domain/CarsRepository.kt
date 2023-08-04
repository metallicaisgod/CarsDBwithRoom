package com.kirillmesh.carsdbwithroom.domain

import kotlinx.coroutines.flow.Flow

interface CarsRepository {

    suspend fun addCar(car: Car): Long

    suspend fun updateCar(car: Car)

    suspend fun deleteCar(car: Car)

    suspend fun getAllCarsSortedByPrice(isAsc: Int): Flow<List<Car>>

    suspend fun getCar(carId: Long): Car

    suspend fun checkTable(): Boolean

    suspend fun getCarsBrandSortedByPrice(brandName: String, isAsc: Int): Flow<List<Car>>

    suspend fun getCarsModelSortedByPrice(modelName: String, isAsc: Int): Flow<List<Car>>

    suspend fun getCarsBrandModelSortedByPrice(
        brandName: String,
        modelName: String,
        isAsc: Int
    ): Flow<List<Car>>
}