package com.EcoMarket.Fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
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
    private var tvCantidadBolsaMandala: TextView? = null
    private var btnAddBolsaAnimales: Button? = null
    private var btnAddBolsa5kg: Button? = null
    private var btnAddBolsaGarfield: Button? = null
    private var btnAddBolsaMandala: Button? = null
    private var imgBolsaAnimales: ImageView? = null
    private var imgBolsa5kg: ImageView? = null
    private var imgBolsaGarfield: ImageView? = null
    private var imgBolsaMandalas: ImageView? = null

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
        prefs = context.getSharedPreferences("carrito", Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_carrito, container, false)
        inicializarVistas(view)
        obtenerCantidades()
        configurarListeners()
        mostrarDetallesCarrito()
        actualizarResumen()
        return view
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
        tvCantidadBolsaMandala = view.findViewById(R.id.tv_cantidad_bolsa_mandalas)

        // Inicialización de los botones de agregar.
        btnAddBolsaAnimales = view.findViewById(R.id.btn_add_bolsa_animales)
        btnAddBolsa5kg = view.findViewById(R.id.btn_add_bolsa_5kg)
        btnAddBolsaGarfield = view.findViewById(R.id.btn_add_bolsa_garfield)
        btnAddBolsaMandala = view.findViewById(R.id.btn_add_bolsa_mandala)


        imgBolsaAnimales = view.findViewById(R.id.img_bolsa_animales)
        imgBolsa5kg = view.findViewById(R.id.img_bolsa_5kg)
        imgBolsaGarfield = view.findViewById(R.id.img_bolsa_garfield)
        imgBolsaMandalas = view.findViewById(R.id.img_bolsa_mandalas)
    }

    private fun obtenerCantidades() {
        cantidadBolsaAnimales = prefs?.getInt(CLAVE_CANTIDAD_ANIMALES, 0) ?: 0
        cantidadBolsa5kg = prefs?.getInt(CLAVE_CANTIDAD_5KG, 0) ?: 0
        cantidadBolsaGarfield = prefs?.getInt(CLAVE_CANTIDAD_GARFIELD, 0) ?: 0
        cantidadBolsaMandalas = prefs?.getInt(CLAVE_CANTIDAD_MANDALAS, 0) ?: 0
    }

    private fun guardarCantidad(clave: String, cantidad: Int) {
        prefs?.edit()?.putInt(clave, cantidad)?.apply()
    }

    private fun configurarListeners() {
        btnEliminarBolsaAnimales?.setOnClickListener {
            if (cantidadBolsaAnimales > 0) {
                cantidadBolsaAnimales--
                guardarCantidad(CLAVE_CANTIDAD_ANIMALES, cantidadBolsaAnimales)
                mostrarDetallesCarrito()
                actualizarResumen()
                Toast.makeText(context, "Se eliminó una Bolsa de Animales", Toast.LENGTH_SHORT).show()
                if (cantidadBolsaAnimales == 0) {
                    layoutBolsaAnimales?.visibility = View.GONE
                }
            } else {
                Toast.makeText(context, "No hay Bolsas de Animales en el carrito", Toast.LENGTH_SHORT).show()
            }
        }

        btnAddBolsaAnimales?.setOnClickListener {
            cantidadBolsaAnimales++
            guardarCantidad(CLAVE_CANTIDAD_ANIMALES, cantidadBolsaAnimales)
            mostrarDetallesCarrito()
            actualizarResumen()
            if (cantidadBolsaAnimales > 0 && layoutBolsaAnimales?.visibility == View.GONE) {
                layoutBolsaAnimales?.visibility = View.VISIBLE
            }
            Toast.makeText(context, "Se añadió una Bolsa de Animales", Toast.LENGTH_SHORT).show()
        }

        btnEliminarBolsa5kg?.setOnClickListener {
            if (cantidadBolsa5kg > 0) {
                cantidadBolsa5kg--
                guardarCantidad(CLAVE_CANTIDAD_5KG, cantidadBolsa5kg)
                mostrarDetallesCarrito()
                actualizarResumen()
                Toast.makeText(context, "Se eliminó una Bolsa de 5kg", Toast.LENGTH_SHORT).show()
                if (cantidadBolsa5kg == 0) {
                    layoutBolsa5kg?.visibility = View.GONE
                }
            } else {
                Toast.makeText(context, "No hay Bolsas de 5kg en el carrito", Toast.LENGTH_SHORT).show()
            }
        }

        btnAddBolsa5kg?.setOnClickListener {
            cantidadBolsa5kg++
            guardarCantidad(CLAVE_CANTIDAD_5KG, cantidadBolsa5kg)
            mostrarDetallesCarrito()
            actualizarResumen()
            if (cantidadBolsa5kg > 0 && layoutBolsa5kg?.visibility == View.GONE) {
                layoutBolsa5kg?.visibility = View.VISIBLE
            }
            Toast.makeText(context, "Se añadió una Bolsa de 5kg", Toast.LENGTH_SHORT).show()
        }

        btnEliminarBolsaGarfield?.setOnClickListener {
            if (cantidadBolsaGarfield > 0) {
                cantidadBolsaGarfield--
                guardarCantidad(CLAVE_CANTIDAD_GARFIELD, cantidadBolsaGarfield)
                mostrarDetallesCarrito()
                actualizarResumen()
                Toast.makeText(context, "Se eliminó una Bolsa de Garfield", Toast.LENGTH_SHORT).show()
                if (cantidadBolsaGarfield == 0) {
                    layoutBolsaGarfield?.visibility = View.GONE
                }
            } else {
                Toast.makeText(context, "No hay Bolsas de Garfield en el carrito", Toast.LENGTH_SHORT).show()
            }
        }

        btnAddBolsaGarfield?.setOnClickListener {
            cantidadBolsaGarfield++
            guardarCantidad(CLAVE_CANTIDAD_GARFIELD, cantidadBolsaGarfield)
            mostrarDetallesCarrito()
            actualizarResumen()
            if (cantidadBolsaGarfield > 0 && layoutBolsaGarfield?.visibility == View.GONE) {
                layoutBolsaGarfield?.visibility = View.VISIBLE
            }
            Toast.makeText(context, "Se añadió una Bolsa de Garfield", Toast.LENGTH_SHORT).show()
        }

        btnEliminarBolsaMandalas?.setOnClickListener {
            if (cantidadBolsaMandalas > 0) {
                cantidadBolsaMandalas--
                guardarCantidad(CLAVE_CANTIDAD_MANDALAS, cantidadBolsaMandalas)
                mostrarDetallesCarrito()
                actualizarResumen()
                Toast.makeText(context, "Se eliminó una Bolsa de Mandalas", Toast.LENGTH_SHORT).show()
                if (cantidadBolsaMandalas == 0) {
                    layoutBolsaMandalas?.visibility = View.GONE
                }
            } else {
                Toast.makeText(context, "No hay Bolsas de Mandalas en el carrito", Toast.LENGTH_SHORT).show()
            }
        }

        btnAddBolsaMandala?.setOnClickListener {
            cantidadBolsaMandalas++
            guardarCantidad(CLAVE_CANTIDAD_MANDALAS, cantidadBolsaMandalas)
            mostrarDetallesCarrito()
            actualizarResumen()
            if (cantidadBolsaMandalas > 0 && layoutBolsaMandalas?.visibility == View.GONE) {
                layoutBolsaMandalas?.visibility = View.VISIBLE
            }
            Toast.makeText(context, "Se añadió una Bolsa de Mandalas", Toast.LENGTH_SHORT).show()
        }

        btnPagar?.setOnClickListener {
            val totalProductos =
                cantidadBolsaAnimales + cantidadBolsa5kg + cantidadBolsaGarfield + cantidadBolsaMandalas
            if (totalProductos > 0) {
                Toast.makeText(
                    context,
                    "Gracias por tu compra con esto ayudas al planeta 💖",
                    Toast.LENGTH_LONG
                ).show()
                prefs?.edit()?.clear()?.apply()
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

    private fun mostrarDetallesCarrito() {
        val precioBolsaAnimalesFormateado = formatoMonedaCO.format(PRECIO_BOLSA_ANIMALES.toLong())
        val precioBolsa5kgFormateado = formatoMonedaCO.format(PRECIO_BOLSA_5KG.toLong())
        val precioBolsaGarfieldFormateado = formatoMonedaCO.format(PRECIO_BOLSA_GARFIELD.toLong())
        val precioBolsaMandalasFormateado = formatoMonedaCO.format(PRECIO_BOLSA_MANDALAS.toLong())

        if (cantidadBolsaAnimales > 0) {
            tvBolsaAnimalesDetalle?.text =
                "Bolsa de Animales - Cantidad: " + cantidadBolsaAnimales + " | Precio: " + precioBolsaAnimalesFormateado + " | Subtotal: " + formatoMonedaCO.format(
                    (cantidadBolsaAnimales * PRECIO_BOLSA_ANIMALES).toLong()
                )
            layoutBolsaAnimales?.visibility = View.VISIBLE
            tvCantidadBolsaAnimales?.text =
                "Cantidad: $cantidadBolsaAnimales"
        } else {
            layoutBolsaAnimales?.visibility = View.GONE
            tvCantidadBolsaAnimales?.text = ""
        }

        if (cantidadBolsa5kg > 0) {
            tvBolsa5kgDetalle?.text =
                "Bolsa de 5kg - Cantidad: " + cantidadBolsa5kg + " | Precio: " + precioBolsa5kgFormateado + " | Subtotal: " + formatoMonedaCO.format(
                    (cantidadBolsa5kg * PRECIO_BOLSA_5KG).toLong()
                )
            layoutBolsa5kg?.visibility = View.VISIBLE
            tvCantidadBolsa5kg?.text = "Cantidad: $cantidadBolsa5kg"
        } else {
            layoutBolsa5kg?.visibility = View.GONE
            tvCantidadBolsa5kg?.text = ""
        }

        if (cantidadBolsaGarfield > 0) {
            tvBolsaGarfieldDetalle?.text =
                "Bolsa de Garfield - Cantidad: " + cantidadBolsaGarfield + " | Precio: " + precioBolsaGarfieldFormateado + " | Subtotal: " + formatoMonedaCO.format(
                    (cantidadBolsaGarfield * PRECIO_BOLSA_GARFIELD).toLong()
                )
            layoutBolsaGarfield?.visibility = View.VISIBLE
            tvCantidadBolsaGarfield?.text = "Cantidad: $cantidadBolsaGarfield"
        } else {
            layoutBolsaGarfield?.visibility = View.GONE
            tvCantidadBolsaGarfield?.text = ""
        }

        if (cantidadBolsaMandalas > 0) {
            tvBolsaMandalasDetalle?.text =
                "Bolsa de Mandalas - Cantidad: " + cantidadBolsaMandalas + " | Precio: " + precioBolsaMandalasFormateado + " | Subtotal: " + formatoMonedaCO.format(
                    (cantidadBolsaMandalas * PRECIO_BOLSA_MANDALAS).toLong()
                )
            layoutBolsaMandalas?.visibility = View.VISIBLE
            tvCantidadBolsaMandala?.text = "Cantidad: $cantidadBolsaMandalas"
        } else {
            layoutBolsaMandalas?.visibility = View.GONE
            tvCantidadBolsaMandala?.text = ""
        }
    }

    private fun actualizarResumen() {
        val totalProductos =
            cantidadBolsaAnimales + cantidadBolsa5kg + cantidadBolsaGarfield + cantidadBolsaMandalas
        val totalPrecio =
            (cantidadBolsaAnimales * PRECIO_BOLSA_ANIMALES) + (cantidadBolsa5kg * PRECIO_BOLSA_5KG) +
                    (cantidadBolsaGarfield * PRECIO_BOLSA_GARFIELD) + (cantidadBolsaMandalas * PRECIO_BOLSA_MANDALAS)
        tvResumen?.text =
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
        mostrarDetallesCarrito()
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

