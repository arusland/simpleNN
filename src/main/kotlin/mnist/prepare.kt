package mnist

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

/**
 * Converts CSV files to images.
 *
 * Source https://git-disl.github.io/GTDLBench/datasets/mnist_datasets/
 */
fun convertCsvToImages(csvFileName: String, outputDirectory: String) {
    val csvFile = File(csvFileName)
    val outputDir = File(outputDirectory)
    if (!outputDir.exists()) {
        outputDir.mkdirs()
    }

    // Read the CSV file and process each line
    val progressStep = 1000
    var count = 1
    csvFile.forEachLine { line ->
        val parts = line.split(",")
        val label = parts[0]
        val pixels = parts.drop(1).map { it.toInt() }

        // Create an image from the pixel data
        val image = createImageFromPixels(pixels)

        // Save the image to the output directory
        val imageFile = File(outputDir, "${label}_${count++}.png")
        ImageIO.write(image, "png", imageFile)
        if (count % progressStep == 0) {
            println("Processed $count images...")
        }
    }
}

fun createImageFromPixels(pixels: List<Int>): BufferedImage {
    val width = 28
    val height = 28
    val image = BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY)

    for (y in 0 until height) {
        for (x in 0 until width) {
            val pixelValue = pixels[y * width + x]
            image.setRGB(x, y, (pixelValue shl 16) or (pixelValue shl 8) or pixelValue)
        }
    }

    return image
}
