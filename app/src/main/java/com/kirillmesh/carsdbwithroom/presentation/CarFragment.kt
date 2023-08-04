package com.kirillmesh.carsdbwithroom.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kirillmesh.carsdbwithroom.databinding.FragmentCarBinding
import com.kirillmesh.carsdbwithroom.domain.Car
import com.kirillmesh.carsdbwithroom.domain.CarDrive
import com.kirillmesh.carsdbwithroom.domain.CarTransmission


class CarFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this)[CarViewModel::class.java]
    }

    private lateinit var onEditingFinishedListener: OnEditingFinishedListener

    private var _binding: FragmentCarBinding? = null
    private val binding: FragmentCarBinding
        get() = _binding ?: throw RuntimeException("FragmentCarBinding == null")


    private var screenMode: String = MODE_UNKNOWN
    private var carId: Long = Car.UNDEFINED_ID

    interface OnEditingFinishedListener {
        fun onEditingFinished()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnEditingFinishedListener) {
            onEditingFinishedListener = context
        } else {
            throw RuntimeException("Activity must implement OnEditingFinishedListener interface")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    private fun parseParams() {
        val args = requireArguments()
        if (!args.containsKey(SCREEN_MODE)) {
            throw RuntimeException("Intent has not extra mode")
        }
        val mode = args.getString(SCREEN_MODE)
        if (mode != MODE_ADD && mode != MODE_EDIT) {
            throw RuntimeException("Mode is unknown $mode")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!args.containsKey(CAR_ID)) {
                throw RuntimeException("Intent has not extra item id")
            }
            carId = args.getLong(CAR_ID, Car.UNDEFINED_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSpinners()
        launchRightMode()
        observeViewModel()
    }

    private fun initSpinners() {
        binding.transmissionSpinner.adapter =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                CarTransmission.values()
            )
        binding.driveSpinner.adapter =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                CarDrive.values()
            )
    }

    private fun launchRightMode() {
        when (screenMode) {
            MODE_ADD -> launchAddMode()
            MODE_EDIT -> launchEditMode()
        }
    }

    private fun launchEditMode() {
        viewModel.car.observe(viewLifecycleOwner) {
            binding.tietBrand.setText(it.brand)
            binding.tietModel.setText(it.model)
            binding.tietYear.setText(it.year.toString())
            binding.tietPrice.setText(it.price.toString())
            binding.tietPower.setText(it.power.toString())
            binding.tietCapacity.setText(it.capacity.toString())
            binding.transmissionSpinner.setSelection(it.transmission?.ordinal ?: 0)
            binding.driveSpinner.setSelection(it.drive?.ordinal ?: 0)
        }
        viewModel.getCar(carId)
        binding.saveButton.setOnClickListener {
            viewModel.editCar(
                binding.tietBrand.text.toString(),
                binding.tietModel.text.toString(),
                binding.tietYear.text.toString().toInt(),
                binding.tietPrice.text.toString().toInt(),
                binding.tietPower.text.toString().toInt(),
                binding.tietCapacity.text.toString().toInt(),
                binding.transmissionSpinner.selectedItem as CarTransmission,
                binding.driveSpinner.selectedItem as CarDrive
            )
        }
    }

    private fun launchAddMode() {
        binding.saveButton.setOnClickListener {
            viewModel.addCar(
                binding.tietBrand.text.toString(),
                binding.tietModel.text.toString(),
                binding.tietYear.text.toString().toInt(),
                binding.tietPrice.text.toString().toInt(),
                binding.tietPower.text.toString().toInt(),
                binding.tietCapacity.text.toString().toInt(),
                binding.transmissionSpinner.selectedItem as CarTransmission,
                binding.driveSpinner.selectedItem as CarDrive
            )
        }
    }

    private fun observeViewModel() {
        viewModel.closeActivity.observe(viewLifecycleOwner) {
            onEditingFinishedListener.onEditingFinished()
        }
    }

    companion object {

        private const val SCREEN_MODE = "extra_mode"
        private const val CAR_ID = "extra_shop_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""

        fun addNewFragmentAddCar(): CarFragment {
            return CarFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_ADD)
                }
            }
        }

        fun addNewFragmentEditCar(carId: Long): CarFragment {
            return CarFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_EDIT)
                    putLong(CAR_ID, carId)
                }
            }
        }
    }
}