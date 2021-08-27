package de.telekom.scqrlogindemo.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.airbnb.lottie.LottieAnimationView
import de.telekom.scqrlogindemo.R
import de.telekom.smartcredentials.camera.factory.SmartCredentialsCameraFactory
import de.telekom.smartcredentials.core.api.CameraApi
import de.telekom.smartcredentials.core.camera.BarcodeType
import de.telekom.smartcredentials.core.camera.CameraScannerLayout
import de.telekom.smartcredentials.core.camera.ScannerCallback
import de.telekom.smartcredentials.core.camera.ScannerPluginUnavailable
import java.util.*

class CameraActivity : AppCompatActivity() {

    private val CAMERA_PERMISSION_RQ = 1234

    private var cameraWrapper: FrameLayout? = null
    private var qrAnimationView: LottieAnimationView? = null
    private var cameraScannerLayout: CameraScannerLayout? = null
    private var isProcessing = true

    private val scannerCallback: ScannerCallback = object : ScannerCallback() {
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

        override fun onInitialized() {
            // no implementation
        }

        override fun onScannerUnavailable(errorMessage: ScannerPluginUnavailable) {
            // no implementation
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        cameraWrapper = findViewById(R.id.camera_wrapper)
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

    override fun onStop() {
        super.onStop()
        if (cameraScannerLayout != null) {
            cameraScannerLayout!!.stopScanner()
            cameraScannerLayout!!.releaseCamera()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (requestCode == CAMERA_PERMISSION_RQ) {
            if (grantResults.size != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getBarcodeScanner()
            } else if (grantResults.size != 0) {
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

    private fun startScanner() {
        cameraWrapper!!.removeAllViews()
        cameraWrapper!!.addView(cameraScannerLayout)
        cameraScannerLayout!!.startScanner()
        qrAnimationView!!.visibility = View.VISIBLE
        qrAnimationView!!.playAnimation()
    }

    private fun getBarcodeScanner() {
        val cameraApi: CameraApi = SmartCredentialsCameraFactory.getCameraApi()
        val response =
            cameraApi.getBarcodeScannerView(this, scannerCallback, BarcodeType.BARCODE_2D_QR_CODE)
        if (response.isSuccessful) {
            cameraScannerLayout = response.data
            startScanner()
        } else {
            Toast.makeText(this, R.string.camera_scanner_layout_failed, Toast.LENGTH_SHORT).show()
        }
    }
}