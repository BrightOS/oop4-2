package ru.brightos.oop42

import android.content.SharedPreferences

class Model(
    val onModelChangedListener: OnModelChangedListener,
    val sharedPreferences: SharedPreferences
) {
    private var _a: Int = 0
    private var _b: Int = 0
    private var _c: Int = 0

    companion object {
        const val SHARED_A = "shared_a"
        const val SHARED_B = "shared_b"
        const val SHARED_C = "shared_c"
    }

    init {
        sharedPreferences.apply {
            _a = getInt(SHARED_A, _a)
            _b = getInt(SHARED_B, _b)
            _c = getInt(SHARED_C, _c)
        }
    }

    fun saveState() {
        sharedPreferences.edit().apply {
            putInt(SHARED_A, _a)
            putInt(SHARED_B, _b)
            putInt(SHARED_C, _c)
            apply()
        }
    }

    var a: Int
        get() = _a
        set(value) {
            if (value > _b)
                _b = value

            if (value > _c)
                _c = value

            _a = value
            onModelChangedListener.onModelChanged(_a, _b, _c)
        }

    var b: Int
        get() = _b
        set(value) {
            _b = value
            if (_b > _c)
                _b = _c

            if (_b < _a)
                _b = _a
            onModelChangedListener.onModelChanged(_a, _b, _c)
        }

    var c: Int
        get() = _c
        set(value) {
            if (value < _b)
                _b = value

            if (value < _a)
                _a = value

            _c = value
            onModelChangedListener.onModelChanged(_a, _b, _c)
        }
}