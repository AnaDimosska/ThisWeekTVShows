package com.example.thisweektvshows.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.bumptech.glide.Glide
import com.example.thisweektvshows.R
import com.example.thisweektvshows.models.Movie
import com.example.thisweektvshows.repo.MoviesRepository
import com.example.thisweektvshows.ui.MovieDetailsActivity
import com.example.thisweektvshows.ui.MyDiffCallback
import com.example.thisweektvshows.util.Constants.Companion.POSTER_PATH
import java.util.*

class MovieAdapter(val listMovies: List<Movie>) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>(), Filterable {

    private var filteredItemList: List<Movie> = listMovies
    private var flag = false

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallback = MyDiffCallback()

    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.movie_item,
                parent,
                false
            )
        ).apply {
            itemView.setOnClickListener {  }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = differ.currentList[position]
        val posterPath = POSTER_PATH + movie.poster_path
        holder.itemView.apply {
            Glide.with(this).load(posterPath)
                .into(holder.itemView.findViewById(R.id.movie_image))
            holder.itemView.findViewById<ImageView>(R.id.favourite_btn).setOnClickListener {
                if (!flag) {
                    flag = true
                    holder.itemView.findViewById<ImageView>(R.id.favourite_btn)
                        .setImageResource(R.drawable.baseline_favorite_24)
                } else {
                    flag = false
                    holder.itemView.findViewById<ImageView>(R.id.favourite_btn)
                        .setImageResource(R.drawable.baseline_favorite_border_24)
                }
            }
           /* holder.itemView.setOnClickListener {
                val intent = Intent(it.context, MovieDetailsActivity::class.java)
                it.context.startActivity(intent)
                intent.apply {
                    putExtra("movie_image", posterPath)
                    putExtra("movie_name", movie.name)
                    putExtra("movie_desc", movie.overview)

                    it.context.startActivity(intent)
                }
            }*/
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = mutableListOf<Movie>()
                if (constraint.isNullOrBlank()) {
                    filteredList.addAll(listMovies)
                } else {
                    val filterPattern =
                        constraint.toString().toLowerCase(Locale.getDefault()).trim()
                    for (item in listMovies) {
                        if (item.title.lowercase(Locale.getDefault()).contains(filterPattern)) {
                            filteredList.add(item)
                        }
                    }
                }
                val filterResults = FilterResults()
                filterResults.count = filteredList.size
                filterResults.values = filteredList
                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredItemList = results?.values as? List<Movie> ?: listMovies
                notifyDataSetChanged()
            }

        }
    }
}