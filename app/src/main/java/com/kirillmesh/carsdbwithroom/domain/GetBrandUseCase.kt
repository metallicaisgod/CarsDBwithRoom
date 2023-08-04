package com.kirillmesh.carsdbwithroom.domain

import kotlinx.coroutines.flow.Flow

class GetBrandUseCase(private val carsRepository: CarsRepository) {

    suspend operator fun invoke(brandName: String, isAsc: Int): Flow<List<Car>> {
        return carsRepository.getCarsBrandSortedByPrice(brandName, isAsc)
    }
}