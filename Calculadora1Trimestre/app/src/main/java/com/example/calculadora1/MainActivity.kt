package com.example.calculadora1

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.calculadora1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private var firstOperand: Double? = null
    private var secondOperand: Double? = null
    private var currentOperation: String? = null
    private var isNewOperation: Boolean = true
    private var lastResult: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)


        if (savedInstanceState != null) {
            firstOperand = savedInstanceState.getDouble("firstOperand", 0.0)
            secondOperand = savedInstanceState.getDouble("secondOperand", 0.0)
            currentOperation = savedInstanceState.getString("currentOperation", null)
            lastResult = savedInstanceState.getDouble("lastResult", 0.0)
            binding.textCounter.text = savedInstanceState.getString("counter", "0")
            isNewOperation = savedInstanceState.getBoolean("isNewOperation", true)

            if (currentOperation != null && firstOperand != null) {
                binding.textCounter.text = "${firstOperand.toString()} ${currentOperation}"
            }
        }

        binding.buttonC.setOnClickListener(this)
        binding.buttonCe.setOnClickListener(this)
        binding.buttonDelete.setOnClickListener(this)
        binding.buttonDivide.setOnClickListener(this)
        binding.buttonMultiply.setOnClickListener(this)
        binding.buttonSubtract.setOnClickListener(this)
        binding.buttonAdd.setOnClickListener(this)
        binding.buttonResult.setOnClickListener(this)
        binding.buttonPercentage.setOnClickListener(this)
        binding.buttonSeven.setOnClickListener(this)
        binding.buttonOne.setOnClickListener(this)
        binding.buttonTwo.setOnClickListener(this)
        binding.buttonThree.setOnClickListener(this)
        binding.buttonFour.setOnClickListener(this)
        binding.buttonFive.setOnClickListener(this)
        binding.buttonSix.setOnClickListener(this)
        binding.buttonEight.setOnClickListener(this)
        binding.buttonNine.setOnClickListener(this)
        binding.buttonZero.setOnClickListener(this)
        binding.buttonPI?.setOnClickListener(this)
        binding.buttonPow?.setOnClickListener(this)
        binding.buttonCos?.setOnClickListener(this)
        binding.buttonSin?.setOnClickListener(this)
        binding.buttonTan?.setOnClickListener(this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putDouble("firstOperand", firstOperand ?: 0.0)
        outState.putDouble("secondOperand", secondOperand ?: 0.0)
        outState.putString("currentOperation", currentOperation)
        outState.putDouble("lastResult", lastResult ?: 0.0)
        outState.putString("counter", binding.textCounter.text.toString())
        outState.putBoolean("isNewOperation", isNewOperation)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.buttonC.id -> {
                deleteAll()
            }

            binding.buttonCe.id -> {
                delEntry()
            }

            binding.buttonDelete.id -> {
                del()
            }

            binding.buttonDivide.id -> {
                setOperator("/")
            }

            binding.buttonMultiply.id -> {
                setOperator("*")
            }

            binding.buttonSubtract.id -> {
                setOperator("-")
            }

            binding.buttonAdd.id -> {
                setOperator("+")
            }

            binding.buttonResult.id -> {
                calculateResult()
            }

            binding.buttonPercentage.id -> {
                handlePercentage()
            }
            // Botones numéricos
            binding.buttonOne.id -> {
                writeNumbers("1")
            }

            binding.buttonTwo.id -> {
                writeNumbers("2")
            }

            binding.buttonThree.id -> {
                writeNumbers("3")
            }

            binding.buttonFour.id -> {
                writeNumbers("4")
            }

            binding.buttonFive.id -> {
                writeNumbers("5")
            }

            binding.buttonSix.id -> {
                writeNumbers("6")
            }

            binding.buttonSeven.id -> {
                writeNumbers("7")
            }

            binding.buttonEight.id -> {
                writeNumbers("8")
            }

            binding.buttonNine.id -> {
                writeNumbers("9")
            }

            binding.buttonZero.id -> {
                writeNumbers("0")
            }

            binding.buttonPI?.id -> {
                pi()
            }

            binding.buttonPow?.id -> {
                pow()
            }

            binding.buttonSin?.id -> {
                sin()
            }

            binding.buttonCos?.id -> {
                cos()
            }

            binding.buttonTan?.id -> {
                tan()
            }
        }
    }

    // Borrar todo
    private fun deleteAll() {
        firstOperand = null
        secondOperand = null
        currentOperation = null
        lastResult = null
        binding.textCounter.text = "0"
        isNewOperation = true
    }

    // Limpiar el texto actual
    private fun delEntry() {
        binding.textCounter.text = "0"
        isNewOperation = true
    }


    private fun del() {
        val currentText = binding.textCounter.text.toString()
        if (currentText.length > 1) {
            binding.textCounter.text = currentText.dropLast(1)
        } else {
            binding.textCounter.text = "0"
        }
    }

    // Escribir
    private fun writeNumbers(number: String) {
        if (isNewOperation) {
            binding.textCounter.text = number
            isNewOperation = false
        } else {
            binding.textCounter.text = binding.textCounter.text.toString() + number
        }
    }


    private fun setOperator(operator: String) {
        if (lastResult != null) {
            firstOperand = lastResult
            lastResult = null
        } else if (firstOperand == null) {
            firstOperand = binding.textCounter.text.toString().toDoubleOrNull()
        }

        if (firstOperand == null) {
            binding.textCounter.text = "Error"
            return
        }

        currentOperation = operator
        binding.textCounter.text = "${formatResult(firstOperand!!)} $operator"
        isNewOperation = true
    }



    // porcentaje
    private fun handlePercentage() {
        if (firstOperand != null) {
            val percentageAmount = binding.textCounter.text.toString().toDoubleOrNull()

            if (percentageAmount != null) {
                lastResult = firstOperand!! * (percentageAmount / 100)
                binding.textCounter.text = formatResult(lastResult!!)
                isNewOperation = true
            }
        }
    }



    //Metodo pi
    private fun pi() {
        val pi = Math.PI

        if (isNewOperation) {
            binding.textCounter.text = pi.toString()
            isNewOperation = false
        } else {
            binding.textCounter.text = "${binding.textCounter.text}$pi"
        }
    }

    private fun sin() {
        val currentValue = binding.textCounter.text.toString().toDoubleOrNull()
        if (currentValue != null) {
            val value = Math.toRadians(currentValue)
            val result = Math.sin(value)
            binding.textCounter.text = formatResult(result)
            isNewOperation = true
        }
    }

    private fun formatResult(value: Double): String {
        return if (value % 1 == 0.0) {
            value.toInt().toString()
        } else {
            value.toString()
        }

    }

    private fun cos() {
        val currentValue = binding.textCounter.text.toString().toDoubleOrNull()
        if (currentValue != null) {
            val value = Math.toRadians(currentValue)
            val result = Math.cos(value)
            binding.textCounter.text = formatResult(result)
            isNewOperation = true
        }
    }

    private fun tan() {
        val currentValue = binding.textCounter.text.toString().toDoubleOrNull()
        if (currentValue != null) {
            val value = Math.toRadians(currentValue)
            val result = Math.tan(value)
            binding.textCounter.text = formatResult(result)
            isNewOperation = true
        }
    }

    private fun pow() {
        val currentText = binding.textCounter.text.toString()
        if (currentOperation == "^") {
            val secondOperand = currentText.toDoubleOrNull()

            if (secondOperand != null) {

                val result = Math.pow(firstOperand!!, secondOperand)
                binding.textCounter.text = formatResult(result)


                firstOperand = result
                currentOperation = null
                isNewOperation = true
            } else {
                binding.textCounter.text = "Error"
            }
        } else {

            val firstValue = currentText.toDoubleOrNull()

            if (firstValue != null) {
                firstOperand = firstValue
                currentOperation = "^"
                binding.textCounter.text = "${formatResult(firstOperand!!)} ^"
                isNewOperation = true
            } else {
                binding.textCounter.text = "Error"
            }
        }
    }

    private fun calculateResult() {
        if (firstOperand == null) return


        if (secondOperand == null) {
            secondOperand = binding.textCounter.text.toString().toDoubleOrNull()
        }

        if (secondOperand == null && currentOperation != "%") {
            binding.textCounter.text = "Error"
            return
        }


        val result = when (currentOperation) {
            "+" -> firstOperand!! + secondOperand!!
            "-" -> firstOperand!! - secondOperand!!
            "*" -> firstOperand!! * secondOperand!!
            "/" -> if (secondOperand != 0.0) firstOperand!! / secondOperand!! else Double.NaN
            "^" -> Math.pow(firstOperand!!, secondOperand!!)
            "%" -> {
                firstOperand!! * (secondOperand ?: 0.0) / 100
            }
            else -> Double.NaN
        }

        if (result.isNaN() || result.isInfinite()) {
            binding.textCounter.text = "Error"
            return
        }


        binding.textCounter.text = formatResult(result)


        lastResult = result

        firstOperand = result
        secondOperand = null
        currentOperation = null
        isNewOperation = true
    }


}


