package com.example.kotlinloginwithfirebase.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kotlinloginwithfirebase.R
import com.example.kotlinloginwithfirebase.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var successMes : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras

        if (bundle != null){
            successMes = bundle.getString("Success_Type","")
            when {
                successMes.equals("Login Successfully",ignoreCase = true) -> {
                    binding.loginSuccessLl.visibility = View.VISIBLE
                    binding.SignUpSuccessLl.visibility = View.GONE
                }
                successMes.equals("SignUp SucessFully",ignoreCase = true)->{
                    binding.SignUpSuccessLl.visibility = View.VISIBLE
                    binding.loginSuccessLl.visibility = View.GONE
                }
            }
        }

        binding.backtoSplachBtn.setOnClickListener {
            onBackPressed()
            finish()
        }
    }
}