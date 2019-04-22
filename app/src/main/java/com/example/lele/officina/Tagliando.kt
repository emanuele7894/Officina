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
import kotlinx.android.synthetic.main.activity_tagliando.*
import kotlinx.android.synthetic.main.activity_tagliando.numPage
import kotlinx.android.synthetic.main.activity_preventivo.*
import kotlinx.android.synthetic.main.activity_preventivo.targaTitP
import kotlinx.android.synthetic.main.activity_tagliando.*
import kotlinx.android.synthetic.main.content_preventivo.*
import java.io.File
import java.io.FileOutputStream
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
            val buttonPrint = findViewById(R.id.printButton) as ImageButton
                val mailButton = findViewById(R.id.mailButton) as ImageButton
                    val nextPage = findViewById(R.id.nextPage) as ImageButton
                        val prevPage = findViewById(R.id.prevPage) as ImageButton

        nextPage.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {


                numPage.text = "2"
                    content_tagliando_print.visibility = View.INVISIBLE
                        content_tagliando_print2.visibility = View.VISIBLE
                            nextPage.visibility = View.INVISIBLE
                                prevPage.visibility = View.VISIBLE




            }
        })


        prevPage.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {


                numPage.text = "1"
                content_tagliando_print.visibility = View.VISIBLE
                content_tagliando_print2.visibility = View.INVISIBLE
                nextPage.visibility = View.VISIBLE
                prevPage.visibility = View.INVISIBLE




            }
        })

        buttonPrint.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                val displayMetrics = DisplayMetrics()
                    windowManager.defaultDisplay.getMetrics(displayMetrics)

                val bitmap = loadBitmapFromView(findViewById(R.id.content_tagliando_print), displayMetrics.widthPixels, displayMetrics.heightPixels)

                content_tagliando_print.visibility = View.INVISIBLE
                     content_tagliando_print2.visibility = View.VISIBLE

                val bitmap2 = loadBitmapFromView(findViewById(R.id.content_tagliando_print2), displayMetrics.widthPixels, displayMetrics.heightPixels)



                doPhotoPrint(bitmap, bitmap2)



            }
        })


        mailButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                val displayMetrics = DisplayMetrics()
                    windowManager.defaultDisplay.getMetrics(displayMetrics)

                val bitmap = loadBitmapFromView(findViewById(R.id.content_tagliando_print), displayMetrics.widthPixels, displayMetrics.heightPixels)
                content_tagliando_print.visibility = View.INVISIBLE
                content_tagliando_print2.visibility = View.VISIBLE

                val bitmap2 = loadBitmapFromView(findViewById(R.id.content_tagliando_print2), displayMetrics.widthPixels, displayMetrics.heightPixels)

                saveImage(bitmap, bitmap2, "condividi")

            }
        })


        buttonBack.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish()
            }
        })

    }

    private fun doPhotoPrint(bitmap: Bitmap, bitmap2: Bitmap) {

        val photoPrinter = PrintHelper(this)
        photoPrinter.scaleMode = PrintHelper.SCALE_MODE_FIT
        photoPrinter.printBitmap("test print", bitmap) ; photoPrinter.printBitmap("test print", bitmap2)

    }


    fun saveImage(bitmap: Bitmap, bitmap2: Bitmap, stringa: String) {
        val root = Environment.getExternalStorageDirectory().toString()

        val myDir = File(root + "/offcinaPrint")
        myDir.mkdirs()

        val fname = "PrintTagliandoP1.jpg"
        val fname2 = "PrintTagliandoP2.jpg"

        val file = File(myDir, fname)
        val file2 = File(myDir, fname2)

        //  Log.i(TAG, "" + file);
        if (file.exists())

            file.delete()
        file2.delete()

        try {
            val out = FileOutputStream(file)
            val out2 = FileOutputStream(file2)

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, out2)

            out.flush()
            out.close()
            out2.flush()
            out2.close()



        } catch (e: Exception) {
            e.printStackTrace()
        }

        val imageUri = FileProvider.getUriForFile(
            this@Tagliando,
            "com.example.homefolder.example.provider", //(use your app signature + ".provider" )
            file
        )
        val imageUri2 = FileProvider.getUriForFile(
            this@Tagliando,
            "com.example.homefolder.example.provider", //(use your app signature + ".provider" )
            file2
        )

        if(stringa == "stampa"){


        }else if (stringa== "condividi"){

            shareImage(imageUri, imageUri2)

        }




    }

    private fun shareImage(file: Uri, file2: Uri) {

        val intent = Intent()
        intent.action = Intent.ACTION_SEND_MULTIPLE
        intent.type = "image/*"

        var files = ArrayList<Uri>()
        files.add(file)
        files.add(file2)

        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "")
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "")
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, files)

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
