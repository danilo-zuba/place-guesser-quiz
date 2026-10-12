package com.example.placeguesser

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val btnJogar = findViewById<Button>(R.id.btnJogar)
        btnJogar.setOnClickListener {
            val intent = Intent(this, image_guesser::class.java)
            startActivity(intent)
        }
        val btnInstrucoes = findViewById<Button>(R.id.btnInstrucoes)
        btnInstrucoes.setOnClickListener {
            val intent = Intent(this, Instrucoes::class.java)
            startActivity(intent)
        }
    }
}