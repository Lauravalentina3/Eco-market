package com.EcoMarket

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.EcoMarket.Fragments.HomeFragment


class InicioActivity : AppCompatActivity() {
        private val SPLASH_TIME_OUT: Long = 2000;
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.fragment_inicio)

            Log.d("SplashActivity", "onCreate: Iniciando Activity incio")

            //Configurar el temporizador de redireccion a Home Activity
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this, HomeFragment::class.java)
                startActivity(intent)
                finish()
            },SPLASH_TIME_OUT)
            // Llevar a MainActivity
//        Handler(mainLooper).postDelayed({
//            startActivity(
//                Intent(this, HomeActivity::class.java).apply {
//                    Intent.FLAG_ACTIVITY_SINGLE_TOP
//                }
//            );
//            finish();
//        }, SPLASH_TIME_OUT);

}}
