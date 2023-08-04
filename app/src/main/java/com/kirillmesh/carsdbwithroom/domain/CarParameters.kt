package com.kirillmesh.carsdbwithroom.domain

import androidx.room.TypeConverter

enum class CarTransmission {
    MT, AT, RT, VT
}

class CarTransmissionConverter {

    @TypeConverter
    fun toCarTransmission(value: Int) = enumValues<CarTransmission>()[value]

    @TypeConverter
    fun fromCarTransmission(value: CarTransmission) = value.ordinal
}

enum class CarDrive {
    FRONT, REAR, FULL
}

class CarDriveConverter {

    @TypeConverter
    fun toCarDrive(value: Int) = enumValues<CarDrive>()[value]

    @TypeConverter
    fun fromCarDrive(value: CarDrive) = value.ordinal
}