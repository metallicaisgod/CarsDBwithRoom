package com.kirillmesh.carsdbwithroom.domain

class AddCarUseCase(private val carsRepository: CarsRepository) {

    suspend operator fun invoke(car: Car): Long {
        return carsRepository.addCar(car)
    }
}