

class Vector(val size: Int, init: (Int) -> Double = { 0.0 }) {
    private val data = DoubleArray(size, init)

    operator fun get(index: Int): Double {
        return data[index]
    }

    operator fun set(index: Int, value: Double) {
        data[index] = value
    }

    operator fun plus(value: Double): Vector {
        for (i in 0 until size) {
            this[i] = this[i] + value
        }
        return this
    }

    companion object {
        val ZERO = Vector(0)

        fun of(size: Int, init: (Int) -> Double = { 0.0 }): Vector = if (size > 0) Vector(size) else ZERO
    }
}
