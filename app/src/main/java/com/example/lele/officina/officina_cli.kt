package com.example.lele.officina

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.example.lele.officina.data.officinaDati
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_officina_cli.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import android.text.method.TextKeyListener
import android.view.KeyEvent


class officina_cli : AppCompatActivity() {

    var editSave =  false
    var stringaCaricata = ""

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
    var idName = ""
    var idTarga = ""
    var chek = false
    var keyC = true
    lateinit var  ref: DatabaseReference
    lateinit var  ref2: DatabaseReference
    lateinit var dati : MutableList<officinaDati>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_officina_cli)



        var k: Intent = Intent(this@officina_cli, interventi_view::class.java)

        numeroCli = findViewById(R.id.numeroCliente)
        titolo = findViewById(R.id.TitoloPreventivo)
        targaTit = findViewById(R.id.targaTit)
        textSave = findViewById(R.id.textSave)
        nome_cognome = findViewById(R.id.editTextNC)
        telefono = findViewById(R.id.editTextTel)
        mail = findViewById(R.id.editTextMail)
        marca_modello = findViewById(R.id.editTextMM)
        targa = findViewById(R.id.editTextTarga)
        note = findViewById(R.id.editTextNote)

        val buttonBack = findViewById(R.id.buttonBack) as ImageButton
        val buttonSave = findViewById(R.id.mailButton) as ImageButton
        val buttonInterv = findViewById(R.id.buttonInterv) as ImageButton
        val buttonTel = findViewById(R.id.buttonTel) as ImageButton
        val buttonMail = findViewById(R.id.buttonMail) as ImageButton
        val deleteButton = findViewById(R.id.printButton) as ImageButton

        val dataRev = findViewById(R.id.buttonDataRev) as Button
        val dataTagl = findViewById(R.id.buttonTagl) as Button



        buttonBack.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish()
            }
        })

        dati = mutableListOf()
        ref = FirebaseDatabase.getInstance().getReference("officinaData")
        ref2 = FirebaseDatabase.getInstance().getReference("officinaInterventi")

        var nomeTitolo =  intent.getStringExtra(".nomeid")

        fun showSoftKeyboard(view: View) {
            if (view.requestFocus()) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
            }
        }

        fun disabled(){

            buttonSave.setImageResource(R.mipmap.pic_107)
            textSave.text = "Edita"
            nome_cognome.isEnabled = false
            telefono.isEnabled = false
            mail.isEnabled = false
            marca_modello.isEnabled = false
            targa.isEnabled = false
            note.isEnabled = false
            dataRev.isEnabled = false
            dataTagl.isEnabled = false
            checkBoxRev.isEnabled = false
            checkBoxTagl.isEnabled = false
        }

        fun enabled(){
            buttonSave.setImageResource(R.mipmap.pic_106)
            nome_cognome.isEnabled = true
            targa.isEnabled = true
            textSave.text = "Salva"
            telefono.isEnabled = true
            mail.isEnabled = true
            marca_modello.isEnabled = true
            note.isEnabled = true
            dataRev.isEnabled = true
            dataTagl.isEnabled = true
            checkBoxRev.isEnabled = true
            checkBoxTagl.isEnabled = true


            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)

            showSoftKeyboard(editTextNC)

        }

      

        if (nomeTitolo == "Nuovo cliente"){

            //Salvare nuovo cliente


            deleteButton.setAlpha(.3f)
                deleteButton.isEnabled = false

            buttonTel.setAlpha(.3f)
                buttonTel.isEnabled = false

            buttonMail.setAlpha(.3f)
                buttonMail.isEnabled = false

                enabled()
                    numeroCli.text = "Cliente N° : " + intent.getStringExtra(".numec")
                        titolo.text = "Nuovo cliente"
                            targaTit.text = "-------"
                                editSave = true
                                    stringaCaricata = "Nuovo"




        }else {

            val numeroId = intent.getStringExtra(".numeid")
                numeroCli.text = "Cliente N° : " + intent.getStringExtra(".numec")
                    titolo.text = nomeTitolo

            editSave = false
            stringaCaricata = "Esistente"
                disabled()

            fun controlTelMail(){


                if (telefono.text.toString() == "") {

                    buttonTel.setAlpha(.3f)
                    buttonTel.isEnabled = false

                } else {

                    buttonTel.setAlpha(1.0f)
                    buttonTel.isEnabled = true

                }

                if (mail.text.toString() == "") {

                    buttonMail.setAlpha(.3f)
                    buttonMail.isEnabled = false

                } else {

                    buttonMail.setAlpha(1.0f)
                    buttonMail.isEnabled = true

                }
            }




            ref.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }



                override fun onDataChange(p0: DataSnapshot) {

                    if (chek == false) {


                        if (p0.exists()) {

                            for (h in p0.children) {
                                val dat = h.getValue(officinaDati::class.java)
                                dati.add(dat!!)

                            }

                            chek = true

                            numeroCli.text = "Cliente N° : " + dati[numeroId.toInt()].id.toString()
                            titolo.text = dati[numeroId.toInt()].name.toString()
                            targaTit.text = dati[numeroId.toInt()].targa.toString()


                            nome_cognome.text =
                                Editable.Factory.getInstance().newEditable(dati[numeroId.toInt()].name.toString())
                            telefono.text =
                                Editable.Factory.getInstance().newEditable(dati[numeroId.toInt()].telefono.toString())
                            mail.text =
                                Editable.Factory.getInstance().newEditable(dati[numeroId.toInt()].mail.toString())
                            marca_modello.text =
                                Editable.Factory.getInstance().newEditable(dati[numeroId.toInt()].marca.toString())
                            targa.text =
                                Editable.Factory.getInstance().newEditable(dati[numeroId.toInt()].targa.toString())
                            checkBoxRev.isChecked = dati[numeroId.toInt()].checkRev
                            checkBoxTagl.isChecked = dati[numeroId.toInt()].checkTagl
                            dataRev.text = dati[numeroId.toInt()].dataRev
                            dataTagl.text = dati[numeroId.toInt()].dataTagl
                            note.text =
                                Editable.Factory.getInstance().newEditable(dati[numeroId.toInt()].note.toString())
                            idName = dati[numeroId.toInt()].idName.toString()
                            idTarga = dati[numeroId.toInt()].targa.toString()


                            controlTelMail()

                        }

                    }



                }



            })


        }


        targa.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                if (targa.length() == 2){

                    targa.setInputType(InputType.TYPE_CLASS_NUMBER)


                }

                if (targa.length() == 5){

                    targa.setInputType(InputType.TYPE_CLASS_TEXT)
                        targa.setKeyListener(TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true))

                }

                if (targa.length() == 4){

                    targa.setInputType(InputType.TYPE_CLASS_NUMBER)


                }

                if (targa.length() == 1){

                    targa.setInputType(InputType.TYPE_CLASS_TEXT)
                        targa.setKeyListener(TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true))

                }



            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {



            }

            override fun afterTextChanged(s: Editable) {

            }
        })

        targa.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                //Perform Code
                val inputManager:InputMethodManager =getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputManager.hideSoftInputFromWindow(currentFocus.windowToken, InputMethodManager.SHOW_FORCED)

                return@OnKeyListener true
            }
            false
        })



        //pulsante datatRevisione


                var cal = Calendar.getInstance()

                val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    cal.set(Calendar.YEAR, year)
                    cal.set(Calendar.MONTH, monthOfYear)
                    cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                    val myFormat = "dd/MM/yy" // mention the format you need
                    val sdf = SimpleDateFormat(myFormat, Locale.ITALY)
                    dataRev.text = sdf.format(cal.time)


                }


                dataRev.setOnClickListener {
                    DatePickerDialog(this@officina_cli, dateSetListener,
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)).show()
                }

        //Pulsante data tagliando

        val dateSetListener2 = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd/MM/yy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.ITALY)
            dataTagl.text = sdf.format(cal.time)


        }


        dataTagl.setOnClickListener {
            DatePickerDialog(this@officina_cli, dateSetListener2,
                cal.get(Calendar.YEAR),
                 cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
     }

        //pulsante chiamata telefono o invio sms
        buttonTel.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                val builder = AlertDialog.Builder(this@officina_cli)
                builder.setTitle("Comunica con il cliente !")
                builder.setMessage("Vuoi effettuare una chiamate o inviare un messaggio ?  al numero di telefono :  ${telefono.text} !!")

                builder.setPositiveButton("Chiama"){dialog, which ->
                    // Do something when user press the positive button


                    phoneNum(telefono.text.toString())

                }

                builder.setNegativeButton("Messaggio "){dialog, wich ->

                    sendSMS(telefono.text.toString())

                }

                // Display a neutral button on alert dialog
                builder.setNeutralButton("Annulla"){_,_ ->

                }

                // Finally, make the alert dialog using builder
                val dialog: AlertDialog = builder.create()

                // Display the alert dialog on app interface
                dialog.show()


            }
        })

        //pulsante invio mail
        buttonMail.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {


                val builder = AlertDialog.Builder(this@officina_cli)
                builder.setTitle("Comunica con il cliente !")
                builder.setMessage("Invia una mail tramite la tua app di posta alla mail  :  ${mail.text} !!")

                builder.setPositiveButton("Invia"){dialog, which ->
                    // Do something when user press the positive button

                    sendMail(mail.text.toString())

                }


                // Display a neutral button on alert dialog
                builder.setNeutralButton("Annulla"){_,_ ->

                }

                // Finally, make the alert dialog using builder
                val dialog: AlertDialog = builder.create()

                // Display the alert dialog on app interface
                dialog.show()



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
                    //showSoftKeyboard(editTextNC)


                }else {
                    //Salva



                    var stringC = nome_cognome.text.toString().trim()
                         var stringD = targa.text.toString().trim()

                    val builder = AlertDialog.Builder(this@officina_cli)
                    builder.setTitle("Attenzione !!")
                    builder.setMessage("I campi Nome e cognome ed il campo Targa non possono essere vuoti ! ")

                    builder.setPositiveButton("Ok"){dialog, which ->
                        // Do something when user press the positive button


                    }

                    val dialog: AlertDialog = builder.create()

                    if (stringC == "" )  {

                        //Salvataggio non eseguito
                        dialog.show()

                    }else  if (stringD == "") {

                        //Salvataggio non eseguito
                        dialog.show()

                    }else{

                        //Salvataggio eseguito

                                    saveMode()
                                        disabled()
                        titolo.text = editTextNC.text.toString()
                            targaTit.text = editTextTarga.text.toString()

                        deleteButton.setAlpha(1.0f)
                            deleteButton.isEnabled = true

                        editSave = false

                        if (telefono.text.toString() == "") {

                            buttonTel.setAlpha(.3f)
                            buttonTel.isEnabled = false

                        } else {

                            buttonTel.setAlpha(1.0f)
                            buttonTel.isEnabled = true

                        }

                        if (mail.text.toString() == "") {

                            buttonMail.setAlpha(.3f)
                            buttonMail.isEnabled = false

                        } else {

                            buttonMail.setAlpha(1.0f)
                            buttonMail.isEnabled = true

                        }

                    }




                }




            }
        })



            deleteButton.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    //Elimina il cliente con tutti i dati e interventi



                    if(editSave == true){




                    }else {

                        val builder = AlertDialog.Builder(this@officina_cli)
                        builder.setTitle("Attenzione !!")
                        builder.setMessage("Vuoi davvero eliminare tutti i dati del cliente ${nome_cognome.text} !!")

                        builder.setPositiveButton("Elimina"){dialog, which ->
                            // Do something when user press the positive button
                            ref.child(idName).removeValue()
                                ref2.child(idTarga).removeValue()
                                    finish()
                            Toast.makeText(applicationContext,"I dati del cliente : ${nome_cognome.text} sono stati eliminati ! ",Toast.LENGTH_SHORT).show()

                        }

                        // Display a neutral button on alert dialog
                        builder.setNeutralButton("Annulla"){_,_ ->

                        }

                        // Finally, make the alert dialog using builder
                        val dialog: AlertDialog = builder.create()

                        // Display the alert dialog on app interface
                        dialog.show()

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
                            k.putExtra(".nomeCognome", nome_cognome.text.toString())
                                k.putExtra(".marca", marca_modello.text.toString())

                        startActivity(k)

                    }


                }
            })


    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveMode(){

        var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            var current = LocalDate.now().format(formatter)

             ref = FirebaseDatabase.getInstance().getReference("officinaData")

      if (stringaCaricata == "Nuovo"){

          idName = nome_cognome.text.toString().trim() + " - " + targa.text.toString().trim()


      }else if (stringaCaricata == "Esistente"){


      }


        val officinaDati = officinaDati(
            idName.toString().trim(),
            intent.getStringExtra(".numec"),
            current.toString(),
            nome_cognome.text.toString().trim(),
            telefono.text.toString().trim(),
            mail.text.toString().trim(),
            marca_modello.text.toString().trim(),
            targa.text.toString().trim(),
            checkBoxRev.isChecked,
            buttonDataRev.text.toString().trim(),
            checkBoxTagl.isChecked,
            buttonTagl.text.toString().trim(),
            note.text.toString().trim()
        )


        ref.child(idName).setValue(officinaDati).addOnCompleteListener {
            //Toast.makeText(applicationContext, "Salvataggio avvenuto con successo!", Toast.LENGTH_LONG)


        }


    }

    fun sendMail(mail: String){
        val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:" + mail) // only email apps should handle this
                intent.putExtra(Intent.EXTRA_EMAIL, mail)
                    intent.putExtra(Intent.EXTRA_SUBJECT,"Officina Bellotti")
                        startActivity(intent)
    }

    fun phoneNum(phone: String){
        val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$phone")
                 startActivity(intent)
    }

    fun sendSMS(phone: String)
    {
        val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("smsto:")
                intent.putExtra("address", phone)
                         startActivity(intent)
    }


}
