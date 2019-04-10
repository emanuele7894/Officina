package com.example.lele.officina

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.view.View
import android.widget.*
import com.example.lele.officina.data.offInterv
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_interventi_view.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import android.widget.ArrayAdapter
import java.text.SimpleDateFormat
import java.util.*
import android.widget.EditText
import android.view.inputmethod.InputMethodManager


class interventi_view : AppCompatActivity() {

    var editSave =  false
    internal lateinit var saveText: TextView
    internal lateinit var  titolo: TextView
    internal lateinit var targaTit: TextView
    internal  lateinit var number: TextView
    internal lateinit var editTextData: EditText
    internal lateinit var editTextTipo: EditText
    internal lateinit var editTextkm: EditText
    internal lateinit var editTextPrezzo: EditText
    internal lateinit var editTextDescr: EditText
    internal lateinit var  spinnetTipo: Spinner
    var idNameT = ""
    var idInter = ""
    var nomeCognome = ""
    var marcaModello = ""
    var numIns = 0
    var lista : ArrayList<String> = ArrayList()
    var listaTipo = arrayOf( "Nessuna Selezione !", "Azzeramento", "Controllo", "Riparazione", "Revisione", "Sostiutuzione", "Tagliando")


    lateinit var  ref: DatabaseReference
    lateinit var datiInterventi : MutableList<offInterv>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interventi_view)

        var k: Intent = Intent(this@interventi_view, Preventivo::class.java)
        var k1: Intent = Intent(this@interventi_view, Controllo::class.java)
        var k2: Intent = Intent(this@interventi_view, Tagliando::class.java)


        number = findViewById(R.id.number)
        saveText = findViewById(R.id.textSave)
        targaTit = findViewById(R.id.targaTit)
        titolo = findViewById(R.id.titolo)

        editTextData = findViewById(R.id.editTextData)
        editTextTipo = findViewById(R.id.editTextTipo)
        editTextkm = findViewById(R.id.editTextKm)
        editTextPrezzo = findViewById(R.id.editTextPrezzo)
        editTextDescr = findViewById(R.id.editTextDescr)
        spinnetTipo = findViewById(R.id.spinnerTipo)



        val buttoDataInterv = findViewById(R.id.buttonData) as ImageButton
        val buttonBack = findViewById(R.id.buttonBack) as ImageButton
        val buttonSave = findViewById(R.id.mailButton) as ImageButton
        val buttonPrev= findViewById(R.id.buttonPreventivo) as ImageButton
        val buttonContr = findViewById(R.id.buttonControllo) as  ImageButton
        val buttonTagl = findViewById(R.id.buttonTagliando) as ImageButton
        val deleteButtonI = findViewById(R.id.deleteButtonI) as ImageButton
        var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        var current = LocalDate.now().format(formatter)

        deleteButtonI.setAlpha(.3f)
        deleteButtonI.isEnabled = false


        fun disabled(){



            buttonSave.setImageResource(R.mipmap.pic_107)

            saveText.text = "Nuovo"
                editTextData.isEnabled = false
                    editTextTipo.isEnabled = false
                        editTextkm.isEnabled = false
                            editTextPrezzo.isEnabled = false
                                editTextDescr.isEnabled = false
                                    buttoDataInterv.isEnabled = false
                                        spinnerTipo.isEnabled = false
        }
        disabled()

        fun enabled(){
            buttonSave.setImageResource(R.mipmap.pic_106)

            saveText.text = "Salva"
                editTextData.isEnabled = true
                   editTextData.text = Editable.Factory.getInstance().newEditable(current.toString().trim())
                        editTextTipo.isEnabled = true
                        editTextTipo.requestFocus()
                            editTextTipo.text = Editable.Factory.getInstance().newEditable("")
                                editTextkm.isEnabled = true
                                    editTextkm.text = Editable.Factory.getInstance().newEditable("")
                                        editTextPrezzo.isEnabled = true
                                             editTextPrezzo.text = Editable.Factory.getInstance().newEditable("")
                                                 editTextDescr.isEnabled = true
                                                    editTextDescr.text = Editable.Factory.getInstance().newEditable("")
                                                        buttoDataInterv.isEnabled = true
                                                            spinnetTipo.isEnabled = true

        }



//pulsante datatIntervento


        var cal = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                    cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd/MM/yy" // mention the format you need
                val sdf = SimpleDateFormat(myFormat, Locale.ITALY)
                    editTextData.text = Editable.Factory.getInstance().newEditable(sdf.format(cal.time))


        }

        editTextkm.setOnFocusChangeListener(View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {

                val string = editTextkm.text.toString()
                    editTextkm.text = Editable.Factory.getInstance().newEditable("$string km")

            }else {

                editTextkm.text = Editable.Factory.getInstance().newEditable("")

            }
        })


        editTextPrezzo.setOnFocusChangeListener(View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {

                val string = editTextPrezzo.text.toString()
                    editTextPrezzo.text = Editable.Factory.getInstance().newEditable("$string €")

            }else {

                editTextPrezzo.text = Editable.Factory.getInstance().newEditable("")

            }
        })


        buttoDataInterv.setOnClickListener {
            DatePickerDialog(this@interventi_view, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }


        targaTit.text = intent.getStringExtra(".targa")
            nomeCognome = intent.getStringExtra(".nomeCognome")
                 marcaModello = intent.getStringExtra(".marca")


        idNameT = intent.getStringExtra(".targa")

        datiInterventi = mutableListOf()
        ref = FirebaseDatabase.getInstance().getReference("officinaInterventi").child(idNameT)



        fun load() {


            carcicatoTipo()

            ref.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onDataChange(p0: DataSnapshot) {




                    if(p0.exists()){

                        for(h in p0.children){
                            val dat = h.getValue(offInterv::class.java)
                            datiInterventi.add(dat!!)

                        }

                        lista.add("Nessuna Selezione !")

                        for(n in datiInterventi.indices){
                            lista.add(datiInterventi[n].idName)
                        }


                        caricato()



                    }
                }

            })

        }

        load()









        buttonBack.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish()
            }
        })

        //Pulsante Preventivo
        buttonPrev.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                k.putExtra(".targa", targaTit.text.toString())
                    k.putExtra(".nomeCognome", nomeCognome)
                        k.putExtra(".marca", marcaModello)
                startActivity(k)
            }
        })

        //Pulsante Controllo
        buttonContr.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                k1.putExtra(".targa", targaTit.text.toString())
                    k1.putExtra(".nomeCognome", nomeCognome)
                        k1.putExtra(".marca", marcaModello)
                startActivity(k1)
            }
        })


        //Pulsante Tagliando
        buttonTagl.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                k2.putExtra(".targa", targaTit.text.toString())
                    k2.putExtra(".nomeCognome", nomeCognome)
                        k2.putExtra(".marca", marcaModello)
                startActivity(k2)
            }
        })


        //Pulsante rimuovi Intervento
        deleteButtonI.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {


                val builder = AlertDialog.Builder(this@interventi_view)
                     builder.setTitle("Attenzione !!")
                         builder.setMessage("Eliminare l'intervento :  $idInter !!")

                builder.setPositiveButton("Elimina"){dialog, which ->
                    // Do something when user press the positive button
                    ref.child(idInter).removeValue()

                    Toast.makeText(applicationContext,"Intervento :  $idInter Eliminato ! ",Toast.LENGTH_SHORT).show()
                        lista = arrayListOf()
                            datiInterventi = mutableListOf()

                    deleteButtonI.setAlpha(.3f)
                        deleteButtonI.isEnabled = false

                    editTextData.text = Editable.Factory.getInstance().newEditable("")
                        editTextTipo.text = Editable.Factory.getInstance().newEditable("")
                             editTextkm.text = Editable.Factory.getInstance().newEditable("")
                                 editTextPrezzo.text = Editable.Factory.getInstance().newEditable("")
                                     editTextDescr.text = Editable.Factory.getInstance().newEditable("")


                                if (number.text == "1"){
                                    finish()
                                }



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
                    //Nuovo Intervento

                    enabled()
                        editSave = true
                    showSoftKeyboard(editTextTipo)

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

    //Carica lista tipo interventi

    private fun carcicatoTipo(){


        var adapter = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            listaTipo // Array
        )
        adapter.setDropDownViewResource(android.R.layout.select_dialog_item)


        spinnerTipo.adapter = adapter

        spinnerTipo.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent:AdapterView<*>, view: View, position: Int, id: Long){
                // Display the selected item text on text view


                    if (listaTipo[position] == "Nessuna Selezione !"){



                    }else {



                        editTextTipo.text = Editable.Factory.getInstance().newEditable(listaTipo[position])

                        showSoftKeyboard(editTextkm)


                    }






            }




            override fun onNothingSelected(parent: AdapterView<*>){
                // Another interface callback

            }
        }



    }


    private fun showSoftKeyboard(view: View) {
        if (view.requestFocus()) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

//Carica lista interventi
    private fun caricato (){

        number.text = datiInterventi.count().toString()

        var adapter = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            lista // Array
        )
        adapter.setDropDownViewResource(android.R.layout.select_dialog_item)


            spinnerInterv.adapter = adapter

        spinnerInterv.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent:AdapterView<*>, view: View, position: Int, id: Long){
                // Display the selected item text on text view

                if(numIns == 0){

                    numIns = 2

                }else if (numIns == 1 ){

                }else {

                    if (lista[position] == "Nessuna Selezione !"){

                    }else {

                        deleteButtonI.setAlpha(1.0f)
                            deleteButtonI.isEnabled = true


                        idInter = datiInterventi[position - 1].idName
                            editTextData.text = Editable.Factory.getInstance().newEditable(datiInterventi[position - 1].data)
                                editTextTipo.text = Editable.Factory.getInstance().newEditable(datiInterventi[position - 1].tipo)
                                    editTextkm.text = Editable.Factory.getInstance().newEditable(datiInterventi[position - 1].km)
                                        editTextPrezzo.text = Editable.Factory.getInstance().newEditable(datiInterventi[position - 1].prezzo)
                                            editTextDescr.text = Editable.Factory.getInstance().newEditable(datiInterventi[position - 1].descr)

                    }


                }



            }

            override fun onNothingSelected(parent: AdapterView<*>){
                // Another interface callback

            }
        }


    }

    private fun saveMode(){
        var idInter = editTextData.text.toString() + " " + editTextTipo.text.toString()
        idNameT = targaTit.text.toString().trim()
        ref = FirebaseDatabase.getInstance().getReference("officinaInterventi").child(idNameT)

        val offInterv = offInterv(
            idInter,
            editTextData.text.toString().trim(),
            editTextTipo.text.toString().trim(),
            editTextkm.text.toString().trim(),
            editTextPrezzo.text.toString().trim(),
            editTextDescr.text.toString().trim()
        )



        ref.child(idInter).setValue(offInterv).addOnCompleteListener {


        }

            lista = arrayListOf()
                datiInterventi = mutableListOf()


    }

}
