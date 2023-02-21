package com.example.calcapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
    companion object{
         var listCalcResult= mutableSetOf<CalcResult>()
    }

    private lateinit var mainActivityViewModel:MainActivityViewModel
    private  var tvInput: TextView?=null
    private lateinit var btnHistory:Button
    var lastNumeric: Boolean = false
    var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        mainActivityViewModel= ViewModelProvider(this).get(MainActivityViewModel::class.java)

        btnHistory.setOnClickListener {
            val intent=Intent(this,HistoryActivity::class.java)
            startActivity(intent)

        }
        mainActivityViewModel.updateTv(tvInput?.text.toString())
        mainActivityViewModel.resultCalcLiveData.observe(this, Observer{
            tvInput?.text=it.toString()
        })


    }

    override fun onResume() {
        val calcResult=intent.getParcelableExtra<CalcResult>("calcResult")
        tvInput?.text=calcResult?.operation
        lastNumeric=true
        super.onStart()
        super.onResume()
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
            if (lastNumeric && !isOperatorAdded(it.toString())) {
                tvInput?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
            mainActivityViewModel.updateTv(tvInput?.text.toString())
        }

    }

    private fun isOperatorAdded(value: String): Boolean {
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
                if (tvValue.startsWith("-")) {
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }
                if (tvValue.contains("-")) {
                    val splitValue = tvValue.split("-")
                    var one = splitValue[0]  //99
                    val two = splitValue[1]   //1

                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }

                    tvInput?.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                    mainActivityViewModel.updateTv(tvInput?.text.toString())
                    listCalcResult.add(CalcResult("$one-$two","=${tvInput?.text}"))
                } else if (tvValue.contains("+")) {
                    val splitValue = tvValue.split("+")
                    var one = splitValue[0]
                    val two = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }

                    tvInput?.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                    mainActivityViewModel.updateTv(tvInput?.text.toString())

                    listCalcResult.add(CalcResult("$one+$two","=${tvInput?.text}"))
                } else if (tvValue.contains("*")) {

                    val splitValue = tvValue.split("*")
                    var one = splitValue[0]  //99
                    val two = splitValue[1]   //1

                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }

                    tvInput?.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                    mainActivityViewModel.updateTv(tvInput?.text.toString())
                    listCalcResult.add(CalcResult("$one*$two","=${tvInput?.text}"))

                } else if (tvValue.contains("/")) {
                    val splitValue = tvValue.split("/")
                    var one = splitValue[0]  //99
                    val two = splitValue[1]   //1

                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }

                    tvInput?.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                    mainActivityViewModel.updateTv(tvInput?.text.toString())
                    listCalcResult.add(CalcResult("$one/$two","=${tvInput?.text}"))
                }


            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    private fun removeZeroAfterDot(result: String): String {
        var value = result
        if (result.contains(".0"))
            value = result.substring(0, result.length - 2)

        return value
    }

    fun init() {
        tvInput = findViewById(R.id.tvInput)
        btnHistory=findViewById(R.id.btnHistory)

    }
}