package web

import debug.Logger
import javafx.scene.image
import me.sargunvohra.lib.pokekotlin.client.{PokeApi, PokeApiClient}
import me.sargunvohra.lib.pokekotlin.model.{EvolutionChain, Pokedex, PokemonEntry, PokemonSpecies}
import scalafx.scene.image.Image
import util.Pokemon

import scala.collection.JavaConverters._
import scala.collection.mutable

class PokeAPI {
  val logger = Logger.get
  val pokeAPI: PokeApi = new PokeApiClient()

  protected  def getPokemonForms(id: Int): Any = {

  }

  protected def getSpritesFor(pokemon: Pokemon): Image = {
    //If has id or name then get from api
    //else
    new Image(new image.Image(pokeAPI.getPokemon(pokemon.id).getSprites.getFrontDefault))
  }

  protected def getPokedex: Pokedex = pokeAPI.getPokedex(7)

  protected def getPokemon: Seq[Pokemon] = {
    (1 to 720).map(x => {
      val y = pokeAPI.getPokemon(x)
      Pokemon(y)
    })
  }
}
