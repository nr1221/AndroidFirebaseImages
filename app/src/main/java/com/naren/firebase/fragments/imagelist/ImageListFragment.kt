package com.naren.firebase.fragments.imagelist

import android.media.Image
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.solver.widgets.Snapshot
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.naren.firebase.R
import com.naren.firebase.data.ImageListData
import kotlinx.android.synthetic.main.fragment_image_list.*
import kotlinx.android.synthetic.main.fragment_image_list.view.*
import java.util.*


class ImageListFragment : Fragment() {

    private var firebaseDatabase = FirebaseDatabase.getInstance().getReference("posts")
    lateinit var adapter : ImageRecyclerAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    var list = mutableListOf<ImageListData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view =  inflater.inflate(R.layout.fragment_image_list, container, false)
        linearLayoutManager = LinearLayoutManager(context)
        view.listFgRecyclerView.layoutManager = linearLayoutManager

        firebaseDatabase.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot!!.exists()) {

                    for (snap in snapshot.children) {

                        val data = snap.getValue(ImageListData::class.java)
                        list?.add(data!!)
                    }


                    try {
                        adapter = ImageRecyclerAdapter(context!!, list)
                        listFgRecyclerView?.adapter = adapter
                    }catch (e : NullPointerException){

                    }

                }
            }
        })

        return view


    }




}