package com.example.newsapp

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NewsItemClicked {

    private lateinit var mAdapter: NewsListAdapter

    var url:String = "https://newsapi.org/v2/top-headlines?country=in&apiKey=${BuildConfig.ApiKey}"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        supportActionBar?.hide();

        recyclerView.layoutManager = LinearLayoutManager(this)
        fetchData()
        mAdapter = NewsListAdapter(this)
        recyclerView.adapter = mAdapter
    }

    private fun fetchData() {
//        url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=a5c1e7db06554f67b80b3a4f3497a793"
        val jsonObjectRequest = object : JsonObjectRequest(
            Method.GET,
            url,
            null,
            Response.Listener {
                val newsJsonArray = it.getJSONArray("articles")
                val newsArray = ArrayList<News>()
                for (i in 0 until newsJsonArray.length()) {
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = News(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")
                    )
                    newsArray.add(news)
                }

                mAdapter.updateNews(newsArray)
            },
            Response.ErrorListener {
            }

        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }
        }

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClicked(item: News) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.category_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.business -> {
                titletv.text = "Business"
                url = "https://newsapi.org/v2/top-headlines?country=in&category=business&apiKey=${BuildConfig.ApiKey}"
                fetchData()
                true
            }
            R.id.entertainment -> {
                titletv.text = "Entertainment"
                url = "https://newsapi.org/v2/top-headlines?country=in&category=entertainment&apiKey=${BuildConfig.ApiKey}"
                fetchData()
                true
            }
            R.id.general -> {
                titletv.text = "General"
                url = "https://newsapi.org/v2/top-headlines?country=in&category=general&apiKey=${BuildConfig.ApiKey}"
                fetchData()
                true
            }
            R.id.health -> {
                titletv.text = "Health"
                url = "https://newsapi.org/v2/top-headlines?country=in&category=health&apiKey=${BuildConfig.ApiKey}"
                fetchData()
                true
            }
            R.id.science -> {
                titletv.text = "Science"
                url = "https://newsapi.org/v2/top-headlines?country=in&category=science&apiKey=${BuildConfig.ApiKey}"
                fetchData()
                true
            }
            R.id.sports -> {
                titletv.text = "Sports"
                url = "https://newsapi.org/v2/top-headlines?country=in&category=sports&apiKey=${BuildConfig.ApiKey}"
                fetchData()
                true
            }
            R.id.technology -> {
                titletv.text = "Technology"
                url = "https://newsapi.org/v2/top-headlines?country=in&category=technology&apiKey=${BuildConfig.ApiKey}"
                fetchData()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}