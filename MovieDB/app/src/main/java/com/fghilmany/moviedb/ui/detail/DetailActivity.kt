package com.fghilmany.moviedb.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.fghilmany.moviedb.BuildConfig
import com.fghilmany.moviedb.R
import com.fghilmany.moviedb.core.data.Resource
import com.fghilmany.moviedb.core.utils.*
import com.fghilmany.moviedb.databinding.ActivityDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailBinding

    private val viewModel: DetailViewModel by viewModel()

    private var isFavorite = false
    private var isPopular: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras
        if (extras != null){
            viewModel.setId(extras.getInt(MOVIE_ID).toString())
            isFavorite = extras.getBoolean(MOVIE_FAVORITE)
            isPopular = extras.getString(MOVIE_POPULAR)
        }


        binding.apply{

            if (isFavorite){
                btAddFavorite.text = resources.getString(R.string.remove_favorite)
                btAddFavorite.icon = ContextCompat.getDrawable(this@DetailActivity, R.drawable.ic_clear)
            }

            viewModel.getDetail()?.observe(this@DetailActivity){
                when(it){
                    is Resource.Loading ->{}
                    is Resource.Success ->{
                        it.data?.apply {
                            tvDesc.text = overview
                            tvTitle.text = title
                            tvDuration.text = runtime.toString()
                            Glide.with(this@DetailActivity)
                                .load(BuildConfig.IMAGE_URL + IMAGE_ORIGINAL_SIZE + posterPath)
                                .into(ivPoster)

                            initToolbar(title)
                            viewModel.setMovieFavorite(this)
                        }
                    }
                    is Resource.Error ->{}
                }
            }

            btAddFavorite.setOnClickListener {
                isFavorite = !isFavorite
                if (isFavorite){
                    btAddFavorite.text = resources.getString(R.string.remove_favorite)
                    btAddFavorite.icon = ContextCompat.getDrawable(this@DetailActivity, R.drawable.ic_clear)
                }else{
                    btAddFavorite.text = resources.getString(R.string.add_to_favorite)
                    btAddFavorite.icon = ContextCompat.getDrawable(this@DetailActivity, R.drawable.ic_add)
                }
                viewModel.setFavorite(isFavorite)

                when(isPopular){
                    POPULAR -> {
                        viewModel.setFavorite()
                        viewModel.setFavoritePopular()
                    }
                    COMING_SON -> {
                        viewModel.setFavorite()
                        viewModel.setFavoriteComingSoon()
                    }
                }
            }
        }
    }

    private fun initToolbar(movieTitle: String?) {
        with(binding){
            collapsingToolbar.apply {
                title = movieTitle
                setCollapsedTitleTextColor(ContextCompat.getColor(context, R.color.white))
                setExpandedTitleColor(ContextCompat.getColor(context, android.R.color.transparent))
                setBackgroundColor(ContextCompat.getColor(context, R.color.dark_background))

            }

            setSupportActionBar(toolbar)
            toolbar.setNavigationIcon(R.drawable.ic_back)
            toolbar.setNavigationOnClickListener {
                finish()
            }

        }
    }
}