package com.kirillmesh.carsdbwithroom.presentation

import androidx.recyclerview.widget.DiffUtil
import com.kirillmesh.carsdbwithroom.domain.Car

class CarsDiffCallback : DiffUtil.ItemCallback<Car>() {

    override fun areItemsTheSame(oldItem: Car, newItem: Car): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Car, newItem: Car): Boolean {
        return oldItem == newItem
    }
}