package com.richujain.theone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.richujain.theone.api.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.richujain.theone.models.LoginResponse

class LoginActivity : AppCompatActivity() {

    var textViewRegister: TextView? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val buttonLogin = findViewById<Button
                >(R.id.buttonLogin)
        textViewRegister = findViewById<TextView
                >(R.id.textViewRegister)
        textViewRegister?.setOnClickListener() {
            intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }
        val editTextForEmail = findViewById<EditText
                >(R.id.editTextForEmail)
        val editTextForPassword = findViewById<EditText
                >(R.id.editTextForPassword)
        buttonLogin.setOnClickListener() {
            if (editTextForEmail.text.trim().isNotEmpty() && editTextForPassword.text.trim()
                    .isNotEmpty()
            ) {
                login(
                    editTextForEmail.text.trim().toString(),
                    editTextForPassword.text.trim().toString()
                );
            }
        }
    }

    fun login(email: String, password: String) {
        RetrofitClient.instance.userLogin(email, password)
            .enqueue(object: Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message+"Here123", Toast.LENGTH_LONG).show()
                }
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if(response.isSuccessful){
                        if(response.body()?.user?.id!! > 0){
                            Log.d("response",""+response.body())
                            val intent = Intent(applicationContext, HomeActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                        }
                        else{
                            Toast.makeText(applicationContext, "Incorrect Credentials", Toast.LENGTH_LONG).show()
                        }

                    }
                    else{
                        Toast.makeText(applicationContext, "Connection Failed", Toast.LENGTH_LONG).show()
                    }
                }
            })
    }
}
