package com.EcoMarket.Fragments

import android.os.Bundle
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

class LoginFragment : Fragment() {

    private lateinit var textViewRegistrar: TextView
    private lateinit var textViewRecuperarContraseña: TextView
    private lateinit var buttonLogin: Button
    private lateinit var editTextCorreo: EditText
    private lateinit var editTextContrasena: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Solo nos interesa capturar los EditText de datos
        textViewRegistrar = view.findViewById(R.id.textRegistrologin)
        textViewRecuperarContraseña = view.findViewById(R.id.textResetPassword)
        buttonLogin = view.findViewById(R.id.buttonLogin)
        editTextCorreo = view.findViewById(R.id.editTextCorreo)
        editTextContrasena = view.findViewById(R.id.contraseña_usuario_login)

        textViewRegistrar.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registroFragment)
        }

        textViewRecuperarContraseña.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_recuperacionContrasenaFragment)
        }

        buttonLogin.setOnClickListener {
            val correo = editTextCorreo.text.toString().trim()
            val contrasena = editTextContrasena.text.toString().trim()

            if (correo.isEmpty()) {
                Toast.makeText(requireContext(), "Por favor ingresa tu correo electrónico", Toast.LENGTH_SHORT).show()
            } else if (contrasena.isEmpty()) {
                Toast.makeText(requireContext(), "Por favor ingresa tu contraseña", Toast.LENGTH_SHORT).show()
            } else {
                // Ya que no están vacíos, ahora sí verificamos
                if (correo == "pepitoperez@gmail.com" && contrasena == "pepito2025") {
                    findNavController().navigate(R.id.action_loginFragment_to_homefragment)
                } else {
                    Toast.makeText(requireContext(), "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}
