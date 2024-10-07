package com.example.therickandmorty.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.therickandmorty.R
import com.example.therickandmorty.databinding.CharacterFragmentBinding
import com.example.therickandmorty.model.CharacterResponse
import com.example.therickandmorty.utils.Utils
import com.example.therickandmorty.viewmodel.CharacterViewModel

class CharacterDetailFragment : Fragment() {

    private lateinit var binding: CharacterFragmentBinding
    private val viewModel: CharacterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CharacterFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let { bundle ->
            val characterName = bundle.getString("characterName")
            val characterId = bundle.getInt("characterId")
            val characterStatus = bundle.getString("characterStatus")
            val characterSpecies = bundle.getString("characterSpecies")
            val characterImage = bundle.getString("characterImage")
            val gender = bundle.getString("gender")
            val isFavourite = bundle.getBoolean("fav")

            binding.tvName.text = characterName
            binding.tvId.text = "ID: $characterId"
            binding.tvStatus.text = "Status: $characterStatus"
            binding.tvSpecies.text = "Species: $characterSpecies"
            binding.tvGender.text = "Gender: $gender"
            binding.tvNameTop.text = characterName

            Glide.with(this)
                .load(characterImage)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(40)))
                .into(binding.ivImage)

            if (isFavourite) {
                binding.ivfavourite.setImageResource(R.drawable.ic_student_filled_star)
            } else {
                binding.ivfavourite.setImageResource(R.drawable.ic_student_empty_star)
            }

            binding.ivBack.setOnClickListener {
                requireActivity().supportFragmentManager.popBackStack()
            }

            viewModel.favoriteCharacters.observe(viewLifecycleOwner) { favoriteCharacters ->
                val isFavorite = favoriteCharacters.any { it.id == characterId }
                updateFavoriteIcon(isFavorite)

                binding.ivfavourite.setOnClickListener {
                    Utils.vibrate(binding.ivfavourite.context,30)
                    viewModel.toggleFavorite(
                        CharacterResponse(
                            id = characterId,
                            name = characterName.toString(),
                            image = characterImage.toString(),
                            status = characterStatus!!,
                            species = characterSpecies!!,
                            gender = gender!!,
                            isFavourite = !isFavorite
                        )
                    )
                }
        }
    }
        }

     fun updateFavoriteIcon(isFavorite: Boolean) {
        binding.ivfavourite.setImageResource(
            if (isFavorite) R.drawable.ic_student_filled_star
            else R.drawable.ic_student_empty_star
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}
