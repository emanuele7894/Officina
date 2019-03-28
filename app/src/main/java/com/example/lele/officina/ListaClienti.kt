package com.example.lele.officina

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ImageButton
import android.widget.ListView
import com.example.lele.officina.adapter.offiListAdapter
import com.example.lele.officina.data.officinaDati
import com.google.firebase.database.*

class ListaClienti : AppCompatActivity() {

    internal lateinit var listView: ListView

    lateinit var  ref: DatabaseReference
    lateinit var dati : MutableList<officinaDati>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_clienti)


        dati = mutableListOf()
        ref = FirebaseDatabase.getInstance().getReference("officinaData")



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

                    val adapter = offiListAdapter(applicationContext, R.layout.offic_list_row, dati)
                        listView.adapter = adapter


                }

            }


        })



        var k1: Intent = Intent(this@ListaClienti, officina_cli::class.java)


        listView = findViewById(R.id.listView) as ListView
        val buttonBackton = findViewById(R.id.buttonBack) as ImageButton

        buttonBackton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish()
            }
        })


        val buttonAdd = findViewById(R.id.buttonAdd) as ImageButton

        buttonAdd.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                var numec = dati.count() + 1

                k1.putExtra(".nomeid", "Nuovo cliente")
                    k1.putExtra(".numec", numec.toString())

                startActivity(k1)

            }
        })




        // ListView Item Click Listener
        listView.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, index, l ->
            val clickItemObj = index



            if (dati.count() == 0){



            }else {


                val numec = clickItemObj + 1

                k1.putExtra(".nomeid", "" )
                     k1.putExtra(".numeid", index.toString().trim() )
                        k1.putExtra(".numec", numec.toString())



                    startActivity(k1)

            }



        }


    }


    override fun onRestart() {
        super.onRestart()

        finish()
        startActivity(getIntent())

    }


}









