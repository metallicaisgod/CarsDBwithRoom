package com.kirillmesh.carsdbwithroom.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kirillmesh.carsdbwithroom.R
import com.kirillmesh.carsdbwithroom.databinding.ActivityMainBinding
import com.kirillmesh.carsdbwithroom.databinding.FilterDialogBinding


class MainActivity : AppCompatActivity(), CarFragment.OnEditingFinishedListener {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    private lateinit var carsAdapter: CarsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if (binding.carFragmentContainer == null)
            supportFragmentManager.popBackStack()
        setupRecyclerView()
        observeViewModel()

        binding.floatingActionButton.setOnClickListener {
            if (isOnePanelMode()) {
                val intent = CarActivity.newIntentAddCar(this)
                startActivity(intent)
            } else {
                launchFragment(CarFragment.addNewFragmentAddCar())
            }
        }
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.car_fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun isOnePanelMode(): Boolean {
        return binding.carFragmentContainer == null
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.filter_item -> {
                showFilterDialog()
                true
            }

            R.id.sort_item -> {
                viewModel.setSort()
                val drawableRes = if (viewModel.sortFlow.value.isAsc) {
                    R.drawable.baseline_sort_asc_24
                } else {
                    R.drawable.baseline_sort_desc_24
                }
                item.icon = getDrawable(drawableRes)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showFilterDialog() {

        val bindingFilterDialog = FilterDialogBinding.inflate(layoutInflater)
        bindingFilterDialog.filterBrandEditText.setText(viewModel.sortFlow.value.brandName ?: "")
        bindingFilterDialog.filterModelEditText.setText(viewModel.sortFlow.value.modelName ?: "")

        val alertDialog = AlertDialog.Builder(this@MainActivity).apply {
            setView(bindingFilterDialog.root)
            setCancelable(false)
            setPositiveButton("Ok") { _, which ->
                if (which == AlertDialog.BUTTON_POSITIVE) {
                    val brand = bindingFilterDialog.filterBrandEditText.text.toString()
                    val model = bindingFilterDialog.filterModelEditText.text.toString()
                    viewModel.setFilter(
                        brand.ifEmpty { null },
                        model.ifEmpty { null }
                    )
                }
            }
            setNegativeButton("Cancel") { _, _ -> }
        }.create()
        alertDialog.show()

    }

    private fun observeViewModel() {
        viewModel.carsList.observe(this) {
            carsAdapter.submitList(it)
        }
    }

    private fun setupRecyclerView() {
        carsAdapter = CarsAdapter()
        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(applicationContext)
            itemAnimator = DefaultItemAnimator()
            adapter = carsAdapter
        }
        setupClickListener()
        setupSwipeListener(binding.recyclerView)
    }

    private fun setupClickListener() {
        carsAdapter.onCarClickListener = {
            if (isOnePanelMode()) {
                val intent = CarActivity.newIntentEditCar(this, it.id)
                startActivity(intent)
            } else {
                launchFragment(CarFragment.addNewFragmentEditCar(it.id))
            }
        }
    }

    private fun setupSwipeListener(recyclerView: RecyclerView) {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val car = carsAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteCar(car)
                Toast.makeText(
                    this@MainActivity,
                    getString(R.string.car_deleted, car.brand, car.model),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        ).attachToRecyclerView(recyclerView)
    }

    override fun onEditingFinished() {
        Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
    }
}