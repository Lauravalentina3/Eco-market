package com.EcoMarket.Fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.tallersegundomomento.R
import android.os.Handler
import android.os.Looper
import androidx.navigation.fragment.findNavController


class ProductosFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_productos, container, false)

        // Acceder a los botones desde el layout
        val btnAdd1 = view.findViewById<Button>(R.id.btn_add1)
        val btnAdd2 = view.findViewById<Button>(R.id.btn_add2)

        // SharedPreferences para almacenar el total de productos añadidos
        val prefs = requireContext().getSharedPreferences("carrito", Context.MODE_PRIVATE)

        btnAdd1?.setOnClickListener {
            val prefs = requireContext().getSharedPreferences("carrito", Context.MODE_PRIVATE)
            val cantidad = prefs.getInt("cantidad_clinique", 0) + 1
            prefs.edit().putInt("cantidad_clinique", cantidad).apply()

            Toast.makeText(context, "Producto añadido al carrito", Toast.LENGTH_SHORT).show()

            // Esperar 2 segundos y navegar al fragmento del carrito
            Handler(Looper.getMainLooper()).postDelayed({
                findNavController().navigate(R.id.carritoFragment)
            }, 2000)
        }


        btnAdd2?.setOnClickListener {
            val prefs = requireContext().getSharedPreferences("carrito", Context.MODE_PRIVATE)
            val cantidad = prefs.getInt("cantidad_maybelline", 0) + 1
            prefs.edit().putInt("cantidad_maybelline", cantidad).apply()

            Toast.makeText(context, "Producto añadido al carrito", Toast.LENGTH_SHORT).show()

            Handler(Looper.getMainLooper()).postDelayed({
                findNavController().navigate(R.id.carritoFragment)
            }, 2000)
        }


        return view
    }
}
