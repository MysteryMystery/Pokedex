package database

import java.io.File

import debug.Logger
import me.sargunvohra.lib.pokekotlin.model.{PokemonEntry, PokemonSpecies}
import util.Pokemon
import web.PokeAPI

import scala.collection.mutable

class Cache {
  val logger = Logger.get
  logger.log(this, logger.LogLevel.INFO, "Started Init DataHandler")

  val fileNameTemplate: String = "%d_%s.json"
  val saveFolder: File = new File("pokedexdata")
  if (!saveFolder.exists()){
    saveFolder.mkdir()
    val allPokemon: mutable.Buffer[Int] = PokeAPI.getPokemon.map(_.getPokemonSpecies.getId)
    for (pokemon <- allPokemon){
      //Save Somehow
      logger.log(this, logger.LogLevel.INFO, pokemon.toString)
    }
  }

  def allSavedPokemon: Seq[Pokemon] = {
    saveFolder.listFiles.map(
      f => {
        val x = f.getName.replaceAll(".json", "").split("_")
        Pokemon(x(0).toInt, x(1))
      }
    )
  }
}
