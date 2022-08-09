package com.example.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

import com.example.a7minutesworkout.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    companion object {
        private const val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW"
        private const val IMPERIAL_UNITS_VIEW = "IMPERIAL_UNIT_VIEW"
    }

    private var currentVisibleView: String = METRIC_UNITS_VIEW

    private var binding: ActivityBmiBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarBmiActivity)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "Calculate Body Mass Index"
        }
        binding?.toolbarBmiActivity?.setNavigationOnClickListener {
            onBackPressed()
        }
        binding?.btnCalculateUnits?.setOnClickListener {
            calculateUnits()
        }

        makeVisibleMetricUnitsView()

        binding?.rgUnits?.setOnCheckedChangeListener { _, checkedId: Int ->

            if (checkedId == R.id.rbMetricUnits) {
                makeVisibleMetricUnitsView()
            } else {
                makeVisibleImperialUnitsView()
            }
        }
    }

    private fun makeVisibleMetricUnitsView() {
        currentVisibleView = METRIC_UNITS_VIEW

        binding?.tilMetricUnitWeight?.visibility = View.VISIBLE
        binding?.tilMetricUnitHeight?.visibility = View.VISIBLE
        binding?.tilImperialFeet?.visibility = View.GONE
        binding?.tilImperialInch?.visibility = View.GONE
        binding?.tilImperialUnitWeight?.visibility = View.GONE

        binding?.etMetricUnitHeight?.text!!.clear() //value is cleared if it is added
        binding?.etMetricUnitWeight?.text!!.clear()

        binding?.llDisplayBMIResult?.visibility = View.INVISIBLE

    }

    private fun makeVisibleImperialUnitsView() {
        currentVisibleView = IMPERIAL_UNITS_VIEW

        binding?.tilMetricUnitWeight?.visibility = View.INVISIBLE
        binding?.tilMetricUnitHeight?.visibility = View.INVISIBLE
        binding?.tilImperialFeet?.visibility = View.VISIBLE
        binding?.tilImperialInch?.visibility = View.VISIBLE
        binding?.tilImperialUnitWeight?.visibility = View.VISIBLE

        binding?.etImperialUnitWeight?.text!!.clear() //value is cleared if it is added
        binding?.etImperialFeet?.text!!.clear()
        binding?.etImperialInch?.text!!.clear()

        binding?.llDisplayBMIResult?.visibility = View.INVISIBLE

    }

    private fun calculateUnits() {
        if (currentVisibleView == METRIC_UNITS_VIEW) {
            if (validateMetricUnits()) {
                val heightValue: Float =
                    binding?.etMetricUnitHeight?.text.toString()
                        .toFloat() / 100   // to get in meters
                val weightValue: Float =
                    binding?.etMetricUnitWeight?.text.toString().toFloat()

                val bmi = weightValue / (heightValue * heightValue)
                displayBMIResult(bmi)

            } else {
                Toast.makeText(this, "Please enter valid values.", Toast.LENGTH_SHORT).show()
            }

        } else {
            if (validateImperialUnits()) {
                val feetValue: String = binding?.etImperialFeet?.text.toString()
                val inchValue: String = binding?.etImperialInch?.text.toString()
                val lbsValue: Float = binding?.etImperialUnitWeight?.text.toString().toFloat()

                val heightValue = inchValue.toFloat() + feetValue.toFloat() * 12
                val bmi = 703 * (lbsValue / (heightValue * heightValue))
                displayBMIResult(bmi)

            } else {
                Toast.makeText(this, "Please enter valid values.", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun displayBMIResult(bmi: Float) {
        val bmiLabel: String
        val bmiDescription: String

        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very severely Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0) {
            bmiLabel = "Severely Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Workout more"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take better care of yourself! Workout more"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0) {
            bmiLabel = "Obese Class | (Severely obese)"
            bmiDescription = "Oops! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class | (Very Severely obese)"
            bmiDescription = "Oops! You are in a very dangerous condition! Act now!"
        }
        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()
        binding?.llDisplayBMIResult?.visibility = View.VISIBLE
        binding?.tvBMIValue?.text = bmiValue
        binding?.tvBMIType?.text = bmiLabel
        binding?.tvBMIDescription?.text = bmiDescription

    }

    private fun validateMetricUnits(): Boolean {
        var isValid = true
        if (binding?.etMetricUnitWeight?.text.toString().isEmpty()) {
            isValid = false
        } else if (binding?.etMetricUnitHeight?.text.toString().isEmpty()) {
            isValid = false
        }
        return isValid
    }

    private fun validateImperialUnits(): Boolean {
        var isValid = true
        when {
            binding?.etImperialUnitWeight?.text.toString().isEmpty() -> {
                isValid = false
            }
            binding?.etImperialFeet?.text.toString().isEmpty() -> {
                isValid = false
            }
            binding?.etImperialInch?.text.toString().isEmpty() -> {
                isValid = false
            }
        }
        return isValid
    }

}