package com.EcoMarket.Fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.EcoMarket.R

class RecuperacionContrasenaFragment : Fragment() {

    private lateinit var ediTextCorreo: EditText
    private lateinit var buttonEnviar: Button
    private lateinit var textResetPassword: TextView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onAttach(context: Context) {
        super.onAttach(context)
        sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recuperacion_contrasena, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        // Inicializando variables generales
        ediTextCorreo = view.findViewById(R.id.editTextEmail)
        buttonEnviar = view.findViewById(R.id.buttonRecuperacion)
        textResetPassword = view.findViewById(R.id.textRegistrologin)  // Inicializado correctamente

        // Configurar el botón de envío
        buttonEnviar.setOnClickListener {
            if (validarCorreo()) {
                // Verificación del correo electrónico
                verificarCorreo()
            }
        }
        //Configurar textview para ir a login
        textResetPassword.setOnClickListener{
            findNavController().navigate(R.id.action_recuperacionContrasenaFragment_to_loginFragment)
        }
    }

    private fun validarCorreo(): Boolean {
        val correo = ediTextCorreo.text.toString().trim()
        if (correo.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            Toast.makeText(requireContext(), "Debes ingresar un correo válido para continuar", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun verificarCorreo() {
        val correo = ediTextCorreo.text.toString().trim()
        val correoRegistrado = sharedPreferences.getString("correo", "") // Usar la propiedad de la clase
        val array = sharedPreferences.all

        if (correo == correoRegistrado) {
            Log.d("Recuperar Contraseña", "verificarCorreo: Verificar el correo del usuario que esté correctamente")
            Toast.makeText(requireContext(), "Se le ha enviado un correo con su nueva contraseña de recuperación", Toast.LENGTH_SHORT).show()

            buttonEnviar.postDelayed({
                findNavController().navigate(R.id.action_recuperacionContrasenaFragment_to_loginFragment)
            }, 1500)
        } else {
            Toast.makeText(requireContext(), "El correo electrónico ingresado no existe en el sistema", Toast.LENGTH_SHORT).show()
            Log.d("Recuperar Contraseña", "verificarCorreo: Error correo: $correo,  correoRegistrado: $correoRegistrado, array: $array")
        }
    }
}