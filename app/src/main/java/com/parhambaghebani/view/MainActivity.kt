package com.parhambaghebani.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.parhambaghebani.viewModel.MainViewModel
import com.parhambaghebani.R
import com.parhambaghebani.utility.Status
import com.parhambaghebani.databinding.ActivityMainBinding
import com.parhambaghebani.utility.DialogLoading
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private val REQUEST_LOCATION_PERMISSION = 1000
    private lateinit var binding: ActivityMainBinding
    private val tehranLatLng by lazy {
        LatLng(35.6, 51.3)
    }
    private val mapFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
    }
    private val dialog by lazy {
        DialogLoading()
    }

    private var map: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        enableMyLocation()
        viewModel.data.observe(this) { response ->
            when (response.status) {
                Status.LOADING -> {
                    showLoading()
                }

                Status.SUCCESS -> {
                    dismissLoading()
                    response.data?.let {
                        map?.addMarker(MarkerOptions().position(LatLng(it.lat, it.lon)).title(it.current.temp.toString()))
                    }
                }

                Status.ERROR -> {
                    dismissLoading()
                    Toast.makeText(this, "خطا در برقراری ارتباط با سرور", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun enableMyLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_PERMISSION)
        } else {
            mapFragment.getMapAsync { map ->
                this.map = map
                enableMyLocation()
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(tehranLatLng, 10F))
                map.uiSettings.setAllGesturesEnabled(true)
                map.isMyLocationEnabled = true

                map.setOnMapClickListener { latLng ->
                    viewModel.onMapClick(latitude = latLng.latitude, longitude = latLng.longitude)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION_PERMISSION && grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
            enableMyLocation()
        }
    }

    private fun showLoading() {
        if (!dialog.isShowing()) {
            dialog.show(supportFragmentManager.beginTransaction(), DialogLoading.Tag)
        }
    }

    private fun dismissLoading() {
        if (dialog.isShowing()) {
            dialog.dismiss()
        }
    }
}