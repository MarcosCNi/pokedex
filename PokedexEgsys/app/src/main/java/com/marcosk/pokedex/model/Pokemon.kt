package com.marcosk.pokedex.model

data class Pokemon(

    var num: Int? =null,
    var name: String,
    var url: String,
    var type: List<PokemonType>? = null,
    var height: Int? = null,
    var weight: Int? = null,

){
    val formattedNumber = num.toString().padStart(1,'0')
    val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$formattedNumber.png"
}

