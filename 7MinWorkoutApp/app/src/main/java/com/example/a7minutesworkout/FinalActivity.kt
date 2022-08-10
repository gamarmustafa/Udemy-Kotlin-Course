package com.example.a7minutesworkout


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.a7minutesworkout.databinding.ActivityFinalBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class FinalActivity : AppCompatActivity() {
    private var binding :ActivityFinalBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinalBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        val HistoryDao = (application as WorkoutApp).db.historyDao()
        addDateToDatebase(HistoryDao)
        setSupportActionBar(binding?.toolbarExercise)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarExercise?.setNavigationOnClickListener {
            onBackPressed()
        }


        binding?.btnFinish?.setOnClickListener{
            finish()

        }
    }

    private fun addDateToDatebase(historyDao: HistoryDao){
        val c = Calendar.getInstance()
        val dateTime = c.time
        Log.e("Date: ",""+dateTime)
        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
        val date = sdf.format(dateTime)
        Log.e("Formatted Date: ",""+date)



        lifecycleScope.launch {
            historyDao.insert(HistoryEntity(date))
            Log.e("Date..:","Added..")
        }
    }
}