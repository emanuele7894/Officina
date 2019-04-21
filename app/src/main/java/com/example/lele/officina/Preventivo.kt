package com.example.lele.officina

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.support.v4.print.PrintHelper
import android.text.Editable
import android.util.DisplayMetrics
import android.view.View
import android.widget.ImageButton
import kotlinx.android.synthetic.main.activity_preventivo.*
import kotlinx.android.synthetic.main.content_preventivo.*
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import android.support.v4.content.FileProvider
import android.content.ActivityNotFoundException




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
            val buttonPrint = findViewById(R.id.printButton) as ImageButton
                val mailButton = findViewById(R.id.mailButton) as ImageButton




        buttonPrint.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {


                val displayMetrics = DisplayMetrics()
                    windowManager.defaultDisplay.getMetrics(displayMetrics)

                val bitmap = loadBitmapFromView(findViewById(R.id.content_preventivo_print), displayMetrics.widthPixels, displayMetrics.heightPixels)
                     doPhotoPrint(bitmap)



            }
        })


        mailButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val displayMetrics = DisplayMetrics()
                windowManager.defaultDisplay.getMetrics(displayMetrics)

                val bitmap = loadBitmapFromView(findViewById(R.id.content_preventivo_print), displayMetrics.widthPixels, displayMetrics.heightPixels)
                    saveImage(bitmap)



            }
        })




        editTextPz1.setOnFocusChangeListener(View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {

                if (editTextPz1.text.toString() == ""){

                    editTextPz1.text =  Editable.Factory.getInstance().newEditable("0.00")

                }else {


                    contoTot1()
                        val string = editTextPz1.text.toString()

                            if (string.contains(".", ignoreCase = true) == false){

                                val stringaA = "${editTextPz1.text.toString()}.00"
                                    editTextPz1.text =  Editable.Factory.getInstance().newEditable(stringaA)

                            }



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
                    val string = editTextPz2.text.toString()

                    if (string.contains(".", ignoreCase = true) == false){

                        val stringaA = "${editTextPz2.text.toString()}.00"
                            editTextPz2.text =  Editable.Factory.getInstance().newEditable(stringaA)

                    }



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
                    val string = editTextPz3.text.toString()

                    if (string.contains(".", ignoreCase = true) == false){

                        val stringaA = "${editTextPz3.text.toString()}.00"
                             editTextPz3.text =  Editable.Factory.getInstance().newEditable(stringaA)

                    }

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
                    val string = editTextPz4.text.toString()

                    if (string.contains(".", ignoreCase = true) == false){

                        val stringaA = "${editTextPz4.text.toString()}.00"
                            editTextPz4.text =  Editable.Factory.getInstance().newEditable(stringaA)

                    }

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
                    val string = editTextPz5.text.toString()

                    if (string.contains(".", ignoreCase = true) == false){

                        val stringaA = "${editTextPz5.text.toString()}.00"
                            editTextPz5.text =  Editable.Factory.getInstance().newEditable(stringaA)

                    }

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
                    val string = editTextPz6.text.toString()

                    if (string.contains(".", ignoreCase = true) == false){

                        val stringaA = "${editTextPz6.text.toString()}.00"
                            editTextPz6.text =  Editable.Factory.getInstance().newEditable(stringaA)

                    }

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
                    val string = editTextPz7.text.toString()

                    if (string.contains(".", ignoreCase = true) == false){

                        val stringaA = "${editTextPz7.text.toString()}.00"
                            editTextPz7.text =  Editable.Factory.getInstance().newEditable(stringaA)

                    }

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
                    val string = editTextPz8.text.toString()

                    if (string.contains(".", ignoreCase = true) == false){

                        val stringaA = "${editTextPz8.text.toString()}.00"
                            editTextPz8.text =  Editable.Factory.getInstance().newEditable(stringaA)

                    }

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
                    val string = editTextPz9.text.toString()

                    if (string.contains(".", ignoreCase = true) == false){

                        val stringaA = "${editTextPz9.text.toString()}.00"
                            editTextPz9.text =  Editable.Factory.getInstance().newEditable(stringaA)

                    }

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
                    val string = editTextPz10.text.toString()

                    if (string.contains(".", ignoreCase = true) == false){

                        val stringaA = "${editTextPz10.text.toString()}.00"
                            editTextPz10.text =  Editable.Factory.getInstance().newEditable(stringaA)

                    }

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
                    val string = editTextPz11.text.toString()

                    if (string.contains(".", ignoreCase = true) == false){

                        val stringaA = "${editTextPz11.text.toString()}.00"
                            editTextPz11.text =  Editable.Factory.getInstance().newEditable(stringaA)

                    }

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

    private fun doPhotoPrint(bitmap: Bitmap) {
        val photoPrinter = PrintHelper(this)
        photoPrinter.scaleMode = PrintHelper.SCALE_MODE_FIT
        photoPrinter.printBitmap("test print", bitmap)
    }


        fun saveImage(bitmap: Bitmap) {
            val root = Environment.getExternalStorageDirectory().toString()
            val myDir = File(root + "/offcinaPrint")
            myDir.mkdirs()

            val fname = "PrintPrev.jpg"
            val file = File(myDir, fname)
            //  Log.i(TAG, "" + file);
            if (file.exists())
                file.delete()
            try {
                val out = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
                out.flush()
                out.close()



            } catch (e: Exception) {
                e.printStackTrace()
            }

            val imageUri = FileProvider.getUriForFile(
                this@Preventivo,
                "com.example.homefolder.example.provider", //(use your app signature + ".provider" )
                file
            )

            shareImage(imageUri)


        }

    private fun shareImage(file: Uri) {

        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.type = "image/*"

        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "")
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "")
        intent.putExtra(Intent.EXTRA_STREAM, file)
        try {
            startActivity(Intent.createChooser(intent, "Share Screenshot"))
        } catch (e: ActivityNotFoundException) {

        }

    }

        fun loadBitmapFromView(v: View, width: Int, height: Int): Bitmap {
            val b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val c = Canvas(b)
            v.draw(c)
            return b
        }

}
