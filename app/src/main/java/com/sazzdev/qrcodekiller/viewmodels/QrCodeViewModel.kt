package com.sazzdev.qrcodekiller.viewmodels

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sazzdev.qrcodekiller.utils.QrCodeUtils

class QrCodeViewModel(context: Context) : ViewModel() {
    private var resources: Resources
    private val _qrCodeImage = MutableLiveData<Drawable>()
    val qrCodeImage: LiveData<Drawable> get() = _qrCodeImage

    private fun generateQrCode(data: String) {
        if (data.isEmpty()) {
            _qrCodeImage.value = null
            return
        }
        val bitMap = QrCodeUtils.createQrCode(
            data,
            1000,
            1000,
            Color.BLACK
        )
        _qrCodeImage.value = BitmapDrawable(resources, bitMap)
    }

    fun onTextChanged(text: CharSequence) {
        generateQrCode(text.toString())
    }

    init {
        resources = context.resources
    }
}