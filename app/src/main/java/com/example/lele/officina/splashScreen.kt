package com.example.lele.officina

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler



class splashScreen : AppCompatActivity() {

    private var splashLoaded = false


    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        if (!splashLoaded) {
            setContentView(R.layout.activity_splash_screen)
            val secondsDelayed = 1




            Handler().postDelayed({
                startActivity(Intent(this@splashScreen, Home::class.java))
                finish()
            }, (secondsDelayed * 2000).toLong())

            splashLoaded = true
        } else {
            val goToMainActivity = Intent(this@splashScreen, Home::class.java)
            goToMainActivity.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(goToMainActivity)
            finish()
        }
    }
}