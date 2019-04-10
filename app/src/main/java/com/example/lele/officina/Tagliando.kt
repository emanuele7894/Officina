package com.example.lele.officina

import android.app.DatePickerDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.ImageButton
import kotlinx.android.synthetic.main.activity_preventivo.*
import kotlinx.android.synthetic.main.content_preventivo.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class Tagliando : AppCompatActivity() {

    var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    var current = LocalDate.now().format(formatter)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tagliando)


        targaTitP.text = intent.getStringExtra(".targa")
        val nomeC = intent.getStringExtra(".nomeCognome")
        editTextNM.text = Editable.Factory.getInstance().newEditable(nomeC)

        val marcaM = intent.getStringExtra(".marca")
        editTextVeicolo.text = Editable.Factory.getInstance().newEditable(marcaM)

        editTextTarga.text = Editable.Factory.getInstance().newEditable(targaTitP.text)

        var cal = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd/MM/yy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.ITALY)
            textViewData.text = Editable.Factory.getInstance().newEditable(sdf.format(cal.time))

        }

        textViewData.text =  Editable.Factory.getInstance().newEditable(current.toString().trim())

        val buttonBack = findViewById(R.id.buttonBack) as ImageButton


        buttonBack.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish()
            }
        })

    }



}
