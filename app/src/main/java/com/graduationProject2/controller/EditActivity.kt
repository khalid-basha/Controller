package com.graduationProject2.controller

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Double.parseDouble
import java.lang.Integer.parseInt


class EditActivity : AppCompatActivity() {
    val sr=SimpleRunnable(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        val colors = arrayOf("Select","Red","Green", "Yellow", "Blue", "Magenta")
        val colorsChar = arrayOf(-1,2,3, 8, 4, 13)
        val s = findViewById<EditText>(R.id.setPointEditable)
        val stepSize=findViewById<EditText>(R.id.stepSizeEditable)
        val accError=findViewById<EditText>(R.id.acceptedErrorEditable)
        val spinner = findViewById<Spinner>(R.id.spinner)
        val button=findViewById<Button>(R.id.updateButton)
        var select=false
        var color=-1
        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                R.layout.color_spinner, colors
            )
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    color = colorsChar[position]

                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    color=-1;
                }


            }
        }

        button.setOnClickListener {

            var sp=0.0
            sp = if (s.text.toString().isNotEmpty()) {
                sp=parseDouble(s.text.toString())
                round(sp,99.99,0.00)
            }
            else
                sr.setPoint

            var acc=0.0
            acc= if (accError.text.toString().isNotEmpty()) {
                acc = parseDouble(accError.text.toString())
                 round(acc,5.0,0.00)
            }
            else
                sr.acceptedError


            var stepS=0
            stepS=if (stepSize.text.toString().isNotEmpty()) {
                stepS = parseInt(stepSize.text.toString())
                round(stepS,10,1)
            }
            else
                sr.stepSize


            val r=AllValues(sp,acc,color,stepS)

            println(r.toString())
            API.getApi()?.sendAllValues(r)?.enqueue(object: Callback<Any>{

                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@EditActivity,
                            "Updated", Toast.LENGTH_SHORT).show()
                        sr.stop1()
                        this@EditActivity.finish()
                    }
                }

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    Toast.makeText(this@EditActivity,
                        "Update Failed", Toast.LENGTH_SHORT).show()
                    println(t.message)
                }

            })

        }

    }

    override fun onPause() {
        super.onPause()
        sr.stop1();
    }

    override fun onResume() {
        super.onResume()
        sr.start()
        val th=Thread(sr)
        th.start()
    }

    companion object {
        fun round(num:Double, max:Double, min:Double ):Double{
            return when {
                num>max -> max
                num<min -> min
                else -> num
            }
        }

        fun round(num:Int ,max:Int ,min:Int ):Int{
            return when {
                num>max -> max
                num<min -> min
                else -> num
            }
        }
    }


}