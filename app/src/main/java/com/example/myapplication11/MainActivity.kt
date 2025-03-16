package com.example.myapplication11

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var tvResult: TextView
    private var currentInput: String = ""
    private var operation: String? = null
    private var firstNumber: Double? = null
    private var isNewOperation = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tvResult = findViewById(R.id.tvResult)

        val buttons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
            R.id.btnDot, R.id.btnNegate, R.id.btnCE, R.id.btnC,
            R.id.btnBackspace, R.id.btnAdd, R.id.btnSub, R.id.btnMul,
            R.id.btnDiv, R.id.btnEqual
        )

        for (id in buttons) {
            findViewById<Button>(id).setOnClickListener { handleButtonClick(it as Button) }
        }
    }
    private fun handleButtonClick(button: Button) {
        when (val buttonText = button.text.toString()) {
            "CE" -> {
                currentInput = ""
                tvResult.text = "0"
            }
            "C" -> {
                currentInput = ""
                firstNumber = null
                operation = null
                tvResult.text = "0"
            }
            "BS" -> {
                currentInput = if (currentInput.isNotEmpty()) currentInput.dropLast(1) else ""
                tvResult.text = if (currentInput.isEmpty()) "0" else currentInput
            }
            "+", "-", "x", "/" -> {
                firstNumber = currentInput.toDoubleOrNull()
                operation = buttonText
                isNewOperation = true
            }
            "=" -> calculateResult()
            "+/-" -> {
                if (currentInput.isNotEmpty()) {
                    currentInput = if (currentInput.startsWith("-")) {
                        currentInput.drop(1)
                    } else {
                        "-$currentInput"
                    }
                    tvResult.text = currentInput
                }
            }
            else -> {
                if (isNewOperation) {
                    currentInput = ""
                    isNewOperation = false
                }
                currentInput += buttonText
                tvResult.text = currentInput
            }
        }
    }

    private fun calculateResult() {
        if (firstNumber != null && operation != null) {
            val secondNumber = currentInput.toDoubleOrNull()
            if (secondNumber != null) {
                val result = when (operation) {
                    "+" -> firstNumber!! + secondNumber
                    "-" -> firstNumber!! - secondNumber
                    "x" -> firstNumber!! * secondNumber
                    "/" -> if (secondNumber != 0.0) firstNumber!! / secondNumber else "Error"
                    else -> "Error"
                }
                tvResult.text = result.toString()
                firstNumber = null
                operation = null
                isNewOperation = true
            }
        }
    }

}
