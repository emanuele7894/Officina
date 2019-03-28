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


        //Pulsante clienti
        val buttonClienti: ImageButton = findViewById(R.id.ButtonClient) as ImageButton



        buttonClienti.setOnClickListener(object : View.OnClickListener {

            override fun onClick(v: View?) {



                startActivity(k)

            }
        })
//fine Pulsante clienti

    }
}
