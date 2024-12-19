package ru.aston.custom_views_aston

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {

    private lateinit var customWheelView: RainbowWheelView
    private lateinit var resultContainer: FrameLayout
    private lateinit var resetButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        customWheelView = findViewById(R.id.rainbowWheel)
        resultContainer = findViewById(R.id.displayContainer)
        resetButton = findViewById(R.id.resetButton)

        customWheelView.onSegmentSelected = { segment ->
            handleSegmentSelection(segment)
        }

        customWheelView.setOnClickListener {
            customWheelView.spin()
        }

        resetButton.setOnClickListener {
            resultContainer.removeAllViews()
        }
    }

    private fun handleSegmentSelection(segment: Int) {
        resultContainer.removeAllViews()

        if (segment in customWheelView.textSegments) {
            val textView = TextView(this).apply {
                text = "Lorem Ipsum"
                textSize = 24f
                setTextColor(Color.BLACK)
                gravity = Gravity.CENTER
                layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    gravity = Gravity.CENTER
                }
            }
            resultContainer.addView(textView)
        } else {
            val imageView = ImageView(this).apply {
                layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    gravity = Gravity.CENTER
                }
            }
            Glide.with(this)
                .load("https://placebear.com/1280/720")
                .into(imageView)
            resultContainer.addView(imageView)
        }
    }

}
