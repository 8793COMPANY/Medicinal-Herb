package com.corporation8793.medicinal_herb.activity.join

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.corporation8793.medicinal_herb.MySharedPreferences
import com.corporation8793.medicinal_herb.R
import com.corporation8793.medicinal_herb.activity.LoginActivity
import com.corporation8793.medicinal_herb.activity.main.MainActivity2
import com.corporation8793.medicinal_herb.herb_wp.rest.repository.BoardRepository
import com.corporation8793.medicinal_herb.herb_wp.rest.repository.NonceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Credentials
import java.io.File


class ProfileActivity : AppCompatActivity() {

    val PERMISSION_Album = 101
    val REQUEST_STORAGE = 100
    lateinit var user_img : ImageView
    lateinit var user_name : EditText
    lateinit var introduction : EditText
    lateinit var img_uri : Uri
    lateinit var type : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)


        val save_btn = findViewById<LinearLayout>(R.id.save_btn)
        user_img = findViewById<ImageView>(R.id.user_img)
        val input_btn = findViewById<ImageView>(R.id.input_btn)
        user_name = findViewById<EditText>(R.id.name)
        introduction = findViewById<EditText>(R.id.introduction)

        type = intent.getStringExtra("type")!!

        if (type.equals("join")){
            Log.e("first","first")
        }else{
            Log.e("edit","edit")
            user_name.setText(MySharedPreferences(this).getString("user_name","산야초"))
            introduction.setText(MySharedPreferences(this).getString("introdution",""))
            Log.e("id", MySharedPreferences(this).getString("id","girl"))
            Log.e("id", MySharedPreferences(this).getString("pw","1234"))
            Log.e("id", MySharedPreferences(this).getString("img","산야초"))
            Log.e("id", MySharedPreferences(this).getString("introdution","안녕"))
            Glide.with(this).load(MySharedPreferences(this).getString("img","산야초")).into(user_img)

//            GlobalScope.launch(Dispatchers.Default) {
//                getUserInfo(MySharedPreferences(applicationContext).getString("id","girl"), MySharedPreferences(applicationContext).getString("pw","1234"))
//            }


        }

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

            if(type.equals("join")) {
                GlobalScope.launch(Dispatchers.Default) {
                    signUp()
                }
            }else{
//                GlobalScope.launch(Dispatchers.Default) {
//                    updateUser()
//                }
            }




//            finish()
//            RestClient.nonceService.runSignUp("nonce",user_name.text.toString()
//                    ,intent.getStringExtra("id")!!,intent.getStringExtra("pw")!!,introduction.text.toString())
        }


    }

    fun updateUser() {
        val testId = MySharedPreferences(this).getString("id","hello")
        val testPw = MySharedPreferences(this).getString("pw","1234")
        val basicAuth = Credentials.basic(testId, testPw)
        val boardRepository = BoardRepository(basicAuth)


        println("====== UsersRU             ======")
        println("------ isValid             ------")
        try {
            val isValid = boardRepository.validationUser()

            println("retrieve User 닉네임(ID) : ${isValid.second?.name}(${isValid.second?.id})")
            println("retrieve Status : ${isValid.first}\n")
            println("retrieve url : ${isValid.second?.url}\n")
//            if(img_uri != null)
//                boardRepository.updateUser(isValid.second?.id,,introduction.text.toString())


        } catch (e: Exception) {
            Log.e("e", e.toString())

            Log.e("e", e.message.toString())
        }
    }

    fun signUp() {
        try {
            val nonceRepository = NonceRepository()


            println("====== SignUp     ======")
            println("------ getNonce() ------")

            nonceRepository.getNonce()
            println("nonce value : ${nonceRepository.nonce}")

            println("------ runSignUp() ------")




            println(intent.getStringExtra("id")!!)
            println(intent.getStringExtra("pw")!!)
            println(user_name.text.toString())


            nonceRepository.runSignUp(intent.getStringExtra("id")!!, intent.getStringExtra("pw")!!, intent.getStringExtra("id")!!+"@gmail.com", user_name.text.toString())
            Log.e("status",nonceRepository.signUpStatus.status)
            Log.e("error",nonceRepository.signUpStatus.error)
            when (nonceRepository.signUpStatus.status) {
                "ok" -> {
                    if(user_img.drawable != null) {
                        Log.e("img", "not null")
                        uploadProfile()
                    }
//                    Toast.makeText(applicationContext, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                    GlobalScope.launch(Dispatchers.Main) {
                        finish()
                        val intent = Intent(this@ProfileActivity, LoginActivity::class.java)
                        startActivity(intent)
                    }
                }
                "error"->{
                    GlobalScope.launch(Dispatchers.Main) {
                    if (nonceRepository.signUpStatus.error.equals("E-mail address is already in use.")){

                            Toast.makeText(applicationContext,"이미 사용중인 아이디입니다.",Toast.LENGTH_SHORT).show()
                    }else if (nonceRepository.signUpStatus.error.equals("Username already exists.")){

                        Toast.makeText(applicationContext,"이미 사용중인 이름입니다.",Toast.LENGTH_SHORT).show()
                    }


                    }
                }
            }
        }catch (e : Exception){
            Log.e("e",e.toString())
        }


    }




    fun uploadProfile(){
        try {
            val testId = intent.getStringExtra("id")!!
            val testPw = intent.getStringExtra("pw")!!
            val basicAuth = Credentials.basic(testId, testPw)
            val boardRepository = BoardRepository(basicAuth)




//            Log.e("file path",img_uri.toFile().path)
//            Log.e("file path",img_uri.path.toString())
//            var path = img_uri.path.toString()
//            val file = File(path)
            val file = File(getPath(img_uri))

//            val file =
            val responseMedia = boardRepository.uploadMedia(file!!)
//
            println("response Media URL : ${responseMedia.first}")
            println("response Media URL : ${responseMedia.second?.guid?.rendered}")
            println("response Media ID : ${responseMedia.second?.id}")
            println("response Media ID : ${responseMedia.second?.date}")


            var id = boardRepository.validationUser().second?.id
            Log.e("introduction",introduction.text.toString())
            boardRepository.updateUser(id,responseMedia.second?.guid?.rendered,introduction.text.toString())
        }catch (e: Exception){
            Log.e("e",e.toString())
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
                        img_uri = uri
                        user_img.setImageURI(uri)
                    }
                }
            }
        }
    }
}