package model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cars")
data class Car (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "car_id")
    var id: Long = 0,
    @ColumnInfo(name = "car_name")
    var name: String? = null,
    @ColumnInfo(name = "car_price")
    var price: String? = null

)