package com.example.calcapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.calcapp.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private val historyAdapter by lazy { HistoryAdapter() }

    private var listData= mutableSetOf<CalcResult>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listData=MainActivity.listCalcResult
        historyAdapter.differ.submitList(loadData().toList())
        historyAdapter.onItemClick={
            val intent=Intent(this,MainActivity::class.java)
            intent.putExtra("calcResult",it)
            startActivity(intent)
        }


        binding.apply {
           rvHistory.apply {
               layoutManager=LinearLayoutManager(this@HistoryActivity)
               adapter=historyAdapter
           }
        }

    }
    private fun loadData() : MutableSet<CalcResult> {
        return listData
    }
}