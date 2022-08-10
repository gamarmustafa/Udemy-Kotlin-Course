package com.example.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minutesworkout.databinding.ActivityHistoryBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

private var binding: ActivityHistoryBinding? = null

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarHistory)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "HISTORY"
        }
        binding?.toolbarHistory?.setNavigationOnClickListener {
            onBackPressed()
        }
        val HistoryDao = (application as WorkoutApp).db.historyDao()
        getAllCompletedDates(HistoryDao)
    }

    private fun getAllCompletedDates(historyDao: HistoryDao){
        lifecycleScope.launch{
            historyDao.fetchAllDates().collect{allCompletedDatesList ->
            if(allCompletedDatesList.isNotEmpty()){

                binding?.tvHistory?.visibility = View.VISIBLE
                binding?.rvHistory?.visibility = View.VISIBLE
                binding?.tvNoDataAvailable?.visibility = View.INVISIBLE

                binding?.rvHistory?.layoutManager = LinearLayoutManager(this@HistoryActivity)
                val dates = ArrayList<String>()
                for (date in allCompletedDatesList){
                    dates.add(date.date)
                }

                val historyAdapter = HistoryAdapter(dates)
                binding?.rvHistory?.adapter = historyAdapter


            }
            else{
                binding?.tvHistory?.visibility = View.GONE
                binding?.rvHistory?.visibility = View.GONE
                binding?.tvNoDataAvailable?.visibility = View.INVISIBLE
            }

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding=null
    }
}