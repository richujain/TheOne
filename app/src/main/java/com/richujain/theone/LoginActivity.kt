package com.richujain.theone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val buttonLogin = findViewById<Button
                >(R.id.buttonLogin)
        buttonLogin.setOnClickListener(){
            if(login()){
                intent = Intent(this,HomeActivity::class.java)
                startActivity(intent)
            }
            else
                Toast.makeText(this@LoginActivity, "Incorrect Credentials!", Toast.LENGTH_SHORT).show()
        }

    }

    private fun login(): Boolean {
        val editTextEmail = findViewById<EditText
                >(R.id.editTextForEmail)
        val editTextPassword = findViewById<EditText
                >(R.id.editTextForPassword)
        return editTextEmail.text.trim().toString().equals("a") && editTextPassword.text.trim().toString().equals("a")
    }

}