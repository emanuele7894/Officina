package com.example.lele.officina

import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.method.TextKeyListener
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_dati_veicolo.*
import kotlinx.android.synthetic.main.activity_dati_veicolo.buttonBack
import kotlinx.android.synthetic.main.content_veicle.*

class datiVeicolo : AppCompatActivity() {


    lateinit var  ref: DatabaseReference
    lateinit var  ref2: DatabaseReference
    var check = false
    var editSave = false
    var dati = hashMapOf<String,String>()
    var imageTesta = hashMapOf<String,String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dati_veicolo)

        codicePannel.visibility = View.INVISIBLE
            codicePannel.alpha = 0.0f

    ditCodice.setKeyListener(TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true))

        fun loadRef(codice: String){


            ref = FirebaseDatabase.getInstance().getReference("datiVeicolo").child(codice)
            ref2 = FirebaseDatabase.getInstance().getReference("documentoTesta").child(codice)

            ref.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }


            //Carico i dati veicolo
                override fun onDataChange(p0: DataSnapshot) {
                    dati.clear()
                    imageTesta.clear()


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


                //CaRICO immagine testa
            ref2.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }


                override fun onDataChange(p0: DataSnapshot) {
                    imageTesta.clear()


                    if (p0.exists()) {

                        for (h in p0.children) {

                            imageTesta.set(h.key.toString(), h.value.toString())

                            var stringa = imageTesta["0"]
                            Toast.makeText(applicationContext,"non presente", Toast.LENGTH_SHORT).show()

                        }



                    }else {

                        //Non esistente
                        Toast.makeText(applicationContext,"non presente", Toast.LENGTH_SHORT).show()


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


        fun showPanel(){
            codicePannel.visibility = View.VISIBLE


            val listVisible = ObjectAnimator.ofFloat(codicePannel, View.ALPHA,  1.0f)
            listVisible.duration = 500
            listVisible.start()

            showSoftKeyboard(ditCodice)

        }

        showPanel()

        addCodic.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

            showPanel()


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
                    logo.setImageResource(R.mipmap.gav)

                }else if(dati["marca"] == "gf"){

                    logo.setImageResource(R.mipmap.gf)

                }

                editTextModello.text = Editable.Factory.getInstance().newEditable(dati["modello"])
                    editTextCilindrata.text = Editable.Factory.getInstance().newEditable(dati["cilindrata"])
                        editTextOlio.text = Editable.Factory.getInstance().newEditable(dati["olioMotore"])
                            editTextRaff.text = Editable.Factory.getInstance().newEditable(dati["raffreddamento"])
                                editTextCambio.text = Editable.Factory.getInstance().newEditable(dati["olioCambio"])
                                    editTextService.text = Editable.Factory.getInstance().newEditable(dati["spiaService"])
                                        editTextBatt.text = Editable.Factory.getInstance().newEditable(dati["batteriaAlternatore"])
                                            editTextVentole.text = Editable.Factory.getInstance().newEditable(dati["ventole"])
                                                editTextCoppie.text = Editable.Factory.getInstance().newEditable(dati["coppie"])


            }else {

                hidKeyboard()

            }

        }

        fun enable(){

            editTextModello.isFocusableInTouchMode = true
            editTextCilindrata.isFocusableInTouchMode = true
            editTextOlio.isFocusableInTouchMode = true
            editTextRaff.isFocusableInTouchMode = true
            editTextCambio.isFocusableInTouchMode = true
            editTextService.isFocusableInTouchMode = true
            editTextBatt.isFocusableInTouchMode = true
            editTextVentole.isFocusableInTouchMode = true
            editTextCoppie.isFocusableInTouchMode = true


        }

        fun disable(){

            editTextModello.isFocusableInTouchMode = false
            editTextCilindrata.isFocusableInTouchMode = false
            editTextOlio.isFocusableInTouchMode = false
            editTextRaff.isFocusableInTouchMode = false
            editTextCambio.isFocusableInTouchMode = false
            editTextService.isFocusableInTouchMode = false
            editTextBatt.isFocusableInTouchMode = false
            editTextVentole.isFocusableInTouchMode = false
            editTextCoppie.isFocusableInTouchMode = false



        }


        fun saveInst(){
            val idNameT = codiceMtext.text.toString().trim()
                var ref2 = FirebaseDatabase.getInstance().getReference("datiVeicolo")
                    var datB = hashMapOf<String,String>()

            datB.set("modello", editTextModello.text.toString())
                datB.set("cilindrata", editTextCilindrata.text.toString())
                    datB.set("olioMotore", editTextOlio.text.toString())
                        datB.set("raffreddamento", editTextRaff.text.toString())
                            datB.set("olioCambio", editTextCilindrata.text.toString())
                                datB.set("coppie", editTextService.text.toString())
                                    datB.set("batteriaAlternatore", editTextBatt.text.toString())
                                        datB.set("ventole", editTextVentole.text.toString())
                                            datB.set("spiaService", editTextCoppie.text.toString())


                ref2.child(idNameT).setValue(datB).addOnCompleteListener {


            }




        }

        saveButtonD.setOnClickListener(object : View.OnClickListener {

            override fun onClick(v: View?) {

                if (editSave == false ){

                    editSave = true
                        enable()
                            saveButtonD.setImageResource(R.mipmap.pic_106)


                }else {

                    editSave = false
                        saveInst()
                            disable()
                                saveButtonD.setImageResource(R.mipmap.pic_107)

                    hidKeyboard()

                }

            }
        })



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

                }, 2000)

                return@OnKeyListener true
            }
            false
        })


    }


}
