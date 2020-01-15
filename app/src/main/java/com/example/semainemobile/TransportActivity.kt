package com.example.semainemobile

import android.content.Context
import android.content.Intent
import android.net.UrlQuerySanitizer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class TransportActivity : AppCompatActivity() {

    private val apihandler = APIHandler(this)

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

        // call API
        val txt = findViewById<TextView>(R.id.apiTextView)

        apihandler.get("https://api.navitia.io/v1/coverage/fr-idf/coords/2.420498;48.851871"){
            data: JSONObject -> txt.text = data.toString()
        }
    }
}