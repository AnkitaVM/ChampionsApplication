package com.example.championsapplication.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.championsapplication.BuildConfig
import com.example.championsapplication.R
import com.example.championsapplication.data.api.ApiConstants
import com.example.championsapplication.databinding.FragmentChampionsDetailsBinding
import com.example.championsapplication.domain.model.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChampionsDetailsFragment : Fragment() {

    private lateinit var binding: FragmentChampionsDetailsBinding

    private val championsDetailsViewModel: ChampionsDetailsViewModel by viewModels()
    private val args: ChampionsDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentChampionsDetailsBinding.inflate(
            inflater,
            container,
            false
        )
        val championId = args.championId
        championsDetailsViewModel.getChampionDetails(championId)
        observeChampionDetails()
        return binding.root
    }

    private fun observeChampionDetails() {
        championsDetailsViewModel.champion.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Success -> {
                    result.data?.let { champion ->
                        binding.txtName.text = champion.name
                        binding.txtTitle.text = " - ".plus(champion.title)
                        binding.txtBlurb.text = champion.blurb
                        champion.championImage?.let {
                            val imageURL =
                                BuildConfig.BASE_URL + ApiConstants.IMAGE_URL + it.full
                            Glide.with(requireContext()).load(imageURL)
                                .into(binding.imgProfile)
                        }
                    }
                }
                is Result.Error -> {
                    Toast.makeText(context, getString(R.string.error_occurred), Toast.LENGTH_LONG)
                        .show()
                }

            }

        })
    }

}