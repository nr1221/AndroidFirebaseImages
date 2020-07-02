package com.naren.firebase.fragments.imagelist

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.naren.firebase.R
import com.naren.firebase.data.ImageListData
import kotlinx.android.synthetic.main.image_recycler_view.view.*

class ImageRecyclerAdapter( val context: Context , val items : MutableList<ImageListData>)
    :RecyclerView.Adapter<ImageRecyclerAdapter.ViewHolder>(){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImageRecyclerAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.image_recycler_view,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ImageRecyclerAdapter.ViewHolder, position: Int) {
        var item = items[position]

        holder.desc.text = item.description
        val adapter = ImageGridAdapter(context , item.images)
        holder.gridView.adapter = adapter
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
            val desc : TextView = view.imageListDescription
            val gridView : RecyclerView = view.imageListGridView
    }

}