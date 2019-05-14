package com.example.lele.officina

import android.animation.ObjectAnimator
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_dati_veicolo.*
import kotlinx.android.synthetic.main.activity_dati_veicolo.buttonBack
import kotlinx.android.synthetic.main.activity_officina_cli.*

class datiVeicolo : AppCompatActivity() {


    lateinit var  ref: DatabaseReference
    var check = false
    var dati = hashMapOf<String,String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dati_veicolo)

        codicePannel.visibility = View.INVISIBLE
            codicePannel.alpha = 0.0f



        fun loadRef(codice: String){
            ref = FirebaseDatabase.getInstance().getReference("datiVeicolo").child(codice)

            ref.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }


                override fun onDataChange(p0: DataSnapshot) {
                    dati.clear()


                    if (p0.exists()) {

                        for (h in p0.children) {

                            dati.set(h.key.toString(), h.value.toString())

                        }
                        check = true


                    }else {

                        //Non esistente
                        Toast.makeText(applicationContext,"non presente", Toast.LENGTH_SHORT).show()
                        check = false

                    }

                }

            })

        }








        fun showSoftKeyboard(view: View) {

            if (view.requestFocus()) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
            }

        }

        fun hidKeyboard(){
            val inputManager:InputMethodManager =getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(currentFocus.windowToken, InputMethodManager.SHOW_FORCED)

        }


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

                showSoftKeyboard(ditCodice)


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


        //Esegue la ricerca del codice motore
        fun ok(){

            if (check == true){

                hidKeyboard()
                     invisiblePannel()

                if (dati["marca"] == "gav"){

                    logo.setImageResource(R.mipmap.gav)

                }else {
                    logo.setImageResource(R.mipmap.dacia)
                }




            }else {

                hidKeyboard()

            }










        }




        closePannel.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                hidKeyboard()
                    invisiblePannel()
            }
        })

        //Funzione OKBUtton
            okButton.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {

                    codiceMtext.text = ditCodice.text.toString()
                        val string = ditCodice.text.toString()
                    loadRef(string)
                    Handler().postDelayed({

                        // Scrittura dati

                        ok()

                    }, 1500)

                }
            })

        ditCodice.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                //Perform Code
                codiceMtext.text = ditCodice.text.toString()
                    val string = ditCodice.text.toString()
                        loadRef(string)

                Handler().postDelayed({

                    // Scrittura dati

                    ok()

                }, 1500)

                return@OnKeyListener true
            }
            false
        })








    }







}
