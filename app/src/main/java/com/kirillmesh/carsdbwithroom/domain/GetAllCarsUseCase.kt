package com.kirillmesh.carsdbwithroom.domain

import kotlinx.coroutines.flow.Flow

class GetAllCarsUseCase(private val carsRepository: CarsRepository) {

    suspend operator fun invoke(isAsc: Int): Flow<List<Car>> {
        return carsRepository.getAllCarsSortedByPrice(isAsc)
    }
}