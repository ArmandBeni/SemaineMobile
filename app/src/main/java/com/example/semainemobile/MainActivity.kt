package com.example.semainemobile

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    // Initialize the APIHandler class with the right context
    private val apiHandler = APIHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        // Example: select an existing textview with the id "text"
        val txt = findViewById<TextView>(R.id.text)

        // Example: fetch data from specified URL and render on the textview
        // You just need to edit line 24 to do whatever you want with the "data" object :)
        apiHandler.get("https://1ef0af89-1dd3-489d-b596-b8a1057fb2c3@api.navitia.io/v1") { data: JSONObject ->
            txt.text = data.toString()
        }
    }
}
