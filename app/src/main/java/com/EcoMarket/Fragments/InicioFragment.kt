package com.EcoMarket.Fragments

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

        // Configurar el temporizador de redirección al RolFragment
        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(R.id.action_inicioFragment_to_rolFragment)
        }, SPLASH_TIME_OUT)
    }
}