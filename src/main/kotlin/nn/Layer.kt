package nn

import math.Matrix
import math.Vector

/**
 * Represents a layer in a [NeuralNetwork].
 */
data class Layer(
    val size: Int,
    val weights: Matrix = Matrix.ZERO, // weights to calculate the input of the next layer
    val biases: Vector = Vector.ZERO, // biases to add to the input of the next layer
    val activation: (Double) -> Double = { it }, // Default to identity function if not specified
    val input: Vector = Vector(size) // stores the last input of the layer calculated by the previous layer
) {
    fun init(nextLayerSize: Int, activation: (Double) -> Double): Layer {
        val weights = Matrix(rows = nextLayerSize, cols = size)
        // init with random values
        for (rowIdx in 0 until weights.rows) {
            val row = weights[rowIdx]
            for (col in 0 until row.size) {
                row[col] = Math.random() * 2 - 1 // Random values between -1 and 1
            }
        }
        val biases = Vector(nextLayerSize)
        for (idx in 0 until biases.size) {
            biases[idx] = Math.random() * 2 - 1 // Random values between -1 and 1
        }
        return this.copy(weights = weights, biases = biases, activation = activation)
    }

    fun forward(input: Vector): Vector {
        check(input.size == size) { "Input size (${input.size}) must match layer size ($size)" }
        this.input.copyFrom(input)

        if (weights == Matrix.ZERO) {
            // If weights are not initialized, probably this layer is the last one
            // Just return the input
            return this.input
        }
        val output = weights * input
        output += biases
        output *= activation // Apply activation function to each element of the output vector
        // the output of this layer is the input for the next layer
        return output
    }
}
