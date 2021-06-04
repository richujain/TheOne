package com.richujain.theone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.richujain.theone.fragments.AccountFragment
import com.richujain.theone.fragments.CloudFragment
import com.richujain.theone.fragments.GalleryFragment
import com.richujain.theone.fragments.SendFragment
import nl.joery.animatedbottombar.AnimatedBottomBar
import kotlin.properties.Delegates

class HomeActivity : AppCompatActivity() {
    private val sendFragment = SendFragment()
    private val galleryFragment = GalleryFragment()
    private val cloudFragment = CloudFragment()
    private val accountFragment = AccountFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val bottom_bar = findViewById<AnimatedBottomBar
                >(R.id.bottom_bar)
        replaceFragment(sendFragment)
        bottom_bar.onTabSelected = {
            when(it.title){
                "Send" -> replaceFragment(sendFragment)
                "Gallery" -> replaceFragment(galleryFragment)
                "Cloud" -> replaceFragment(cloudFragment)
                "Account" -> replaceFragment(accountFragment)
            }
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer,fragment)
        transaction.commit()
    }

}