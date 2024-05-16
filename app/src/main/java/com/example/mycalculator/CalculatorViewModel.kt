package com.example.mycalculator


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.mozilla.javascript.Context
import org.mozilla.javascript.Scriptable

class CalculatorViewModel : ViewModel() {
    private val _equitionText = MutableLiveData("")
    val equitionText : LiveData<String> = _equitionText

    private val _resultText = MutableLiveData("0")
    val resultText : LiveData<String> = _resultText


    fun onButtonClick(btn : String){
        Log.i("Clicked Button ",btn)

        _equitionText.value?.let {
            if(btn == "AC"){
                _equitionText.value = ""
                _resultText.value = "0"
                return
            }
            if(btn == "C"){
                if(it.isNotEmpty()){
                    _equitionText.value = it.substring(0,it.length-1)
                    return
                }
            }
            if(btn == "="){
                if(it.isNotEmpty()){
                    _equitionText.value = _resultText.value
                    return
                }
            }
            _equitionText.value = it+btn

            try {
                _resultText.value = calculatorResult(_equitionText.value.toString())
            }catch (_ : Exception){}

            Log.i("Equation ",_equitionText.value.toString())
        }
    }
    fun calculatorResult(equation : String) : String {
        val context : Context = Context.enter()
        context.optimizationLevel = -1
        val scriptable : Scriptable = context.initStandardObjects()
        var finalResult = context.evaluateString(scriptable,equation,"Javascript",1,null).toString()
        if(finalResult.endsWith(".0")){
            finalResult = finalResult.replace(".0","")
        }
        return finalResult
    }
}