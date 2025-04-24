package com.EcoMarket.Fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.EcoMarket.R
import java.text.NumberFormat
import java.util.Locale

class ProductosFragment : Fragment() {

    private lateinit var prefs: SharedPreferences
    // Declarar los botones y textviews de los productos como propiedades de la clase
    private lateinit var btnAddBolsaAnimales: Button
    private lateinit var btnAddBolsa5kg: Button
    private lateinit var btnAddBolsaGarfield: Button
    private lateinit var btnAddMandalas: Button
    private lateinit var btnEliminarBolsaAnimales: Button
    private lateinit var btnEliminarBolsa5kg: Button
    private lateinit var btnEliminarBolsaGarfield: Button
    private lateinit var btnEliminarBolsaMandalas: Button

    private lateinit var tvCantidadBolsaAnimales: TextView
    private lateinit var tvCantidadBolsa5kg: TextView
    private lateinit var tvCantidadBolsaGarfield: TextView
    private lateinit var tvCantidadBolsaMandalas: TextView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        prefs = context.getSharedPreferences("carrito", Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_productos, container, false)

        // Inicializar los botones y textviews en onCreateView
        btnAddBolsaAnimales = view.findViewById(R.id.btn_add_bolsa_animales)
        btnAddBolsa5kg = view.findViewById(R.id.btn_add_bolsa_5kg)
        btnAddBolsaGarfield = view.findViewById(R.id.btn_add_bolsa_garfield)
        btnAddMandalas = view.findViewById(R.id.btn_add_bolsa_mandala)

        btnEliminarBolsaAnimales = view.findViewById(R.id.btn_eliminar_bolsa_animales)
        btnEliminarBolsa5kg = view.findViewById(R.id.btn_eliminar_bolsa_5kg)
        btnEliminarBolsaGarfield = view.findViewById(R.id.btn_eliminar_bolsa_garfield)
        btnEliminarBolsaMandalas = view.findViewById(R.id.btn_eliminar_bolsa_mandala)

        tvCantidadBolsaAnimales = view.findViewById(R.id.tv_cantidad_bolsa_animales)
        tvCantidadBolsa5kg = view.findViewById(R.id.tv_cantidad_bolsa_5kg)
        tvCantidadBolsaGarfield = view.findViewById(R.id.tv_cantidad_bolsa_garfield)
        tvCantidadBolsaMandalas = view.findViewById(R.id.tv_cantidad_bolsa_mandala)

        // Configurar listeners para los botones
        configurarListeners()
        actualizarCantidadesEnUI()

        return view
    }



    private fun configurarListeners() {
        btnAddBolsaAnimales.setOnClickListener { agregarEliminarProductoAlCarrito("cantidad_bolsa_animales",  "Bolsa ecológica de animales",tvCantidadBolsaAnimales, true) }
        btnAddBolsa5kg.setOnClickListener { agregarEliminarProductoAlCarrito("cantidad_bolsa_5kg",  "Bolsa ecológica de 5kg",tvCantidadBolsa5kg, true) }
        btnAddBolsaGarfield.setOnClickListener { agregarEliminarProductoAlCarrito("cantidad_bolsa_garfield",  "Bolsa de Garfield",tvCantidadBolsaGarfield, true) }
        btnAddMandalas.setOnClickListener { agregarEliminarProductoAlCarrito("cantidad_bolsa_mandala", "Bolsa de Mandalas",tvCantidadBolsaMandalas, true) }

        btnEliminarBolsaAnimales.setOnClickListener { agregarEliminarProductoAlCarrito("cantidad_bolsa_animales", "Bolsa ecológica de animales", tvCantidadBolsaAnimales, false) }
        btnEliminarBolsa5kg.setOnClickListener { agregarEliminarProductoAlCarrito("cantidad_bolsa_5kg", "Bolsa ecológica de 5kg", tvCantidadBolsa5kg, false) }
        btnEliminarBolsaGarfield.setOnClickListener { agregarEliminarProductoAlCarrito("cantidad_bolsa_garfield", "Bolsa de Garfield", tvCantidadBolsaGarfield, false) }
        btnEliminarBolsaMandalas.setOnClickListener { agregarEliminarProductoAlCarrito("cantidad_bolsa_mandala", "Bolsa de Mandalas", tvCantidadBolsaMandalas, false) }
    }

    private fun agregarEliminarProductoAlCarrito(cantidadKey: String, nombreProducto: String, textViewCantidad: TextView, agregar: Boolean) {
        var cantidad = prefs.getInt(cantidadKey, 0)
        if (agregar) {
            cantidad++
        } else {
            if (cantidad > 0) {
                cantidad--
            }
        }
        prefs.edit().putInt(cantidadKey, cantidad).apply()

        val mensaje = if (agregar) "$nombreProducto añadido al carrito" else "$nombreProducto eliminado del carrito"
        Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
        actualizarCantidadesEnUI()
        actualizarCantidadProductoEnUI(cantidadKey, textViewCantidad)


    }

    private fun actualizarCantidadesEnUI() {
        val cantidadBolsaAnimales = prefs.getInt("cantidad_bolsa_animales", 0)
        val cantidadBolsa5kg = prefs.getInt("cantidad_bolsa_5kg", 0)
        val cantidadBolsaGarfield = prefs.getInt("cantidad_bolsa_garfield", 0)
        val cantidadBolsaMandalas = prefs.getInt("cantidad_bolsa_mandala", 0)

        tvCantidadBolsaAnimales.text = cantidadBolsaAnimales.toString()
        tvCantidadBolsa5kg.text = cantidadBolsa5kg.toString()
        tvCantidadBolsaGarfield.text = cantidadBolsaGarfield.toString()
        tvCantidadBolsaMandalas.text = cantidadBolsaMandalas.toString()
    }
    private fun actualizarCantidadProductoEnUI(cantidadKey: String, textView: TextView){
        val cantidad = prefs.getInt(cantidadKey,0)
        textView.text = cantidad.toString()
    }
}
