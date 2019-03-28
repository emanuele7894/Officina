package com.example.lele.officina

import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.text.Editable
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.example.lele.officina.adapter.offiListAdapter
import com.example.lele.officina.data.officinaDati
import com.google.firebase.database.*
import java.time.LocalDateTime

class officina_cli : AppCompatActivity() {

    var editSave =  false

    internal lateinit var  numeroCli: TextView
    internal lateinit var titolo: TextView
    internal lateinit var  textSave: TextView
    internal lateinit var  targaTit: TextView
    internal lateinit var  nome_cognome: EditText
    internal lateinit var  telefono: EditText
    internal lateinit var  mail: EditText
    internal lateinit var  marca_modello: EditText
    internal lateinit var  targa: EditText
    internal lateinit var  note: EditText

    lateinit var  ref: DatabaseReference
    lateinit var dati : MutableList<officinaDati>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_officina_cli)



        var k: Intent = Intent(this@officina_cli, interventi_view::class.java)

        numeroCli = findViewById(R.id.numeroCliente)
        titolo = findViewById(R.id.titoloO1)
        targaTit = findViewById(R.id.targaTit)
        textSave = findViewById(R.id.textSave)
        nome_cognome = findViewById(R.id.editTextNC)
        telefono = findViewById(R.id.editTextTel)
        mail = findViewById(R.id.editTextMail)
        marca_modello = findViewById(R.id.editTextMM)
        targa = findViewById(R.id.editTextTarga)
        note = findViewById(R.id.editTextNote)

        val buttonBack = findViewById(R.id.buttonBack) as ImageButton
        val buttonSave = findViewById(R.id.buttonSave) as ImageButton
        val buttonInterv = findViewById(R.id.buttonInterv) as ImageButton
        val buttonTel = findViewById(R.id.buttonTel) as ImageButton
        val buttonMail = findViewById(R.id.buttonMail) as ImageButton



        buttonBack.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish()
            }
        })

        dati = mutableListOf()
        ref = FirebaseDatabase.getInstance().getReference("officinaData")

        var nomeTitolo =  intent.getStringExtra(".nomeid")



        fun disabled(){

            buttonSave.setImageResource(R.mipmap.pic_107)

            textSave.text = "Edita"
            nome_cognome.isEnabled = false
            telefono.isEnabled = false
            mail.isEnabled = false
            marca_modello.isEnabled = false
            targa.isEnabled = false
            note.isEnabled = false
        }

        fun enabled(){
            buttonSave.setImageResource(R.mipmap.pic_106)

            textSave.text = "Salva"
            nome_cognome.isEnabled = true
            telefono.isEnabled = true
            mail.isEnabled = true
            marca_modello.isEnabled = true
            targa.isEnabled = true
            note.isEnabled = true
        }



        if (nomeTitolo == "Nuovo cliente"){

            //Salvare nuovo cliente
            enabled()

            numeroCli.text = "Cliente N° : " + intent.getStringExtra(".numec")
                titolo.text = "Nuovo cliente"
                    targaTit.text = "-------"
                        editSave = true

        }else {

            val numeroId = intent.getStringExtra(".numeid")
                numeroCli.text = "Cliente N° : " + intent.getStringExtra(".numec")
                    titolo.text = nomeTitolo

            editSave = false
                disabled()


            ref.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onDataChange(p0: DataSnapshot) {

                    if(p0.exists()){

                        for(h in p0.children){
                            val dat = h.getValue(officinaDati::class.java)
                            dati.add(dat!!)

                        }

                        numeroCli.text = dati[numeroId.toInt()].id.toString()
                            titolo.text = dati[numeroId.toInt()].name.toString()
                                targaTit.text = dati[numeroId.toInt()].targa.toString()


                        nome_cognome.text = Editable.Factory.getInstance().newEditable(dati[numeroId.toInt()].name.toString())
                            telefono.text = Editable.Factory.getInstance().newEditable(dati[numeroId.toInt()].telefono.toString())
                                mail.text = Editable.Factory.getInstance().newEditable(dati[numeroId.toInt()].mail.toString())
                                    marca_modello.text = Editable.Factory.getInstance().newEditable(dati[numeroId.toInt()].marca.toString())
                                        targa.text = Editable.Factory.getInstance().newEditable(dati[numeroId.toInt()].targa.toString())
                                            note.text = Editable.Factory.getInstance().newEditable(dati[numeroId.toInt()].note.toString())


                    }

                }


            })




        }


        //pulsante chiamata telefono o invio sms
        buttonTel.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {


            }
        })

        //pulsante invio mail
        buttonMail.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {


            }
        })

        //Esegue il salvataggio dei dati cliente
        buttonSave.setOnClickListener(object : View.OnClickListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onClick(v: View?) {
                if ( editSave == false ){
                    //edita
                    enabled()
                        editSave = true


                }else {
                    //Salva

                    var stringC = nome_cognome.text.toString().trim()
                         var stringD = targa.text.toString().trim()


                    if (stringC == "" )  {

                        //Salvataggio non eseguito

                    }else  if (stringD == "") {

                        //Salvataggio non eseguito

                    }else{

                        //Salvataggio eseguito

                                    saveMode()
                                        disabled()

                        editSave = false

                    }




                }

            }
        })

//Pulsante interventi

            buttonInterv.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    if (targa.text.toString() == ""){

                        //Non accede a interventi

                    }else {

                        k.putExtra(".targa", targa.text.toString())
                            startActivity(k)

                    }


                }
            })


    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveMode(){

        val current = LocalDateTime.now()
    val ref = FirebaseDatabase.getInstance().getReference("officinaData")
        val idName = nome_cognome.text.toString().trim() + "-" + targa.text.toString().trim()
        val officinaDati = officinaDati(
            intent.getStringExtra(".numec"),
            current.toString().trim(),
            nome_cognome.text.toString().trim(),
            telefono.text.toString().trim(),
            mail.text.toString().trim(),
            marca_modello.text.toString().trim(),
            targa.text.toString().trim(),
            note.text.toString().trim()
        )

        ref.child(idName).setValue(officinaDati).addOnCompleteListener {
            Toast.makeText(applicationContext, "Salvataggio avvenuto con successo!", Toast.LENGTH_LONG)
        }


    }




}
