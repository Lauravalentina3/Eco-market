package com.EcoMarket.Fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.tallersegundomomento.R

class CarritoFragment : Fragment() {

    private val precioClinique = 50000
    private val precioMaybelline = 45000

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_carrito, container, false)

        val prefs = requireContext().getSharedPreferences("carrito", Context.MODE_PRIVATE)

        // Cantidades actuales
        var cantidadClinique = prefs.getInt("cantidad_clinique", 0)
        var cantidadMaybelline = prefs.getInt("cantidad_maybelline", 0)

        // Vistas
        val layoutClinique = view.findViewById<LinearLayout>(R.id.layout_clinique)
        val layoutMaybelline = view.findViewById<LinearLayout>(R.id.layout_maybelline)
        val tvClinique = view.findViewById<TextView>(R.id.tv_clinique_detalle)
        val tvMaybelline = view.findViewById<TextView>(R.id.tv_maybelline_detalle)
        val tvResumen = view.findViewById<TextView>(R.id.tv_resumen)
        val btnEliminar1 = view.findViewById<Button>(R.id.btn_eliminar_clinique)
        val btnEliminar2 = view.findViewById<Button>(R.id.btn_eliminar_maybelline)
        val btnPagar = view.findViewById<Button>(R.id.btn_pagar)

        // Función para actualizar resumen
        fun actualizarResumen() {
            val totalProductos = cantidadClinique + cantidadMaybelline
            val totalPrecio = (cantidadClinique * precioClinique) + (cantidadMaybelline * precioMaybelline)
            tvResumen.text = "Total de productos: $totalProductos  |  Total a pagar: $$totalPrecio"
        }

        // Mostrar detalles
        if (cantidadClinique > 0) {
            tvClinique.text = "Cantidad: $cantidadClinique | Precio: $$precioClinique | Subtotal: $${cantidadClinique * precioClinique}"
        } else {
            layoutClinique.visibility = View.GONE
        }

        if (cantidadMaybelline > 0) {
            tvMaybelline.text = "Cantidad: $cantidadMaybelline | Precio: $$precioMaybelline | Subtotal: $${cantidadMaybelline * precioMaybelline}"
        } else {
            layoutMaybelline.visibility = View.GONE
        }

        actualizarResumen()

        // Eliminar Clinique
        btnEliminar1.setOnClickListener {
            cantidadClinique = 0
            prefs.edit().putInt("cantidad_clinique", 0).apply()
            layoutClinique.visibility = View.GONE
            actualizarResumen()
            Toast.makeText(context, "Producto eliminado", Toast.LENGTH_SHORT).show()
        }

        // Eliminar Maybelline
        btnEliminar2.setOnClickListener {
            cantidadMaybelline = 0
            prefs.edit().putInt("cantidad_maybelline", 0).apply()
            layoutMaybelline.visibility = View.GONE
            actualizarResumen()
            Toast.makeText(context, "Producto eliminado,espero tomaras una buena decision..", Toast.LENGTH_SHORT).show()
        }

        // Pagar
        btnPagar.setOnClickListener {
            if (cantidadClinique + cantidadMaybelline > 0) {
                Toast.makeText(context, "Gracias por tu compra estrellita 💖", Toast.LENGTH_LONG).show()
                prefs.edit().clear().apply()
                layoutClinique.visibility = View.GONE
                layoutMaybelline.visibility = View.GONE
                cantidadClinique = 0
                cantidadMaybelline = 0
                actualizarResumen()
            } else {
                Toast.makeText(context, "Tu carrito está vacío 😢 estas segura?", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
}
