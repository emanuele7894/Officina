package com.example.lele.officina

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_eod_view.*
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.TextKeyListener
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import com.example.lele.officina.data.officinaDati
import com.google.firebase.database.*


class eod_view : AppCompatActivity() {


    lateinit var  ref: DatabaseReference
    var keyC = true
    var dati = hashMapOf<String,String>()
    var modello = "Fiat"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eod_view)

        fun loadRef(){
            ref = FirebaseDatabase.getInstance().getReference("obd").child(modello)

            ref.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }


                override fun onDataChange(p0: DataSnapshot) {


                    if (p0.exists()) {

                        for (h in p0.children) {

                            dati.set(h.key.toString(), h.value.toString())

                        }


                    }

                }

            })

        }

        loadRef()

        selMarca.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                dati.clear()
                    modello = "Bmw"

                loadRef()


            }
        })










        buttonBack.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish()
            }
        })




        editTextCode.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {



                if (keyC == true) {



                    if (count == 1) {

                        editTextCode.setInputType(InputType.TYPE_CLASS_NUMBER)
                            keyC = false


                     }




                }

                if (editTextCode.length() == 0) {

                    editTextCode.setInputType(InputType.TYPE_CLASS_TEXT)
                        editTextCode.setKeyListener(TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true))
                            keyC = true



                }


            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {



            }

            override fun afterTextChanged(s: Editable) {

            }
        })


        fun ok(){

            var stringa = editTextCode.text.toString()
                var stringaB = dati[stringa]


            if(editTextCode.text.toString() == ""){


            }else {

                if(stringaB == null){

                    titleCode.text = "Codice non presente"
                        codeDesc.text = "Codice: $stringa non presente , controlla di aver inserito il codice in modo corretto oppure effettua una ricerca su Google con il pulsante G. "

                }else{

                    codeDesc.text = stringaB
                        titleCode.text = stringa

                }
                val inputManager:InputMethodManager =getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputManager.hideSoftInputFromWindow(currentFocus.windowToken, InputMethodManager.SHOW_FORCED)

            }

        }

        editTextCode.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                //Perform Code
                ok()
                return@OnKeyListener true
            }
            false
        })


        okButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                ok()


            }
        })



    }


}
