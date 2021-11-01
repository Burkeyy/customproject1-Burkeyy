package com.example.bookapp

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.Volley
import org.json.JSONException
import android.widget.Toast
import com.android.volley.toolbox.JsonObjectRequest


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val searchButton = findViewById<ImageView>(R.id.BtnSearch)
        val textView = findViewById<TextView>(R.id.SearchBooks)


        searchButton.setOnClickListener {
            if (textView.text.toString().isEmpty()) textView.error = "Please enter a query"
            else {
                searchBooks(textView.text.toString())
            }
        }
    }
    fun searchBooks (query: String){
        val bookList = arrayListOf<Book>()
        val apiKey = "AIzaSyByENWBv0DIG_84WsedCEjINQTH7Oa7EuQ"
        val baseURL = "https://www.googleapis.com/books/v1/volumes?"
        val searchParam = "q"
        val maxResults = "maxResults"
        val searchKey = "key"
        val printType = "printType"
        val orderBy = "orderBy"
        val uri = Uri.parse(baseURL).buildUpon()
            .appendQueryParameter(searchParam, query)
            .appendQueryParameter(maxResults, "40")
            .appendQueryParameter(printType, "books")
            .appendQueryParameter(orderBy, "relevance")
            .appendQueryParameter(searchKey, apiKey)
            .build()

        val queue = Volley.newRequestQueue(this)
        val booksObjRequest = JsonObjectRequest(
            Request.Method.GET, uri.toString(), null,
            { response ->
                try {
                    val itemsArray = response.getJSONArray("items")
                    Log.i("array", itemsArray.toString())
                    for (i in 0 until itemsArray.length()) {
                        val itemsObj = itemsArray.getJSONObject(i)
                        val volumeObj = itemsObj.getJSONObject("volumeInfo")
                        val description = volumeObj.optString("description")
                        val title = volumeObj.getString("title")
                        val subtitle = volumeObj.optString("subtitle")
                        val authorsArray = volumeObj.optJSONArray("authors")
                        val authorsArrayList = ArrayList<String>()

                        if (authorsArray != null) {
                            for (j in 0 until authorsArray.length()) {
                                authorsArrayList.add(authorsArray.getString(j))
                            }
                        }
                        else {
                            authorsArrayList.add("N/A")
                        }
                        val avgRate = volumeObj.optInt("averageRating")
                        val images = volumeObj.optJSONObject("imageLinks")
                        val thumbnail = images?.optString("thumbnail")
                        val rateCount = volumeObj.optInt("ratingsCount")
                        Log.i("thumbnail", thumbnail.toString())
                        val bookInfo = Book(
                            title,
                            authorsArrayList,
                            avgRate,
                            rateCount,
                            thumbnail,
                            description,
                            subtitle
                        )
                        bookList.add(bookInfo)
                        val recyclerView = findViewById<RecyclerView>(R.id.bookList)
                        val adapter = rvAdapter(bookList, this)
                        recyclerView.layoutManager = LinearLayoutManager(this)
                        recyclerView.adapter = adapter
                    }
                } catch (ex: JSONException) {
                    ex.printStackTrace()
                    Toast.makeText(this, "No Data Found$ex", Toast.LENGTH_SHORT).show()
                }
            }) {}
        queue.add(booksObjRequest)
    }
}
