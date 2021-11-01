package com.example.bookapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class rvAdapter(private val data: ArrayList<Book>, val context: Context) : RecyclerView.Adapter<rvAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): rvAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.book_layout, parent, false) as View
        return ViewHolder(view)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: rvAdapter.ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    inner class ViewHolder(private val v: View) : RecyclerView.ViewHolder(v) {
        private var bookTitle: TextView = v.findViewById(R.id.bookTitle)
        private var bookAuthor: TextView = v.findViewById(R.id.bookAuthor)
        private var bookRating = v.findViewById<RatingBar>(R.id.activityRating)
        private var bookImage: ImageView = v.findViewById(R.id.bookImage)
        private var bookNumRatings: TextView = v.findViewById(R.id.numRatings)

        fun bind(item: Book) {
            Picasso.get()
                .load(item.thumbNail)
                .placeholder(android.R.drawable.picture_frame)
                .fit()
                .into(bookImage)

            v.setOnClickListener{
                val i = Intent(it.context, BookActivity::class.java)
                i.putExtra("image", item.thumbNail)
                i.putExtra("title", item.title)
                i.putExtra("subtitle", item.subtitle)
                i.putExtra("rating", item.rating)
                i.putExtra("description", item.description)
                i.putExtra("author", item.author)
                startActivity(it.context, i, null)
            }
            bookTitle.text = item.title
            bookAuthor.text = "by "+ item.author[0]
            if (item.rating != null)
                bookRating.rating = item.rating.toFloat()
            if (item.numRatings != null)
                bookNumRatings.text = "No. of Ratings: " + item.numRatings.toString()
            }
        }
    }
