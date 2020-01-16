package com.example.semainemobile

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_transport.*

class TransportActivity : AppCompatActivity() {

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
    }
}

