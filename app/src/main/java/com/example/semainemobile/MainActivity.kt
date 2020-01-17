package com.example.semainemobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (supportActionBar != null)
            supportActionBar?.hide()

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