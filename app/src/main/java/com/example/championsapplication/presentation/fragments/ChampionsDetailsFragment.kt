package com.example.championsapplication.presentation.fragments

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
import com.example.championsapplication.data.api.IMAGE_URL
import com.example.championsapplication.databinding.FragmentChampionsDetailsBinding
import com.example.championsapplication.domain.model.Result
import com.example.championsapplication.presentation.utils.ErrorMessageHandler
import com.example.championsapplication.presentation.viewmodels.ChampionsDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ChampionsDetailsFragment : Fragment() {

    private lateinit var binding: FragmentChampionsDetailsBinding
    private val championsDetailsViewModel: ChampionsDetailsViewModel by viewModels()
    private val args: ChampionsDetailsFragmentArgs by navArgs()

    @Inject
    lateinit var errorMessageHandler: ErrorMessageHandler

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val championId = args.championId
        championsDetailsViewModel.getChampionDetails(championId)
        observeChampionDetails()
    }

    private fun observeChampionDetails() {
        championsDetailsViewModel.champion.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Success -> {
                    result.data?.let { champion ->
                        binding.apply {
                            pbProgress.visibility = View.INVISIBLE
                            txtName.text = champion.name
                            txtTitle.text = " - ".plus(champion.title)
                            txtBlurb.text = champion.blurb
                            champion.championImage?.let {
                                val imageURL =
                                    BuildConfig.BASE_URL + IMAGE_URL + it.full
                                Glide.with(requireContext()).load(imageURL)
                                    .into(imgProfile)
                            }
                        }
                    }
                }
                is Result.Loading -> {
                    binding.pbProgress.visibility = View.VISIBLE
                }
                is Result.Error -> {
                    binding.pbProgress.visibility = View.INVISIBLE
                    Toast.makeText(
                        context,
                        result.errorType?.let { errorMessageHandler.getErrorMessage(it) },
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    }
}