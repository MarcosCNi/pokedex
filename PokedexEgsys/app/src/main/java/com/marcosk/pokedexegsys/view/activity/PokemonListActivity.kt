package com.marcosk.pokedexegsys.view.activity

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.marcosk.pokedexegsys.R
import com.marcosk.pokedexegsys.databinding.ActivityPokemonInfoBinding
import com.marcosk.pokedexegsys.databinding.ActivityPokemonListBinding
import com.marcosk.pokedexegsys.model.Pokemon
import com.marcosk.pokedexegsys.view.recyclerview.adapter.PokemonListAdapter
import com.marcosk.pokedexegsys.viewmodel.PokemonViewModel
import com.marcosk.pokedexegsys.viewmodel.PokemonViewModelFactory
import java.util.*

class PokemonListActivity : AppCompatActivity(R.layout.activity_pokemon_list),
    PokemonListAdapter.ItemClick {

    private lateinit var dialog: AlertDialog

    private val binding by lazy {
        ActivityPokemonListBinding.inflate(layoutInflater)
    }
    private val viewModel by lazy{
        ViewModelProvider (this, PokemonViewModelFactory ())
            .get (PokemonViewModel::class.java)
    }
    private val adapter by lazy {
        PokemonListAdapter(this, viewModel.pokedex.value!!, this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configPokemonViewModel()
        configSearchView()
        configFloatingActionBtn()
        setContentView(binding.root)
    }

    //config the Floating action Button
    private fun configFloatingActionBtn() {
        binding.pokemonListFloatingActionButton.setOnClickListener {
            val randomPokemon = adapter.randomPokemon()
            if (randomPokemon != null) {
                setupDialog(randomPokemon)
            }
        }
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
            configRecyclerView()
        }
    }


    private fun configRecyclerView() {
        binding.pokemonListRecyclerView.post{
            binding.pokemonListRecyclerView.layoutManager = GridLayoutManager(applicationContext, 2)
            binding.pokemonListRecyclerView.adapter = adapter
        }
    }

    //Set on pokemon card click
    override fun onPokemonClick(pokemon: Pokemon?) {
        if (pokemon != null){
            setupDialog(pokemon)
        }
    }

    //Config the alert dialog
    private fun setupDialog(pokemon: Pokemon) {
        val build = AlertDialog.Builder(this)
        val bindingDialog = ActivityPokemonInfoBinding.inflate(layoutInflater)

        //Load the data to the custom Dialog
        loadData(bindingDialog, pokemon)
        bindingDialog.pokemonInfoExitBtn.setOnClickListener {
            dialog.hide()
        }
        build.setView(bindingDialog.root)
        dialog = build.create()
        dialog.show()
    }

    //Load all the information displayed on alert dialog
    private fun loadData(bindingDialog: ActivityPokemonInfoBinding, pokemon: Pokemon) {
        Glide.with(bindingDialog.pokemonInfo).load(pokemon.imageUrl)
            .into(bindingDialog.pokemonInfoImg)

        //Config the pokemon name with FirstChar Uppercase
        bindingDialog.pokemonInfoName.text = pokemon.name.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }
        bindingDialog.pokemonInfoNumber.text = "Nº ${pokemon.num}"
        bindingDialog.pokemonInfoType1.text = pokemon?.type?.get(0)!!.name
        if (pokemon.type!!.size > 1) {
            bindingDialog.pokemonInfoType2.visibility = View.VISIBLE
            bindingDialog.pokemonInfoType2.text = pokemon?.type?.get(1)!!.name
        } else {
            bindingDialog.pokemonInfoType2.visibility = View.GONE
        }
        bindingDialog.pokemonInfoWeight.text = "Weight: ${pokemon.weight}"
        bindingDialog.pokemonInfoHeight.text = "Height: ${pokemon.height}"
    }
}