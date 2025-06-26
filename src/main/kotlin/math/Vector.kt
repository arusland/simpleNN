package math

/**
 * Represents a mathematical vector of a given size.
 */
class Vector(val size: Int) : Iterable<Double> {
    private val data = DoubleArray(size)

    operator fun get(index: Int): Double {
        return data[index]
    }

    operator fun set(index: Int, value: Double) {
        data[index] = value
    }

    operator fun plus(value: Double): Vector {
        val result = Vector(size)

        for (idx in 0 until size) {
            result[idx] = data[idx] + value
        }
        return result
    }

    operator fun minus(vector: Vector): Vector {
        check(vector.size == size) { "Vector sizes do not match: ${vector.size} != $size" }

        val result = Vector(size)
        for (idx in 0 until size) {
            result[idx] = data[idx] - vector[idx]
        }
        return result
    }

    operator fun times(value: Double): Vector {
        val result = Vector(size)

        for (idx in 0 until size) {
            result[idx] = data[idx] * value
        }
        return result
    }

    operator fun times(vector: Vector): Vector {
        check(vector.size == size) { "Vector sizes do not match: ${vector.size} != $size" }

        val result = Vector(size)
        for (idx in 0 until size) {
            result[idx] = data[idx] * vector[idx]
        }
        return result
    }

    operator fun times(vector: Matrix): Vector {
        check(vector.rows == size) { "Matrix rows (${vector.rows}) must match vector size ($size)" }

        val result = Vector(vector.cols)
        for (col in 0 until vector.cols) {
            var sum = 0.0
            for (row in 0 until size) {
                sum += data[row] * vector[row][col]
            }
            result[col] = sum
        }
        return result
    }

    operator fun times(func: (Double) -> Double): Vector {
        val result = Vector(size)
        for (idx in 0 until size) {
            result[idx] = func(data[idx])
        }
        return result
    }

    operator fun plusAssign(vector: Vector) {
        check(vector.size == size) { "Vector sizes do not match: ${vector.size} != $size" }

        for (idx in 0 until size) {
            data[idx] += vector[idx]
        }
    }

    operator fun timesAssign(func: (Double) -> Double) {
        for (idx in 0 until size) {
            data[idx] = func(data[idx])
        }
    }

    operator fun timesAssign(value: Double) {
        for (idx in 0 until size) {
            data[idx] *= value
        }
    }

    operator fun timesAssign(vector: Vector) {
        check(vector.size == size) { "Vector sizes do not match: ${vector.size} != $size" }

        for (idx in 0 until size) {
            data[idx] *= vector[idx]
        }
    }

    fun transpose(): Matrix {
        val matrix = Matrix(size, 1)
        for (i in 0 until size) {
            matrix[i][0] = data[i]
        }
        return matrix
    }

    fun argMax(): Int {
        var maxIndex = -1
        for (i in 0 until size) {
            if (maxIndex < 0 || data[i] > data[maxIndex]) {
                maxIndex = i
            }
        }
        return maxIndex
    }

    override fun iterator(): Iterator<Double> = data.iterator()

    /**
     * Calculates outer product of this vector with another vector.
     */
    fun outer(input: Vector): Matrix {
        val result = Matrix(size, input.size)
        for (i in 0 until size) {
            for (j in 0 until input.size) {
                result[i][j] = data[i] * input[j]
            }
        }
        return result
    }

    fun copyFrom(vector: Vector) {
        check(vector.size == size) { "Output size (${vector.size}) must match vector size ($size)" }

        for (i in 0 until size) {
            data[i] = vector[i]
        }
    }

    fun fill(value: Double) {
        for (i in 0 until size) {
            data[i] = value
        }
    }

    override fun toString(): String {
        return data.joinToString(", ", "Vector($size)[", "]")
    }

    companion object {
        val ZERO = Vector(0)

        fun of(size: Int): Vector = if (size > 0) Vector(size) else ZERO
    }
}