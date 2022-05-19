package com.corporation8793.medicinal_herb.activity.main

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.PaintDrawable
import android.opengl.Visibility
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.corporation8793.medicinal_herb.dto.ActionBar
import com.corporation8793.medicinal_herb.R
import com.corporation8793.medicinal_herb.databinding.ActivityQnaBinding
import com.corporation8793.medicinal_herb.dto.EventItem
import com.corporation8793.medicinal_herb.herb_wp.rest.RestClient
import com.corporation8793.medicinal_herb.herb_wp.rest.data.board.Post
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.logging.Logger

class QnaActivity : AppCompatActivity() {
    lateinit var binding : ActivityQnaBinding
    lateinit var dialog:Dialog
    lateinit var register_dialog:Dialog

    val PERMISSION_Album = 101
    val REQUEST_STORAGE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()

        binding.qnaRegistrationBtn.setOnClickListener {
            showOkDialog()
            Log.e("qna","click")
        }

//        binding.photoRegistration.setOnClickListener {
//            showOkDialog()
//        }



    }

    fun init(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_qna)
        binding.setActionBar(ActionBar("묻고 답하기", R.color.black))

        binding.actionBar.backHome.setOnClickListener {
            finish()
            var intent : Intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }

        binding.photoRegistration.setOnClickListener {
            requirePermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSION_Album)
        }
    }

    fun showDialog(){
        val layoutInflater = LayoutInflater.from(this)
        val view = layoutInflater.inflate(R.layout.dialog_photo_select, null)




        val alertDialog = AlertDialog.Builder(this)
                .setView(view).create()



        alertDialog.show()

        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)


        val mLayoutParams = WindowManager.LayoutParams()
        mLayoutParams.width = (size.x * 0.7f).toInt()
        mLayoutParams.height = (size.y * 0.4f).toInt()

        alertDialog.window?.attributes = mLayoutParams

    }

    fun showOkDialog(){
        val layoutInflater = LayoutInflater.from(this)
//        val view = layoutInflater.inflate(R.layout.dialog_posting_check, null)
//
//
//
//        val alertDialog = AlertDialog.Builder(this)
//                .setView(view).create()


        val dlgView = View.inflate(this, R.layout.dialog_photo_select, null)
        val dlg = AlertDialog.Builder(this).create()
        dlg.setView(dlgView)
        dlg.window?.setBackgroundDrawable(PaintDrawable(Color.WHITE))
        dlg.show()





//        val display = windowManager.defaultDisplay
//        val size = Point()
//        display.getSize(size)
//
//        // Generate custom width and height and
//        // add to the dialog attributes
//        // we multiplied the width and height by 0.5,
//        // meaning reducing the size to 50%
//        val mLayoutParams = WindowManager.LayoutParams()
//        mLayoutParams.width = (size.x * 0.7f).toInt()
//        mLayoutParams.height = (size.y * 0.3f).toInt()
//
//        dlg.window?.attributes = mLayoutParams


    }

    fun dialogTest(){
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_chitchat_select)
        dialog.window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.show()

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
                        if (binding.photoRegistrationIcon.visibility != View.INVISIBLE){
                            binding.photoRegistrationIcon.visibility = View.INVISIBLE
                            binding.photoRegistrationText.visibility = View.INVISIBLE
                        }
                        binding.photoRegistration.setImageURI(uri)
                    }
                }
            }
        }
    }
}