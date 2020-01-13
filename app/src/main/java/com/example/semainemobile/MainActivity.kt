package com.example.semainemobile

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //Add clicklister on signin button
        signinButton.setOnClickListener {
            val login = loginEditText.text.toString()
            val password = passwordEditText.text.toString()


            if (login.isNotEmpty() && password.isNotEmpty()){
                // Green
                textView.text=getString(R.string.label_success_login)
                textView.setTextColor(ContextCompat.getColor(this, R.color.awesome_green))

                // Snackbar.make(mainContainer, R.string.label_success_login, Snackbar.LENGTH_SHORT).show()

                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)

                // Pour empêcher le retour sur cet écran, on le quitte
                this.finish()

            } else {
                // Red
                textView.text=getString(R.string.label_fail_login)
                textView.setTextColor(ContextCompat.getColor(this, R.color.awesome_red))
                // textView.setBackgroundColor(ContextCompat.getColor(this, R.color.awesome_red))
            }
        }
    }
}
