package com.fghilmany.moviedb.ui.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fghilmany.moviedb.BuildConfig
import com.fghilmany.moviedb.R
import com.fghilmany.moviedb.core.domain.model.ComingSoonMovie
import com.fghilmany.moviedb.core.domain.model.FavoriteMovie
import com.fghilmany.moviedb.core.domain.model.PopularMovie
import com.fghilmany.moviedb.core.utils.*
import com.fghilmany.moviedb.databinding.ItemFavoriteBinding
import com.fghilmany.moviedb.databinding.ItemMovieHomeBinding
import com.fghilmany.moviedb.databinding.ItemPopularBinding
import com.fghilmany.moviedb.ui.detail.DetailActivity
import java.util.*
import kotlin.collections.ArrayList

class FavoriteAdapter: RecyclerView.Adapter<FavoriteAdapter.ViewHolder>(), Filterable {

    private val listFavoriteImmutable = ArrayList<FavoriteMovie>()
    private var listFavorite = ArrayList<FavoriteMovie>()

    fun setFavorite(list: List<FavoriteMovie>?){
        if (list.isNullOrEmpty()) return
        listFavorite.clear()
        listFavorite.addAll(list)
    }

    init {
        listFavorite = listFavoriteImmutable
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favorite, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = listFavorite[position]
        holder.bind(result)
    }

    override fun getItemCount(): Int = listFavorite.size

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = ItemFavoriteBinding.bind(view)
        fun bind(result: FavoriteMovie) {
            with(binding){
                result.apply {
                    tvTitle.text = title
                    tvOriginalTitle.text = originalTitle
                    tvOverview.text = overview
                    Glide.with(itemView.context)
                        .load(BuildConfig.IMAGE_URL+ IMAGE_ORIGINAL_SIZE+backdropPath)
                        .placeholder(R.drawable.ic_loading)
                        .into(ivBackdrop)
                }
                itemView.setOnClickListener {
                    Intent(itemView.context, DetailActivity::class.java).apply {
                        putExtra(MOVIE_ID, result.id)
                        putExtra(MOVIE_FAVORITE, result.isFavorite)
                        putExtra(MOVIE_POPULAR, FAVORITE)
                        itemView.context.startActivity(this)
                    }
                }
            }
        }

    }

    override fun getFilter(): Filter =
        object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                listFavorite = if (charSearch.isEmpty()){
                    listFavoriteImmutable
                }else{
                    val result = ArrayList<FavoriteMovie>()
                    if (charSearch == ""){
                        listFavorite = listFavoriteImmutable
                    }else{
                        listFavoriteImmutable.forEach{ movie ->
                            if (movie.title.lowercase(Locale.ROOT).contains(charSearch.lowercase(Locale.ROOT))){
                                result.add(movie)
                            }
                        }
                    }

                    result
                }
                val filterResult = FilterResults()
                filterResult.values = listFavorite
                return filterResult
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                listFavorite = results?.values as ArrayList<FavoriteMovie>
                notifyDataSetChanged()
            }

        }

}