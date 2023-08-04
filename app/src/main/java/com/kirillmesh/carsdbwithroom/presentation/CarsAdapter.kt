package com.kirillmesh.carsdbwithroom.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kirillmesh.carsdbwithroom.databinding.CarListItemBinding
import com.kirillmesh.carsdbwithroom.domain.Car
import com.kirillmesh.carsdbwithroom.domain.CarDrive
import com.kirillmesh.carsdbwithroom.domain.CarTransmission

class CarsAdapter : ListAdapter<Car, CarsAdapter.CarViewHolder>(CarsDiffCallback()) {

    var onCarClickListener: ((Car) -> Unit)? = null

    class CarViewHolder(
        val binding: CarListItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val binding = CarListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val car = getItem(position)
        val binding = holder.binding
        with(binding) {
            brandTextView.text = car.brand
            modelTextView.text = car.model
            yearTextView.text = car.year.toString()
            priceTextView.text = car.price.toString()
            powerTextView.text = car.power.toString()
            capacityTextView.text = car.capacity.toString()
            transmissionTextView.text = car.transmission?.name ?: CarTransmission.MT.name
            driveTextView.text = car.drive?.name ?: CarDrive.FRONT.name

            holder.itemView.setOnClickListener {
                onCarClickListener?.invoke(car)
            }
        }
    }
}