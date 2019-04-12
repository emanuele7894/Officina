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



class Preventivo : AppCompatActivity() {

    var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    var current = LocalDate.now().format(formatter)
    var conto1 = 0.00
    var conto2 = 0.00

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preventivo)

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



        fun contoTot1(){


            conto1 = editTextPz1.text.toString().toDouble() + editTextPz2.text.toString().toDouble() + editTextPz3.text.toString().toDouble() +
                     editTextPz4.text.toString().toDouble() + editTextPz5.text.toString().toDouble() + editTextPz6.text.toString().toDouble() +
                     editTextPz7.text.toString().toDouble() + editTextPz8.text.toString().toDouble() + editTextPz9.text.toString().toDouble() +
                     editTextPz10.text.toString().toDouble() + editTextPz11.text.toString().toDouble()

            conto2 = editTextPz11.text.toString().toDouble()
            val formattedPx12 = String.format("%.2f €", conto1)

            editTextPz12.text  = Editable.Factory.getInstance().newEditable(formattedPx12)
                val iva = conto2 * 1.22
                        val ivaA = iva - conto2

            conto1 = conto1 + ivaA

            val formattedPx13 = String.format("%.2f €", ivaA)
            val formattedPz14 = String.format("%.2f €", conto1)


            editTextPz13.text  = Editable.Factory.getInstance().newEditable(formattedPx13)
                editTextPz14.text  = Editable.Factory.getInstance().newEditable(formattedPz14)


        }

        val buttonBack = findViewById(R.id.buttonBack) as ImageButton



        editTextPz1.setOnFocusChangeListener(View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {

                if (editTextPz1.text.toString() == ""){

                    editTextPz1.text =  Editable.Factory.getInstance().newEditable("0.00")

                }else {


                    contoTot1()
                    //    val number = editTextPz1.text.toString().toBigDecimal()
                      //      val formatted = String.format("%.2f", number)
                        //        editTextPz1.text =  Editable.Factory.getInstance().newEditable(formatted)


                }



            }else {


                if (editTextPz1.text.toString() == "0.00"){

                    editTextPz1.text =  Editable.Factory.getInstance().newEditable("")

                }else {

                }




            }
        })
        editTextPz2.setOnFocusChangeListener(View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {

                if (editTextPz2.text.toString() == ""){

                    editTextPz2.text =  Editable.Factory.getInstance().newEditable("0.00")

                }else {



                    contoTot1()



                }



            }else {


                if (editTextPz2.text.toString() == "0.00"){

                    editTextPz2.text =  Editable.Factory.getInstance().newEditable("")

                }else {

                }

            }
        })
        editTextPz3.setOnFocusChangeListener(View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {

                if (editTextPz3.text.toString() == ""){

                    editTextPz3.text =  Editable.Factory.getInstance().newEditable("0.00")

                }else {

                    contoTot1()

                }



            }else {

                if (editTextPz3.text.toString() == "0.00"){

                    editTextPz3.text =  Editable.Factory.getInstance().newEditable("")

                }else {

                }

            }
        })

        editTextPz4.setOnFocusChangeListener(View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {

                if (editTextPz4.text.toString() == ""){

                    editTextPz4.text =  Editable.Factory.getInstance().newEditable("0.00")

                }else {

                    contoTot1()

                }



            }else {

                if (editTextPz4.text.toString() == "0.00"){

                    editTextPz4.text =  Editable.Factory.getInstance().newEditable("")

                }else {

                }
            }
        })
        editTextPz5.setOnFocusChangeListener(View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {

                if (editTextPz5.text.toString() == ""){

                    editTextPz5.text =  Editable.Factory.getInstance().newEditable("0.00")

                }else {

                    contoTot1()

                }



            }else {


                if (editTextPz5.text.toString() == "0.00"){

                    editTextPz5.text =  Editable.Factory.getInstance().newEditable("")

                }else {

                }

            }
        })
        editTextPz6.setOnFocusChangeListener(View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {

                if (editTextPz6.text.toString() == ""){

                    editTextPz6.text =  Editable.Factory.getInstance().newEditable("0.00")

                }else {

                    contoTot1()

                }



            }else {

                if (editTextPz6.text.toString() == "0.00"){

                    editTextPz6.text =  Editable.Factory.getInstance().newEditable("")

                }else {

                }


            }
        })
        editTextPz7.setOnFocusChangeListener(View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {

                if (editTextPz7.text.toString() == ""){

                    editTextPz7.text =  Editable.Factory.getInstance().newEditable("0.00")

                }else {

                    contoTot1()

                }



            }else {

                if (editTextPz7.text.toString() == "0.00"){

                    editTextPz7.text =  Editable.Factory.getInstance().newEditable("")

                }else {

                }
            }
        })
        editTextPz8.setOnFocusChangeListener(View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {

                if (editTextPz8.text.toString() == ""){

                    editTextPz8.text =  Editable.Factory.getInstance().newEditable("0.00")

                }else {

                    contoTot1()

                }



            }else {

                if (editTextPz8.text.toString() == "0.00"){

                    editTextPz8.text =  Editable.Factory.getInstance().newEditable("")

                }else {

                }

            }
        })
        editTextPz9.setOnFocusChangeListener(View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {

                if (editTextPz9.text.toString() == ""){

                    editTextPz9.text =  Editable.Factory.getInstance().newEditable("0.00")

                }else {

                    contoTot1()

                }



            }else {

                if (editTextPz9.text.toString() == "0.00"){

                    editTextPz9.text =  Editable.Factory.getInstance().newEditable("")

                }else {

                }
            }
        })
        editTextPz10.setOnFocusChangeListener(View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {

                if (editTextPz10.text.toString() == ""){

                    editTextPz10.text =  Editable.Factory.getInstance().newEditable("0.00")

                }else {

                    contoTot1()

                }



            }else {

                if (editTextPz10.text.toString() == "0.00"){

                    editTextPz10.text =  Editable.Factory.getInstance().newEditable("")

                }else {

                }

            }
        })
        editTextPz11.setOnFocusChangeListener(View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {

                if (editTextPz11.text.toString() == ""){

                    editTextPz11.text =  Editable.Factory.getInstance().newEditable("0.00")

                }else {

                    contoTot1()

                }



            }else {

                if (editTextPz11.text.toString() == "0.00"){

                    editTextPz11.text =  Editable.Factory.getInstance().newEditable("")

                }else {

                }

            }
        })


        buttonBack.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish()
            }
        })








    }
}
