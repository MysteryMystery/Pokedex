package web

import me.sargunvohra.lib.pokekotlin.client.{PokeApi, PokeApiClient}
import me.sargunvohra.lib.pokekotlin.model.PokemonSpecies
import net.liftweb.json._

object PokeAPI {
  val pokeAPI: PokeApi = new PokeApiClient()
  val bulbasaur: PokemonSpecies = pokeAPI.getPokemonSpecies(1)

  def getPokemonForms(id: Int): Any = {

  }
}
