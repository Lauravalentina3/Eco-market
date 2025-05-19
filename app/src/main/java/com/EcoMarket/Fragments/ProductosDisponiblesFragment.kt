package com.EcoMarket.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import com.EcoMarket.R

class ProductosDisponiblesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_productos_disponibles, container, false)

        // Encuentra los botones "disponible"
        val botonDisponible1: Button = view.findViewById(R.id.btn_disponible1)
        val botonDisponible3: Button = view.findViewById(R.id.btn_disponible3)
        val botonDisponible2: Button = view.findViewById(R.id.btn_disponible2)
        // Encuentra cualquier otro botón "disponible" que tengas

        // Configura el OnClickListener para el botón 1
        botonDisponible1.setOnClickListener {
            view.findNavController().navigate(R.id.productosFragment)
        }

        // Configura el OnClickListener para el botón 3
        botonDisponible2.setOnClickListener {
            view.findNavController().navigate(R.id.productosFragment)
        }
// Configura el OnClickListener para el botón 3
        botonDisponible3.setOnClickListener {
            view.findNavController().navigate(R.id.productosFragment)
        }




        // Ejemplo para btn_agotado4:
        val botonAgotado4: Button = view.findViewById(R.id.btn_agotado4)
        botonAgotado4.setOnClickListener {
            view.findNavController().navigate(R.id.productosFragment)
        }

        return view
    }
}