package com.kirillmesh.carsdbwithroom.domain

class CheckTableUseCase(private val carsRepository: CarsRepository) {

    suspend operator fun invoke(): Boolean {
        return carsRepository.checkTable()
    }
}