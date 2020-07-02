package com.naren.firebase.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.naren.firebase.R


class HomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        view.findViewById<Button>(R.id.uploadImage).setOnClickListener {
            val action  = HomeFragmentDirections.actionUpload()
            findNavController().navigate(action)
        }

        view.findViewById<Button>(R.id.uploadedImage).setOnClickListener {
            val action = HomeFragmentDirections.actionList()
            findNavController().navigate(action)
        }
    }

}