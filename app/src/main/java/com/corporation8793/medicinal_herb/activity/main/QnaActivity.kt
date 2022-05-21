package com.corporation8793.medicinal_herb.activity.main

import android.Manifest
import android.app.Dialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.corporation8793.medicinal_herb.MySharedPreferences
import com.corporation8793.medicinal_herb.dto.ActionBar
import com.corporation8793.medicinal_herb.R
import com.corporation8793.medicinal_herb.databinding.ActivityQnaBinding
import com.corporation8793.medicinal_herb.herb_wp.rest.RestClient
import com.corporation8793.medicinal_herb.herb_wp.rest.repository.BoardRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Credentials
import java.io.File
import java.text.SimpleDateFormat

class QnaActivity : AppCompatActivity() {
    lateinit var binding : ActivityQnaBinding
    lateinit var dialog:Dialog
    lateinit var register_dialog:Dialog

    val PERMISSION_Album = 101
    val PERMISSION_CAMERA = 102;
    val REQUEST_STORAGE = 100
    val REQUEST_CAMERA = 1
    lateinit var img_uri : Uri
    lateinit var prefs : MySharedPreferences
    lateinit var category : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        prefs = MySharedPreferences(applicationContext)
        binding.qnaRegistrationBtn.setOnClickListener {
            createPost(binding.photoRegistrationText.text.toString())
            showDialog(R.layout.dialog_posting_check,0.6f,0.3f)
            Log.e("qna","click")
        }

//        binding.photoRegistration.setOnClickListener {
//            showOkDialog()
//        }



    }

    val REQUEST_IMAGE_CAPTURE = 1



    fun init(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_qna)
        binding.setActionBar(ActionBar(intent.getStringExtra("category"), R.color.black))
        category = if(intent.getStringExtra("category")!!.equals("묻고 답하기")) RestClient.CATEGORY_QNA else RestClient.CATEGORY_CHITCHAT

        binding.actionBar.backHome.setOnClickListener {
            finish()
            var intent : Intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }
        binding.photoRegistration.clipToOutline = true

        binding.photoRegistration.setOnClickListener {
            showDialog(R.layout.dialog_photo_select,0.65f,0.3f)

        }
    }

    fun getPath(uri: Uri?): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = managedQuery(uri, projection, null, null, null)
        startManagingCursor(cursor)
        val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        return cursor.getString(column_index)
    }

    fun createPost(content:String) {
        GlobalScope.launch(Dispatchers.Default) {
            val testId = prefs.getString("id","hello")
            val testPw = prefs.getString("pw","1234")
            val basicAuth = Credentials.basic(testId, testPw)
            val boardRepository = BoardRepository(basicAuth)
            var htmlContent = ""


            println("====== CreatePostWithMedia ======")



            println("------ Upload              ------")
            // test image (Lenna.png)
            var responseMediaId = "0"
            if (binding.photoRegistration.drawable != null) {
                val file = File(getPath(img_uri))
                val responseMedia = boardRepository.uploadMedia(file)
                responseMediaId = responseMedia.second?.id!!
                println("response Media URL : ${responseMedia.second?.guid?.rendered}")
                println("response Media ID : ${responseMedia.second?.id}")
                htmlContent += "<p><img src=\"${responseMedia.second?.guid?.rendered}\"></p>"
            }



//        println("------ Create              ------")
            var responseCode = boardRepository.createPost(
                    title = "CreatePostWithMedia",
                    content = content,
                    categories = category,
                    featured_media = responseMediaId
            )

            println("response Code : $responseCode\n")

        }
    }




    private fun showDialog(layout : Int, width :Float, height : Float) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialog.setCancelable(false)
        dialog.setContentView(layout)
        dialog.show()

//        dialog.findViewById<TextView>(R.id.qna).setOnClickListener{
//            dialog.dismiss()
//        }
//
        if (layout == R.layout.dialog_posting_check){
            dialog.findViewById<Button>(R.id.ok_btn).setOnClickListener{
               dialog.dismiss()
                finish()
            }
        }else{
            dialog.findViewById<ConstraintLayout>(R.id.album_area).setOnClickListener{
                requirePermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSION_Album)
                dialog.dismiss()

            }

            dialog.findViewById<ConstraintLayout>(R.id.camera_area).setOnClickListener{
                requirePermissions(arrayOf(Manifest.permission.CAMERA), PERMISSION_CAMERA)
                dialog.dismiss()

            }

            dialog.findViewById<ConstraintLayout>(R.id.file_area).setOnClickListener{
                dialog.dismiss()

            }
        }




        dialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));

//        dialog.getWindow()!!.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)

        // Generate custom width and height and
        // add to the dialog attributes
        // we multiplied the width and height by 0.5,
        // meaning reducing the size to 50%
        val mLayoutParams = WindowManager.LayoutParams()
        mLayoutParams.width = (size.x * width).toInt()
        mLayoutParams.height = (size.y * height).toInt()

        dialog.window?.attributes = mLayoutParams

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
            PERMISSION_CAMERA -> openCamera()
            PERMISSION_Album -> openGallery()
        }
    }

    private fun permissionDenied(requestCode: Int) {
        when (requestCode) {
            PERMISSION_CAMERA -> Toast.makeText(
                    this,
                    "카메라 권한을 승인해야 카메라를 사용할 수 있습니다.",
                    Toast.LENGTH_LONG
            ).show()

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

    private fun openCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                REQUEST_CAMERA -> {
                    if (binding.photoRegistrationIcon.visibility != View.INVISIBLE){
                        binding.photoRegistrationIcon.visibility = View.INVISIBLE
                        binding.photoRegistrationText.visibility = View.INVISIBLE
                    }
                    val imageBitmap = data!!.extras!!.get("data") as Bitmap
                    binding.photoRegistration.setImageBitmap(imageBitmap)
                }
                REQUEST_STORAGE -> {
                    data?.data?.let { uri ->
                        if (binding.photoRegistrationIcon.visibility != View.INVISIBLE){
                            binding.photoRegistrationIcon.visibility = View.INVISIBLE
                            binding.photoRegistrationText.visibility = View.INVISIBLE
                        }
                        img_uri = uri
                        binding.photoRegistration.setImageURI(uri)
                    }
                }
            }
        }
    }


}