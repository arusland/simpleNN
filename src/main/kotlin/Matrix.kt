class Matrix(val rows: Int, var cols: Int) : Iterable<Vector> {
    private val data: Array<Vector> = Array(rows) { Vector.of(cols) }

    /**
     * Initializes the matrix with a given number of rows and columns.
     */
    fun resize(cols: Int, init: () -> Double = { 0.0 }) {
        this.cols = cols
        for (row in 0 until rows) {
            data[row] = Vector.of(cols) { init() }
        }
    }

    /**
     * Multiplies this matrix by a vector.
     */
    operator fun times(vector: DoubleArray): Vector {
        if (vector.size != cols) {
            error("Vector size (${vector.size}) must match the number of columns in the matrix ($cols).")
        }
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

    override fun iterator(): Iterator<Vector> = data.iterator()

    override fun toString(): String {
        return buildString {
            for (row in data) {
                append(row.joinToString(", ", "[", "]"))
                append('\n')
            }
        }
    }

    companion object {
        fun create(rows: Int, cols: Int = 0): Matrix = Matrix(rows, cols)
        private val emptyArray = DoubleArray(0)
    }
}
