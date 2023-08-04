package com.kirillmesh.carsdbwithroom.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.kirillmesh.carsdbwithroom.data.CarsRepositoryImpl
import com.kirillmesh.carsdbwithroom.domain.AddCarUseCase
import com.kirillmesh.carsdbwithroom.domain.Car
import com.kirillmesh.carsdbwithroom.domain.CarDrive
import com.kirillmesh.carsdbwithroom.domain.CarTransmission
import com.kirillmesh.carsdbwithroom.domain.CheckTableUseCase
import com.kirillmesh.carsdbwithroom.domain.DeleteCarUseCase
import com.kirillmesh.carsdbwithroom.domain.GetAllCarsUseCase
import com.kirillmesh.carsdbwithroom.domain.GetBrandModelUseCase
import com.kirillmesh.carsdbwithroom.domain.GetBrandUseCase
import com.kirillmesh.carsdbwithroom.domain.GetModelUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CarsRepositoryImpl(application)

    private val addCarUseCase = AddCarUseCase(repository)
    private val deleteCarUseCase = DeleteCarUseCase(repository)
    private val getAllCarsUseCase = GetAllCarsUseCase(repository)
    private val getBrandUseCase = GetBrandUseCase(repository)
    private val getModelUseCase = GetModelUseCase(repository)
    private val getBrandModelUseCase = GetBrandModelUseCase(repository)
    private val checkTableUseCase = CheckTableUseCase(repository)

    val sortFlow = MutableStateFlow(FilterSortState())

    @OptIn(ExperimentalCoroutinesApi::class)
    val carsListFlow = sortFlow.flatMapLatest {

        val isAscInt = if (it.isAsc) 1 else 2
        if (it.brandName != null) {
            if (it.modelName != null) {
                getBrandModelUseCase(it.brandName!!, it.modelName!!, isAscInt)
            } else {
                Log.d("MainViewModel", "getBrandUseCase")
                getBrandUseCase(it.brandName!!, isAscInt)
            }
        } else {
            if (it.modelName != null) {
                getModelUseCase(it.modelName!!, isAscInt)
            } else {
                Log.d("MainViewModel", "getAllCarsUseCase2")
                getAllCarsUseCase(isAscInt)
            }
        }
    }

    val carsList = carsListFlow.asLiveData()

    init {
        viewModelScope.launch {
            if (!checkTableUseCase()) {
                Log.d("MainViewModel", "table is empty")
                startList.forEach {
                    addCarUseCase(it)
                }
            }
        }
    }

    fun setFilter(brandName: String?, modelName: String?) {

        val curState = sortFlow.value
        sortFlow.value = curState.copy(
            brandName = brandName,
            modelName = modelName
        )
    }

    fun setSort() {

        val curState = sortFlow.value
        val curSort = curState.isAsc
        sortFlow.value = curState.copy(
            isAsc = !curSort
        )
    }

    fun deleteCar(car: Car) {
        viewModelScope.launch {
            deleteCarUseCase.invoke(car)
        }
    }

    companion object {

        private val startList = listOf(
            Car(
                brand = "Skoda",
                model = "Octavia",
                year = 2019,
                price = 1_800_000,
                power = 150,
                capacity = 1400,
                transmission = CarTransmission.RT,
                drive = CarDrive.FRONT
            ),
            Car(
                brand = "Skoda",
                model = "Rapid",
                year = 2017,
                price = 1_200_000,
                power = 90,
                capacity = 1600,
                transmission = CarTransmission.AT,
                drive = CarDrive.FRONT
            ),
            Car(
                brand = "Skoda",
                model = "Kodiaq",
                year = 2020,
                price = 2_200_000,
                power = 179,
                capacity = 1800,
                transmission = CarTransmission.RT,
                drive = CarDrive.FULL
            ),
            Car(
                brand = "BMW",
                model = "3-series M340i",
                year = 2020,
                price = 5_480_000,
                power = 387,
                capacity = 3000,
                transmission = CarTransmission.AT,
                drive = CarDrive.REAR
            ),
            Car(
                brand = "BMW",
                model = "5-series530i",
                year = 2019,
                price = 5_480_000,
                power = 249,
                capacity = 2000,
                transmission = CarTransmission.AT,
                drive = CarDrive.FULL
            ),
            Car(
                brand = "BMW",
                model = "5-series530i",
                year = 2019,
                price = 5_480_000,
                power = 249,
                capacity = 2000,
                transmission = CarTransmission.AT,
                drive = CarDrive.FULL
            ),
            Car(
                brand = "Volkswagen",
                model = "Passat",
                year = 2019,
                price = 3_200_000,
                power = 240,
                capacity = 2000,
                transmission = CarTransmission.RT,
                drive = CarDrive.FULL
            ),
            Car(
                brand = "Volkswagen",
                model = "Passat",
                year = 2020,
                price = 2_630_000,
                power = 150,
                capacity = 1400,
                transmission = CarTransmission.RT,
                drive = CarDrive.FRONT
            ),
            Car(
                brand = "Volkswagen",
                model = "Polo",
                year = 2016,
                price = 839_000,
                power = 110,
                capacity = 1600,
                transmission = CarTransmission.MT,
                drive = CarDrive.FRONT
            ),
            Car(
                brand = "Mercedes-Benz",
                model = "C-class",
                year = 2022,
                price = 6_690_000,
                power = 224,
                capacity = 1500,
                transmission = CarTransmission.AT,
                drive = CarDrive.FULL
            ),
            Car(
                brand = "Mercedes-Benz",
                model = "G-class",
                year = 2019,
                price = 15_817_305,
                power = 422,
                capacity = 4000,
                transmission = CarTransmission.AT,
                drive = CarDrive.FULL
            )
        )
    }
}