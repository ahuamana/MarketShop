package com.paparazziteam.marketshop.providers

import android.content.Context
import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.safetynet.SafetyNetAppCheckProviderFactory
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.paparazziteam.marketshop.utils.CompressorBitmapImage
import java.io.File
import java.util.*

class ImageProvider {

    var mStorage:StorageReference
    var mFirebaseStorage:FirebaseStorage
    var mFirebaseAppCheck:FirebaseAppCheck

    init {

        mFirebaseStorage = FirebaseStorage.getInstance()
        mStorage = mFirebaseStorage.getReference()
        mFirebaseAppCheck = FirebaseAppCheck.getInstance()
        mFirebaseAppCheck.installAppCheckProviderFactory(SafetyNetAppCheckProviderFactory.getInstance())

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