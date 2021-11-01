package com.example.bookapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.squareup.picasso.Picasso

class BookActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)

        val author = intent.getStringArrayListExtra("author")
        val title = intent.getStringExtra("title")
        val subtitle = intent.getStringExtra("subtitle")
        val image = intent.getStringExtra("image")
        val rating = intent.getIntExtra("rating", 0)
        val description = intent.getStringExtra("description")

        val imageView = findViewById<ImageView>(R.id.activityImage)
        val titleView = findViewById<TextView>(R.id.activityTitle)
        val subtitleView = findViewById<TextView>(R.id.activitySubtitle)
        val ratingBar = findViewById<RatingBar>(R.id.activityRating)
        val authorView = findViewById<TextView>(R.id.activityAuthor)
        val descView = findViewById<TextView>(R.id.activityDesc)
        descView.movementMethod = ScrollingMovementMethod()

        Picasso.get()
            .load(image)
            .placeholder(android.R.drawable.picture_frame)
            .fit()
            .into(imageView)

        titleView.text = title
        subtitleView.text = subtitle
        ratingBar.rating = rating.toFloat()
        if (author != null) {
            authorView.text = "by " + author[0]
        }
        descView.text = description
    }
}