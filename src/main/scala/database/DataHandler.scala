package database

import java.io.File
import java.nio.file.Files

import debug.Logger
import net.liftweb.json._
import util.Pokemon

import scala.collection.JavaConverters._

class DataHandler {
  val logger = Logger.get
  logger.log(this, logger.LogLevel.INFO, "Started Init DataHandler")

  val fileNameTemplate: String = "%d_%s.json"
  val saveFolder: File = new File("pokedexdata")
  if (!saveFolder.exists())
    saveFolder.mkdir()

  def save(pokemonID: Int, pokemonName: String): Unit = {
    logger.log(this, logger.LogLevel.INFO, s"Saving Pokemon: $pokemonID / $pokemonName")
    val f = new File(fileNameTemplate.replaceFirst("%d", pokemonID.toString).replaceFirst("%s", pokemonName))
    if (f.exists())
      f.delete()
    f.createNewFile()
    //Call API then save it here

    logger.log(this, logger.LogLevel.INFO, s"Saved Pokemon: $pokemonID / $pokemonName")
  }

  /**
    * Provides a clear layer of abstraction, typesafety and readability
    * @param pokemonName
    * @return JSON value corresponding to that pokemon
    */
  def load(pokemonName: String): JValue = {
    logger.log(this, logger.LogLevel.INFO, s"LOADING $pokemonName")
    val of = getFile(pokemonName)
    if (of.isDefined)
      parse(Files.lines(of.get.toPath).toArray.mkString(""))
    else
      parse("")
  }

  /**
    * Provides a clear layer of abstraction, typesafety and readability
    * @param pokemonID
    * @return JSON value corresponding to that pokemon
    */
  def load(pokemonID: Int): JValue = {
    logger.log(this, logger.LogLevel.INFO, s"LOADING $pokemonID")
    val of = getFile(pokemonID.toString)
    if (of.isDefined)
      parse(Files.lines(of.get.toPath).toArray.mkString(""))
    else
      parse("")
  }

  def allSavedPokemon: Seq[Pokemon] = {
    saveFolder.listFiles.map(
      f => {
        val x = f.getName.replaceAll(".json", "").split("_")
        Pokemon(x(0).toInt, x(1))
      }
    )
  }

  private def getFile(pokeName: String): Option[File] = saveFolder.listFiles.find(_.getName.contains(pokeName))

  private def allSaveFiles: Array[File] = {
    getRecursiveListOfFiles(saveFolder).map(_.asInstanceOf[File])
  }

  private def getRecursiveListOfFiles(dir: File): Array[Object] = {
    val these = dir.listFiles
    these ++ these.filter(_.isDirectory).flatMap(getRecursiveListOfFiles)
  }

  private def pokeAPIKeys(levelKey: String = ""): Seq[String] = {
    logger.log(this, logger.LogLevel.INFO, s"Getting API keys...")
    if (levelKey == ""){
      //iterate root
    } else{
      //iterate that level
    }
    Seq()
  }
}
