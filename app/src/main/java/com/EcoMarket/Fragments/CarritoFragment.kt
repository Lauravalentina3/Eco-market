package com.EcoMarket.Fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.EcoMarket.R
import java.text.NumberFormat
import java.util.Locale

class CarritoFragment : Fragment() {

    // Constantes para los precios de los productos.
    private  val PRECIO_BOLSA_ANIMALES = 10600
    private  val PRECIO_BOLSA_5KG = 20400
    private  val PRECIO_BOLSA_GARFIELD = 20400
    private  val PRECIO_BOLSA_MANDALAS = 20400

    // SharedPreferences para guardar el estado del carrito
    private lateinit var prefs: android.content.SharedPreferences

    // Vistas del fragmento.  Declararlas como variables de clase permite acceder a ellas desde varios métodos.
    private lateinit var layoutBolsaAnimales: LinearLayout
    private lateinit var layoutBolsa5kg: LinearLayout
    private lateinit var layoutBolsaGarfield: LinearLayout
    private lateinit var layoutBolsaMandalas: LinearLayout
    private lateinit var tvBolsaAnimalesDetalle: TextView
    private lateinit var tvBolsa5kgDetalle: TextView
    private lateinit var tvBolsaGarfieldDetalle: TextView
    private lateinit var tvBolsaMandalasDetalle: TextView
    private lateinit var tvResumen: TextView
    private lateinit var btnEliminarBolsaAnimales: Button
    private lateinit var btnEliminarBolsa5kg: Button
    private lateinit var btnEliminarBolsaGarfield: Button
    private lateinit var btnEliminarBolsaMandalas: Button
    private lateinit var btnPagar: Button

    // Cantidades de productos.  Se inicializan en onCreateView.
    private var cantidadBolsaAnimales: Int = 0
    private var cantidadBolsa5kg: Int = 0
    private var cantidadBolsaGarfield: Int = 0
    private var cantidadBolsaMandalas: Int = 0
    private val localeES = Locale("es", "ES")
    private val formatoMonedaES = NumberFormat.getCurrencyInstance(localeES)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Inicializar SharedPreferences en onAttach, antes de onCreateView
        prefs = context.getSharedPreferences("carrito", Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? { // El tipo de retorno debe ser View?
        // Inflar el layout del fragmento
        val view = inflater.inflate(R.layout.fragment_carrito, container, false)

        // Inicializar las vistas usando findViewById
        inicializarVistas(view)
        // Obtener las cantidades del carrito desde SharedPreferences
        obtenerCantidades()
        // Configurar listeners de los botones
        configurarListeners()
        // Mostrar los detalles del carrito
        mostrarDetallesCarrito()
        // Actualizar el resumen del carrito
        actualizarResumen()

        return view // Devolver la vista inflada
    }

    private fun inicializarVistas(view: View) {
        layoutBolsaAnimales = view.findViewById(R.id.layout_bolsa_animales)
        layoutBolsa5kg = view.findViewById(R.id.layout_bolsa_5kg)
        layoutBolsaGarfield = view.findViewById(R.id.layout_bolsa_garfield)
        layoutBolsaMandalas = view.findViewById(R.id.layout_bolsa_mandalas)

        tvBolsaAnimalesDetalle = view.findViewById(R.id.tv_bolsa_animales_detalle)
        tvBolsa5kgDetalle = view.findViewById(R.id.tv_bolsa_5kg_detalle)
        tvBolsaGarfieldDetalle = view.findViewById(R.id.tv_bolsa_garfield_detalle)
        tvBolsaMandalasDetalle = view.findViewById(R.id.tv_bolsa_mandalas_detalle)

        tvResumen = view.findViewById(R.id.tv_resumen)
        btnEliminarBolsaAnimales = view.findViewById(R.id.btn_eliminar_bolsa_animales)
        btnEliminarBolsa5kg = view.findViewById(R.id.btn_eliminar_bolsa_5kg)
        btnEliminarBolsaGarfield = view.findViewById(R.id.btn_eliminar_bolsa_garfield)
        btnEliminarBolsaMandalas = view.findViewById(R.id.btn_eliminar_bolsa_mandalas)
        btnPagar = view.findViewById(R.id.btn_pagar)
    }

    private fun obtenerCantidades() {
        cantidadBolsaAnimales = prefs.getInt("cantidad_bolsa_animales", 0)
        cantidadBolsa5kg = prefs.getInt("cantidad_bolsa_5kg", 0)
        cantidadBolsaGarfield = prefs.getInt("cantidad_bolsa_garfield", 0)
        cantidadBolsaMandalas = prefs.getInt("cantidad_bolsa_mandalas", 0)
    }

    private fun configurarListeners() {
        btnEliminarBolsaAnimales.setOnClickListener {
            cantidadBolsaAnimales = 0
            prefs.edit().putInt("cantidad_bolsa_animales", 0).apply()
            layoutBolsaAnimales.visibility = View.GONE
            actualizarResumen()
            mostrarDetallesCarrito()
            Toast.makeText(context, "Producto eliminado", Toast.LENGTH_SHORT).show()
        }

        btnEliminarBolsa5kg.setOnClickListener {
            cantidadBolsa5kg = 0
            prefs.edit().putInt("cantidad_bolsa_5kg", 0).apply()
            layoutBolsa5kg.visibility = View.GONE
            actualizarResumen()
            mostrarDetallesCarrito()
            Toast.makeText(context, "Producto eliminado", Toast.LENGTH_SHORT).show()
        }

        btnEliminarBolsaGarfield.setOnClickListener {
            cantidadBolsaGarfield = 0
            prefs.edit().putInt("cantidad_bolsa_garfield", 0).apply()
            layoutBolsaGarfield.visibility = View.GONE
            actualizarResumen()
            mostrarDetallesCarrito()
            Toast.makeText(context, "Producto eliminado", Toast.LENGTH_SHORT).show()
        }

        btnEliminarBolsaMandalas.setOnClickListener {
            cantidadBolsaMandalas = 0
            prefs.edit().putInt("cantidad_bolsa_mandalas", 0).apply()
            layoutBolsaMandalas.visibility = View.GONE
            actualizarResumen()
            mostrarDetallesCarrito()
            Toast.makeText(context, "Producto eliminado", Toast.LENGTH_SHORT).show()
        }

        btnPagar.setOnClickListener {
            val totalProductos = cantidadBolsaAnimales + cantidadBolsa5kg + cantidadBolsaGarfield + cantidadBolsaMandalas
            if (totalProductos > 0) {
                Toast.makeText(context, "Gracias por tu compra estrellita 💖", Toast.LENGTH_LONG).show()
                prefs.edit().clear().apply() // Clear the cart.
                resetCantidades()
                mostrarDetallesCarrito()
                actualizarResumen()
            } else {
                Toast.makeText(context, "Tu carrito está vacío 😢 estas segura?", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun mostrarDetallesCarrito() {

        val precioBolsaAnimalesFormateado = formatoMonedaES.format(PRECIO_BOLSA_ANIMALES)
        val precioBolsa5kgFormateado = formatoMonedaES.format(PRECIO_BOLSA_5KG)
        val precioBolsaGarfieldFormateado = formatoMonedaES.format(PRECIO_BOLSA_GARFIELD)
        val precioBolsaMandalasFormateado = formatoMonedaES.format(PRECIO_BOLSA_MANDALAS)

        // Mostrar detalles de los productos.  La lógica es la misma, solo cambian los nombres de las variables.
        if (cantidadBolsaAnimales > 0) {
            tvBolsaAnimalesDetalle.text =
                "Cantidad: $cantidadBolsaAnimales | Precio: ${precioBolsaAnimalesFormateado} | Subtotal: ${formatoMonedaES.format(cantidadBolsaAnimales * PRECIO_BOLSA_ANIMALES)}"
            layoutBolsaAnimales.visibility = View.VISIBLE
        } else {
            layoutBolsaAnimales.visibility = View.GONE
        }

        if (cantidadBolsa5kg > 0) {
            tvBolsa5kgDetalle.text =
                "Cantidad: $cantidadBolsa5kg | Precio: ${precioBolsa5kgFormateado} | Subtotal: ${formatoMonedaES.format(cantidadBolsa5kg * PRECIO_BOLSA_5KG)}"
            layoutBolsa5kg.visibility = View.VISIBLE
        } else {
            layoutBolsa5kg.visibility = View.GONE
        }

        if (cantidadBolsaGarfield > 0) {
            tvBolsaGarfieldDetalle.text =
                "Cantidad: $cantidadBolsaGarfield | Precio: ${precioBolsaGarfieldFormateado} | Subtotal: ${formatoMonedaES.format(cantidadBolsaGarfield * PRECIO_BOLSA_GARFIELD)}"
            layoutBolsaGarfield.visibility = View.VISIBLE
        } else {
            layoutBolsaGarfield.visibility = View.GONE
        }

        if (cantidadBolsaMandalas > 0) {
            tvBolsaMandalasDetalle.text =
                "Cantidad: $cantidadBolsaMandalas | Precio: ${precioBolsaMandalasFormateado} | Subtotal: ${formatoMonedaES.format(cantidadBolsaMandalas * PRECIO_BOLSA_MANDALAS)}"
            layoutBolsaMandalas.visibility = View.VISIBLE
        } else {
            layoutBolsaMandalas.visibility = View.GONE
        }
    }

    private fun actualizarResumen() {
        val totalProductos = cantidadBolsaAnimales + cantidadBolsa5kg + cantidadBolsaGarfield + cantidadBolsaMandalas
        val totalPrecio =
            (cantidadBolsaAnimales * PRECIO_BOLSA_ANIMALES) + (cantidadBolsa5kg * PRECIO_BOLSA_5KG) +
                    (cantidadBolsaGarfield * PRECIO_BOLSA_GARFIELD) + (cantidadBolsaMandalas * PRECIO_BOLSA_MANDALAS)
        tvResumen.text = "Total de productos: $totalProductos  |  Total a pagar: ${formatoMonedaES.format(totalPrecio)}"
    }

    private fun resetCantidades() {
        cantidadBolsaAnimales = 0
        cantidadBolsa5kg = 0
        cantidadBolsaGarfield = 0
        cantidadBolsaMandalas = 0
    }
}

