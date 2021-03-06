package com.example.semainemobile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_code_dialog.*

class CodeDialogFragment: DialogFragment() {
    var onClick: (() -> Unit)? = null

    companion object {
        fun newInstance(): CodeDialogFragment{
            val fragment = CodeDialogFragment()

            val arguments = Bundle()

            fragment.arguments = arguments

            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_code_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        shareCodeText.setOnClickListener {
            onClick?.invoke()
        }
    }
}