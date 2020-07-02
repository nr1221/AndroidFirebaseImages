package com.naren.firebase.fragments.imagelist

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.naren.firebase.R
import kotlinx.android.synthetic.main.image_list_grid.view.*

class ImageGridAdapter(private val context: Context ,private val items : MutableList<String>?)
    :RecyclerView.Adapter<ImageGridAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageGridAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.image_list_grid,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
       return items!!.size
    }

    override fun onBindViewHolder(holder: ImageGridAdapter.ViewHolder, position: Int) {

       Glide.with(context).load(items?.get(position)).into(holder.imageView)
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val imageView : ImageView = view.gridlayoutImageView
    }
}