package com.example.lele.officina

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_eod_view.*
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher



class eod_view : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eod_view)

        buttonBack.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish()
            }
        })

        editTextCode.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                if (count > 0){

                    editTextCode.setInputType(InputType.TYPE_CLASS_NUMBER)


                }

                if(editTextCode.text.toString() == ""){

                    editTextCode.setInputType(InputType.TYPE_CLASS_TEXT)
                        editTextCode.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS)



                }

            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {



            }

            override fun afterTextChanged(s: Editable) {

            }
        })




        okButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {



            }
        })
    }
}
