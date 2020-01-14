package com.example.semainemobile

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
        apiHandler.get("https://example.com/api") {
                data: JSONObject -> txt.text = data.toString()
        }
    }
}
