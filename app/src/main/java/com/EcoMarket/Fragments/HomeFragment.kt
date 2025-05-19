package com.EcoMarket.Fragments

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.EcoMarket.R

class HomeFragment : Fragment() {

    private lateinit var textViewHoras: TextView
    private lateinit var textViewMinutos: TextView
    private lateinit var textViewSegundos: TextView
    private lateinit var textodireccion: TextView

    private val handler = Handler(Looper.getMainLooper())
    private var tiempoRestante = 2 * 60 * 60 * 1000L

    private val runnable: Runnable = object : Runnable {
        override fun run() {
            if (tiempoRestante > 0) {
                val horas = (tiempoRestante / (1000 * 60 * 60)).toInt()
                val minutos = ((tiempoRestante / (1000 * 60)) % 60).toInt()
                val segundos = ((tiempoRestante / 1000) % 60).toInt()

                textViewHoras.text = String.format("%02d", horas)
                textViewMinutos.text = String.format("%02d", minutos)
                textViewSegundos.text = String.format("%02d", segundos)

                tiempoRestante -= 1000
                handler.postDelayed(this, 1000)
            } else {
                textViewHoras.text = "00"
                textViewMinutos.text = "00"
                textViewSegundos.text = "00"

                textViewHoras.setTextColor(Color.RED)
                textViewMinutos.setTextColor(Color.RED)
                textViewSegundos.setTextColor(Color.RED)

                Toast.makeText(requireContext(), "¡Tiempo finalizado!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewHoras = view.findViewById(R.id.textViewHoras)
        textViewMinutos = view.findViewById(R.id.textViewMinutos)
        textViewSegundos = view.findViewById(R.id.textViewSegundos)
        textodireccion = view.findViewById(R.id.textodireccion)

        handler.post(runnable)

        textodireccion.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.ubicacionFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(runnable)
    }
}
