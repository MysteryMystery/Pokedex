package util

import me.sargunvohra.lib.pokekotlin.model.Move

case class PokemonMove (move: Move){
  override def toString: String = move.getName
}
