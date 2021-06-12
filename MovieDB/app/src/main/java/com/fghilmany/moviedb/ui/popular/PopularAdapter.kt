package com.fghilmany.moviedb.ui.popular

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
import com.fghilmany.moviedb.core.domain.model.PopularMovie
import com.fghilmany.moviedb.core.utils.IMAGE_ORIGINAL_SIZE
import com.fghilmany.moviedb.core.utils.MOVIE_FAVORITE
import com.fghilmany.moviedb.core.utils.MOVIE_ID
import com.fghilmany.moviedb.core.utils.MOVIE_POPULAR
import com.fghilmany.moviedb.databinding.ItemMovieHomeBinding
import com.fghilmany.moviedb.databinding.ItemPopularBinding
import com.fghilmany.moviedb.ui.detail.DetailActivity
import java.util.*
import kotlin.collections.ArrayList

class PopularAdapter: RecyclerView.Adapter<PopularAdapter.ViewHolder>(), Filterable {

    private val listPopularImmutable = ArrayList<PopularMovie>()
    private var listPopular = ArrayList<PopularMovie>()

    fun setList(list: List<PopularMovie>?){
        if (list.isNullOrEmpty()) return
        listPopularImmutable.clear()
        listPopularImmutable.addAll(list)
    }

    init {
        listPopular = listPopularImmutable
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_popular, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = listPopular[position]
        holder.bind(result)
    }

    override fun getItemCount(): Int = listPopular.size

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = ItemPopularBinding.bind(view)
        fun bind(result: PopularMovie) {
            with(binding){
                result.apply {
                    tvTitle.text = title
                    tvOverview.text = overview
                    Glide.with(itemView.context)
                        .load(BuildConfig.IMAGE_URL+ IMAGE_ORIGINAL_SIZE+posterPath)
                        .placeholder(R.drawable.ic_loading)
                        .into(ivPoster)
                }
                itemView.setOnClickListener {
                    Intent(itemView.context, DetailActivity::class.java).apply {
                        putExtra(MOVIE_ID, result.id)
                        putExtra(MOVIE_FAVORITE, result.isFavorite)
                        putExtra(MOVIE_POPULAR, MOVIE_POPULAR)
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
                listPopular = if (charSearch.isEmpty()){
                    listPopularImmutable
                }else{
                    val result = ArrayList<PopularMovie>()
                    if (charSearch == ""){
                        listPopular = listPopularImmutable
                    }else{
                        listPopularImmutable.forEach{ movie ->
                            if (movie.title.toString().lowercase(Locale.ROOT).contains(charSearch.lowercase(Locale.ROOT))){
                                result.add(movie)
                            }
                        }
                    }

                    result
                }
                val filterResult = FilterResults()
                filterResult.values = listPopular
                return filterResult
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                listPopular = results?.values as ArrayList<PopularMovie>
                notifyDataSetChanged()
            }

        }

}