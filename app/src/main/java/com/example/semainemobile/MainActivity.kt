package com.example.semainemobile

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.VideoView
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_confirmation_dialog.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (supportActionBar != null)
            supportActionBar?.hide()

        val typeface = Typeface.createFromAsset(assets, "MuktaVaani-Medium.ttf")
        randTextView.typeface = typeface
        buttonMainActivity.typeface = typeface


        buttonMainActivity.setOnClickListener {

            val confirmationDialogFragment = ConfirmationDialogFragment.newInstance()
            confirmationDialogFragment.onClick = {
                // confirmationDialogFragment.dismiss()

                val intent = Intent(this, TransportActivity::class.java)
                startActivity(intent)
            }
            confirmationDialogFragment.show(supportFragmentManager, "confirmation_dialog")
        }
        val images = intArrayOf(R.drawable.img1, R.drawable.img2)
        val quotes = arrayOf("“Amuse toi bien, pas de bêtises” - Maman", "“Juste un verre et je rentre” \uD83D\uDE0F")

        val rand = Random()
        imageView.setImageResource(images[rand.nextInt(images.size)])
        randTextView.text = quotes[rand.nextInt(images.size)]
    }
}