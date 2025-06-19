class Vector(val size: Int, init: (Int) -> Double = { 0.0 }) : Iterable<Double> {
    private val data = DoubleArray(size, init)

    operator fun get(index: Int): Double {
        return data[index]
    }

    operator fun set(index: Int, value: Double) {
        data[index] = value
    }

    operator fun plus(value: Double): Vector {
        return Vector(size, { idx -> this[idx] + value })
    }

    operator fun plus(array: Vector): Vector {
        if (array.size != size) {
            error("Vector size (${array.size}) must match the vector size ($size).")
        }
        return Vector(size, { idx -> this[idx] + array[idx] })
    }

    override fun iterator(): Iterator<Double> = data.iterator()

    fun apply(operation: (Double) -> Double): Vector {
        for (idx in 0 until size) {
            data[idx] = operation(data[idx])
        }
        return this
    }

    companion object {
        val ZERO = Vector(0)

        fun of(size: Int, init: (Int) -> Double = { 0.0 }): Vector = if (size > 0) Vector(size) else ZERO
    }
}
