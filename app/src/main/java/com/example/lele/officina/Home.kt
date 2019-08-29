package com.example.lele.officina

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import com.google.firebase.FirebaseApp







class Home : AppCompatActivity() {

    internal lateinit var editTextNote: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                }
            }
        } else {


        }


            editTextNote = findViewById(R.id.editTextNote)

        FirebaseApp.initializeApp(this)

            var k1: Intent = Intent(this@Home, eod_view::class.java )
                var k2: Intent = Intent(this@Home, datiVeicolo::class.java )


            val obdButton = findViewById(R.id.obdButton) as ImageButton
                val dataButton = findViewById(R.id.dataButton) as ImageButton


        //pulsante obdButton
        obdButton.setOnClickListener(object : View.OnClickListener {

            override fun onClick(v: View?) {



                startActivity(k1)

            }
        })


        dataButton.setOnClickListener(object : View.OnClickListener {

            override fun onClick(v: View?) {

                startActivity(k2)

            }
        })







    }

    override fun onBackPressed() {

        editTextNote.text = Editable.Factory.getInstance().newEditable("FOCUS")
        saveNote(editTextNote.text.toString())
        super.onBackPressed()


    }

    override fun onUserLeaveHint() {
        editTextNote.text = Editable.Factory.getInstance().newEditable("FOCUS")
        saveNote(editTextNote.text.toString())
        super.onUserLeaveHint()
    }

    private fun saveNote(testo: String){




    }






}
