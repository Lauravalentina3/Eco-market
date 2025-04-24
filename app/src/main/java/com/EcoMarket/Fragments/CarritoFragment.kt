package com.EcoMarket.Fragments

import android.content.Context
import android.content.SharedPreferences
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
    private val PRECIO_BOLSA_ANIMALES = 10600
    private val PRECIO_BOLSA_5KG = 20400
    private val PRECIO_BOLSA_GARFIELD = 20400
    private val PRECIO_BOLSA_MANDALAS = 20400

    // Claves para SharedPreferences
    private val CLAVE_CANTIDAD_ANIMALES = "cantidad_bolsa_animales"
    private val CLAVE_CANTIDAD_5KG = "cantidad_bolsa_5kg"
    private val CLAVE_CANTIDAD_GARFIELD = "cantidad_bolsa_garfield"
    private val CLAVE_CANTIDAD_MANDALAS = "cantidad_bolsa_mandalas"

    // SharedPreferences para guardar el estado del carrito
    private var prefs: SharedPreferences? = null

    // Vistas del fragmento.  Declararlas como variables de clase permite acceder a ellas desde varios métodos.
    private var layoutBolsaAnimales: LinearLayout? = null
    private var layoutBolsa5kg: LinearLayout? = null
    private var layoutBolsaGarfield: LinearLayout? = null
    private var layoutBolsaMandalas: LinearLayout? = null
    private var tvBolsaAnimalesDetalle: TextView? = null
    private var tvBolsa5kgDetalle: TextView? = null
    private var tvBolsaGarfieldDetalle: TextView? = null
    private var tvBolsaMandalasDetalle: TextView? = null
    private var tvResumen: TextView? = null
    private var btnEliminarBolsaAnimales: Button? = null
    private var btnEliminarBolsa5kg: Button? = null
    private var btnEliminarBolsaGarfield: Button? = null
    private var btnEliminarBolsaMandalas: Button? = null
    private var btnPagar: Button? = null
    private var tvCantidadBolsaAnimales: TextView? = null
    private var tvCantidadBolsa5kg: TextView? = null
    private var tvCantidadBolsaGarfield: TextView? = null
    private var tvCantidadBolsaMandalas: TextView? = null

    // Cantidades de productos.  Se inicializan en onCreate.
    private var cantidadBolsaAnimales = 0
    private var cantidadBolsa5kg = 0
    private var cantidadBolsaGarfield = 0
    private var cantidadBolsaMandalas = 0
    private val localeCO = Locale("es", "CO")
    private val formatoMonedaCO: NumberFormat = NumberFormat.getCurrencyInstance(localeCO)
    private var context: Context? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context
        // Inicializar SharedPreferences en onAttach, antes de onCreateView
        prefs = context.getSharedPreferences("carrito", Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

        tvCantidadBolsaAnimales = view.findViewById(R.id.tv_cantidad_bolsa_animales)
        tvCantidadBolsa5kg = view.findViewById(R.id.tv_cantidad_bolsa_5kg)
        tvCantidadBolsaGarfield = view.findViewById(R.id.tv_cantidad_bolsa_garfield)
        tvCantidadBolsaMandalas = view.findViewById(R.id.tv_cantidad_bolsa_mandala)
    }

    private fun obtenerCantidades() {
        cantidadBolsaAnimales = prefs!!.getInt(CLAVE_CANTIDAD_ANIMALES, 0)
        cantidadBolsa5kg = prefs!!.getInt(CLAVE_CANTIDAD_5KG, 0)
        cantidadBolsaGarfield = prefs!!.getInt(CLAVE_CANTIDAD_GARFIELD, 0)
        cantidadBolsaMandalas = prefs!!.getInt(CLAVE_CANTIDAD_MANDALAS, 0)
    }

    private fun guardarCantidad(clave: String, cantidad: Int) {
        prefs!!.edit().putInt(clave, cantidad).apply()
    }

    private fun configurarListeners() {
        btnEliminarBolsaAnimales!!.setOnClickListener {
            eliminarProducto(CLAVE_CANTIDAD_ANIMALES, 0)
            cantidadBolsaAnimales = 0
            layoutBolsaAnimales!!.visibility = View.GONE
            actualizarResumen()
            mostrarDetallesCarrito()
            Toast.makeText(context, "Producto eliminado", Toast.LENGTH_SHORT).show()
        }

        btnEliminarBolsa5kg!!.setOnClickListener {
            eliminarProducto(CLAVE_CANTIDAD_5KG, 0)
            cantidadBolsa5kg = 0
            layoutBolsa5kg!!.visibility = View.GONE
            actualizarResumen()
            mostrarDetallesCarrito()
            Toast.makeText(context, "Producto eliminado", Toast.LENGTH_SHORT).show()
        }

        btnEliminarBolsaGarfield!!.setOnClickListener {
            eliminarProducto(CLAVE_CANTIDAD_GARFIELD, 0)
            cantidadBolsaGarfield = 0
            layoutBolsaGarfield!!.visibility = View.GONE
            actualizarResumen()
            mostrarDetallesCarrito()
            Toast.makeText(context, "Producto eliminado", Toast.LENGTH_SHORT).show()
        }

        btnEliminarBolsaMandalas!!.setOnClickListener {
            eliminarProducto(CLAVE_CANTIDAD_MANDALAS, 0)
            cantidadBolsaMandalas = 0
            layoutBolsaMandalas!!.visibility = View.GONE
            actualizarResumen()
            mostrarDetallesCarrito()
            Toast.makeText(context, "Producto eliminado", Toast.LENGTH_SHORT).show()
        }

        btnPagar!!.setOnClickListener {
            val totalProductos =
                cantidadBolsaAnimales + cantidadBolsa5kg + cantidadBolsaGarfield + cantidadBolsaMandalas
            if (totalProductos > 0) {
                Toast.makeText(
                    context,
                    "Gracias por tu compra con esto ayudas al planeta 💖",
                    Toast.LENGTH_LONG
                ).show()
                prefs!!.edit().clear().apply() // Clear the cart.
                resetCantidades()
                mostrarDetallesCarrito()
                actualizarResumen()
            } else {
                Toast.makeText(
                    context,
                    "Tu carrito está vacío 😢 ¿no quieres ayudar al medio ambiente? ",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun eliminarProducto(clave: String, cantidad: Int) {
        prefs!!.edit().putInt(clave, cantidad).apply()
        obtenerCantidades() // Actualizar las cantidades después de eliminar
    }

    private fun mostrarDetallesCarrito() {
        // Formatear los precios usando el formato de moneda de Colombia
        val precioBolsaAnimalesFormateado = formatoMonedaCO.format(PRECIO_BOLSA_ANIMALES.toLong())
        val precioBolsa5kgFormateado = formatoMonedaCO.format(PRECIO_BOLSA_5KG.toLong())
        val precioBolsaGarfieldFormateado = formatoMonedaCO.format(PRECIO_BOLSA_GARFIELD.toLong())
        val precioBolsaMandalasFormateado = formatoMonedaCO.format(PRECIO_BOLSA_MANDALAS.toLong())

        // Mostrar detalles de los productos
        if (cantidadBolsaAnimales > 0) {
            tvBolsaAnimalesDetalle!!.text =
                "Cantidad: " + cantidadBolsaAnimales + " | Precio: " + precioBolsaAnimalesFormateado + " | Subtotal: " + formatoMonedaCO.format(
                    (cantidadBolsaAnimales * PRECIO_BOLSA_ANIMALES).toLong()
                )
            layoutBolsaAnimales!!.visibility = View.VISIBLE
            tvCantidadBolsaAnimales!!.text =
                "Cantidad: $cantidadBolsaAnimales" // Actualizar la cantidad mostrada
        } else {
            layoutBolsaAnimales!!.visibility = View.GONE
            tvCantidadBolsaAnimales!!.text = ""
        }

        if (cantidadBolsa5kg > 0) {
            tvBolsa5kgDetalle!!.text =
                "Cantidad: " + cantidadBolsa5kg + " | Precio: " + precioBolsa5kgFormateado + " | Subtotal: " + formatoMonedaCO.format(
                    (cantidadBolsa5kg * PRECIO_BOLSA_5KG).toLong()
                )
            layoutBolsa5kg!!.visibility = View.VISIBLE
            tvCantidadBolsa5kg!!.text = "Cantidad: $cantidadBolsa5kg"
        } else {
            layoutBolsa5kg!!.visibility = View.GONE
            tvCantidadBolsa5kg!!.text = ""
        }

        if (cantidadBolsaGarfield > 0) {
            tvBolsaGarfieldDetalle!!.text =
                "Cantidad: " + cantidadBolsaGarfield + " | Precio: " + precioBolsaGarfieldFormateado + " | Subtotal: " + formatoMonedaCO.format(
                    (cantidadBolsaGarfield * PRECIO_BOLSA_GARFIELD).toLong()
                )
            layoutBolsaGarfield!!.visibility = View.VISIBLE
            tvCantidadBolsaGarfield!!.text = "Cantidad: $cantidadBolsaGarfield"
        } else {
            layoutBolsaGarfield!!.visibility = View.GONE
            tvCantidadBolsaGarfield!!.text = ""
        }

        if (cantidadBolsaMandalas > 0) {
            tvBolsaMandalasDetalle!!.text =
                "Cantidad: " + cantidadBolsaMandalas + " | Precio: " + precioBolsaMandalasFormateado + " | Subtotal: " + formatoMonedaCO.format(
                    (cantidadBolsaMandalas * PRECIO_BOLSA_MANDALAS).toLong()
                )
            layoutBolsaMandalas!!.visibility = View.VISIBLE
            tvCantidadBolsaMandalas!!.text = "Cantidad: $cantidadBolsaMandalas"
        } else {
            layoutBolsaMandalas!!.visibility = View.GONE
            tvCantidadBolsaMandalas!!.text = ""
        }
    }

    private fun actualizarResumen() {
        val totalProductos =
            cantidadBolsaAnimales + cantidadBolsa5kg + cantidadBolsaGarfield + cantidadBolsaMandalas
        val totalPrecio =
            (cantidadBolsaAnimales * PRECIO_BOLSA_ANIMALES) + (cantidadBolsa5kg * PRECIO_BOLSA_5KG) +
                    (cantidadBolsaGarfield * PRECIO_BOLSA_GARFIELD) + (cantidadBolsaMandalas * PRECIO_BOLSA_MANDALAS)
        tvResumen!!.text =
            "Total de productos: " + totalProductos + "  |  Total a pagar: " + formatoMonedaCO.format(
                totalPrecio.toLong()
            )
    }

    private fun resetCantidades() {
        cantidadBolsaAnimales = 0
        cantidadBolsa5kg = 0
        cantidadBolsaGarfield = 0
        cantidadBolsaMandalas = 0
        guardarCantidad(CLAVE_CANTIDAD_ANIMALES, 0)
        guardarCantidad(CLAVE_CANTIDAD_5KG, 0)
        guardarCantidad(CLAVE_CANTIDAD_GARFIELD, 0)
        guardarCantidad(CLAVE_CANTIDAD_MANDALAS, 0)
    }

    // Métodos para agregar productos desde fuera del fragmento.  Estos métodos SÍ actualizan las cantidades y la UI.
    fun agregarBolsaAnimales(cantidad: Int) {
        cantidadBolsaAnimales += cantidad
        guardarCantidad(CLAVE_CANTIDAD_ANIMALES, cantidadBolsaAnimales)
        mostrarDetallesCarrito() // Actualizar la UI
        actualizarResumen()
        Toast.makeText(context, "Bolsa de Animales agregada al carrito", Toast.LENGTH_SHORT).show()
    }

    fun agregarBolsa5kg(cantidad: Int) {
        cantidadBolsa5kg += cantidad
        guardarCantidad(CLAVE_CANTIDAD_5KG, cantidadBolsa5kg)
        mostrarDetallesCarrito()
        actualizarResumen()
        Toast.makeText(context, "Bolsa de 5kg agregada al carrito", Toast.LENGTH_SHORT).show()
    }

    fun agregarBolsaGarfield(cantidad: Int) {
        cantidadBolsaGarfield += cantidad
        guardarCantidad(CLAVE_CANTIDAD_GARFIELD, cantidadBolsaGarfield)
        mostrarDetallesCarrito()
        actualizarResumen()
        Toast.makeText(context, "Bolsa de Garfield agregada al carrito", Toast.LENGTH_SHORT).show()
    }

    fun agregarBolsaMandalas(cantidad: Int) {
        cantidadBolsaMandalas += cantidad
        guardarCantidad(CLAVE_CANTIDAD_MANDALAS, cantidadBolsaMandalas)
        mostrarDetallesCarrito()
        actualizarResumen()
        Toast.makeText(context, "Bolsa de Mandalas agregada al carrito", Toast.LENGTH_SHORT).show()
    }
}