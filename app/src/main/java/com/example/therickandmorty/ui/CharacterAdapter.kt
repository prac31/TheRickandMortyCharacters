package com.example.therickandmorty.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.therickandmorty.R
import com.example.therickandmorty.databinding.ItemCharacterBinding
import com.example.therickandmorty.model.CharacterResponse
import com.example.therickandmorty.utils.Utils


class CharacterAdapter(
    private var characters: List<Pair<CharacterResponse, String>>,
    private var favoriteCharacters: List<CharacterResponse>,
    val onItemClicked: (CharacterResponse) -> Unit,
    val onFavoriteClicked: (CharacterResponse) -> Unit
) : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    inner class CharacterViewHolder(private val binding: ItemCharacterBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(character: CharacterResponse, episodeName: String) {
            binding.tvName.text = character.name
            binding.tvDescription.text = "${character.species} : ${character.gender} : ${character.location?.name} : $episodeName"

            Glide.with(binding.ivCharacter.context).load(character.image)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(30)))
                .into(binding.ivCharacter)

            val isFavorite = favoriteCharacters.any { it.id == character.id }
            if (isFavorite) {
                binding.ivfavourite.setImageResource(R.drawable.ic_student_filled_star)
            } else {
                binding.ivfavourite.setImageResource(R.drawable.ic_student_empty_star)
            }

            binding.root.setOnClickListener {
                onItemClicked(character)
            }

            binding.ivfavourite.setOnClickListener {
                Utils.vibrate(binding.ivfavourite.context,30)
                onFavoriteClicked(character)
                notifyItemChanged(bindingAdapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = characters[position]
        holder.bind(character.first, character.second)
    }

    override fun getItemCount(): Int {
        return characters.size
    }

    fun updateList(newCharacters: List<Pair<CharacterResponse, String>>, newFavorites: List<CharacterResponse>) {
        characters = newCharacters
        favoriteCharacters = newFavorites
        notifyDataSetChanged()
    }
}

