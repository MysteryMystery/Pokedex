package web

import debug.Logger
import javafx.scene.image
import me.sargunvohra.lib.pokekotlin.client.{PokeApi, PokeApiClient}
import me.sargunvohra.lib.pokekotlin.model.{Pokedex, PokemonEntry, PokemonSpecies}
import scalafx.scene.image.Image
import util.Pokemon

import scala.collection.JavaConverters._
import scala.collection.mutable

object PokeAPI {
  val logger = Logger.get
  val pokeAPI: PokeApi = new PokeApiClient()
  val bulbasaur: PokemonSpecies = pokeAPI.getPokemonSpecies(1)

  def getPokemonForms(id: Int): Any = {

  }

  def getSpritesFor(pokemon: Pokemon): Image = {
    //If has id or name then get from api
    //else
    new Image(new image.Image(PokeAPI.pokeAPI.getPokemon(pokemon.id).getSprites.getFrontDefault))
  }

  def getPokedex: Pokedex = pokeAPI.getPokedex(7)

  def getPokemon: mutable.Buffer[PokemonEntry] = getPokedex.getPokemonEntries.asScala
}
