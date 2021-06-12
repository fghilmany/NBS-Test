package com.fghilmany.moviedb.ui.favorite

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fghilmany.moviedb.databinding.FragmentFavoriteBinding
import kotlinx.android.synthetic.main.fragment_popular.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoriteViewModel by viewModel()

    private lateinit var favoriteAdapter : FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteAdapter = FavoriteAdapter()

        viewModel.getFavorite().observe(viewLifecycleOwner){
            favoriteAdapter.setFavorite(it)
            favoriteAdapter.notifyDataSetChanged()
        }


        binding.rvFavorite.apply {
            setHasFixedSize(true)
            adapter = favoriteAdapter
        }

        binding.etSearch.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                favoriteAdapter.filter.filter(s)
                if (s.isNullOrBlank()){
                    group_search.visibility = View.GONE
                }else{
                    group_search.visibility = View.VISIBLE
                }
                tv_search_title.text = s
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

    }

}