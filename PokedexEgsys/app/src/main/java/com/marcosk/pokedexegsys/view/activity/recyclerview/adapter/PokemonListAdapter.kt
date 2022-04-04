package com.marcosk.pokedexegsys.view.activity.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.marcosk.pokedexegsys.databinding.ActivityPokemonItemBinding
import com.marcosk.pokedexegsys.model.Pokemon
import java.util.*

class PokemonListAdapter(
    private var context : Context,
    private var pokedex: List<Pokemon?>
    ) : RecyclerView.Adapter<PokemonListAdapter.ViewHolder>() {

    private var pokemonList = pokedex.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ActivityPokemonItemBinding
            .inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    class ViewHolder(private val binding: ActivityPokemonItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun binding(pokemon: Pokemon?) {
            val name = binding.pokemonItemName
            val num = binding.pokemonItemNumber
            pokemon?.let {
                binding.pokemonItemImg.load(pokemon.imageUrl)
                name.text = pokemon.name.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.getDefault()
                    ) else it.toString()
                }
                num.text = "Nº ${pokemon.num}"
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = pokemonList[position]
        holder.binding(data)
    }

    override fun getItemCount() = pokemonList.size

    fun searchPokemon(query: String): Boolean {
        pokemonList.clear()
        pokemonList.addAll(pokedex.filter { it!!.name.contains(query, true) })

        notifyDataSetChanged()

        return pokemonList.isEmpty()
    }

    fun clearSearch(){
        pokemonList = pokedex.toMutableList()
        notifyDataSetChanged()
    }

}