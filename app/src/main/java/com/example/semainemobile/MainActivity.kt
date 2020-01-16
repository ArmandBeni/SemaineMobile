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
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.semainemobile.network.data.AddressResult
import com.example.semainemobile.network.AddressService
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Header

class MainActivity : AppCompatActivity() {

    private val PERMISSION_ID = 42
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private lateinit var addressService: AddressService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (supportActionBar != null)
            supportActionBar?.hide()

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        getLastLocation()

        buttonMainActivity.setOnClickListener {
            val intent = Intent(this, TransportActivity::class.java)
            startActivity(intent)
        }

        val imageView = findViewById<ImageView>(R.id.posImageView) as ImageView

        var myAddress = ""

        imageView.setOnClickListener {
            Toast.makeText(this@MainActivity, "lat : ${latTextView.text}, lon : ${lonTextView.text}", Toast.LENGTH_SHORT).show()
            var test = findViewById<EditText>(R.id.editText)

            test.setText(myAddress)
        }

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.navitia.io/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val navitiaApiKey = "61ea5007-af1a-4b44-bc6e-b692f489a477"
        val txt = findViewById<TextView>(R.id.textAddress)

        addressService = retrofit.create(AddressService::class.java)

        addressService.getAddress(navitiaApiKey).enqueue(object: Callback<AddressResult> {
            override fun onFailure(call: Call<AddressResult>, t: Throwable) {
                t.toString()
                txt.text = "fail"
            }

            override fun onResponse(call: Call<AddressResult>, response: Response<AddressResult>) {
                response.body()
                myAddress = response.body()?.address?.label ?: "Impossible de trouver votre position"
                txt.text = response.body()?.address?.label ?: "Impossible de trouver votre position"
            }

        })

        //API Request for address
        // Insérer ici la clé d'API Navitia
//        val navitiaApiKey = "1ef0af89-1dd3-489d-b596-b8a1057fb2c3"
//        val addressService: AddressService = NavitiaService()
//            .getAddressService()
//
//        val response:Response<AddressResult> = addressService.findAddress("fr-idf", 2.420498f, 48.851871f, navitiaApiKey).execute()
//
//        val txt = findViewById<TextView>(R.id.textAddress)
//
//        if(response.isSuccessful) {
//            println("hello")
//        } else {
//            print("Fail !!! ")
//        }
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
                        findViewById<TextView>(R.id.latTextView).text = location.latitude.toString()
                        findViewById<TextView>(R.id.lonTextView).text = location.longitude.toString()
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
            val mLastLocation: Location = locationResult.lastLocation
            findViewById<TextView>(R.id.latTextView).text = mLastLocation.latitude.toString()
            findViewById<TextView>(R.id.lonTextView).text = mLastLocation.longitude.toString()
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