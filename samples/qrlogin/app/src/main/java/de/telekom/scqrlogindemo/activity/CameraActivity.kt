package de.telekom.scqrlogindemo.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import com.airbnb.lottie.LottieAnimationView
import de.telekom.scqrlogindemo.R
import de.telekom.smartcredentials.camera.factory.SmartCredentialsCameraFactory
import de.telekom.smartcredentials.core.api.CameraApi
import de.telekom.smartcredentials.core.camera.BarcodeType
import de.telekom.smartcredentials.core.camera.ScannerCallback
import de.telekom.smartcredentials.core.camera.SurfaceContainer

const val CAMERA_PERMISSION_RQ = 1234

class CameraActivity : AppCompatActivity() {

    private var qrAnimationView: LottieAnimationView? = null
    private var previewView: PreviewView? = null
    private var isProcessing = true

    private val scannerCallback: ScannerCallback = object : ScannerCallback {
        override fun onDetected(detectedValues: List<String>) {
            runOnUiThread {
                if (isProcessing) {
                    isProcessing = false
                    qrAnimationView!!.pauseAnimation()
                    val qrValues = ArrayList(detectedValues)
                    val intent = Intent()
                    intent.putExtra(MainActivity.KEY_BARCODE, qrValues.toString())
                    setResult(RESULT_OK, intent)
                    finish()
                }
            }
        }

        override fun onScanFailed(e: Exception?) {
            Toast.makeText(this@CameraActivity, R.string.qr_scan_failed, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        previewView = findViewById(R.id.preview_view)
        qrAnimationView = findViewById(R.id.scan_qr_animation_view)
        this.supportActionBar?.setTitle(R.string.qr_scanning)
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onStart() {
        super.onStart()
        if (!hasCameraPermission()) {
            requestCameraPermission()
        } else {
            getBarcodeScanner()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (requestCode == CAMERA_PERMISSION_RQ) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getBarcodeScanner()
            } else if (grantResults.isNotEmpty()) {
                Toast.makeText(this, R.string.camera_permission_not_granted, Toast.LENGTH_SHORT)
                    .show()
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun hasCameraPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA),
            CAMERA_PERMISSION_RQ
        )
    }

    private fun getBarcodeScanner() {
        val cameraApi: CameraApi<PreviewView> = SmartCredentialsCameraFactory.getCameraApi()
        val response = cameraApi.getBarcodeScannerView(
            this, SurfaceContainer(previewView),
            this, scannerCallback, BarcodeType.BARCODE_2D_QR_CODE
        )

        if (response.isSuccessful) {
            qrAnimationView!!.visibility = View.VISIBLE
            qrAnimationView!!.playAnimation()
        } else {
            Toast.makeText(this, R.string.camera_scanner_layout_failed, Toast.LENGTH_SHORT).show()
        }
    }
}