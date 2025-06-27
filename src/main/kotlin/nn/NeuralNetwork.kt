package nn

import math.Matrix
import math.Vector

/**
 * Represents a neural network consisting of multiple [Layer]s.
 */
class NeuralNetwork(
    val learningRate: Double,
    val activation: ActivationFunction = ActivationFunction.SIGMOID,
    vararg layers: Layer
) {
    val layers = layers.toMutableList()
    var progressHandler: ((TrainResult) -> Unit)? = null

    fun init() {
        // Initialize the weights and biases for each layer
        for (index in 0 until layers.size - 1) {
            // Initialize the current layer with the size of the next layer and replace the current layer
            layers[index] = layers[index].init(nextLayerSize = layers[index + 1].size, activation.activation)
        }
    }

    /**
     * Trains the neural network using the provided inputs and labels.
     *
     * @param inputs The input data as a matrix where each row is an input vector.
     * @param labels The labels for the input data as an array of class indices.
     */
    fun train(
        inputs: Matrix,
        labels: IntArray,
        epoch: Int = 1000,
        batchSize: Int = 1,
        progressStep: Int = 100
    ) {
        val classSize = layers.last().size
        val targets = Vector(classSize)

        for (epoch in 1..epoch) {
            var correct = 0
            var errorSum = 0.0
            for (batch in 1..batchSize) {
                val inputIndex = (Math.random() * inputs.rows).toInt()
                // prepare target/known vector
                val label = labels[inputIndex]
                targets.fill(0.0) // Reset targets for each input
                targets[label] = 1.0 // Set the target for the correct class

                // Forward pass
                val input = inputs[inputIndex]
                val output = forward(input)
                val result = output.argMax()
                if (result == label) {
                    correct++
                }
                // Calculate the error
                for (i in 0 until classSize) {
                    val diff = output[i] - targets[i]
                    errorSum += diff * diff
                }
                // Backward pass
                backward(targets)
            }
            // Print progress
            if (epoch % progressStep == 0) {
                val accuracy = correct.toDouble() / batchSize
                println("Epoch $epoch: Accuracy = $accuracy, Error Sum = $errorSum")
                progressHandler?.invoke(TrainResult(accuracy, errorSum, epoch))
            }
        }
    }

    fun forward(input: Vector): Vector {
        var output = input
        for (layer in layers) {
            // outputs of the previous layer are the inputs for the next layer
            output = layer.forward(output)
        }
        return output
    }

    private fun backward(targets: Vector) {
        // Calculate the error for the last layer
        var errors = targets - layers.last().input
        // Backpropagation through the layers
        for (i in layers.size - 1 downTo 1) {
            val layer = layers[i]
            val input = layer.input

            // Calculate the gradient for the current layer
            val gradients = input * activation.derivative // Apply the derivative of the activation function
            gradients *= errors // Element-wise multiplication with the errors
            gradients *= learningRate // Scale by the learning rate

            val prevLayer = layers[i - 1]
            val prevErrors = errors * prevLayer.weights

            // Update biases by adding the gradients
            prevLayer.biases += gradients

            // Update weights by adding the deltas
            val deltas: Matrix = gradients.outer(prevLayer.input)
            prevLayer.weights += deltas

            // Update errors for the next layer
            errors = prevErrors
        }
    }
}

data class TrainResult(
    val accuracy: Double,
    val errorSum: Double,
    val epoch: Int
)
