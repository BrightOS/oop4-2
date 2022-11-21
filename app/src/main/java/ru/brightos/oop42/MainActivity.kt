package ru.brightos.oop42

import android.content.SharedPreferences
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.google.android.material.slider.LabelFormatter
import ru.brightos.oop42.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    lateinit var sharedPreferences: SharedPreferences
    lateinit var model: Model

    val labelFormatter = object : LabelFormatter {
        override fun getFormattedValue(value: Float): String {
            return (value * 100).toInt().toString()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (this::model.isInitialized.not())
            model = Model(
                onModelChangedListener = object : OnModelChangedListener {
                    override fun onModelChanged(a: Int, b: Int, c: Int) {
                        binding.aEditText.setText(a.toString())
                        binding.bEditText.setText(b.toString())
                        binding.cEditText.setText(c.toString())

                        binding.aSlider.value = a.toFloat() / 100
                        binding.bSlider.value = b.toFloat() / 100
                        binding.cSlider.value = c.toFloat() / 100

                        binding.aNumberPicker.value = a
                        binding.bNumberPicker.value = b
                        binding.cNumberPicker.value = c
                    }
                },
                sharedPreferences = getSharedPreferences("oop42_preferences", MODE_PRIVATE)
            )
        model.onModelChangedListener.onModelChanged(model.a, model.b, model.c)

        binding.aNumberPicker.apply {
            maxValue = 100
            minValue = 0
        }
        binding.bNumberPicker.apply {
            maxValue = 100
            minValue = 0
        }
        binding.cNumberPicker.apply {
            maxValue = 100
            minValue = 0
        }

        binding.aSlider.setLabelFormatter(labelFormatter)
        binding.bSlider.setLabelFormatter(labelFormatter)
        binding.cSlider.setLabelFormatter(labelFormatter)

        binding.aEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                model.a = Integer.parseInt("${binding.aEditText.text}")
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        binding.bEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                model.b = Integer.parseInt("${binding.bEditText.text}")
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        binding.cEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                model.c = Integer.parseInt("${binding.cEditText.text}")
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        binding.aSlider.addOnChangeListener { slider, value, fromUser ->
            if (fromUser)
                model.a = (value * 100).toInt()
        }
        binding.bSlider.addOnChangeListener { slider, value, fromUser ->
            if (fromUser)
                model.b = (value * 100).toInt()
        }
        binding.cSlider.addOnChangeListener { slider, value, fromUser ->
            if (fromUser)
                model.c = (value * 100).toInt()
        }

        binding.aNumberPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            println("Picker value $newVal")
            model.a = picker.value
        }
        binding.bNumberPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            model.b = picker.value
        }
        binding.cNumberPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            model.c = picker.value
        }
    }

    override fun onResume() {
        super.onResume()
        println("onResume")
    }

    override fun onPause() {
        println("onPause")
        model.saveState()
        super.onPause()
    }


}