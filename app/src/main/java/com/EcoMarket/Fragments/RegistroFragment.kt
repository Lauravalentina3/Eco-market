package com.EcoMarket.Fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
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
import android.util.Patterns

class RegistroFragment : Fragment() {
    private lateinit var editTextNombres: EditText
    private lateinit var editTextApellidos: EditText
    private lateinit var editTextCorreo: EditText
    private lateinit var buttonRegistro: Button
    private lateinit var editTextContrasena: EditText
    private lateinit var editTextRepetirContrasena: EditText
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var textRegistrologin: TextView
    private lateinit var textOlvidarContrasena: TextView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el layout para este fragmento
        return inflater.inflate(R.layout.fragment_registro, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("RegistroFragment", "onViewCreated: Registro Fragment Iniciando")


        // Inicializar variables de vista
        editTextNombres = view.findViewById(R.id.editTextNombres)
        if (editTextNombres == null) {
            Log.e("RegistroFragment", "Error: editTextNombres es nulo")
        }
        editTextApellidos = view.findViewById(R.id.editTextApellidos)
        if (editTextApellidos == null) {
            Log.e("RegistroFragment", "Error: editTextApellidos es nulo")
        }
        editTextCorreo = view.findViewById(R.id.editTextCorreo)
        if (editTextCorreo == null) {
            Log.e("RegistroFragment", "Error: editTextCorreo es nulo")
        }
        editTextContrasena = view.findViewById(R.id.editTextContrasena)
        if (editTextContrasena == null) {
            Log.e("RegistroFragment", "Error: editTextContrasena es nulo")
        }
        editTextRepetirContrasena = view.findViewById(R.id.editTextRepetirContrasena)
        if (editTextRepetirContrasena == null) {
            Log.e("RegistroFragment", "Error: editTextRepetirContrasena es nulo")
        }
        buttonRegistro = view.findViewById(R.id.buttonRegistro)
        if (buttonRegistro == null) {
            Log.e("RegistroFragment", "Error: editTexteRegistro nulo")
        }
        textRegistrologin = view.findViewById(R.id.textRegistrologin)
        if (textRegistrologin == null) {
            Log.e("RegistroFragment", "Error: editTextRegistroLogin nulo")
        }
        textOlvidarContrasena = view.findViewById(R.id.buttonRegresar)
        if (textOlvidarContrasena == null) {
            Log.e("RegistroFragment", "Error: editTexteOlvideContraseña nulo")
        }

        // Configuración listener botón de registro
        buttonRegistro.setOnClickListener {
            if (validarCampos()) {
                // método de guardar datos de usuario
                guardarDatosUsuario()
                // redireccionamiento a LoginFragment
                findNavController().navigate(R.id.action_registroFragment_to_loginFragment)
            }
        }

        // Configurar listener para "Iniciar Sesión"
        textRegistrologin.setOnClickListener {
            Log.d("RegistroFragment", "Clic en Iniciar Sesión")
            findNavController().navigate(R.id.action_registroFragment_to_loginFragment)
            Log.d("RegistroFragment", "Navegación a LoginFragment completada")
        }


        // Configurar listener para "Olvidé mi Contraseña"
        textOlvidarContrasena.setOnClickListener {

            findNavController().navigate(R.id.action_registroFragment_to_recuperacionContrasenaFragment)
        }
    }

    private fun guardarDatosUsuario() {
        val editor = sharedPreferences.edit()
        editor.putString("nombres", editTextNombres.text.toString().trim())
        editor.putString("apellidos", editTextApellidos.text.toString().trim())
        editor.putString("correo", editTextCorreo.text.toString().trim())
        editor.putString("Contraseña", editTextContrasena.text.toString().trim())
        editor.putString("RepetirContraseña", editTextRepetirContrasena.text.toString().trim())
        editor.putString("id1", "hola")

        val correoRegistrado = sharedPreferences.getString("correo", "")
        val id1 = sharedPreferences.getString("id1", "")

        editor.apply()
        Log.d("Registro Fragment", "guardarDatosUsuario: Datos del usuario guardados")
        Toast.makeText(requireContext(), "Registro exitoso", Toast.LENGTH_SHORT).show()
        Log.d("Recuperar Contraseña", "verificarCorreo: Error correo: $correoRegistrado, id: $id1")
    }

    private fun validarCampos(): Boolean {
        val nombres = editTextNombres.text.toString().trim()
        val apellidos = editTextApellidos.text.toString().trim()
        val correo = editTextCorreo.text.toString().trim()
        val contraseña = editTextContrasena.text.toString().trim()
        val repetirContraseña = editTextRepetirContrasena.text.toString().trim()

        var isValid = true

        if (nombres.isEmpty()) {
            Toast.makeText(requireContext(), "El campo de Nombres es obligatorio", Toast.LENGTH_SHORT)
                .show()
            isValid = false
        }
        if (apellidos.isEmpty()) {
            Toast.makeText(requireContext(), "El campo de Apellidos es obligatorio", Toast.LENGTH_SHORT)
                .show()
            isValid = false
        }
        if (correo.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(correo).matches()) { // Validar correo
            Toast.makeText(requireContext(), "Ingrese un correo electrónico válido", Toast.LENGTH_SHORT).show()
            isValid = false
        }
        if (contraseña.isEmpty()) {
            Toast.makeText(requireContext(), "El campo de Contraseña es obligatorio", Toast.LENGTH_SHORT)
                .show()
            isValid = false
        }
        if (repetirContraseña.isEmpty()) {
            Toast.makeText(
                requireContext(),
                "El campo de Repetir Contraseña es obligatorio",
                Toast.LENGTH_SHORT
            ).show()
            isValid = false
        }
        if (contraseña != repetirContraseña) {
            Toast.makeText(requireContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        return isValid
    }
}