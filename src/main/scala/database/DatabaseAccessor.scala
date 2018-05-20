package database

import java.sql._

import net.liftweb.json._
import web.WebHandler

import scala.collection.mutable

@Deprecated
class DatabaseAccessor {
  //Make call to pokeAPI -> import into local db the info that I want
  val pokeAPI: String = "https://pokeapi.co/api/v2"

  val indexTable: String = "pokemon"
  val mainTable: String = "pokemonstats"

  init

  @throws(classOf[Exception])
  def getConnection: Connection = {
    val x = DriverManager.getConnection("jdbc:h2:./test")
    x.setAutoCommit(true)
    x
  }

  def createDB: Unit = {
    Seq(
      "CREATE TABLE IF NOT EXISTS pokemon (index INTEGER PRIMARY KEY, name VARCHAR);",
      "CREATE TABLE IF NOT EXISTS pokemonstats (index INTEGER PRIMARY KEY, height FLOAT" //etc....
    ).foreach(getConnection.createStatement.execute)
  }

  def populateDB: Unit = {
    //Add isEmpty checks
    // Get the Calls from the JSON API -> for each => if not key == next -> add to db
    var url = s"$pokeAPI/pokemon"

    var pokemonSet = parse(
      WebHandler.get(url)
    )

    pokemonSet.children.foreach(
      jvalue => jvalue
    )
  }

  def init: Unit = {
    val c = getConnection
    c.prepareStatement( "CREATE TABLE IF NOT EXISTS pokemon (index INTEGER PRIMARY KEY, name VARCHAR);").execute

    c.prepareStatement("CREATE TABLE IF NOT EXISTS pokemonstats index INTEGER PRIMARY KEY").execute()

    val column_names: mutable.MutableList[String] = mutable.MutableList[String]()
    //Get json -> json foreach key add to column_names
    // column_names foreach
    column_names.foreach(
      col => c.prepareStatement(s"ALTER TABLE IF EXISTS $mainTable ADD COLUMN IF NOT EXISTS $col VARCHAR;").execute()
    )

    c.close()
  }

  private def pokeAPIKeys(levelKey: String = ""): Seq[String] = {
    if (levelKey == ""){
      //iterate root
    } else{
      //iterate that level
    }
    Seq()
  }
}
