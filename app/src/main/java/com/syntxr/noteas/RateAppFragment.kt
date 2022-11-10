package com.syntxr.noteas

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.syntxr.noteas.R
import com.google.android.material.button.MaterialButton

class RateAppFragment: Fragment(R.layout.fragment_rate_app) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnRate = view.findViewById<MaterialButton>(R.id.btn_rate_app)

        btnRate.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps"))
            startActivity(intent)
        }
    }
}