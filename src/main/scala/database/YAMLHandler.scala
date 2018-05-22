package database

import java.io.{File, FileInputStream, FileWriter}

import scala.collection.JavaConverters._
import org.yaml.snakeyaml.{DumperOptions, Yaml}
import util.Pokemon
import java.util

import me.sargunvohra.lib.pokekotlin.model._

trait YAMLHandler {
  val saveFolder: File

  def dumpPokemon(file: File, pokemon: Pokemon): Unit = {
    val map: java.util.Map[String, Object] = new util.HashMap[String, Object]()
    map.put("id", pokemon.id.toString)
    map.put("name", pokemon.name)
    map.put("sprite", pokemon.sprite)
    map.put("forms", pokemon.forms.asJava)
    map.put("base_stats", pokemon.baseStats.asJava)
    map.put("move_list", pokemon.moves.asJava)
    map.put("types", pokemon.types.asJava)
    new Yaml(dumperOptions) dump(map, new FileWriter(file))
  }

  protected def loadYaml(file: File): Pokemon = {
    val loaded = new Yaml().load(new FileInputStream(file)).asInstanceOf[java.util.Map[String, Object]]
    Pokemon(
      loaded.get("id").asInstanceOf[String].toInt,
      loaded.get("name").asInstanceOf[String],
      loaded.get("sprite").asInstanceOf[PokemonSprites],
      loaded.get("forms").asInstanceOf[util.List[NamedApiResource]].asScala.toList,
      loaded.get("base_stats").asInstanceOf[util.List[PokemonStat]].asScala.toList,
      loaded.get("move_list").asInstanceOf[util.List[PokemonMove]].asScala.toList,
      loaded.get("types").asInstanceOf[util.List[PokemonType]].asScala.toList
    )
  }

  private def dumperOptions: DumperOptions = {
    val options = new DumperOptions
    options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK)
    options
  }
}


//Pokemon(id:Int, name: String, sprite: PokemonSprites, forms: List[NamedApiResource], baseStats: List[PokemonStat], moves: List[pokekotlin.model.PokemonMove], types: List[PokemonType])