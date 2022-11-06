package com.michaelgrundvig.storage.label

import org.krysalis.barcode4j.impl.upcean.UPCEBean
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider
import java.awt.Image
import java.awt.image.BufferedImage
import kotlin.math.roundToInt

object BarcodeGenerator {
    fun generateBarcodeImage(barcodeText: String?, width: Int, height: Int): BufferedImage {
        val barcodeGenerator = UPCEBean()
        val canvas = BitmapCanvasProvider(1200, BufferedImage.TYPE_BYTE_BINARY, true, 0)
        barcodeGenerator.generateBarcode(canvas, barcodeText)
        val image = canvas.bufferedImage
        val croppedHeight = (width.toDouble() / image.width.toDouble() * height.toDouble()).roundToInt()
        val croppedImage = image.getSubimage(0, image.height - croppedHeight, image.width, croppedHeight)
        return resize(croppedImage, width, height)
    }

    fun resize(img: BufferedImage, newW: Int, newH: Int): BufferedImage {
        val tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH)
        val dimg = BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB)
        val g2d = dimg.createGraphics()
        g2d.drawImage(tmp, 0, 0, null)
        g2d.dispose()
        return dimg
    }
}