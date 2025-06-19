class Layer(
    val size: Int,
    val activation: (Double) -> Double,
    val weights: Matrix = Matrix.create(rows = size),
    val biases: Vector = Vector(size)
) {
    fun init(nextLayerSize: Int) {
        weights.resize(cols = nextLayerSize, { Math.random() })
    }

    fun forward(inputs: DoubleArray): Vector {
        if (inputs.size != weights.rows) {
            error("Input size (${inputs.size}) must match the number of rows in the weights matrix (${weights.rows}).")
        }
        var outputs = weights * inputs
        outputs += biases
        return outputs.apply { activation(it) }
    }
}
