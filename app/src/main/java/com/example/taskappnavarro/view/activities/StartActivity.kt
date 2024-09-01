package com.example.taskappnavarro.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.example.taskappnavarro.R
import com.example.taskappnavarro.view.activities.MainActivity

class StartActivity : AppCompatActivity() {

    private lateinit var lottieAnimationView: LottieAnimationView
    private lateinit var handlerThread: HandlerThread
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        // Referencia a la animación
        lottieAnimationView = findViewById(R.id.lottieAnimationView)

        // Habilitar Merge Paths para KitKat y superiores
        lottieAnimationView.enableMergePathsForKitKatAndAbove(true)

        // Inicializar el HandlerThread
        handlerThread = HandlerThread("StartActivityHandlerThread").apply { start() }
        handler = Handler(handlerThread.looper)

        // Usar el HandlerThread para ejecutar la transición a MainActivity
        handler.postDelayed({
            // Detener la animación y liberar recursos
            lottieAnimationView.cancelAnimation()

            // Navegar a la MainActivity
            val intent = Intent(this@StartActivity, MainActivity::class.java)
            startActivity(intent)
            finish()

            // Limpiar el HandlerThread
            handlerThread.quitSafely()
        }, 3000) // 3000 ms = 3 segundos
    }
}
