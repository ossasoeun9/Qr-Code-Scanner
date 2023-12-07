package com.sazzdev.qrcodekiller.views

import android.Manifest
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.ExperimentalGetImage
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.firebase.FirebaseApp
import com.sazzdev.qrcodekiller.R
import com.sazzdev.qrcodekiller.databinding.ActivityMainBinding
import com.sazzdev.qrcodekiller.viewmodels.QrCodeViewModel


@ExperimentalGetImage
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val REQUEST_PERMISSION_NOTIFICATION = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), REQUEST_PERMISSION_NOTIFICATION)
            }
        }
        FirebaseApp.initializeApp(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.qrCodeViewModel = QrCodeViewModel(this)
        binding.lifecycleOwner = this
        binding.scanBtn.setOnClickListener {
            val newIntent = Intent(this, ScanQrCodeActivity::class.java)
            startActivity(newIntent)
        }
        val qrCode = intent.getStringExtra("qrCode")
        val runBy = intent.getStringExtra("runBy")

        if (qrCode != null) {
            val alertDialog = AlertDialog.Builder(this).also {
                it.setTitle("$runBy Qr Code")
                it.setMessage(qrCode)
                it.setPositiveButton("COPY") { _, _ ->
                    val clipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                    val clipData = ClipData.newPlainText("Simple Text", qrCode)
                    clipboardManager.setPrimaryClip(clipData)
                }
                it.setNegativeButton("Close", null)
            }
            alertDialog.create()
            alertDialog.show()
        }
    }
}