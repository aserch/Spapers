package com.syntxr.noteas

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.syntxr.noteas.R
import com.google.android.material.card.MaterialCardView


class AboutMeFragment:Fragment(R.layout.fragment_about_me) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val github = view.findViewById<MaterialCardView>(R.id.card_github)

        github.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/aserch/"))
            startActivity(intent)
        }
    }
}