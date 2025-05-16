package com.EcoMarket.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.EcoMarket.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions

class UbicacionFragment : Fragment(), OnMapReadyCallback {
    private lateinit var maps: GoogleMap;

    // Ubicaciones predefinidas
    private val ubication1 = LatLng(4.664442334606499, -74.05587327586935);
    private val ubication2 = LatLng(4.631187845815241, -74.08685583351244);
    private val ubication3 = LatLng(4.608120001676332, -74.07538776061747);

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ubicaciones, container, false);

        val mapFragment = childFragmentManager.findFragmentById(R.id.googleMapsFragment) as SupportMapFragment;
        mapFragment.getMapAsync(this);

        // Configurar los botones
        view.findViewById<Button>(R.id.storeAddress).setOnClickListener {
            moveToLocation(ubication1, R.string.defaultAddress.toString());
        };

        view.findViewById<Button>(R.id.storeAddressOne).setOnClickListener {
            moveToLocation(ubication2, R.string.storeAddressOne.toString());
        };

        view.findViewById<Button>(R.id.storeAddressTwo).setOnClickListener {
            moveToLocation(ubication3, R.string.storeAddressTwo.toString());
        };

        return view;
    }

    override fun onMapReady(googleMap: GoogleMap) {
        maps = googleMap;

        // Habilitar controles de Zoom
        maps.uiSettings.isZoomControlsEnabled = true;

        // Mover a la primera ubicación por defecto
        moveToLocation(ubication1, R.string.defaultAddress.toString());
    }

    private fun moveToLocation(location: LatLng, title: String) {
        // Limpiar marcadores anteriores
        maps.clear();

        // Añadir marcador a la ubicación seleccionada
        maps.addMarker(MarkerOptions().position(location).title(title));

        // Mover hacía la ubicación y hacer Zoom
        maps.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 12f));
    }
}