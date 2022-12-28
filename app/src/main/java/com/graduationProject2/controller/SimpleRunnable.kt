package com.graduationProject2.controller

import android.annotation.SuppressLint
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SimpleRunnable(private val mainActivity: AppCompatActivity?) : Runnable {

    private var state = false
    var setPoint = 0.0
    var actualTemperature = 0.0
    var acceptedError = 0.0
    var stepSize = 0
    var lightColor = -1


    override fun run() {

        while (state) {

            API.getApi()?.getAllValues()?.enqueue(
                object : Callback<InformationResponse> {
                    @SuppressLint("SetTextI18n")
                    override fun onResponse(
                        call: Call<InformationResponse>,
                        response: Response<InformationResponse>
                    ) {
                        if (response.isSuccessful) {
                            println("receive")
                            setPoint = response.body()?.sp!!
                            actualTemperature = response.body()?.act!!
                            acceptedError = response.body()?.acceptedError!!
                            stepSize = response.body()?.setValue!!
                            var lightColor = response.body()?.light!!

                            if (mainActivity is MainActivity) {
                                val sp = mainActivity.findViewById<TextView>(R.id.sTemp)
                                val act = mainActivity.findViewById<TextView>(R.id.actTemp)
                                val stSize = mainActivity.findViewById<TextView>(R.id.sSize)
                                val accError = mainActivity.findViewById<TextView>(R.id.accError)
                                val light = mainActivity.findViewById<TextView>(R.id.lColor)
                                sp.text = String.format("%.2f", setPoint) + " \u2103"
                                act.text = String.format("%.2f", actualTemperature) + " \u2103"
                                stSize.text = "\u00B1 $stepSize"
                                accError.text = "\u00B1 " + String.format("%.2f", acceptedError)
                                light.text = when (lightColor) {
                                    2 -> "Red"
                                    4 -> "Blue"
                                    8 -> "Yellow"
                                    3 -> "Green"
                                    13 -> "Magenta"
                                    else -> "White"
                                }
                            } else if (mainActivity is EditActivity) {
                                val s = mainActivity.findViewById<EditText>(R.id.setPointEditable)
                                val spSize =
                                    mainActivity.findViewById<EditText>(R.id.stepSizeEditable)
                                val accError =
                                    mainActivity.findViewById<EditText>(R.id.acceptedErrorEditable)
                                s.hint = String.format("%.2f", setPoint) + " \u2103"
                                spSize.hint = stepSize.toString()
                                accError.hint = String.format("%.2f", acceptedError)

                            }
                            println("******************** rec *****************")
                        }
                    }

                    override fun onFailure(call: Call<InformationResponse>, t: Throwable) {
                        println("******************** not rec *****************")
                        Toast.makeText(mainActivity,
                            "Connection Failed", Toast.LENGTH_SHORT).show()

                    }
                })
            Thread.sleep(5000)
        }
    }

    fun stop1() {
        state = false
    }

    fun start() {
        state = true
    }

}