package com.EcoMarket.Fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.EcoMarket.R
import com.EcoMarket.Activities.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class LoginFragment : Fragment() {

    private lateinit var textViewRegistrar: TextView
    private lateinit var textViewRecuperarContraseña: TextView
    private lateinit var buttonLogin: Button
    private lateinit var editTextCorreo: EditText
    private lateinit var editTextContrasena: EditText
    private lateinit var btnGoogle: Button

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 123
    private val TAG = "GoogleSignIn"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializar vistas
        textViewRegistrar = view.findViewById(R.id.textRegistrologin)
        textViewRecuperarContraseña = view.findViewById(R.id.textResetPassword)
        buttonLogin = view.findViewById(R.id.buttonLogin)
        editTextCorreo = view.findViewById(R.id.editTextCorreo)
        editTextContrasena = view.findViewById(R.id.contraseña_usuario_login)
        btnGoogle = view.findViewById(R.id.btnGoogle)

        // Configurar Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

        btnGoogle.setOnClickListener {
            signIn()
        }

        // Botón de login con correo/contraseña
        buttonLogin.setOnClickListener {
            val correo = editTextCorreo.text.toString().trim()
            val contrasena = editTextContrasena.text.toString().trim()

            if (correo.isEmpty()) {
                Toast.makeText(requireContext(), "Por favor ingresa tu correo electrónico", Toast.LENGTH_SHORT).show()
            } else if (contrasena.isEmpty()) {
                Toast.makeText(requireContext(), "Por favor ingresa tu contraseña", Toast.LENGTH_SHORT).show()
            } else {
                if (correo == "pepitoperez@gmail.com" && contrasena == "pepito2025") {
                    findNavController().navigate(R.id.action_loginFragment_to_homefragment)
                } else {
                    Toast.makeText(requireContext(), "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Registro y recuperación
        textViewRegistrar.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registroFragment)
        }

        textViewRecuperarContraseña.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_recuperacionContrasenaFragment)
        }
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN && resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            Log.d(TAG, "signInSuccess: ${account.email}")
            Toast.makeText(requireContext(), "Bienvenido ${account.displayName}", Toast.LENGTH_LONG).show()

            findNavController().navigate(R.id.action_loginFragment_to_homefragment)

        } catch (e: ApiException) {
            Log.e(TAG, "signInResult:failed code=${e.statusCode}")

            val mensaje = when (e.statusCode) {
                10 -> "Error de configuración. Verifica la huella SHA-1"
                12500 -> "Error con Google Play Services"
                12501 -> "Inicio de sesión cancelado por el usuario"
                else -> "Error al iniciar sesión (código: ${e.statusCode})"
            }

            Toast.makeText(requireContext(), mensaje, Toast.LENGTH_LONG).show()
        }
    }
}
