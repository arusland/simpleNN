package mnist

import math.Matrix
import nn.ActivationFunction
import nn.Layer
import nn.NeuralNetwork
import java.awt.image.BufferedImage
import java.nio.file.Paths
import javax.imageio.ImageIO
import kotlin.io.path.inputStream
import kotlin.io.path.listDirectoryEntries

class MnistApp {
    companion object {
        /**
         * Application to train a neural network on the MNIST dataset.
         */
        @JvmStatic
        fun main(args: Array<String>) {
            val model = NeuralNetwork(
                learningRate = 0.001,
                activation = ActivationFunction.SIGMOID,
                Layer(size = 784), // Input layer for MNIST (28x28 images flattened)
                Layer(size = 512),
                Layer(size = 128),
                Layer(size = 32),
                Layer(size = 10) // Output layer for 10 classes (digits 0-9)
            )
            model.init()

            println("Listing images in the directory...")
            val imagesDir = Paths.get("/home/ruslan/Downloads/MNIST_CSV/images/train")
            val imageFiles = imagesDir.listDirectoryEntries(glob = "*.png")
            val samples = imageFiles.size
            println("Loading $samples images from $imagesDir...")
            val bufferedImages = arrayOfNulls<BufferedImage>(samples)
            val labels = IntArray(samples)
            for (i in 0 until samples) {
                val imageFile = imageFiles[i]
                bufferedImages[i] = ImageIO.read(imageFile.inputStream())
                labels[i] = imageFile.fileName.toString()[0].toString().toInt() // Extract label from filename
            }
            println("Converting images to training data...")
            val trainingData = Matrix(samples, 784)
            for (i in 0 until trainingData.rows) {
                val image = bufferedImages[i] ?: error("Image at index $i is null")
                for (x in 0 until 28) {
                    for (y in 0 until 28) {
                        // Convert pixel to normalized value between 0 and 1
                        val pixelValue = image.getRGB(x, y) and 0xFF // Get the pixel value (grayscale)
                        trainingData[i][x + y * 28] = pixelValue / 255.0
                    }
                }
                labels[i] = imageFiles[i].fileName.toString()[0].toString().toInt() // Extract label from filename
            }
            println("Training the model...")
            model.train(trainingData, labels, epoch = 5_000, batchSize = 100)
            println("Training completed")
        }
    }
}
