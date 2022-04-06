package com.marcosk.pokedexegsys.view.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.marcosk.pokedexegsys.databinding.ActivityPokemonItemBinding
import com.marcosk.pokedexegsys.model.Pokemon
import java.util.*

class PokemonListAdapter(
    private var context : Context,
    private var pokedex: List<Pokemon?>,
    var itemClick: ItemClick
    ) : RecyclerView.Adapter<PokemonListAdapter.ViewHolder>() {

    private var pokemonList = pokedex.toMutableList()

    private var isFiltered = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ActivityPokemonItemBinding
            .inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    interface ItemClick{
        fun onPokemonClick(pokemon: Pokemon?)
    }

    class ViewHolder(private val binding: ActivityPokemonItemBinding) : RecyclerView.ViewHolder(binding.root){

        val cardView = binding.pokemonItemCard

        fun binding(pokemon: Pokemon?) {
            val name = binding.pokemonItemName
            val num = binding.pokemonItemNumber
            pokemon?.let {
                Glide.with(binding.pokemonItemCard).load(it.imageUrl).into(binding.pokemonItemImg)
                name.text = pokemon.name.replaceFirstChar { name ->
                    if (name.isLowerCase()) name.titlecase(
                        Locale.getDefault()
                    ) else name.toString()
                }
                num.text = "Nº ${pokemon.num}"
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokemon = pokemonList[position]
        holder.cardView.setOnClickListener{
            itemClick.onPokemonClick(pokemon)
        }
        holder.binding(pokemon)

    }

    override fun getItemCount() = pokemonList.size

    //Return a list of searched pokemon
    fun searchPokemon(query: String): Boolean {
        pokemonList.clear()
        pokemonList.addAll(pokedex.filter {
            it!!.name.contains(query, true)
        })
        notifyDataSetChanged()
        return pokemonList.isEmpty()
    }

    fun filterPokemon(query: String, position: Int){
        pokemonList.clear()
        if (position==0){
            pokemonList = pokedex.toMutableList()
        }else{
            pokemonList.addAll(pokedex.filter {
                if (it!!.type!!.size > 1){
                    it!!.type?.get(0)!!.name.contains(query, true) || it!!.type?.get(1)!!.name.contains(query, true)
                }else{
                    it!!.type?.get(0)!!.name.contains(query, true)
                }
            })
        }

        notifyDataSetChanged()
    }

    //Return a random Pokemon
    fun randomPokemon(): Pokemon? {
        return pokemonList[(0 until (pokemonList.size)).random()]
    }
}