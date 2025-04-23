package com.example.tallersegundomomento.fragments

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tallersegundomomento.R

class EditarPerfilFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_editarperfil, container, false)

        val prefs = requireContext().getSharedPreferences("usuario", Context.MODE_PRIVATE)

        val etNombre = view.findViewById<EditText>(R.id.et_nombre)
        val etCorreo = view.findViewById<EditText>(R.id.et_correo)
        val etTelefono = view.findViewById<EditText>(R.id.et_telefono)
        val btnGuardar = view.findViewById<Button>(R.id.btn_guardar)
        val btnCambiarFoto = view.findViewById<Button>(R.id.btn_cambiar_foto)

        // Datos existentes (o por defecto)
        etNombre.setText(prefs.getString("nombre", "Laura Guarín"))
        etCorreo.setText(prefs.getString("correo", "lauraguasan05@gmail.com"))
        etTelefono.setText(prefs.getString("telefono", "3106558734"))

        // Botón simbólico de cambiar foto
        btnCambiarFoto.setOnClickListener {
            Toast.makeText(context, "Función para cambiar foto no implementada aún pero proximamnte la profe nos enseñara como hacerlo", Toast.LENGTH_SHORT).show()
        }

        // Guardar cambios
        btnGuardar.setOnClickListener {
            val nombre = etNombre.text.toString()
            val correo = etCorreo.text.toString()
            val telefono = etTelefono.text.toString()

            prefs.edit()
                .putString("nombre", nombre)
                .putString("correo", correo)
                .putString("telefono", telefono)
                .apply()

            Toast.makeText(context, "✅ Tus datos fueron guardados exitosamente,ya eres otra persona", Toast.LENGTH_SHORT).show()

            Handler(Looper.getMainLooper()).postDelayed({
                findNavController().popBackStack() // Regresa a PerfilFragment
            }, 2000)
        }

        return view
    }
}
