package com.fghilmany.moviedb.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fghilmany.moviedb.BuildConfig
import com.fghilmany.moviedb.R
import com.fghilmany.moviedb.core.domain.model.ComingSoonMovie
import com.fghilmany.moviedb.core.utils.*
import com.fghilmany.moviedb.databinding.ItemMovieHomeBinding
import com.fghilmany.moviedb.ui.detail.DetailActivity

class ComingSoonrAdapter: RecyclerView.Adapter<ComingSoonrAdapter.ViewHolder>() {

    private val listComingSoon = ArrayList<ComingSoonMovie>()

    fun setList(list: List<ComingSoonMovie>?){
        if (list.isNullOrEmpty()) return
        listComingSoon.clear()
        listComingSoon.addAll(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie_home, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = listComingSoon[position]
        holder.bind(result)
    }

    override fun getItemCount(): Int = listComingSoon.size

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = ItemMovieHomeBinding.bind(view)
        fun bind(result: ComingSoonMovie) {
            with(binding){
                result.apply {
                    Glide.with(itemView.context)
                        .load(BuildConfig.IMAGE_URL+ IMAGE_ORIGINAL_SIZE+posterPath)
                        .placeholder(R.drawable.ic_loading)
                        .into(ivPoster)
                }

                itemView.setOnClickListener {
                    Intent(itemView.context, DetailActivity::class.java).apply {
                        putExtra(MOVIE_ID, result.id)
                        putExtra(MOVIE_FAVORITE, result.isFavorite)
                        putExtra(MOVIE_POPULAR, COMING_SON)
                        itemView.context.startActivity(this)
                    }
                }
            }
        }

    }

}