package com.example.lele.officina

import android.animation.ObjectAnimator
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import kotlinx.android.synthetic.main.activity_dati_veicolo.*

class datiVeicolo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dati_veicolo)

        codicePannel.visibility = View.INVISIBLE
            codicePannel.alpha = 0.0f




        buttonBack.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish()
            }
        })

        addCodic.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                codicePannel.visibility = View.VISIBLE


                val listVisible = ObjectAnimator.ofFloat(codicePannel, View.ALPHA,  1.0f)
                        listVisible.duration = 500
                             listVisible.start()


            }
        })

        fun invisiblePannel(){



            val listVisible = ObjectAnimator.ofFloat(codicePannel, View.ALPHA,  0.0f)
                listVisible.duration = 500
                    listVisible.start()



            Handler().postDelayed({

                codicePannel.visibility = View.INVISIBLE

            }, 500)

        }

        closePannel.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                invisiblePannel()
            }
        })



    }







}
