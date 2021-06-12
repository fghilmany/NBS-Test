package com.fghilmany.moviedb.ui.popular

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.fghilmany.moviedb.core.data.Resource
import com.fghilmany.moviedb.databinding.FragmentPopularBinding
import kotlinx.android.synthetic.main.fragment_popular.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PopularFragment : Fragment() {

    private var _binding :FragmentPopularBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PopularViewModel by viewModel()

    private val popularAdapter = PopularAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPopularBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getPopularMovie().observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading -> {}
                is Resource.Success -> {
                    popularAdapter.setList(it.data)
                    popularAdapter.notifyDataSetChanged()
                }
                is Resource.Error -> {
                    Toast.makeText(context, "Something Wrong", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.rvPopular.apply {
            setHasFixedSize(true)
            adapter = popularAdapter
        }

        binding.etSearch.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                popularAdapter.filter.filter(s)
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