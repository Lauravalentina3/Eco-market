package com.EcoMarket.Fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.EcoMarket.R

class InicioFragment : Fragment() {

    private val SPLASH_TIME_OUT: Long = 2000;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el layout para este fragmento
        return inflater.inflate(R.layout.fragment_inicio, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("InicioFragment", "onViewCreated: Iniciando Fragment de inicio")

        // Configurar el temporizador de redirección a Home Activity
        Handler(Looper.getMainLooper()).postDelayed({
            // Navegar al HomeFragment (asumiendo que estás usando Navigation Component)
            findNavController().navigate(R.id.HomeFragment)
            // Si no usas Navigation Component, puedes iniciar la actividad así:
            // val intent = Intent(requireContext(), HomeActivity::class.java)
            // startActivity(intent)
        }, SPLASH_TIME_OUT)
    }
}