package com.kirillmesh.carsdbwithroom.domain

class UpdateCarUseCase(private val carsRepository: CarsRepository) {

    suspend operator fun invoke(car: Car) {
        return carsRepository.updateCar(car)
    }
}