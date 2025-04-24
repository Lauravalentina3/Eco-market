package com.EcoMarket


import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.EcoMarket.Fragments.LoginFragment

class RegistroActivity : AppCompatActivity() {
    private lateinit var editTextNombres: EditText
    private lateinit var editTextApellidos: EditText
    private lateinit var editTextCorreo: EditText
    private lateinit var buttonRegistro: Button
    private lateinit var editTextContraseña: EditText
    private lateinit var editTextRepetirContrasena: EditText
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var buttonRegresar: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_registro)

        Log.d("RegistroActivity", "onCreate: Registro Activity Iniciando")

        //Inicializar las variables
        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE)

        //Inicializar variables de vista
        editTextNombres = findViewById(R.id.editTextNombres)
        editTextApellidos = findViewById(R.id.editTextApellidos)
        editTextCorreo = findViewById(R.id.editTextCorreo)
        editTextContraseña = findViewById(R.id.editTextContrasena)
        editTextRepetirContrasena = findViewById(R.id.editTextRepetirContrasena)
        buttonRegistro = findViewById(R.id.buttonRegistro)
        buttonRegresar = findViewById(R.id.buttonRegresar)

        //Configuracion listener boton de registro

        buttonRegistro.setOnClickListener {
            if (validarCampos()){
                //metodo de guardar datos de usuario
                guardarDatosUsuario()
                //redireccionamiento
                val intent = Intent(this, LoginFragment::class.java)
                startActivity(intent)
                finish()
            }
        }
        buttonRegresar.setOnClickListener{
            val intent = Intent(this, LoginFragment::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun guardarDatosUsuario() {
        val editor = sharedPreferences.edit()
        editor.putString("nombres",editTextNombres.text.toString().trim())
        editor.putString("apellidos",editTextApellidos.text.toString().trim())
        editor.putString("correo",editTextCorreo.text.toString().trim())
        editor.putString("Contraseña",editTextContraseña.text.toString().trim())
        editor.putString("RepetirContraseña",editTextRepetirContrasena.text.toString().trim())
        editor.putString("id1","hola")

        val correoRegistrado = sharedPreferences.getString("correo","")
        val id1 = sharedPreferences.getString("id1","")

        editor.apply()
        Log.d("Registro Activity", "guardarDatosUsuario: Datos del usuario guardados")
        Toast.makeText(this,"Registro exitoso",Toast.LENGTH_SHORT).show()
        Log.d("Recuperar Contraseña", "verificarCorreo: Error correo: $correoRegistrado, id: $id1")
    }
    private fun validarCampos(): Boolean {
        val nombres = editTextNombres.text.toString().trim()
        val apellidos = editTextApellidos.text.toString().trim()
        val correo = editTextCorreo.text.toString().trim()
        val contraseña = editTextContraseña.text.toString().trim()
        val RepetirContraseña = editTextRepetirContrasena.text.toString().trim()

        var isValid = true

        if (nombres.isEmpty()) {
            Toast.makeText(this, "El campo de Nombres es obligatorio", Toast.LENGTH_SHORT).show()
            isValid = false
        }
        if (apellidos.isEmpty()) {
            Toast.makeText(this, "El campo de Apellidos es obligatorio", Toast.LENGTH_SHORT).show()
            isValid = false
        }
        if (correo.isEmpty()) {
            Toast.makeText(this, "El campo de Correo Electrónico es obligatorio", Toast.LENGTH_SHORT).show()
            isValid = false
        }
        if (contraseña.isEmpty()) {
            Toast.makeText(this, "El campo de Contraseña es obligatorio", Toast.LENGTH_SHORT).show()
            isValid = false
        }
        if (RepetirContraseña.isEmpty()) {
            Toast.makeText(this, "El campo de  Repetir Contraseña es obligatorio", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        return isValid
    }

}