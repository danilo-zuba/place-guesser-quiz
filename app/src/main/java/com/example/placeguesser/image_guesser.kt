package com.example.placeguesser

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import coil.load
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

class image_guesser : AppCompatActivity() {

    companion object {
        const val TOTAL_QUESTIONS = 20
        const val EXTRA_SCORE = "extra_score"
        const val EXTRA_TOTAL = "extra_total"
        private val COLOR_CORRECT = Color.parseColor("#4CAF50")
        private val COLOR_WRONG = Color.parseColor("#F44336")
    }

    private lateinit var txvProgresso: TextView
    private lateinit var txvEnunciado: TextView
    private lateinit var txvResposta: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var scroll: ScrollView
    private lateinit var ivPergunta: ImageView
    private lateinit var layoutImagens: LinearLayout
    private lateinit var layoutTexto: LinearLayout
    private lateinit var btnProximo: Button
    private lateinit var btnSair: Button
    private lateinit var imageViews: List<ImageView>
    private lateinit var optionButtons: List<Button>
    private var defaultButtonTint: ColorStateList? = null

    private var questions: List<Question> = emptyList()
    private var currentOptions: List<Option> = emptyList()
    private var index = 0
    private var score = 0
    private var answered = false
    private var hasError = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_image_guesser)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        txvProgresso = findViewById(R.id.txvProgresso)
        txvEnunciado = findViewById(R.id.txvEnunciado)
        txvResposta = findViewById(R.id.txvResposta)
        progressBar = findViewById(R.id.progressBar)
        scroll = findViewById(R.id.scrollConteudo)
        ivPergunta = findViewById(R.id.ivPergunta)
        layoutImagens = findViewById(R.id.layoutImagens)
        layoutTexto = findViewById(R.id.layoutTexto)
        btnProximo = findViewById(R.id.btnProximo)
        btnSair = findViewById(R.id.btnSair)

        imageViews = listOf(
            findViewById(R.id.ivImagem1), findViewById(R.id.ivImagem2),
            findViewById(R.id.ivImagem3), findViewById(R.id.ivImagem4)
        )
        optionButtons = listOf(
            findViewById(R.id.btnOpcao1), findViewById(R.id.btnOpcao2),
            findViewById(R.id.btnOpcao3), findViewById(R.id.btnOpcao4)
        )
        defaultButtonTint = optionButtons[0].backgroundTintList

        imageViews.forEachIndexed { i, iv -> iv.setOnClickListener { onImageOptionClicked(i) } }
        optionButtons.forEachIndexed { i, b -> b.setOnClickListener { onTextOptionClicked(i) } }

        btnProximo.setOnClickListener {
            if (hasError) {
                hasError = false
                loadQuiz()
            } else {
                goNext()
            }
        }
        btnSair.setOnClickListener { finish() }

        loadQuiz()
    }

    // ------------------------------------------------------------------
    // Carregamento
    // ------------------------------------------------------------------
    private fun loadQuiz() {
        showLoading()
        lifecycleScope.launch {
            try {
                val result = QuizRepository.fetchQuiz(TOTAL_QUESTIONS)
                if (result.isEmpty()) {
                    showError("Nenhuma pergunta encontrada no banco de dados.")
                    return@launch
                }
                questions = result
                index = 0
                score = 0
                showQuestion()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                showError("Não foi possível carregar o quiz. Verifique sua internet e tente novamente.")
            }
        }
    }

    private fun showLoading() {
        progressBar.visibility = View.VISIBLE
        scroll.visibility = View.GONE
        txvResposta.visibility = View.GONE
        btnProximo.visibility = View.GONE
        txvProgresso.text = "Carregando..."
    }

    private fun showError(message: String) {
        hasError = true
        progressBar.visibility = View.GONE
        scroll.visibility = View.GONE
        txvProgresso.text = ""
        txvResposta.text = message
        txvResposta.visibility = View.VISIBLE
        btnProximo.text = "Tentar novamente"
        btnProximo.visibility = View.VISIBLE
    }

    // ------------------------------------------------------------------
    // Exibição da pergunta
    // ------------------------------------------------------------------
    private fun showQuestion() {
        val q = questions[index]
        answered = false
        currentOptions = q.options

        progressBar.visibility = View.GONE
        scroll.visibility = View.VISIBLE
        txvResposta.visibility = View.GONE
        btnProximo.visibility = View.GONE
        updateProgress()
        txvEnunciado.text = q.statement

        // reset de estado visual
        ivPergunta.visibility = View.GONE
        layoutImagens.visibility = View.GONE
        layoutTexto.visibility = View.GONE
        imageViews.forEach { it.setBackgroundColor(Color.TRANSPARENT) }
        optionButtons.forEach {
            it.visibility = View.GONE
            it.backgroundTintList = defaultButtonTint
        }

        when (q.type) {
            "IMAGE_CHOICE" -> {
                layoutImagens.visibility = View.VISIBLE
                imageViews.forEachIndexed { i, iv ->
                    val url = q.options.getOrNull(i)?.imageUrl
                    if (url != null) {
                        iv.visibility = View.VISIBLE
                        iv.load(url) { crossfade(true) }
                    } else {
                        iv.visibility = View.INVISIBLE
                    }
                }
            }
            else -> { // IMAGE_MULTIPLE_CHOICE e TRUE_FALSE
                val url = q.imageUrl
                if (url != null) {
                    ivPergunta.visibility = View.VISIBLE
                    ivPergunta.load(url) { crossfade(true) }
                }
                layoutTexto.visibility = View.VISIBLE
                optionButtons.forEachIndexed { i, b ->
                    val opt = q.options.getOrNull(i)
                    if (opt != null) {
                        b.visibility = View.VISIBLE
                        b.text = opt.text ?: ""
                    }
                }
            }
        }
        scroll.scrollTo(0, 0)
    }

    private fun updateProgress() {
        txvProgresso.text = "Pergunta ${index + 1}/${questions.size}  •  $score pts"
    }

    // ------------------------------------------------------------------
    // Respostas
    // ------------------------------------------------------------------
    private fun onImageOptionClicked(i: Int) {
        if (answered) return
        val chosen = currentOptions.getOrNull(i) ?: return
        answered = true

        val correctIndex = currentOptions.indexOfFirst { it.isCorrect }
        imageViews.getOrNull(correctIndex)?.setBackgroundColor(COLOR_CORRECT)
        if (!chosen.isCorrect) imageViews[i].setBackgroundColor(COLOR_WRONG)

        finishAnswer(chosen.isCorrect)
    }

    private fun onTextOptionClicked(i: Int) {
        if (answered) return
        val chosen = currentOptions.getOrNull(i) ?: return
        answered = true

        val correctIndex = currentOptions.indexOfFirst { it.isCorrect }
        optionButtons.getOrNull(correctIndex)?.backgroundTintList = ColorStateList.valueOf(COLOR_CORRECT)
        if (!chosen.isCorrect) optionButtons[i].backgroundTintList = ColorStateList.valueOf(COLOR_WRONG)

        finishAnswer(chosen.isCorrect)
    }

    private fun finishAnswer(correct: Boolean) {
        if (correct) score++
        updateProgress()

        val explanation = questions[index].explanation
        val header = if (correct) "Correto! +1 ponto" else "Errado!"
        txvResposta.text = if (explanation.isNullOrBlank()) header else "$header\n$explanation"
        txvResposta.visibility = View.VISIBLE

        btnProximo.text = if (index == questions.size - 1) "Ver resultado" else "Próxima"
        btnProximo.visibility = View.VISIBLE
    }

    private fun goNext() {
        index++
        if (index >= questions.size) openResults() else showQuestion()
    }

    private fun openResults() {
        val intent = Intent(this, TelaResultados::class.java)
            .putExtra(EXTRA_SCORE, score)
            .putExtra(EXTRA_TOTAL, questions.size)
        startActivity(intent)
        finish()
    }
}
