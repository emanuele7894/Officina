package com.example.lele.officina

import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.example.lele.officina.data.offInterv
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class interventi_view : AppCompatActivity() {

    var editSave =  false
    internal lateinit var saveText: TextView
    internal lateinit var  titolo: TextView
    internal lateinit var targaTit: TextView
    internal lateinit var editTextData: EditText
    internal lateinit var editTextTipo: EditText
    internal lateinit var editTextkm: EditText
    internal lateinit var editTextPrezzo: EditText
    internal lateinit var editTextDescr: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interventi_view)

        saveText = findViewById(R.id.textSave)
        targaTit = findViewById(R.id.targaTit)
        titolo = findViewById(R.id.titolo)
        editTextData = findViewById(R.id.editTextData)
        editTextTipo = findViewById(R.id.editTextTipo)
        editTextkm = findViewById(R.id.editTextKm)
        editTextPrezzo = findViewById(R.id.editTextPrezzo)
        editTextDescr = findViewById(R.id.editTextDescr)

        val buttonBack = findViewById(R.id.buttonBack) as ImageButton
        val buttonSave = findViewById(R.id.buttonSave) as ImageButton
        val buttonPrev= findViewById(R.id.buttonPreventivo) as ImageButton
        val buttonContr = findViewById(R.id.buttonControllo) as  ImageButton
        val buttonTagl = findViewById(R.id.buttonTagliando) as ImageButton
        val colorRed = "B20D0D"
        val colorGray = "#BA353835"

        fun disabled(){

            buttonSave.setImageResource(R.mipmap.pic_107)
            titolo.setBackgroundColor(Color.GRAY)

            saveText.text = "Nuovo"
                editTextData.isEnabled = false
                    editTextTipo.isEnabled = false
                        editTextkm.isEnabled = false
                            editTextPrezzo.isEnabled = false
                                editTextDescr.isEnabled = false
        }

        fun enabled(){
            buttonSave.setImageResource(R.mipmap.pic_106)
            titolo.setBackgroundColor(Color.RED)

            saveText.text = "Salva"
                editTextData.isEnabled = true
                    editTextTipo.isEnabled = true
                        editTextkm.isEnabled = true
                            editTextPrezzo.isEnabled = true
                                editTextDescr.isEnabled = true
        }



        disabled()


        lateinit var  ref: DatabaseReference

        targaTit.text = intent.getStringExtra(".targa")



        buttonBack.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish()
            }
        })


        //Esegue il salvataggio dei dati cliente
        buttonSave.setOnClickListener(object : View.OnClickListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onClick(v: View?) {


                if ( editSave == false ){
                    //Nuovo Intervento

                    enabled()
                        editSave = true

                }else {
                    //Salva

                    var stringC = editTextData.text.toString().trim()
                    var stringD = editTextkm.text.toString().trim()


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

    }

    private fun saveMode(){

        val ref = FirebaseDatabase.getInstance().getReference("officinaInterventi")
        val idName = targaTit.text.toString().trim()
        val offInterv = offInterv(
            editTextData.toString().trim(),
            editTextTipo.text.toString().trim(),
            editTextkm.text.toString().trim(),
            editTextPrezzo.text.toString().trim(),
            editTextDescr.text.toString().trim()
        )

        ref.child(idName).setValue(offInterv).addOnCompleteListener {
            Toast.makeText(applicationContext, "Salvataggio avvenuto con successo!", Toast.LENGTH_LONG)
        }

    }

}
