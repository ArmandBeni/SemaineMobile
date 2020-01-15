package com.example.semainemobile

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class TransportActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context: Context): Intent {
            val intent = Intent(context, TransportActivity::class.java)

            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transport)

        if (supportActionBar != null)
            supportActionBar?.hide()
    }
}
