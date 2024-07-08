package com.example.kotlinloginwithfirebase.Activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinloginwithfirebase.R
import com.example.kotlinloginwithfirebase.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {  // Removed abstract
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()

        binding.EyePasswordImg.setOnClickListener {
            passwordVisibility(binding.passwordEdt, binding.EyePasswordImg)
        }

        binding.confirmPasswordEdt.setOnClickListener {
            passwordVisibility(binding.confirmPasswordEdt, binding.EyeConfirmImg)
        }

        binding.signUpBtn.setOnClickListener {
            signUp()
        }

        binding.backImg.setOnClickListener {
            onBackPressed()
        }
    }

    private fun passwordVisibility(editText: EditText, eyeIcon: ImageView) {
        if (editText.transformationMethod == PasswordTransformationMethod.getInstance()) {
            editText.transformationMethod = HideReturnsTransformationMethod.getInstance()
            eyeIcon.setImageResource(R.drawable.eyeopen_img)
        } else {
            editText.transformationMethod = PasswordTransformationMethod.getInstance()
            eyeIcon.setImageResource(R.drawable.eyeclosed_img)
        }
    }

    private fun signUp() {
        val email = binding.gmailEdt.text.toString().trim()
        val password = binding.passwordEdt.text.toString().trim()
        val confirmPassword = binding.confirmPasswordEdt.text.toString().trim()

        if (TextUtils.isEmpty(email)) {
            binding.gmailEdt.error = "Enter an Email"
            binding.gmailEdt.requestFocus()
            return
        }

        if (TextUtils.isEmpty(password)) {
            binding.passwordEdt.error = "Enter a Password"
            binding.passwordEdt.requestFocus()
            return
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            binding.confirmPasswordEdt.error = "Enter a Confirm Password"
            binding.confirmPasswordEdt.requestFocus()
            return
        }

        if (password != confirmPassword) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val intent = Intent(this, HomeActivity::class.java)
                val bundle = Bundle()
                bundle.putString("Success_Type", "SignUp Successfully")
                intent.putExtras(bundle)
                startActivity(intent)
                finish()

            } else {
                Toast.makeText(this, "SignUp failed: " + task.exception?.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
