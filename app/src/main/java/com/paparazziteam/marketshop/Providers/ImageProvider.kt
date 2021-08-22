package com.paparazziteam.marketshop.Providers

import android.content.Context
import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.paparazziteam.marketshop.Utils.CompressorBitmapImage
import java.io.File
import java.util.*

class ImageProvider {

    var mStorage:StorageReference
    var mFirebaseStorage:FirebaseStorage

    init {

        mFirebaseStorage = FirebaseStorage.getInstance()
        mStorage = mFirebaseStorage.getReference()
    }

    //method to
    fun save(context: Context, file: File): UploadTask
    {
        var imageByte = CompressorBitmapImage.getImage(context, file.path,500,500)
        var storage: StorageReference = mStorage.child(Date().toString() + ".jpg")

        mStorage = storage
        var task:UploadTask =storage.putBytes(imageByte!!)

        return task
    }

    fun getDownloadUri() : Task<Uri>
    {
        return mStorage.downloadUrl
    }
}