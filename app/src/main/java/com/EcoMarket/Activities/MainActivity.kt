package com.EcoMarket.Activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.EcoMarket.R
import com.google.android.material.navigation.NavigationView
import android.widget.ImageView // Importa la clase ImageView

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbar: Toolbar
    private lateinit var navView: NavigationView
    private lateinit var drawerToggle: ActionBarDrawerToggle
    private lateinit var carritoComprasImageView: ImageView // Declara la variable para el ImageView del carrito

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.mainToolBar)
        setSupportActionBar(toolbar)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController
        drawerLayout = findViewById(R.id.mainActivity)
        navView = findViewById(R.id.mainNavMenu)

        drawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homefragment,
                R.id.perfilfragment,
                R.id.editarperfilfragment,
                R.id.productosFragment,
                R.id.carritoFragment
            ),
            drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Inicia la variable carritoComprasImageView después de findViewById
        carritoComprasImageView = findViewById(R.id.carrito_compras) // Encuentra el ImageView por su ID

        // Configuracion de  el OnClickListener para el carrito de compras
        carritoComprasImageView.setOnClickListener {
            navController.navigate(R.id.carritoFragment) // Navega al CarritoFragment
        }

        setupVisibility()

        navController.addOnDestinationChangedListener { _, destination, _ ->
            setupVisibility(destination.id)
        }
    }

    private fun setupVisibility(destinationId: Int = navController.currentDestination?.id ?: R.id.inicioFragment) {
        when (destinationId) {
            R.id.loginFragment, R.id.registroFragment, R.id.recuperacionContrasenaFragment, R.id.inicioFragment, R.id.rolFragment -> {
                toolbar.visibility = View.GONE
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                navView.visibility = View.GONE
            }
            else -> {
                toolbar.visibility = View.VISIBLE
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                navView.visibility = View.VISIBLE
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
