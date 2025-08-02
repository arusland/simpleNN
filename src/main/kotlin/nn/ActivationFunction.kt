package nn

import kotlin.math.exp

enum class ActivationFunction(
    val activation: (Double) -> Double,
    val derivative: (Double) -> Double
) {
    SIGMOID(
        activation = { x -> 1.0 / (1.0 + exp(-x)) },
        derivative = { x -> x * (1.0 - x) }
    ),
    TANH(
        activation = { x ->
            val exp = exp(x)
            val expMinus = exp(-x)
            (exp - expMinus) / (exp + expMinus)
        },
        derivative = { x -> 1.0 - x * x }
    ),
}
