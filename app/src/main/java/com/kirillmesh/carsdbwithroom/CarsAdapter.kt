package com.kirillmesh.carsdbwithroom

import model.Car
import android.content.Context

import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup

import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView


class CarsAdapter(context: Context, cars: ArrayList<Car>, mainActivity: MainActivity) :
    RecyclerView.Adapter<CarsAdapter.MyViewHolder>() {
    private val context: Context
    private val cars: ArrayList<Car>
    private val mainActivity: MainActivity

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var nameTextView: TextView
        var priceTextView: TextView

        init {
            nameTextView = view.findViewById(R.id.nameTextView)
            priceTextView = view.findViewById(R.id.priceTextView)
        }
    }

    init {
        this.context = context
        this.cars = cars
        this.mainActivity = mainActivity
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View =
            LayoutInflater.from(parent.context).inflate(R.layout.car_list_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val car: Car = cars[position]
        holder.nameTextView.text = car.name
        holder.priceTextView.text = context.getString(R.string.add_dollar_sign, car.price)
        holder.itemView.setOnClickListener{
                mainActivity.addAndEditCars(true, car, position)
            }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
    }

    override fun getItemCount(): Int {
        return cars.size
    }
}