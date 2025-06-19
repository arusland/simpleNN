

class NeuralNetwork(val learningRate: Double,
                    val layers: List<Layer>,
                    val activation: (Double) -> Double,
) {
    fun train(x: Array<DoubleArray>, y: Array<Double>) {
        x.forEachIndexed { index, input ->
            forward(input, y[index])
        }
    }

    private fun forward(inputs: DoubleArray, y: Double) {
        val prevNeurons = inputs.clone()
        for (index in 1..layers.size) {
            val prevLayer =  layers[index - 1]
            val layer = layers[index]
            // calculate neurons for current layer
            val neurons = DoubleArray(layer.size)

        }
    }

}
