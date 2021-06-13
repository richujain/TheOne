package com.richujain.theone

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.disposables.Disposable

class RegisterActivity : AppCompatActivity() {
    var editTextForEmailRegister: EditText? = null;
    var editTextForPasswordRegister: EditText? = null;
    var email: String? = null;
    var password: String? = null;
    private var disposable: Disposable? = null
    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val buttonRegister = findViewById<Button
                >(R.id.buttonRegister)
        editTextForEmailRegister = findViewById<EditText
                >(R.id.editTextForEmailRegister)
        editTextForPasswordRegister = findViewById<EditText
                >(R.id.editTextForPasswordRegister)
        email = editTextForEmailRegister?.text?.trim().toString()
        password = editTextForPasswordRegister?.text?.trim().toString()
        buttonRegister.setOnClickListener() {
            if(email?.isNotEmpty() == true && password?.isNotEmpty() == true){
                //create()
                Toast.makeText(this@RegisterActivity, "Success!", Toast.LENGTH_SHORT).show()

            }
            else{
                Toast.makeText(this@RegisterActivity, "Incorrect Credentials!", Toast.LENGTH_SHORT).show()
            }
        }
    }


}