package com.EcoMarket.Fragments

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
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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

    private lateinit var registerTextView: TextView
    private lateinit var resetPasswordTextView: TextView
    private lateinit var loginButton: Button
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var googleSignInButton: Button
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var googleSignInLauncher: ActivityResultLauncher<Intent>
    private val GOOGLE_SIGN_IN_REQUEST_CODE = 123
    private val TAG = "GoogleSignIn"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configurar Google Sign In
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .build()

        // Crear el cliente de Google SignIn
        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), googleSignInOptions)

        // Inicializar el ActivityResultLauncher para el inicio de sesión con Google
        googleSignInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleSignInResult(task)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el layout para este fragmento
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializar las vistas después de que el layout ha sido inflado
        registerTextView = view.findViewById(R.id.textRegistrologin)
        resetPasswordTextView = view.findViewById(R.id.textResetPassword)
        loginButton = view.findViewById(R.id.buttonLogin)
        emailEditText = view.findViewById(R.id.editTextCorreo)
        passwordEditText = view.findViewById(R.id.contraseña_usuario_login)
        googleSignInButton = view.findViewById(R.id.btnGoogle) // ID Correcto del botón de Google

        registerTextView.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registroFragment)
        }

        resetPasswordTextView.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_recuperacionContrasenaFragment)
        }

        loginButton.setOnClickListener {
            val correo = emailEditText.text.toString().trim()
            val contrasena = passwordEditText.text.toString().trim()

            if (correo.isEmpty()) {
                Toast.makeText(requireContext(), "Por favor ingresa tu correo electrónico", Toast.LENGTH_SHORT).show()
            } else if (contrasena.isEmpty()) {
                Toast.makeText(requireContext(), "Por favor ingresa tu contraseña", Toast.LENGTH_SHORT).show()
            } else {
                // Aquí deberías implementar la lógica de autenticación real (como se explicó anteriormente)
                if (correo == "pepitoperez@gmail.com" && contrasena == "pepito2025") {
                    findNavController().navigate(R.id.action_loginFragment_to_homefragment)
                } else {
                    Toast.makeText(requireContext(), "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                }
            }
        }

        googleSignInButton.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        googleSignInLauncher.launch(signInIntent) // Usar el ActivityResultLauncher
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            // Inicio de sesión exitoso con Google
            Log.d(TAG, "signInSuccess: ${account?.email}")
            Toast.makeText(requireContext(), "Bienvenido ${account?.displayName}", Toast.LENGTH_LONG).show()

            // Ir a MainActivity
            val intent = Intent(requireContext(), MainActivity::class.java)
            account?.email?.let { intent.putExtra("USER_EMAIL", it) }
            account?.displayName?.let { intent.putExtra("USER_NAME", it) }
            startActivity(intent)
            requireActivity().finish() // Opcional: Cerrar la pantalla de inicio de sesión
        } catch (exception: ApiException) {
            // Error en el inicio de sesión con Google
            Log.w(TAG, "signInResult:failed code=${exception.statusCode}")

            val mensaje = when (exception.statusCode) {
                10 -> "Error de configuración. Verifica la huella SHA-1"
                12500 -> "Error con Google Play Services"
                12501 -> "Inicio de sesión cancelado por el usuario"
                else -> "Error al iniciar sesión (código: ${exception.statusCode})"
            }
            Toast.makeText(requireContext(), mensaje, Toast.LENGTH_LONG).show()
        }
    }
}

