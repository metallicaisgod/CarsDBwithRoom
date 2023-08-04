package com.kirillmesh.carsdbwithroom.domain

import kotlinx.coroutines.flow.Flow

class GetBrandModelUseCase(private val carsRepository: CarsRepository) {

    suspend operator fun invoke(brandName: String, modelName: String, isAsc: Int): Flow<List<Car>> {
        return carsRepository.getCarsBrandModelSortedByPrice(brandName, modelName, isAsc)
    }
}