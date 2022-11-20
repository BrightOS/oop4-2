package ru.brightos.oop42

class Model(val onModelChangedListener: OnModelChangedListener) {
    private var _a: Int = 0
    private var _b: Int = 0
    private var _c: Int = 0

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