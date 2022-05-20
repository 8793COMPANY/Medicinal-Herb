package com.corporation8793.medicinal_herb.activity.join

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import com.corporation8793.medicinal_herb.R
import android.widget.LinearLayout
import android.widget.ImageView
import android.widget.EditText
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.corporation8793.medicinal_herb.herb_wp.rest.RestClient

class ProfileActivity : AppCompatActivity() {

    val PERMISSION_Album = 101
    val REQUEST_STORAGE = 100
    lateinit var user_img : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val save_btn = findViewById<LinearLayout>(R.id.save_btn)
        user_img = findViewById<ImageView>(R.id.user_img)
        val input_btn = findViewById<ImageView>(R.id.input_btn)
        val user_name = findViewById<EditText>(R.id.name)
        val introduction = findViewById<EditText>(R.id.introduction)
        user_name.requestFocus()
        user_img.setOnClickListener { v: View? ->
            requirePermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSION_Album)
        }
        input_btn.setOnClickListener { v: View? ->
            if (introduction.isEnabled) {
                introduction.isEnabled = false
            } else {
                introduction.isEnabled = true
                introduction.requestFocus()
            }
        }
        save_btn.setOnClickListener { v: View? ->
            finish()
            RestClient.nonceService.runSignUp("nonce",user_name.text.toString()
                    ,intent.getStringExtra("id")!!,intent.getStringExtra("pw")!!,introduction.text.toString())
        }


    }

    fun requirePermissions(permissions: Array<String>, requestCode: Int) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            permissionGranted(requestCode)
        } else {
            // isAllPermissionsGranted : 권한이 모두 승인 되었는지 여부 저장
            // all 메서드를 사용하면 배열 속에 들어 있는 모든 값을 체크할 수 있다.
            val isAllPermissionsGranted =
                    permissions.all { checkSelfPermission(it) == PackageManager.PERMISSION_GRANTED }
            if (isAllPermissionsGranted) {
                permissionGranted(requestCode)
            } else {
                // 사용자에 권한 승인 요청
                ActivityCompat.requestPermissions(this, permissions, requestCode)
            }
        }
    }





    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
            permissionGranted(requestCode)
        } else {
            permissionDenied(requestCode)
        }
    }

    private fun permissionGranted(requestCode: Int) {
        when (requestCode) {
//            PERMISSION_CAMERA -> openCamera()
            PERMISSION_Album -> openGallery()
        }
    }

    private fun permissionDenied(requestCode: Int) {
        when (requestCode) {
//            PERMISSION_CAMERA -> Toast.makeText(
//                    this,
//                    "카메라 권한을 승인해야 카메라를 사용할 수 있습니다.",
//                    Toast.LENGTH_LONG
//            ).show()

            PERMISSION_Album -> Toast.makeText(
                    this,
                    "저장소 권한을 승인해야 앨범에서 이미지를 불러올 수 있습니다.",
                    Toast.LENGTH_LONG
            ).show()
        }
    }

    fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        startActivityForResult(intent, REQUEST_STORAGE)

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
//                REQUEST_CAMERA -> {
//                    realUri?.let { uri ->
//                        imageView.setImageURI(uri)
//                    }
//                }
                REQUEST_STORAGE -> {
                    data?.data?.let { uri ->

                        user_img.setImageURI(uri)
                    }
                }
            }
        }
    }
}