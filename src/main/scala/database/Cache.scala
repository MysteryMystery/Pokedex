package database

import java.io._

import debug.Logger
import me.sargunvohra.lib.pokekotlin.model.{PokemonEntry, PokemonSpecies}
import util.Pokemon
import web.PokeAPI

import scala.collection.mutable
import scala.collection.JavaConverters._

class Cache extends PokeAPI with YAMLHandler {
  override val logger = Logger.get
  logger.log(this, logger.LogLevel.INFO, "Started Init DataHandler")

  override val saveFolder: File = new File("pokedexdata")
  if (!saveFolder.exists()){
    saveFolder.mkdir()
    (1 to 720).foreach(x => {
      val y = pokeAPI.getPokemon(x)
      save(Pokemon(y.getId, y.getName, y.getSprites, y.getForms.asScala.toList, y.getStats.asScala.toList, y.getMoves.asScala.toList, y.getTypes.asScala.toList))
    })
  }

  /*def allSavedPokemon: Seq[Pokemon] = {
    saveFolder.listFiles.map(
      f => {
        val x = f.getName.replaceAll(".(json|bin|yml)", "").split("_")
        Pokemon(x(0).toInt, x(1))
      }
    )
  }*/

  def save(pokemon: Pokemon): Unit = {
    dumpPokemon(new File(saveFolder.getPath + "/" + s"${pokemon.id}_${pokemon.name}.yml"), pokemon)
  }

  def loadPokemon(id: Int): Pokemon = {
    val f = saveFolder.listFiles.find(_.getName.contains(s"${id}_"))
    if (f.isDefined)
      {
        loadYaml(f.get)
      }
    else
      Pokemon(-1, null, null, null, null, null, null)
  }

  def loadPokemon: Seq[Pokemon] = {
    saveFolder.listFiles().map(x => {
      loadYaml(new File(saveFolder.getPath + "/" + x.getName))
    })
  }
}
