package com.kirillmesh.carsdbwithroom.domain

class GetCarUseCase(private val carsRepository: CarsRepository) {

    suspend operator fun invoke(carId: Long): Car {
        return carsRepository.getCar(carId)
    }
}