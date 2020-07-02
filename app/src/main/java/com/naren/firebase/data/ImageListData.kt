package com.naren.firebase.data

data class ImageListData(
    val description : String ?= null,
    val images : MutableList<String> ?= null
)