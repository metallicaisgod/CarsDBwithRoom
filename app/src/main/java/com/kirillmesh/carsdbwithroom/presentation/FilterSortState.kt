package com.kirillmesh.carsdbwithroom.presentation

data class FilterSortState(

    var brandName: String? = null,
    var modelName: String? = null,
    var isAsc: Boolean = true
)
