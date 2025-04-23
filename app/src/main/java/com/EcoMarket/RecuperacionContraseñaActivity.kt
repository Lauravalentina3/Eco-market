package com.EcoMarket

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.util.Patterns
import android.widget.TextView
import android.widget.Toast


class RecuperacionContraseñaActivity : AppCompatActivity(){

    private lateinit var ediTextCorreo : EditText
    private lateinit var buttonEnviar: Button
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var textResetPassword: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_recuperacion_contrasena)

        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE)

        //Inicializando variables generales
        ediTextCorreo = findViewById(R.id.editTextEmail)
        buttonEnviar = findViewById(R.id.buttonRecuperacion)

        //Configurar el boton de envio
        buttonEnviar.setOnClickListener{
            if(validarCorreo()){
                //Verifiacion del correo electronico
                verificarCorreo()
            }
        }
    }
    private fun validarCorreo(): Boolean {
        val correo = ediTextCorreo.text.toString().trim()
        if (correo.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            Toast.makeText(this, "Debes ingresar un correo valido para continuar", Toast.LENGTH_SHORT).show();

            return false
        }
        return true
    }

    private fun verificarCorreo(){
        val correo = ediTextCorreo.text.toString().trim()
        val correoRegistrado = sharedPreferences.getString("correo","")
        val array = sharedPreferences.all

        if (correo == correoRegistrado){
            Log.d("Recuperar Contraseña", "verificarCorreo: Verificar el correo del usuario que este correctamente")
            Toast.makeText(this, "Se le ha enviado un correo con su nueva contraseña de recuperación", Toast.LENGTH_SHORT).show();

            buttonEnviar.postDelayed({
                val intent = Intent(this,loginActivity::class.java)
                startActivity(intent)
                finish()
            },1500)
        }else{
            Toast.makeText(this, "El correo electronico ingresado no existe en el sistema", Toast.LENGTH_SHORT).show();
            Log.d("Recuperar Contraseña", "verificarCorreo: Error correo: $correo,  correoRegistrado: $correoRegistrado, array: $array")

        }
    }
}