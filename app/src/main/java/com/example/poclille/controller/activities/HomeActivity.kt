package com.example.poclille.controller.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.poclille.R
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        ActivityHomeVlilleButton.setOnClickListener { goToActivity(0) }
        ActivityHomeRoubaixButton.setOnClickListener { goToActivity(1) }
    }

    //Go to main activity
    private fun goToActivity(choice:Int){
        val intent = Intent(this, MainActivity::class.java).apply{
            putExtra("choice",choice)
        }
        startActivity(intent)
    }
}