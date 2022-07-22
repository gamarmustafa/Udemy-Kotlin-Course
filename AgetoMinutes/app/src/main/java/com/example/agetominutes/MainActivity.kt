package com.example.agetominutes

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private var textviewselectedDate: TextView? = null
    private var textviewageInMinutes: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button: Button = findViewById(R.id.buttonDatePicker)
        textviewselectedDate = findViewById(R.id.SelectedDate)
        textviewageInMinutes = findViewById(R.id.ageInMinutes)
        button.setOnClickListener {
            clickDatePicker()
        }


    }

    private fun clickDatePicker() {

        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val days = myCalendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                val selectedDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
                textviewselectedDate?.text = selectedDate

                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                val theDate = sdf.parse(selectedDate)
                theDate?.let {
                    val selectedDateInMinutes =
                        theDate.time / 60000   // minutes between selected date and 1970 midnight

                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                    currentDate?.let {
                        val currentDateInMinutes =
                            currentDate.time / 60000  // minutes between now and 1970 midnight

                        val differenceInMinutes = (currentDateInMinutes - selectedDateInMinutes)

                        textviewageInMinutes?.text = differenceInMinutes.toString()
                    }

                }

            },
            year,
            month,
            days
        )

        dpd.datePicker.maxDate =
            System.currentTimeMillis() - 86400000    //to be able to choose dates only in the past
        dpd.show()

    }
}