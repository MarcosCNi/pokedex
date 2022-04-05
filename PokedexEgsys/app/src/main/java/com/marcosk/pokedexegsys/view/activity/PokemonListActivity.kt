package com.marcosk.pokedexegsys.view.activity

import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.marcosk.pokedexegsys.R
import com.marcosk.pokedexegsys.databinding.ActivityPokemonListBinding
import com.marcosk.pokedexegsys.model.Pokemon
import com.marcosk.pokedexegsys.view.activity.recyclerview.adapter.PokemonListAdapter
import com.marcosk.pokedexegsys.viewmodel.PokemonViewModel
import com.marcosk.pokedexegsys.viewmodel.PokemonViewModelFactory

class PokemonListActivity : AppCompatActivity(R.layout.activity_pokemon_list) {

    private val binding by lazy {
        ActivityPokemonListBinding.inflate(layoutInflater)
    }
    private val viewModel by lazy{
        ViewModelProvider (this, PokemonViewModelFactory ())
            .get (PokemonViewModel::class.java)
    }
    private val adapter by lazy {
        PokemonListAdapter(this, viewModel.pokedex.value!!)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configPokemonViewModel()
        configSearchView()
        setContentView(binding.root)
    }

    private fun configSearchView() {
        binding.pokemonListSearchView.clearFocus()
        binding.pokemonListSearchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(text: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(text: String): Boolean {
                adapter.searchPokemon(text)
                return true
            }
        })
    }

    private fun configPokemonViewModel() {
        viewModel.pokedex.observe(this) {
            configRecyclerView(it)
        }
    }

    private fun configRecyclerView(list: List<Pokemon?>) {
        binding.pokemonListRecyclerView.post{
            binding.pokemonListRecyclerView.layoutManager = GridLayoutManager(applicationContext, 2)
            binding.pokemonListRecyclerView.adapter = adapter
        }
    }
}

