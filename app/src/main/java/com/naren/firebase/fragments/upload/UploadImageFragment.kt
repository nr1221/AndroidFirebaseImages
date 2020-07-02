package com.naren.firebase.fragments.upload

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.VisibleForTesting
import androidx.recyclerview.widget.GridLayoutManager
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.naren.firebase.R
import com.naren.firebase.data.Upload
import kotlinx.android.synthetic.main.fragment_upload_image.*
import kotlinx.android.synthetic.main.fragment_upload_image.view.*
import kotlinx.android.synthetic.main.fragment_upload_image.view.UpFgPickImage
import java.util.*


class UploadImageFragment : Fragment() {


    lateinit var adapter: UploadImageAdapter
    var gridLayoutManager = GridLayoutManager(context , 3)
    var imageList = mutableListOf<Uri?>()
    var urlList = mutableListOf<String>()
    private var storageReference : StorageReference ?= null
    private var firebaseDatabase = FirebaseDatabase.getInstance().reference




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        storageReference = FirebaseStorage.getInstance().reference
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view =  inflater.inflate(R.layout.fragment_upload_image, container, false)
        view.upFgRecyclerView.layoutManager = gridLayoutManager
        adapter = UploadImageAdapter(requireContext() , imageList)
        view.upFgRecyclerView.adapter = adapter

        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



        UpFgPickImage.setOnClickListener {
                ImagePicker.with(this)
                    .compress(1024)
                    .maxResultSize(1080,1080)
                    .start()
        }

        UpFgBtnUpload.setOnClickListener {

            if (checkFields()) {
                uploadPost()
                upFgProgressBar.visibility = View.VISIBLE
            } else
                Toast.makeText(context, "Please fill all the fields..", Toast.LENGTH_LONG).show()
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK){

                val fileUri : Uri? = data?.data
                imageList.add(fileUri)

                upFgProgressBar.visibility = View.VISIBLE
                uploadImages(fileUri)
                upFgRecyclerView.visibility = View.VISIBLE
                adapter.notifyDataSetChanged()
        }
        else if (resultCode == ImagePicker.RESULT_ERROR){
            Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        }
        else{

            Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
        }

    }


    private fun uploadPost(){
        val upload = Upload(etDescription.text.toString() , urlList)
        firebaseDatabase.child("posts").child(UUID.randomUUID().toString()).setValue(upload)
            .addOnSuccessListener {
                upFgProgressBar.visibility = View.GONE
                uiRefresh()
            }.addOnFailureListener {
                Toast.makeText(context,"Something went wrong..",Toast.LENGTH_LONG).show()

            }

    }


    private fun uploadImages(filePath : Uri?){

        if(filePath!= null){

            val ref = storageReference?.child("uploads/"+UUID.randomUUID().toString())
            val uploadTask = ref?.putFile(filePath)

            val urlTask = uploadTask?.continueWithTask(Continuation <UploadTask.TaskSnapshot , Task<Uri>>{task ->
                if (!task.isSuccessful){
                    task.exception?.let {
                        throw it

                    }
                    Log.d("result","try again!")
                }
                return@Continuation ref.downloadUrl
            })?.addOnCompleteListener { task ->
                if (task.isSuccessful){
                    val downloadUrl = task.result.toString()
                    urlList.add(downloadUrl)
                    upFgProgressBar.visibility = View.GONE
                    Log.d("result","Task successful!!")
                }

            }?.addOnFailureListener {

            }
        }else{
            Toast.makeText(context, "Please Upload an Image", Toast.LENGTH_SHORT).show()
        }
    }


    private fun uiRefresh(){
        imageList.clear()
        upFgRecyclerView.visibility = View.GONE
        etDescription.setText("")
    }

    private fun checkFields() : Boolean{
        return imageList.isNotEmpty() && !etDescription.text.isNullOrEmpty()
    }


}
