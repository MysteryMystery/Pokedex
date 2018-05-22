package database

import java.io.File
import java.nio.file.Files

import database.PokeAPI.MoveCategories.MoveCategory
import database.PokeAPI.PokemonTypes.PokemonType
import debug.Logger

import scala.collection.JavaConverters._
import javafx.scene.image
import scalafx.scene.image.Image

import scala.collection.mutable.ListBuffer

object PokeAPI {
  val logger = Logger.get
  val noPokemon: Pokemon = Pokemon(0, "Non Selected")

  implicit class PokemonOps(pokemon: Pokemon) {
    def sprite: Image = new Image(new image.Image(getClass.getResource(s"/images/sprites/${pokemon.id}.png").toExternalForm))

    def forms: Seq[Image] = {
      //new File(getClass.getResource("/images/sprites").getPath).listFiles().toList.filter(_.getName.contains(pokemon.id.toString)).map(m => new Image(new image.Image(m.getPath)))
      var tr = ListBuffer[Image]()
      try {
        tr += new Image(new image.Image(getClass.getResource(s"/images/sprites/${pokemon.id}-mega.png").toExternalForm))
      } catch {
        case _ =>
      }
      try {
        tr += new Image(new image.Image(getClass.getResource(s"/images/sprites/${pokemon.id}-alola.png").toExternalForm))
      } catch {
        case _ =>
      }
      tr
    }

    def evoChain: EvolutionChain = {
      if (pokemon.id == 0)
        return EvolutionChain(pokemon, pokemon, pokemon)
      //SQL magic here
      EvolutionChain(Pokemon(1, "Bulbasaur"), Pokemon(2, "Ivysaur"), Pokemon(3, "Venusaur"))
    }

    def baseStats: BaseStats = {
      if (pokemon.id == 0)
        return BaseStats(0,0,0,0,0,0)
      //Some SQL magic here
      BaseStats(45, 49, 49, 65, 65, 45)
    }

    def moveList: Seq[PokemonMove] = {
      if (pokemon.id == 0)
        return Seq()
      //Some SQL magic here
      Seq(PokemonMove(1, "Pound", PokemonTypes.NORMAL, MoveCategories.PHYSICAL, "Hits the target"))
    }

    def types: Seq[PokemonType] = {
      if (pokemon.id == 0)
        return Seq(PokemonTypes.NONE)
      //Some SQL magic here
      Seq(PokemonTypes.GRASS)
    }
  }

  case class EvolutionChain(chain: Pokemon*) extends Iterable[Pokemon] {
    override def iterator: Iterator[Pokemon] = chain.iterator
  }

  case class BaseStats(hp: Int, atk: Int, defense: Int, spAtk: Int, spDef: Int, spd: Int)

  case class PokemonMove(id: Int, name: String, typee: PokemonType, category: MoveCategory, description: String) {
    override def toString: String = name
  }

  case class Pokemon(id:Int, name: String){
    override def toString: String = s"$id. $name"
  }

  object PokemonTypes extends Enumeration {
    type PokemonType = Value
    val
    NONE,
    NORMAL,
    FIGHTING,
    FLYING,
    POISON,
    GROUND,
    ROCK,
    BUG,
    GHOST,
    STEEL,
    FIRE,
    WATER,
    GRASS,
    ELECTRIC,
    PSYCHIC,
    ICE,
    DRAGON,
    DARK,
    FAIRY
    = Value
  }

  object MoveCategories extends Enumeration {
    type MoveCategory = Value
    val
      SPECIAL,
      PHYSICAL,
      STATUS
              = Value
  }
}


