package com.kirillmesh.carsdbwithroom.data

import android.app.Application
import com.kirillmesh.carsdbwithroom.domain.Car
import com.kirillmesh.carsdbwithroom.domain.CarsRepository
import kotlinx.coroutines.flow.Flow

class CarsRepositoryImpl(application: Application) : CarsRepository {

    private val carsListDAO = CarsAppDatabase.geInstance(application).getCarDAO()

    override suspend fun addCar(car: Car): Long {

        return carsListDAO.addCar(car)
    }

    override suspend fun updateCar(car: Car) {

        carsListDAO.updateCar(car)
    }

    override suspend fun deleteCar(car: Car) {

        carsListDAO.deleteCar(car)
    }

    override suspend fun getAllCarsSortedByPrice(isAsc: Int): Flow<List<Car>> {

        return carsListDAO.getAllCarsSortedByPrice(isAsc)
    }

    override suspend fun getCar(carId: Long): Car {

        return carsListDAO.getCar(carId)
    }

    override suspend fun checkTable(): Boolean {

        return carsListDAO.checkTable()
    }

    override suspend fun getCarsBrandSortedByPrice(
        brandName: String,
        isAsc: Int
    ): Flow<List<Car>> {

        return carsListDAO.getCarsBrandSortedByPrice(brandName, isAsc)
    }

    override suspend fun getCarsModelSortedByPrice(
        modelName: String,
        isAsc: Int
    ): Flow<List<Car>> {

        return carsListDAO.getCarsModelSortedByPrice(modelName, isAsc)
    }

    override suspend fun getCarsBrandModelSortedByPrice(
        brandName: String,
        modelName: String,
        isAsc: Int
    ): Flow<List<Car>> {

        return carsListDAO.getCarsBrandModelSortedByPrice(brandName, modelName, isAsc)
    }
}