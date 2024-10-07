    package com.example.therickandmorty.ui

    import android.os.Bundle
    import android.view.View
    import androidx.activity.viewModels
    import androidx.appcompat.app.AppCompatActivity
    import androidx.recyclerview.widget.LinearLayoutManager
    import com.example.therickandmorty.databinding.ActivityMainBinding
    import com.example.therickandmorty.viewmodel.CharacterViewModel
    import dagger.hilt.android.AndroidEntryPoint

    @AndroidEntryPoint
    class MainActivity : AppCompatActivity() {

        private lateinit var binding: ActivityMainBinding
        private val viewModel: CharacterViewModel by viewModels()
        private lateinit var adapter: CharacterAdapter

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.rvMain.layoutManager = LinearLayoutManager(this)

            // Observe Loading
            viewModel.showLoader.observe(this) {
                binding.rvProgress.visibility = if (it == true) View.VISIBLE else View.GONE
            }

            // Observe characters and filtered characters
            viewModel.filteredCharacters.observe(this) { characters ->
                viewModel.favoriteCharacters.observe(this) { favoriteCharacters ->
                    if (::adapter.isInitialized) {
                        adapter.updateList(characters, favoriteCharacters)
                    } else {
                        adapter = CharacterAdapter(
                            characters = characters,
                            favoriteCharacters = favoriteCharacters,
                            onItemClicked = { character ->
                                // Handle item click
                                val fragment = CharacterDetailFragment().apply {
                                    arguments = Bundle().apply {
                                        putString("characterName", character.name)
                                        putInt("characterId", character.id)
                                        putString("characterStatus", character.status)
                                        putString("characterSpecies", character.species)
                                        putString("characterImage", character.image)
                                        putString("gender", character.gender)
                                        putBoolean("fav", character.isFavourite)
                                    }
                                }

                                supportFragmentManager.beginTransaction().addToBackStack(null)
                                    .replace(binding.fragment.id, fragment, "fragment")
                                    .commit()
                            },
                            onFavoriteClicked = { character ->
                                viewModel.toggleFavorite(character)
                            }
                        )
                        binding.rvMain.adapter = adapter
                    }
                }
            }

            viewModel.fetchCharacters()

            //search handling
            binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let {
                        viewModel.filterCharacters(it)
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let { viewModel.filterCharacters(it) }
                    return false
                }
            })
            binding.searchView.setOnClickListener {
                binding.searchView.isIconified = false
                binding.searchView.requestFocus()
            }
        }
    }
