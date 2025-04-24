package com.EcoMarket.Fragments // ¡Importante! Cambiar al paquete de Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.EcoMarket.R

class LoginFragment : Fragment() {
    private lateinit var textViewRegistrar: TextView
    private lateinit var textViewrecuperarContraseña: TextView
    private lateinit var buttonLogin: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewRegistrar = view.findViewById(R.id.textRegistrologin)
        textViewrecuperarContraseña = view.findViewById(R.id.textResetPassword)
        buttonLogin = view.findViewById(R.id.buttonLogin)

        textViewRegistrar.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registroFragment)
        }

        textViewrecuperarContraseña.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_recuperacionContrasenaFragment)
        }

        buttonLogin.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_homefragment)
        }
        }
    }
