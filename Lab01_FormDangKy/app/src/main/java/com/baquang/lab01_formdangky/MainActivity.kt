package com.baquang.lab01_formdangky

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.baquang.lab01_formdangky.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            val name = binding.edtName.text?.toString()?.trim().orEmpty()
            val email = binding.edtEmail.text?.toString()?.trim().orEmpty()
            val pass = binding.edtPassword.text?.toString()?.trim().orEmpty()

            val msg = if (name.isEmpty() || email.isEmpty() || pass.isEmpty()) {
                "Vui lòng nhập đủ thông tin"
            } else {
                "Đăng ký thành công!"
            }

            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
    }
}