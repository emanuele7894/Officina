package com.example.lele.officina

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.google.firebase.FirebaseApp
import android.support.v4.app.ActivityCompat
import android.preference.PreferenceManager
import android.support.constraint.ConstraintSet
import android.text.Editable
import android.util.Log
import com.example.lele.officina.data.KeyboardEventListener
import kotlinx.android.synthetic.main.activity_home.*




class Home : AppCompatActivity() {


    var aperto = true
    var con =  ConstraintSet()


    internal lateinit var editTextNote: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        con.clone(mainLayout)
            editTextNote = findViewById(R.id.editTextNote)

        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val testo = preferences.getString("note", "fail")

        editTextNote.text = Editable.Factory.getInstance().newEditable(testo)



        setupPermissions()

        val REQUEST_EXTERNAL_STORAGE = 1
        val PERMISSIONS_STORAGE = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        ActivityCompat.requestPermissions(
            this, PERMISSIONS_STORAGE,
            REQUEST_EXTERNAL_STORAGE
        )



















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



    fun noteFull(){

        if(aperto == true){

            var con2 =  ConstraintSet()
                con2.clone(mainLayout)
                    con2.connect(R.id.imageView23,ConstraintSet.TOP,R.id.logo,ConstraintSet.BOTTOM,0)
                         con2.applyTo(mainLayout)

        }else {

            con.applyTo(mainLayout)




        }



    }


    override fun onUserLeaveHint() {

        aperto = false
            noteFull()

        saveNote(editTextNote.text.toString())
            super.onUserLeaveHint()
    }


    override fun onBackPressed() {

        aperto = false
            noteFull()

        saveNote(editTextNote.text.toString())


            super.onBackPressed()


    }


    override fun onResume() {
        super.onResume()
        KeyboardEventListener(this) {
            Log.v("Keyboard checker", "Keyboard is open = $it")
            if (it) {
             //"Keyboard Open"
                aperto = true
                noteFull()

            } else {
               //"Keyboard closed"

                aperto = false
                    noteFull()

            }
        }
    }



    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (permission != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(applicationContext,"non permesso" , Toast.LENGTH_SHORT).show()

        }else {


        }

    }



    private fun saveNote(testo: String){


        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = preferences.edit()

        editor.putString("note", testo)
        editor.apply()





    }

}
