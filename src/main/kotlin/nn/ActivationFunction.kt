package nn

import kotlin.math.exp

enum class ActivationFunction(
    val activation: (Double) -> Double,
    val derivative: (Double) -> Double
) {
    SIGMOID(
        activation = { x -> 1.0 / (1.0 + exp(-x)) },
        derivative = { x -> x * (1.0 - x) }
    );
}
