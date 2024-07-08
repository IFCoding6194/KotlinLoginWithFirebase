package com.example.kotlinloginwithfirebase.Activity

import android.os.Bundle
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import android.content.Intent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kotlinloginwithfirebase.R
import com.example.kotlinloginwithfirebase.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()

        binding.backImg.setOnClickListener {
            onBackPressed()
        }

        binding.loginBtn.setOnClickListener {
            login()
        }

        binding.eyeImg.setOnClickListener {
            passwordVisiblity(binding.passwordEdt,binding.eyeImg)
        }
    }

    private fun passwordVisiblity(editText: EditText,eyeImg:ImageView){
        if (editText.transformationMethod == PasswordTransformationMethod.getInstance()){
            editText.transformationMethod = HideReturnsTransformationMethod.getInstance()
            eyeImg.setImageResource(R.drawable.eyeopen_img)
        }else{
            editText.transformationMethod = PasswordTransformationMethod.getInstance()
            eyeImg.setImageResource(R.drawable.eyeclosed_img)
        }
    }

    private fun login(){
        val email : String = binding.gmailEdt.text.toString().trim()
        val password : String = binding.passwordEdt.text.toString().trim()

        if (TextUtils.isEmpty(email)){
            binding.gmailEdt.error = "Enter a Email"
            binding.gmailEdt.requestFocus()
            return
        }
        if (TextUtils.isEmpty(password)){
            binding.passwordEdt.error = "Enter a Password"
            binding.passwordEdt.requestFocus()
            return
        }
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this) {task ->
            if (task.isSuccessful){
                val intent = Intent(this, HomeActivity::class.java)
                val bundle = Bundle() // Corrected syntax, removed parentheses
                bundle.putString("Success_Type", "Login Successfully")
                intent.putExtras(bundle)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this,"Login failed "+ task.exception.toString(),Toast.LENGTH_SHORT).show()
            }
        }
    }
}