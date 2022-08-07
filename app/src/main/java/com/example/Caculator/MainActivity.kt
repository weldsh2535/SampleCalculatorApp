package com.example.Caculator

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
     var lastNumeric:Boolean = false
    var lastDot:Boolean = false
    lateinit var tvInput:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvInput = findViewById<TextView>(R.id.tvInput)
    }
    fun onDigit(view: View) {
        tvInput.append((view as Button).text)
        lastNumeric = true
    }
    fun onClear(view:View){
        tvInput.text = ""
        lastDot = false
        lastNumeric = false
    }
    fun onDecimalPoint(view:View) {
        if(lastNumeric && !lastDot){
            tvInput.append(".")
            lastNumeric=false
            lastDot=true
        }
    }
    fun onOperator(view:View){
        if(lastNumeric && !isOperatorAdded(tvInput.text.toString())){
            tvInput.append((view as Button).text)
        }
    }
    fun onEqual(view:View){
        if(lastNumeric){
            var tvValue = tvInput.text.toString()
            var prefix = ""
            try{
                if(tvValue.startsWith("-")){
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }
                if(tvValue.contains("-")){
                    val splitValue = tvValue.split("-")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    Log.d("MyTag","The split == $two")
                    if(!two.isEmpty()) {
                        if (!prefix.isEmpty()) {
                            one = prefix + one
                        }
                        tvInput.text =
                            removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                    }
                    else{
                       toastMessage()
                    }
                } else  if(tvValue.contains("*")){
                    val splitValue = tvValue.split("*")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if(!two.isEmpty()) {
                        if (!prefix.isEmpty()) {
                            one = prefix + one
                        }
                        tvInput.text =
                            removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                    }
                    else{
                        toastMessage()
                    }
                }else  if(tvValue.contains("/")){
                    val splitValue = tvValue.split("/")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    Log.d("MyTag","$one")
                    Log.d("MyTag","$two")
                    if(!two.isEmpty()) {
                        if (!prefix.isEmpty()) {
                            one = prefix + one
                        }
                        tvInput.text =
                            removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                    }
                    else{
                        toastMessage()
                    }
                }else  if(tvValue.contains("+")){
                    val splitValue = tvValue.split("+")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    Log.d("MyTag","$one")
                    if(!two.isEmpty()) {
                        if (!prefix.isEmpty()) {
                            one = prefix + one
                        }
                        tvInput.text =
                            removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                    }else{
                        toastMessage()
                    }
                }else  if(tvValue.contains("%")){
                    val splitValue = tvValue.split("%")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if(!two.isEmpty()) {
                        if (!prefix.isEmpty()) {
                            one = prefix + one
                        }
                        tvInput.text =
                            removeZeroAfterDot((one.toDouble() % two.toDouble()).toString())
                    }else{
                        toastMessage()
                    }
                }

            }catch (e:ArithmeticException){
                e.printStackTrace()
            }
        }
    }
    private fun removeZeroAfterDot(result:String):String{
        var value = result
        if(result.contains(".0"))
            value = result.substring(0,result.length-2)
        return value
    }
    private fun isOperatorAdded(value:String):Boolean{
        return if(value.startsWith("-")){
            false
        }else{
            value.contains("/")||value.contains("*")
                    ||value.contains("+")||value.contains("-")
        }
    }
    fun toastMessage(){
        Toast.makeText(
            this@MainActivity,
            "Invalid format used.",
            Toast.LENGTH_SHORT
        ).show()
    }
}