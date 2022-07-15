package com.example.championsapplication.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.championsapplication.BuildConfig
import com.example.championsapplication.R
import com.example.championsapplication.data.api.ApiConstants
import com.example.championsapplication.data.model.Result
import com.example.championsapplication.databinding.FragmentChampionsDetailsBinding
import javax.inject.Inject

class ChampionsDetailsFragment : Fragment() {

    private lateinit var binding: FragmentChampionsDetailsBinding

    @Inject
    lateinit var championsDetailsModelFactory: ChampionsDetailsModelFactory
    lateinit var championsDetailsViewModel: ChampionsDetailsViewModel
    private val args: ChampionsDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChampionsDetailsBinding.inflate(
            inflater,
            container,
            false
        )
        (activity!!.application as ChampionsApplication).daggerComponent.inject(this)
        val championId = args.championId
        championsDetailsViewModel = ViewModelProvider(
            this,
            championsDetailsModelFactory
        )[ChampionsDetailsViewModel::class.java]

        championsDetailsViewModel.getChampionDetails(championId)
        observeChampionDetails()

//        binding.txtName.text = args.champion.name
//        binding.txtTitle.text = args.champion.title
//        binding.txtBlurb.text = args.champion.blurb
//        if (args.champion.championImage != null) {
//            val imageURL =
//                BuildConfig.BASE_URL + ApiConstants.IMAGE_URL + args.champion.championImage!!.full
//            Glide.with(requireContext()).load(imageURL)
//                .into(binding.imgProfile)
//        }
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
                        if (champion.championImage != null) {
                            val imageURL =
                                BuildConfig.BASE_URL + ApiConstants.IMAGE_URL + champion.championImage!!.full
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