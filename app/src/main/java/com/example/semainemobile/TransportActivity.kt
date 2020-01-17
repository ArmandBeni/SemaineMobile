package com.example.semainemobile

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.semainemobile.network.AddressService
import com.example.semainemobile.network.data.AddressResult
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.activity_transport.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TransportActivity : AppCompatActivity() {

    private val PERMISSION_ID = 42
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private lateinit var addressService: AddressService

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, TransportActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transport)

        if (supportActionBar != null)
            supportActionBar?.hide()

        // show code

        getCodeBtn.setOnClickListener {

            val codeDialogFragment = CodeDialogFragment.newInstance()
            codeDialogFragment.onClick = {
                codeDialogFragment.dismiss()
            }
            codeDialogFragment.show(supportFragmentManager, "code_dialog")
        }

        // create your code

        getCodeView.setOnClickListener{

            val getCodeDialogFragment = GetCodeDialogFragment.newInstance()
            getCodeDialogFragment.onClick = {
                getCodeDialogFragment.dismiss()
            }
            getCodeDialogFragment.show(supportFragmentManager, "get_code_dialog")
        }

        // Get location
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        var pinPosition = findViewById<ImageView>(R.id.pinPosition)
        pinPosition.setOnClickListener {
            getLastLocation()
        }

    }
    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {

                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    val location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {

                        //Get Address position
                        var myAddress:String
                        var editAddress = findViewById<EditText>(R.id.editAddress)

                        //Call Api
                        val retrofit = Retrofit.Builder()
                            .baseUrl("https://api.navitia.io/v1/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()

                        val navitiaApiKey = "15f15f9c-5799-4afa-8ef4-38649ed6a20c"

                        addressService = retrofit.create(AddressService::class.java)
                        addressService.getAddress(location.longitude.toString(), location.latitude.toString(), navitiaApiKey).enqueue(object: Callback<AddressResult> {
                            override fun onFailure(call: Call<AddressResult>, t: Throwable) {
                                myAddress = "Impossible de trouver ta position"
                                t.toString()
                            }

                            override fun onResponse(call: Call<AddressResult>, response: Response<AddressResult>) {
                                response.body()
                                myAddress = response.body()?.address?.label ?: "Impossible de trouver ta position"
                                editAddress.setText(myAddress)
                            }
                        })
                    }
                }
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationClient.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            // val mLastLocation: Location = locationResult.lastLocation
            // findViewById<TextView>(R.id.latTextView).text = mLastLocation.latitude.toString()
            // findViewById<TextView>(R.id.lonTextView).text = mLastLocation.longitude.toString()
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_ID
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLastLocation()
            }
        }
    }
}

