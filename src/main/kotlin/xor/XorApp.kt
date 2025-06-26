package xor

import math.Matrix
import nn.ActivationFunction
import nn.Layer
import nn.NeuralNetwork

fun main() {
    val model = NeuralNetwork(
        learningRate = 0.01,
        activation = ActivationFunction.SIGMOID,
        Layer(size = 2),
        Layer(size = 4),
        Layer(size = 2)
    )
    model.init()

    val trainingData = Matrix.of(arrayOf(
        doubleArrayOf(0.0, 0.0),
        doubleArrayOf(0.0, 1.0),
        doubleArrayOf(1.0, 0.0),
        doubleArrayOf(1.0, 1.0)
    ))
    val labels = intArrayOf(0, 1, 1, 0) // XOR labels

    model.train(trainingData, labels, epoch = 5_000, batchSize = 50)
}
