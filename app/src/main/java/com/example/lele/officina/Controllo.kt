package com.example.lele.officina

import android.app.DatePickerDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.support.v4.content.FileProvider
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




class Controllo : AppCompatActivity() {

    var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    var current = LocalDate.now().format(formatter)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_controllo)



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
        val buttonPrint = findViewById(R.id.printButton) as ImageButton
        val mailButton = findViewById(R.id.mailButton) as ImageButton

        buttonPrint.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {


                val displayMetrics = DisplayMetrics()
                    windowManager.defaultDisplay.getMetrics(displayMetrics)

                val bitmap = loadBitmapFromView(findViewById(R.id.content_controllo_print), displayMetrics.widthPixels, displayMetrics.heightPixels)
                    doPhotoPrint(bitmap)



            }
        })


        mailButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                val displayMetrics = DisplayMetrics()
                    windowManager.defaultDisplay.getMetrics(displayMetrics)

                val bitmap = loadBitmapFromView(findViewById(R.id.content_controllo_print), displayMetrics.widthPixels, displayMetrics.heightPixels)
                    saveImage(bitmap)



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

        val fname = "PrintContr.jpg"
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
            this@Controllo,
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
