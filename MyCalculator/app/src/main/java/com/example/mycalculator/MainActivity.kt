package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {
    private var tvInput: TextView? = null
    var lastNumeric: Boolean = false
    var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput = findViewById(R.id.tvInput)
    }

    fun onDigit(view: View) {

        tvInput?.append((view as Button).text)
        lastNumeric = true
        lastDot = false
    }

    fun onClear(view: View) {
        tvInput?.text = ""
    }

    fun onDecimalPoint(view: View) {
        if (lastNumeric && !lastDot) {
            tvInput?.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onOperator(view: View) {
        tvInput?.text?.let {
            if (lastNumeric && !isOperatorAdded(it.toString())) {  //if last value is digit and any operator hasn't added yet
                tvInput?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
        }
    }

    private fun isOperatorAdded(value: String): Boolean {  // to make sure only one operator added at the same time
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("/") || value.contains("*") || value.contains("+") || value.contains("-")
        }
    }

    fun onEqual(view: View) {
        if (lastNumeric) {
            var tvValue = tvInput?.text.toString()
            var prefix = ""
            try {
                if (tvValue.startsWith("-")) {    //if try subtract from negative number
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }

                if (tvValue.contains("-")) {
                    val splitValue = tvValue.split("-")
                    //99-1
                    var one = splitValue[0]  //99
                    var two = splitValue[1]  //1

                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }

                    tvInput?.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                } else if (tvValue.contains("+")) {  //Addition
                    val splitValue = tvValue.split("+")
                    //99-1
                    var one = splitValue[0]  //99
                    var two = splitValue[1]  //1

                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }


                    tvInput?.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                } else if (tvValue.contains("/")) {  //Division
                    val splitValue = tvValue.split("/")
                    //99-1
                    var one = splitValue[0]  //99
                    var two = splitValue[1]  //1

                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }


                    tvInput?.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                } else if (tvValue.contains("*")) {  //Multiplication
                    val splitValue = tvValue.split("*")
                    //99-1
                    var one = splitValue[0]  //99
                    var two = splitValue[1]  //1

                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }


                    tvInput?.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                }
            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    private fun removeZeroAfterDot(result: String): String {
        var value = result
        if (result.contains(".0")) {
            value = result.substring(0, result.length - 2)
        }
        return value
    }
}