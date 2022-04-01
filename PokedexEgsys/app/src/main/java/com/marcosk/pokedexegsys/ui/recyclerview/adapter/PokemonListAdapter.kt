package com.marcosk.pokedexegsys.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.marcosk.pokedexegsys.databinding.ActivityPokemonItemBinding
import com.marcosk.pokedexegsys.model.Pokemon

class PokemonListAdapter(
    var context : Context,
    var pokedex: List<Pokemon>
    ) : RecyclerView.Adapter<PokemonListAdapter.ViewHolder> () {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = ActivityPokemonItemBinding
            .inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    class ViewHolder(private val binding: ActivityPokemonItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun binding(pokemon: Pokemon) {
            val name = binding.pokemonItemName
            name.text = pokemon.name

            binding.pokemonItemImg.load(pokemon.img)
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var data = pokedex[position]
        holder.binding(data)
    }

    override fun getItemCount() = pokedex.size
}