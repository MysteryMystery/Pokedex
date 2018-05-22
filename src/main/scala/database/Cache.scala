package database

import java.io._
import java.nio.file.{Files, Path, Paths}

import database.PokeAPI.Pokemon
import debug.Logger

import scala.collection.mutable
import scala.collection.JavaConverters._

class Cache {
  val logger = Logger.get
  logger.log(this, logger.LogLevel.INFO, "Started Init DataHandler")

  val saveFolder: File = new File("pokedexdata")
  if (!saveFolder.exists()){
    //new File(getClass.getResource("/db/PokedexDatabase").toExternalForm)

  }

  /*def allSavedPokemon: Seq[Pokemon] = {
    saveFolder.listFiles.map(
      f => {
        val x = f.getName.replaceAll(".(json|bin|yml)", "").split("_")
        Pokemon(x(0).toInt, x(1))
      }
    )
  }*/

  /*def loadPokemon(id: Int): Pokemon = {
    val f = saveFolder.listFiles.find(_.getName.contains(s"${id}_"))
    if (f.isDefined)
      {
        loadYaml(f.get)
      }
    else
      Pokemon(-1, null)
  }*/

  def loadPokemon: Seq[Pokemon] = {
    Seq(Pokemon(1, "Bulbasaur"))
  }
}
