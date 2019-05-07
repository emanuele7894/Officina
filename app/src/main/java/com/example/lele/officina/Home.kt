package com.example.lele.officina

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import com.google.firebase.FirebaseApp

class Home : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        FirebaseApp.initializeApp(this)

        var k: Intent = Intent(this@Home, ListaClienti::class.java)
            var k1: Intent = Intent(this@Home, eod_view::class.java )
                var k2: Intent = Intent(this@Home, datiVeicolo::class.java )


        //Pulsante clienti
        val buttonClienti: ImageButton = findViewById(R.id.ButtonClient) as ImageButton
            val obdButton = findViewById(R.id.obdButton) as ImageButton
                val dataButton = findViewById(R.id.dataButton) as ImageButton


        buttonClienti.setOnClickListener(object : View.OnClickListener {

            override fun onClick(v: View?) {



                startActivity(k)

            }
        })
//fine Pulsante clienti

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





}
