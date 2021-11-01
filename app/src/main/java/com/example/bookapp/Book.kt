package com.example.bookapp

data class Book(
    val title: String,
    val author: ArrayList<String>,
    val rating: Int?,
    val numRatings: Int?,
    val thumbNail: String?,
    val description: String?,
    val subtitle: String?
)
