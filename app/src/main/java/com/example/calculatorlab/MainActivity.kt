package com.example.calculatorlab

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        for(id in listOf(R.id.tv_one, R.id.tv_two, R.id.tv_three,
            R.id.tv_four, R.id.tv_five, R.id.tv_six, R.id.tv_seven,
            R.id.tv_eight, R.id.tv_nine, R.id.tv_zero, R.id.tv_plus,
            R.id.tv_minus, R.id.tv_multi,R.id.tv_del)){
            val tv = findViewById<TextView>(id)
            tv.setOnClickListener {
                addTextOnScreen(tv.text.toString())
            }
        }

        findViewById<TextView>(R.id.tv_result).setOnClickListener {
            counting()
        }

        findViewById<TextView>(R.id.tv_c).setOnClickListener {
            erasing()
        }

    }

    private fun addTextOnScreen(str: String){
        val screen = findViewById<TextView>(R.id.tv_main_view)
        if(!screen.text.contains(Regex("^[0-9\\.\\+\\-\\*\\/]+$"))){
            screen.text = ""
            screen.textSize = 40f
        }
        screen.append(str)
    }

    private fun counting(){
        val screen = findViewById<TextView>(R.id.tv_main_view)
        val expression = screen.text
        val symbols = charArrayOf('+', '-', '*', '/')
        if(symbols.any{ expression.contains(it)}){
            val els = expression.split(Regex( "[+\\-*/]"))
            if(els.count() == 2 && els[0].isNotEmpty() && els[1].isNotEmpty()){
                var op: String? = null
                for(sym in symbols){
                    if(expression.contains(sym)){
                        op = sym.toString()
                    }
                }
                screen.text = when(op){
                    "+" -> (els[0].toDouble() + els[1].toDouble()).toString()
                    "-" -> (els[0].toDouble() - els[1].toDouble()).toString()
                    "*" -> (els[0].toDouble() * els[1].toDouble()).toString()
                    "/" -> (els[0].toDouble() / els[1].toDouble()).toString()
                    else -> "None"
                }
            } else{
                screen.textSize = 20f
                screen.text = "Я умеею считать только корректные односложные операции... (◉ _ ◉)"
            }
        }
    }

    private fun erasing(){
        val screen = findViewById<TextView>(R.id.tv_main_view)
        if(screen.text.isNotEmpty()) {
            screen.text = screen.text.substring(0, screen.text.length - 1)
        }
    }
}
