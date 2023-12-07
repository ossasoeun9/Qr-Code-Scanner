package com.sazzdev.qrcodekiller.utils

import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter

object QrCodeUtils {
    fun createQrCode(
        data: String,
        width: Int = 200,
        height: Int = 200,
        color: Int = Color.DKGRAY
    ): Bitmap? {
        val qrCode = MultiFormatWriter().encode(
            data,
            BarcodeFormat.QR_CODE,
            width,
            height
        ) ?: return null

        val w: Int = qrCode.width
        val h: Int = qrCode.height
        val pixels = IntArray(width * height)

        for (y in 0 until h) {
            val offset = y * w
            for (x in 0 until w) {
                val isTrue = qrCode.get(x, y)
                pixels[offset + x] = if (isTrue) color else Color.WHITE
            }
        }

        val bitMap = Bitmap.createBitmap(
            width,
            height,
            Bitmap.Config.ARGB_8888
        )
        bitMap.setPixels(pixels, 0, width, 0, 0, w, h)
        return bitMap
    }
}