package com.example.championsapplication.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.championsapplication.R
import com.example.championsapplication.databinding.FragmentChampionsListBinding
import com.example.championsapplication.domain.model.Result
import com.example.championsapplication.presentation.adapter.ChampionsRecyclerViewAdapter
import com.example.championsapplication.presentation.viewmodels.ChampionsListViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * A fragment representing a list of Items.
 */
@AndroidEntryPoint
class ChampionsListFragment : Fragment() {
    private val championsViewModel: ChampionsListViewModel by viewModels()
    lateinit var championsAdapter: ChampionsRecyclerViewAdapter
    private lateinit var binding: FragmentChampionsListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentChampionsListBinding.inflate(
                inflater,
                container,
                false
            )
        // Set the adapter
        setChampionsAdapter()
        championsViewModel.getAllChampions()
        observeChampionsData()
        return binding.root
    }

    private fun observeChampionsData() {
        championsViewModel.champions.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Success -> {
                    result.data?.let { champions ->
                        championsAdapter.differ.submitList(champions)
                        binding.pbProgress.visibility = View.INVISIBLE
                    }
                }
                is Result.Loading -> {
                    binding.pbProgress.visibility = View.VISIBLE
                }
                is Result.Error -> {
                    binding.pbProgress.visibility = View.INVISIBLE
                    Toast.makeText(context, getString(R.string.error_occurred), Toast.LENGTH_LONG)
                        .show()
                }
            }
        })
    }

    private fun setChampionsAdapter() {
        championsAdapter = ChampionsRecyclerViewAdapter()
        binding.rvChampions.apply {
            layoutManager = LinearLayoutManager(context)
            championsAdapter.setChampionItemClickListener { selectedChampionId ->
                goToChampionDetailsFragment(selectedChampionId)
            }
            adapter = championsAdapter
        }
    }

    private fun goToChampionDetailsFragment(championId: String) {
        val actionToChampionDetails: NavDirections =
            ChampionsListFragmentDirections.actionChampionsListFragmentToChampionsDetailsFragment(
                championId
            )
        findNavController().navigate(actionToChampionDetails)
    }
}