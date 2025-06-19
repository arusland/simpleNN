class Layer(
    val size: Int,
    val activation: (Double) -> Double,
    val weights: Matrix = Matrix.create(rows = size),
    val biases: DoubleArray = DoubleArray(size)
) {
    fun init(nextLayerSize: Int) {
        weights.resize(cols = nextLayerSize, { Math.random() })
    }

    fun forward(inputs: DoubleArray): DoubleArray {
        if (inputs.size != weights.rows) {
            error("Input size (${inputs.size}) must match the number of rows in the weights matrix (${weights.rows}).")
        }
        val outputs = weights * inputs
        for (i in outputs.indices) {
            outputs[i] += biases[i]
        }
        return outputs
    }
}
