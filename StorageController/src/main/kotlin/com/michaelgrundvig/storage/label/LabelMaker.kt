package com.michaelgrundvig.storage.label

import org.w3c.dom.Element
import java.awt.*
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import javax.imageio.*
import kotlin.math.roundToInt

class LabelMaker() {

    // Configuration settings
    private val dpi = 1200
    private val pageWidth = 8.5
    private val pageHeight = 11.0
    private val margin = (0.25 * dpi).roundToInt()
    private val gap = (0.125 * dpi).roundToInt()
    private val labelWidth = (1.115 * dpi).roundToInt()
    private val labelHeight = (0.5 * dpi).roundToInt()
    private val width = (dpi * pageWidth).roundToInt()
    private val height = (dpi * pageHeight).roundToInt()

    fun generateLabelSheet(startIn:Int = 0): ByteArray {

        var start = startIn

        val imageType = BufferedImage.TYPE_INT_RGB

        val imageBuffer = BufferedImage(width, height, imageType)
        val graphics = imageBuffer.createGraphics()

        graphics.paint = Color.white
        graphics.setRenderingHint(
            RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_ON
        )

        // Fills the entire image
        graphics.fillRect(0, 0, imageBuffer.width, imageBuffer.height)

        graphics.stroke = BasicStroke(10f)

        var currentX = margin
        var currentY = margin

        // Top to bottom
        while (currentY + gap + labelHeight < height - margin) {

            // Left to right
            while (currentX + gap + labelWidth <= width - margin) {

                graphics.paint = Color.LIGHT_GRAY
                graphics.drawRect(currentX, currentY, labelWidth, labelHeight)
                graphics.paint = Color.BLACK

                graphics.drawImage(
                    BarcodeGenerator.generateBarcodeImage(
                        (start++).toString().padStart(7, '0'),
                        (labelWidth * 0.9).roundToInt(),
                        (labelHeight * 0.9).roundToInt()
                    ),
                    currentX + labelWidth / 20,
                    currentY + labelHeight / 20,
                    null
                )

                currentX += labelWidth + gap
            }
            currentX = margin
            currentY += labelHeight + gap
        }

        val iw = ImageIO.getImageWritersByFormatName("jpeg")
        while (iw.hasNext()) {
            val writer = iw.next()
            val writeParams = writer.defaultWriteParam
            writeParams.compressionMode = ImageWriteParam.MODE_EXPLICIT
            // Adjust the image quality
            writeParams.compressionQuality = 1f
            val data = writer.getDefaultImageMetadata(ImageTypeSpecifier(imageBuffer), writeParams)
            val tree = data.getAsTree("javax_imageio_jpeg_image_1.0") as Element
            val jfif = tree.getElementsByTagName("app0JFIF").item(0) as Element
            jfif.setAttribute("Xdensity", dpi.toString() + "")
            jfif.setAttribute("Ydensity", dpi.toString() + "")
            jfif.setAttribute("resUnits", "1") // density is dots per inch
            val out = ByteArrayOutputStream()
            ImageIO.createImageOutputStream(out).use {
                writer.output = it
                writer.write(data, IIOImage(imageBuffer, null, null), writeParams)
            }
            return out.toByteArray()
        }

        return ByteArray(0)
    }
}