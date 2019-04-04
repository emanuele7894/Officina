package com.example.lele.officina.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.lele.officina.R
import com.example.lele.officina.data.officinaDati


class offiListAdapter(val mCtx: Context, val layoutResId: Int, val listOff: List<officinaDati>)
    : ArrayAdapter<officinaDati>(mCtx, layoutResId, listOff) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId, null)
        val textName = view.findViewById<TextView>(R.id.TitoloPreventivo)
        val itemText = view.findViewById<TextView>(R.id.itemO)
        val targaName = view.findViewById<TextView>(R.id.targaItem)
        val dat = listOff[position]
        val num = position + 1

        itemText.text = num.toString().trim()
        textName.text = dat.name
        targaName.text = dat.targa



        return view

    }


}

