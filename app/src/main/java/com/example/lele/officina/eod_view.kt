package com.example.lele.officina

import android.animation.ObjectAnimator
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import kotlinx.android.synthetic.main.activity_eod_view.*
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.TextKeyListener
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ListView
import com.example.lele.officina.adapter.CustomAdapter
import com.example.lele.officina.data.ImageModel
import com.example.lele.officina.data.officinaDati
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_eod_view.buttonBack
import kotlinx.android.synthetic.main.activity_interventi_view.view.*
import kotlinx.android.synthetic.main.activity_lista_clienti.*
import android.content.Intent
import android.net.Uri


class eod_view : AppCompatActivity() {


    lateinit var  ref: DatabaseReference
    var keyC = true
    var openL = true
    var dati = hashMapOf<String,String>()
    var modello = "Fiat"


    internal lateinit var lv: ListView
    private var customeAdapter: CustomAdapter? = null
    private var imageModelArrayList: ArrayList<ImageModel>? = null
    private val myImageList = intArrayOf(R.mipmap.alfa_romeo,R.mipmap.altri_veicoli,R.mipmap.audi,R.mipmap.bmw, R.mipmap.citroen,R.mipmap.dacia,R.mipmap.fiat,R.mipmap.ford,R.mipmap.hyundai,R.mipmap.iveco,
        R.mipmap.lancia,R.mipmap.mercedes,R.mipmap.miniold,R.mipmap.nissan,R.mipmap.opel,R.mipmap.peugeot,R.mipmap.renault,R.mipmap.rover,R.mipmap.saab,R.mipmap.seat,
        R.mipmap.skoda,R.mipmap.smart,R.mipmap.volkswagen2,R.mipmap.volvo)
    private val myImageNameList = arrayOf("Alfa Romeo","ALtri veicoli", "Audi","Bmw","Citroen","Dacia","Fiat","Ford",
        "Hyundai", "Iveco", "Lancia", "Mercedes-benz", "Mini", "Nissan", "Opel", "Peugeot", "Renault", "Rover", "Saab",
        "Seat", "Skoda", "Smart", "Volkswagen", "Volvo")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eod_view)

        lv = findViewById(R.id.listView) as ListView

        imageModelArrayList = populateList()
        customeAdapter = CustomAdapter(this, imageModelArrayList!!)
        lv!!.adapter = customeAdapter


        //Carica Dati array
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


                if (openL == true){
                    //Lista Visibile

                    lv.visibility = View.VISIBLE

                    val listVisible = ObjectAnimator.ofFloat(lv, View.ALPHA,  1.0f)
                        listVisible.duration = 500
                            listVisible.start()
                    openL = false
                        selMarca.alpha = 0.4f



                }else {




                    val listVisible = ObjectAnimator.ofFloat(lv, View.ALPHA,  0.0f)
                        listVisible.duration = 500
                            listVisible.start()



                    Handler().postDelayed({
                        lv.visibility = View.INVISIBLE
                    }, 500)
                    openL = true
                        selMarca.alpha = 1.0f


                }



            }
        })

        lv.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, index, l ->


            dati.clear()

            if(index == 0){

                    modello = "Fiat"
                        marcaText.text = myImageNameList[index]
                            selMarca.setImageResource(R.mipmap.alfa_romeo)

            }else if (index == 1){

                    modello = "Standard"
                        marcaText.text = myImageNameList[index]
                            selMarca.setImageResource(R.mipmap.altri_veicoli)

            }else if (index == 2){

                modello = "Vag"
                    marcaText.text = myImageNameList[index]
                        selMarca.setImageResource(R.mipmap.audi)

            }else if (index == 3){

                modello = "Bmw"
                    marcaText.text = myImageNameList[index]
                        selMarca.setImageResource(R.mipmap.bmw)

            }else if (index == 4){

                modello = "PSA"
                    marcaText.text = myImageNameList[index]
                        selMarca.setImageResource(R.mipmap.citroen)

            }else if (index == 5){

                modello = "Standard"
                    marcaText.text = myImageNameList[index]
                        selMarca.setImageResource(R.mipmap.dacia)

            }else if (index == 6){

                modello = "Fiat"
                    marcaText.text = myImageNameList[index]
                        selMarca.setImageResource(R.mipmap.fiat)

            }else if (index == 7){

                modello = "Ford"
                    marcaText.text = myImageNameList[index]
                        selMarca.setImageResource(R.mipmap.ford)

            }else if (index == 8){

                modello = "Standard"
                    marcaText.text = myImageNameList[index]
                        selMarca.setImageResource(R.mipmap.hyundai)

            }else if (index == 9){

                modello = "Standard"
                    marcaText.text = myImageNameList[index]
                        selMarca.setImageResource(R.mipmap.iveco)

            }else if (index == 10){

                modello = "Fiat"
                    marcaText.text = myImageNameList[index]
                        selMarca.setImageResource(R.mipmap.lancia)

            }else if (index == 11){

                modello = "Mercedes"
                    marcaText.text = myImageNameList[index]
                        selMarca.setImageResource(R.mipmap.mercedes)

            }else if (index == 12){

                modello = "Standard"
                    marcaText.text = myImageNameList[index]
                        selMarca.setImageResource(R.mipmap.miniold)

            }else if (index == 13){

                modello = "Standard"
                    marcaText.text = myImageNameList[index]
                        selMarca.setImageResource(R.mipmap.nissan)

            }else if (index == 14){

                modello = "Standard"
                    marcaText.text = myImageNameList[index]
                        selMarca.setImageResource(R.mipmap.opel)

            }else if (index == 15){

                modello = "PSA"
                    marcaText.text = myImageNameList[index]
                        selMarca.setImageResource(R.mipmap.peugeot)

            }else if (index == 16){

                modello = "Standard"
                    marcaText.text = myImageNameList[index]
                        selMarca.setImageResource(R.mipmap.renault)

            }else if (index == 17){

                modello = "Standard"
                    marcaText.text = myImageNameList[index]
                        selMarca.setImageResource(R.mipmap.rover)

            }else if (index == 18){

                modello = "Standard"
                    marcaText.text = myImageNameList[index]
                        selMarca.setImageResource(R.mipmap.saab)

            }else if (index == 19){

                modello = "Vag"
                    marcaText.text = myImageNameList[index]
                        selMarca.setImageResource(R.mipmap.seat)

            }else if (index == 20){

                modello = "Vag"
                    marcaText.text = myImageNameList[index]
                        selMarca.setImageResource(R.mipmap.skoda)

            }else if (index == 21){

                modello = "Standard"
                    marcaText.text = myImageNameList[index]
                        selMarca.setImageResource(R.mipmap.smart)

            }else if (index == 22){

                modello = "Vag"
                    marcaText.text = myImageNameList[index]
                        selMarca.setImageResource(R.mipmap.volkswagen2)

            }else if (index == 23){

                modello = "Standard"
                    marcaText.text = myImageNameList[index]
                        selMarca.setImageResource(R.mipmap.volvo)

            }


            loadRef()
                openL = true
                    selMarca.alpha = 1.0f



            //chiusura Lista
            val listVisible = ObjectAnimator.ofFloat(lv, View.ALPHA,  0.0f)
                listVisible.duration = 500
                    listVisible.start()


            Handler().postDelayed({

                lv.visibility = View.INVISIBLE

            }, 500)


        }







        searchInternet.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                val stringa = editTextCode.text.toString()
                    val stringab = marcaText.text.toString()
                        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com/search?q= $stringa $stringab code error"))

                startActivity(browserIntent)
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
                    var stringaCode = "Codice: $stringa non presente , controlla di aver inserito il codice in modo corretto oppure effettua una ricerca su Google con il pulsante G. "

                    titleCode.text = "Codice non presente"
                        codeDesc.text = Editable.Factory.getInstance().newEditable(stringaCode)

                }else{


                    codeDesc.text =  Editable.Factory.getInstance().newEditable(stringaB)
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


    private fun populateList(): ArrayList<ImageModel> {

        val list = ArrayList<ImageModel>()

        for (i in 0..myImageNameList.count() - 1) {
            val imageModel = ImageModel()
            imageModel.setNames(myImageNameList[i])
            imageModel.setImage_drawables(myImageList[i])
            list.add(imageModel)
        }

        return list
    }


}
