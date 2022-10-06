package com.example.camerademo

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    lateinit var img: ImageView
    lateinit var btnTakePic: Button
    lateinit var btnGallery: Button
    private val REQUEST_CODE_FOR_PIC = 1
    private val REQUEST_CODE_FOR_GALLERY = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        img = findViewById(R.id.img)
        btnTakePic=findViewById(R.id.btn_pic)
        btnGallery = findViewById(R.id.btn_gallery)

        btnTakePic.setOnClickListener {
            takeAPic()
        }
        btnGallery.setOnClickListener {
            uploadFromGallery()
        }
    }

    private fun takeAPic(){
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA),
                REQUEST_CODE_FOR_PIC)
        }else{
            val takePicIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(takePicIntent, REQUEST_CODE_FOR_PIC)
        }
    }

    private fun uploadFromGallery(){
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_CODE_FOR_GALLERY)
        }else{
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, REQUEST_CODE_FOR_GALLERY)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode==REQUEST_CODE_FOR_PIC && resultCode == Activity.RESULT_OK){
            val takenImg = data?.extras?.get("data") as Bitmap
            img.setImageBitmap(takenImg)
        }else if (requestCode == REQUEST_CODE_FOR_GALLERY && resultCode == Activity.RESULT_OK){
            val selectedPicUri = data?.data
            img.setImageURI(selectedPicUri)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}