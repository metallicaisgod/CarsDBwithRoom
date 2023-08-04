package com.kirillmesh.carsdbwithroom.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "cars")
@TypeConverters(CarTransmissionConverter::class, CarDriveConverter::class)
data class Car(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = UNDEFINED_ID,
    @ColumnInfo(name = "brand")
    var brand: String? = null,
    @ColumnInfo(name = "model")
    var model: String? = null,
    @ColumnInfo(name = "year")
    var year: Int? = null,
    @ColumnInfo(name = "price")
    var price: Int? = null,
    @ColumnInfo(name = "power")
    var power: Int? = null,
    @ColumnInfo(name = "capacity")
    var capacity: Int? = null,
    @ColumnInfo(name = "transmission")
    var transmission: CarTransmission? = null,
    @ColumnInfo(name = "drive")
    var drive: CarDrive? = null
) {
    companion object {
        const val UNDEFINED_ID = 0L
    }
}