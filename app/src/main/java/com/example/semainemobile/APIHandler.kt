package com.example.semainemobile

import android.content.Context
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class APIHandler(ctx: Context) {
    private val context = ctx

    fun get(url: String, cb: (JSONObject) -> Unit) {
        val queue = Volley.newRequestQueue(this.context)

        val req = object: JsonObjectRequest(Method.GET, url, null,
            Response.Listener { response ->
                val data = JSONObject("{error: false, data: ${response}}")
                cb(data)
            }, Response.ErrorListener { error ->
                val data = JSONObject("{error: true, msg: ${error}}")
                cb(data)
            }
        )
        {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "1ef0af89-1dd3-489d-b596-b8a1057fb2c3" // Replace with your own auth token
                return headers
            }
        }

        queue.add(req)
    }
}