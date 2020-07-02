package com.naren.firebase.fragments.upload

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.naren.firebase.R
import kotlinx.android.synthetic.main.image_layout.view.*

class UploadImageAdapter (private val context: Context, private val items : MutableList<Uri?>)
    : RecyclerView.Adapter<UploadImageAdapter.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UploadImageAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.image_layout,parent,false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: UploadImageAdapter.ViewHolder, position: Int) {
        val item = items[position]
        Glide.with(context).load(item)
            .into(holder.imageView)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val imageView : ImageView = itemView.upFgImageView

    }

}