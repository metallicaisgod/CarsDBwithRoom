package com.kirillmesh.carsdbwithroom.domain

class DeleteCarUseCase(private val carsRepository: CarsRepository) {

    suspend operator fun invoke(car: Car) {
        return carsRepository.deleteCar(car)
    }
}