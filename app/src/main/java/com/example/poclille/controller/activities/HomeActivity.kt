package com.example.poclille.controller.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.poclille.R

class HomeActivity : AppCompatActivity() {
    private lateinit var mButtonVLille: Button
    private lateinit var mButtonRoubaix: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        mButtonVLille=findViewById(R.id.activity_home_vlille_button)
        mButtonRoubaix=findViewById(R.id.activity_home_roubaix_button)
        mButtonVLille.setOnClickListener { goToActivity(0) }
        mButtonRoubaix.setOnClickListener { goToActivity(1) }
    }

    //Go to main activity
    private fun goToActivity(choice:Int){
        val intent = Intent(this, MainActivity::class.java).apply{
            putExtra("choice",choice)
        }
        startActivity(intent)
    }
}