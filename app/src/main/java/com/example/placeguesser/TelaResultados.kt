package com.example.placeguesser

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class TelaResultados : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.tela_resultados)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val score = intent.getIntExtra(image_guesser.EXTRA_SCORE, 0)
        val total = intent.getIntExtra(image_guesser.EXTRA_TOTAL, 0)

        findViewById<TextView>(R.id.txvPontuacao).text = "$score / $total"
        findViewById<TextView>(R.id.txvMensagem).text = when {
            total == 0 -> ""
            score * 100 / total >= 80 -> "Excelente! Você conhece muito bem o Brasil!"
            score * 100 / total >= 50 -> "Muito bem! Dá para melhorar ainda mais."
            else -> "Continue praticando e tente de novo!"
        }

        findViewById<Button>(R.id.btnJogarNovamente).setOnClickListener {
            startActivity(Intent(this, image_guesser::class.java))
            finish()
        }
        findViewById<Button>(R.id.btnMenu).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
            finish()
        }
    }
}
