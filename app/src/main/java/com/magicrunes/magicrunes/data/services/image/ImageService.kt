package com.magicrunes.magicrunes.data.services.image

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import com.magicrunes.magicrunes.MagicRunesApp
import java.nio.ByteBuffer

fun Bitmap.rotate(degrees: Float): Bitmap {
    val matrix = Matrix().apply { postRotate(degrees) }
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}

class ImageService(val app: MagicRunesApp) {
    fun getImageResource(name: String): Int {
        return app.resources.getIdentifier(
            name,
            "drawable",
            app.packageName
        )
    }

    fun convertResourceToBitmap(
        resource: Int,
        isNeedReverse: Boolean
    ): Bitmap {
        val bitmap = BitmapFactory.decodeResource(
            app.resources,
            resource
        )
        return if (isNeedReverse)bitmap.rotate(180f) else bitmap
    }

    fun getRuneBitmap(runeName: String, isReverse: Boolean): Bitmap =
        convertResourceToBitmap(
            getImageResource(runeName),
            isReverse
        )

    fun bitmapToArrayByte(bitmap: Bitmap): ByteArray {
        val size: Int = bitmap.rowBytes * bitmap.height
        val byteBuffer: ByteBuffer = ByteBuffer.allocate(size)
        bitmap.copyPixelsToBuffer(byteBuffer)

        return byteBuffer.array()
    }

    fun arrayByteToBitmap(byteArray: ByteArray, width: Int, height: Int): Bitmap {
        val configBmp = Bitmap.Config.valueOf("bitmap")
        val bitmap = Bitmap.createBitmap(width, height, configBmp)
        val buffer = ByteBuffer.wrap(byteArray)
        bitmap.copyPixelsFromBuffer(buffer)

        return bitmap
    }
}