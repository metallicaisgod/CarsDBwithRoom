package com.kirillmesh.carsdbwithroom

import data.CarsAppDatabase
import model.Car
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private lateinit var carsAdapter: CarsAdapter
    private val cars: ArrayList<Car> = ArrayList()
    private lateinit var recyclerView: RecyclerView
    private lateinit var carsAppDatabase: CarsAppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        carsAppDatabase =
            Room.databaseBuilder(applicationContext, CarsAppDatabase::class.java, "CarsDB")
                .build()


        carsAdapter = CarsAdapter(this, cars, this@MainActivity)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = carsAdapter

        lifecycleScope.launch {
            cars.addAll(carsAppDatabase.getCarDAO().getAllCars())
            carsAdapter.notifyItemRangeInserted(0, cars.size - 1)
        }

        val floatingActionButton =
            findViewById<View>(R.id.floatingActionButton) as FloatingActionButton
        floatingActionButton.setOnClickListener{
                addAndEditCars(false, null, -1)
            }
    }

    fun addAndEditCars(isUpdate: Boolean, car: Car?, position: Int) {
        val layoutInflaterAndroid = LayoutInflater.from(applicationContext)
        val view: View = layoutInflaterAndroid.inflate(R.layout.layout_add_car, null)
        val alertDialogBuilderUserInput  = AlertDialog.Builder(this@MainActivity)
        alertDialogBuilderUserInput.setView(view)
        val newCarTitle: TextView = view.findViewById(R.id.newCarTitle)
        val nameEditText: EditText = view.findViewById(R.id.nameEditText)
        val priceEditText: EditText = view.findViewById(R.id.priceEditText)
        newCarTitle.text = if (!isUpdate) "Add Car" else "Edit Car"
        if (isUpdate && car != null) {
            nameEditText.setText(car.name)
            priceEditText.setText(car.price)
        }
        alertDialogBuilderUserInput
            .setCancelable(false)
            .setPositiveButton(if (isUpdate) "Update" else "Save"
            ) { _, _ -> }
            .setNegativeButton(if (isUpdate) "Delete" else "Cancel"
            ) { dialogBox, _ ->
                if (isUpdate) {
                    deleteCar(car!!, position)
                } else {
                    dialogBox.cancel()
                }
            }
        val alertDialog: AlertDialog = alertDialogBuilderUserInput.create()
        alertDialog.show()
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
            .setOnClickListener{
                    if (TextUtils.isEmpty(nameEditText.text.toString())) {
                        Toast.makeText(this@MainActivity, "Enter car name!", Toast.LENGTH_SHORT)
                            .show()
                       // return
                    } else if (TextUtils.isEmpty(priceEditText.text.toString())) {
                        Toast.makeText(this@MainActivity, "Enter car price!", Toast.LENGTH_SHORT)
                            .show()
                        //return
                    } else {
                        alertDialog.dismiss()
                    }
                    if (isUpdate && car != null) {
                        updateCar(
                            nameEditText.text.toString(),
                            priceEditText.text.toString(),
                            position
                        )
                    } else {
                        createCar(nameEditText.text.toString(), priceEditText.text.toString())
                    }
                }
    }

    private fun deleteCar(car: Car, position: Int) {
        lifecycleScope.launch {
            cars.removeAt(position)
            carsAppDatabase.getCarDAO().deleteCar(car)
            carsAdapter.notifyItemRemoved(position)
            carsAdapter.notifyItemRangeChanged(position, cars.size)
        }
    }

    private fun updateCar(name: String, price: String, position: Int) {
        lifecycleScope.launch {
            val car: Car = cars[position]
            car.name = name
            car.price = price
            carsAppDatabase.getCarDAO().updateCar(car)
            cars[position] = car
            carsAdapter.notifyItemChanged(position)
        }
    }

    private fun createCar(name: String, price: String) {
        lifecycleScope.launch {
            val id: Long = carsAppDatabase.getCarDAO().addCar(Car(0, name, price))
            val car: Car = carsAppDatabase.getCarDAO().getCar(id)
            cars.add(car)
            carsAdapter.notifyItemInserted(cars.size - 1)
        }
    }
}