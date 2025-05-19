package com.EcoMarket.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.EcoMarket.R

class RolFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_roles, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val administradorBoton: Button = view.findViewById(R.id.administrador_boton)
        val usuarioGeneralBoton: Button = view.findViewById(R.id.usuriogeneral_boton)

        administradorBoton.setOnClickListener {
            findNavController().navigate(R.id.action_rolFragment_to_homeFragment)
        }

        usuarioGeneralBoton.setOnClickListener {
            findNavController().navigate(R.id.action_rolFragment_to_loginFragment)
        }
    }
}