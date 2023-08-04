package com.kirillmesh.carsdbwithroom.domain

import kotlinx.coroutines.flow.Flow

class GetModelUseCase(private val carsRepository: CarsRepository) {

    suspend operator fun invoke(modelName: String, isAsc: Int): Flow<List<Car>> {
        return carsRepository.getCarsModelSortedByPrice(modelName, isAsc)
    }
}