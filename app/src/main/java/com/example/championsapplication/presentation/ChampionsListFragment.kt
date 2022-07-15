package com.example.championsapplication.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.championsapplication.R
import com.example.championsapplication.data.model.Champion
import com.example.championsapplication.data.model.Result
import com.example.championsapplication.databinding.FragmentChampionsListBinding
import com.example.championsapplication.di.CHAMPIONS_DETAILS_VIEW_MODEL_TYPE
import com.example.championsapplication.di.CHAMPIONS_LIST_VIEW_MODEL_TYPE
import javax.inject.Inject
import javax.inject.Named

/**
 * A fragment representing a list of Items.
 */
class ChampionsListFragment : Fragment() {

    @Inject
    lateinit var championsViewModelFactory: ChampionsViewModelFactory
    lateinit var championsViewModel: ChampionsListViewModel

    private var championsList = mutableListOf<Champion>()
    private lateinit var championsAdapter: ChampionsRecyclerViewAdapter
    private lateinit var binding: FragmentChampionsListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentChampionsListBinding.inflate(
                inflater,
                container,
                false
            )

        (activity!!.application as ChampionsApplication).daggerComponent.inject(this)

        // Set the adapter
        setChampionsAdapter()
        championsViewModel = ViewModelProvider(
            this,
            championsViewModelFactory
        )[ChampionsListViewModel::class.java]

        championsViewModel.getAllChampions()
        observeChampionsData()


        return binding.root
    }

    private fun observeChampionsData() {
        championsViewModel.champions.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Success -> {
                    result.data?.let { champions ->
                        championsList.clear()
                        championsList.addAll(champions)
                        championsAdapter.notifyDataSetChanged()
                        binding.pbProgress.visibility = View.INVISIBLE
                    }
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
        binding.rvChampions.apply {
            layoutManager = LinearLayoutManager(context)
            championsAdapter =
                ChampionsRecyclerViewAdapter(championsList, object : ItemClickListener {
                    override fun onItemClick(position: Int) {
                        goToChampionDetailsFragment(championsList[position].id)
                    }

                })
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