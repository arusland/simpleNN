

class NeuralNetwork(val learningRate: Double,
                    val layers: List<Layer>,
                    val activation: (Double) -> Double,
) {
    fun init() {
        // Initialize the weights and biases for each layer
        for (index in 0 until layers.size - 1) {
            val layer = layers[index]
            layer.init(layers[index + 1].size)
        }
    }

    fun train(x: Array<DoubleArray>, y: Array<Double>) {
        val prevNeurons = x[0]
        for (index in 1..layers.size) {
            val prevLayer =  layers[index - 1]
            val layer = layers[index]
            // calculate neurons for current layer
            val neurons = DoubleArray(layer.size)

        }
    }
}
