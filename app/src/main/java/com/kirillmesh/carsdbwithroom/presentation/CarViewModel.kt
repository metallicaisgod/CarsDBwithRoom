package com.kirillmesh.carsdbwithroom.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kirillmesh.carsdbwithroom.data.CarsRepositoryImpl
import com.kirillmesh.carsdbwithroom.domain.AddCarUseCase
import com.kirillmesh.carsdbwithroom.domain.Car
import com.kirillmesh.carsdbwithroom.domain.CarDrive
import com.kirillmesh.carsdbwithroom.domain.CarTransmission
import com.kirillmesh.carsdbwithroom.domain.GetCarUseCase
import com.kirillmesh.carsdbwithroom.domain.UpdateCarUseCase
import kotlinx.coroutines.launch

class CarViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CarsRepositoryImpl(application)

    private val addCarUseCase = AddCarUseCase(repository)
    private val updateCarUseCase = UpdateCarUseCase(repository)
    private val getCarUseCase = GetCarUseCase(repository)

    private val _car = MutableLiveData<Car>()
    val car: LiveData<Car> = _car

    private val _closeActivity = MutableLiveData<Unit>()
    val closeActivity: LiveData<Unit> = _closeActivity

    fun getCar(carId: Long) {
        viewModelScope.launch {
            val carItem = getCarUseCase(carId)
            _car.value = carItem
        }
    }

    fun editCar(
        brandName: String?,
        modelName: String?,
        year: Int?,
        price: Int?,
        power: Int?,
        capacity: Int?,
        transmission: CarTransmission?,
        drive: CarDrive?
    ) {
        _car.value?.let {
            viewModelScope.launch {
                val carItem = it.copy(
                    brand = brandName,
                    model = modelName,
                    year = year,
                    price = price,
                    power = power,
                    capacity = capacity,
                    transmission = transmission,
                    drive = drive
                )
                updateCarUseCase(carItem)
                finishActivity()
            }
        }
    }

    fun addCar(
        brandName: String?,
        modelName: String?,
        year: Int?,
        price: Int?,
        power: Int?,
        capacity: Int?,
        transmission: CarTransmission?,
        drive: CarDrive?
    ) {
        viewModelScope.launch {
            val carItem = Car(
                brand = brandName,
                model = modelName,
                year = year,
                price = price,
                power = power,
                capacity = capacity,
                transmission = transmission,
                drive = drive
            )
            addCarUseCase(carItem)
            finishActivity()
        }
    }

    private fun finishActivity() {
        _closeActivity.value = Unit
    }
}
