package math

/**
 * Represents a mathematical matrix with a specified number of rows and columns.
 * Each row is represented as a vector.
 */
class Matrix(val rows: Int, val cols: Int) : Iterable<Vector> {
    private val data: Array<Vector> = Array(rows) { row -> Vector.of(cols) }

    /**
     * Multiplies this matrix by a vector.
     */
    operator fun times(vector: Vector): Vector {
        check(vector.size == cols) { "math.Vector size (${vector.size}) must match the number of columns in the matrix ($cols)." }

        val result = Vector(rows)
        for (row in 0 until rows) {
            var sum = 0.0
            for (col in 0 until cols) {
                sum += data[row][col] * vector[col]
            }
            result[row] = sum
        }
        return result
    }

    operator fun plusAssign(vector: Matrix) {
        check(vector.rows == rows && vector.cols == cols) { "Matrix sizes do not match: ${vector.rows}x${vector.cols} != $rows x $cols" }

        for (row in 0 until rows) {
            data[row] += vector[row]
        }
    }

    operator fun get(row: Int): Vector {
        return data[row]
    }

    override fun iterator(): Iterator<Vector> = data.iterator()

    override fun toString(): String {
        return buildString {
            append("Matrix(")
            append(rows)
            append("x")
            append(cols)
            append(")\n")
            for (row in data) {
                append(row.joinToString(", ", "[", "]"))
                append('\n')
            }
        }
    }

    companion object {
        fun of(data: Array<DoubleArray>): Matrix {
            val rows = data.size
            val cols = if (rows > 0) data[0].size else 0
            val matrix = Matrix(rows, cols)
            for (row in 0 until rows) {
                for (col in 0 until cols) {
                    matrix[row][col] = data[row][col]
                }
            }
            return matrix
        }

        val ZERO = Matrix(0, 0)
    }
}