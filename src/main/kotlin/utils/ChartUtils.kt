package utils

import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartPanel
import org.jfree.chart.plot.PlotOrientation
import org.jfree.data.Range
import org.jfree.data.xy.XYSeries
import org.jfree.data.xy.XYSeriesCollection
import javax.swing.JFrame
import javax.swing.SwingUtilities

/**
 * Utility object for creating and updating a chart to visualize neural network training accuracy.
 */
object ChartUtils {
    private var frame: JFrame? = null
    private var series: XYSeries? = null
    private var dataset: XYSeriesCollection? = null

    fun createChart() {
        if (frame != null) return // Chart already created

        SwingUtilities.invokeLater {
            // Create dataset
            series = XYSeries("Accuracy")
            dataset = XYSeriesCollection(series)

            // Create chart
            val chart = ChartFactory.createXYLineChart(
                "Neural Network Training",
                "Epoch",
                "Accuracy",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
            )

            // Configure chart appearance
            val plot = chart.xyPlot
            plot.rangeAxis.range = Range(0.0, 1.0)

            // Create and display frame
            frame = JFrame("Neural Network Training")
            frame!!.defaultCloseOperation = JFrame.DISPOSE_ON_CLOSE
            frame!!.contentPane.add(ChartPanel(chart))
            frame!!.setSize(800, 600)
            frame!!.isVisible = true
        }
    }

    fun updateChart(accuracyData: List<Pair<Int, Double>>) {
        SwingUtilities.invokeLater {
            if (frame == null) {
                createChart()
            }

            // Clear existing data
            series!!.clear()

            // Add new data points
            accuracyData.forEach { (epoch, accuracy) ->
                series!!.add(epoch.toDouble(), accuracy)
            }
        }
    }

    fun addDataPoint(epoch: Int, accuracy: Double) {
        SwingUtilities.invokeLater {
            if (frame == null) {
                createChart()
            }

            series!!.add(epoch.toDouble(), accuracy)
        }
    }

    // Keep for backward compatibility
    fun displayAccuracyChart(accuracyData: List<Pair<Int, Double>>) {
        if (frame == null) {
            createChart()
        }
        updateChart(accuracyData)
    }
}

fun main() {
    // Example usage of ChartUtils
    ChartUtils.createChart()
    ChartUtils.addDataPoint(1, 0.8)
    ChartUtils.addDataPoint(2, 0.85)
    ChartUtils.addDataPoint(3, 0.9)

    // Simulate updating the chart with new data every second
    Thread {
        for (i in 4..10) {
            Thread.sleep(1000) // Simulate waiting for new data
            ChartUtils.addDataPoint(i, 0.8 + i * 0.01) // Simulated accuracy data
        }
    }.start()
}
