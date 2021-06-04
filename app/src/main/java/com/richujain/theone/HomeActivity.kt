package com.richujain.theone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import nl.joery.animatedbottombar.AnimatedBottomBar
import kotlin.properties.Delegates

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val bottom_bar = findViewById<AnimatedBottomBar
                >(R.id.bottom_bar)
        bottom_bar.onTabSelected = {
            Log.d("bottom_bar", "Selected tab: " + it.title)
        }
    }
}