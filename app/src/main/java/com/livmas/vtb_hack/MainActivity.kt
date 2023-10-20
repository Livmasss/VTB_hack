package com.livmas.vtb_hack

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.livmas.vtb_hack.databinding.ActivityMainBinding
import com.livmas.vtb_hack.enums.RouteType
import com.livmas.vtb_hack.fragments.InputFragment
import com.livmas.vtb_hack.object_creaters.Locator
import com.livmas.vtb_hack.object_creaters.Marker
import com.livmas.vtb_hack.object_creaters.TotalRouter
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKit
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.map.CameraPosition

class MainActivity : AppCompatActivity() {
    lateinit var mapkit: MapKit
    lateinit var binding: ActivityMainBinding
    lateinit var holder: MapObjectsHolder

    private lateinit var locator: Locator
    private lateinit var marker: Marker
    lateinit var router: TotalRouter

    override fun onCreate(savedInstanceState: Bundle?) {
        grantLocationPermissions()
        presetMap()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        holder = MapObjectsHolder(binding.mvMap.map.mapObjects)

        binding.mvMap.map.isNightModeEnabled = true

        locator = Locator(mapkit, binding.mvMap, holder)
        marker = Marker(this)
        router = TotalRouter(binding.mvMap.map.mapObjects, holder)

        initViews()
    }

    override fun onStart() {
        super.onStart()
        mapkit.onStart()
        binding.mvMap.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapkit.onStop()
        binding.mvMap.onStop()
    }

    private fun presetMap() {
        val key = BuildConfig.MAPKIT_API_KEY
        MapKitFactory.setApiKey(key)
        MapKitFactory.initialize(this)
        mapkit = MapKitFactory.getInstance()
    }

    private fun initViews() {
        binding.apply {
            fabRequest.setOnClickListener {
                val fragment = InputFragment()

                fragment.show(supportFragmentManager, "123")
            }

            ibCar.setOnClickListener {
                router.type = RouteType.Driving
            }
            ibWalking.setOnClickListener {
                router.type = RouteType.Walking
            }
            ibMasstransit.setOnClickListener {
                router.type = RouteType.Mass
            }

            pbLoading.visibility = View.GONE
            fabFocus.setOnClickListener { zoom() }
        }
    }

    private fun zoom() {
        if (holder.location == null)
            return
        binding.mvMap.map.move( CameraPosition(
            holder.location!!,
            15f,
            0f,
            0f
        ),
            Animation(
                Animation.Type.SMOOTH,
                2f
            ),
            null
        )
    }


    private fun grantLocationPermissions() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 0)
    }
}
