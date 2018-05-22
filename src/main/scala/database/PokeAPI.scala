package database

import java.io.File
import java.nio.file.Files

import database.PokeAPI.MoveCategories.MoveCategory
import database.PokeAPI.PokemonTypes.PokemonType

import scala.collection.JavaConverters._
import javafx.scene.image
import scalafx.scene.image.Image

object PokeAPI {

  implicit class PokemonOps(pokemon: Pokemon) {
    def sprite: Image = new Image(new image.Image(getClass.getResource(s"/images/sprites/${pokemon.id}.png").toExternalForm))

    def forms: Seq[Image] = new File(getClass.getResource("/images/sprites").getPath).listFiles().toList.filter(_.getName.contains(pokemon.id.toString)).map(m => new Image(new image.Image(m.getPath)))

    def evoChain: EvolutionChain = EvolutionChain(Pokemon(1, "Bulbasaur"), Pokemon(2, "Ivysaur"), Pokemon(3, "Venusaur"))

    def baseStats: BaseStats = {
      //Some SQL magic here
      BaseStats(45, 49, 49, 65, 65, 45)
    }

    def moveList: Seq[PokemonMove] = {
      //Some SQL magic here
      Seq(PokemonMove(1, "Pound", PokemonTypes.NORMAL, MoveCategories.PHYSICAL, "Hits the target"))
    }

    def types: Seq[PokemonType] = {
      //Some SQL magic here
      Seq(PokemonTypes.GRASS)
    }
  }

  case class EvolutionChain(chain: Pokemon*) extends Iterable[Pokemon] {
    override def iterator: Iterator[Pokemon] = chain.iterator
  }

  case class BaseStats(hp: Int, atk: Int, defense: Int, spAtk: Int, spDef: Int, spd: Int)

  case class PokemonMove(id: Int, name: String, typee: PokemonType, category: MoveCategory, description: String)

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


