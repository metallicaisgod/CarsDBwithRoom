package com.kirillmesh.carsdbwithroom.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kirillmesh.carsdbwithroom.R
import com.kirillmesh.carsdbwithroom.domain.Car

class CarActivity : AppCompatActivity(), CarFragment.OnEditingFinishedListener {

    private var screenMode = MODE_UNKNOWN
    private var carId = Car.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car)
        parseIntent()
        if (savedInstanceState == null) {
            launchRightMode()
        }
    }

    private fun launchRightMode() {

        val fragment = when (screenMode) {
            MODE_ADD -> CarFragment.addNewFragmentAddCar()
            MODE_EDIT -> CarFragment.addNewFragmentEditCar(carId)
            else -> throw RuntimeException("Mode is unknown $screenMode")
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.car_fragment_container, fragment)
            .commit()
    }

    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("Intent has not extra mode")
        }
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if (mode != MODE_ADD && mode != MODE_EDIT) {
            throw RuntimeException("Mode is unknown $mode")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_CAR_ID)) {
                throw RuntimeException("Intent has not extra item id")
            }
            carId = intent.getLongExtra(EXTRA_CAR_ID, Car.UNDEFINED_ID)
        }
    }

    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_CAR_ID = "extra_car_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""

        fun newIntentAddCar(context: Context): Intent {
            val intent = Intent(context, CarActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditCar(context: Context, carId: Long): Intent {
            val intent = Intent(context, CarActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_CAR_ID, carId)
            return intent
        }
    }

    override fun onEditingFinished() {
        finish()
    }
}