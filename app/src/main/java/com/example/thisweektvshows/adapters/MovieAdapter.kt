package com.example.thisweektvshows.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.thisweektvshows.R
import com.example.thisweektvshows.models.Movie
import com.example.thisweektvshows.ui.MyDiffCallback
import com.example.thisweektvshows.util.Constants.Companion.POSTER_PATH
import java.util.*
import kotlin.collections.ArrayList

class MovieAdapter(val listMovies: List<Movie>): RecyclerView.Adapter<MovieAdapter.MovieViewHolder>(),Filterable {

    private var filteredItemList: List<Movie> = listMovies

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
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(POSTER_PATH + movie.poster_path)
                .into(holder.itemView.findViewById(R.id.movie_image))
            //holder.itemView.findViewById<TextView>(R.id.movie_title).text = movie.name
            setOnItemClickListener {
                onItemClickListener?.let {
                    it(movie)
                }
            }
        }
    }

    private var onItemClickListener: ((Movie) -> Unit)? = null
    fun setOnItemClickListener(listener: (Movie) -> Unit) {
        onItemClickListener = listener
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = mutableListOf<Movie>()
                if (constraint.isNullOrBlank()) {
                    filteredList.addAll(listMovies)
                } else {
                    val filterPattern = constraint.toString().toLowerCase(Locale.getDefault()).trim()
                    for (item in listMovies) {
                        if (item.name.lowercase(Locale.getDefault()).contains(filterPattern)) {
                            filteredList.add(item)
                        }
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredItemList = results?.values as? List<Movie> ?: listMovies
                notifyDataSetChanged()
            }

        }
    }
}