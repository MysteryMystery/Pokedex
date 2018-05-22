package util

import me.sargunvohra.lib.pokekotlin
import me.sargunvohra.lib.pokekotlin.model.{NamedApiResource, PokemonSprites, PokemonStat, PokemonType}
import web.PokeAPI
import scala.collection.JavaConverters._

//case class Pokemon (id: Int, name: String){
//  override def toString: String = s"$id. $name"
//}

//@SerialVersionUID(100L)
//case class Pokemon(pokemonSpecies: me.sargunvohra.lib.pokekotlin.model.Pokemon) extends Serializable {
//  override def toString: String = s"${pokemonSpecies.getId}. ${pokemonSpecies.getName}"
//}

//Have to get evo chain anothere way
case class Pokemon(id:Int, name: String, sprite: PokemonSprites, forms: List[NamedApiResource], baseStats: List[PokemonStat], moves: List[pokekotlin.model.PokemonMove], types: List[PokemonType]){
  override def toString: String = s"$id. $name"

  def this(pokemon: pokekotlin.model.Pokemon) = {
    this(pokemon.getId, pokemon.getName, pokemon.getSprites, pokemon.getForms.asScala.toList, pokemon.getStats.asScala.toList, pokemon.getMoves.asScala.toList, pokemon.getTypes.asScala.toList)
  }
}

object Pokemon{
  def apply(pokemon: pokekotlin.model.Pokemon) = new Pokemon(pokemon)
}
