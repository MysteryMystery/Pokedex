import Pokedex.pokemonListView
import database.DataHandler
import debug.Logger
import javafx.beans.value.{ChangeListener, ObservableValue}
import javafx.event
import javafx.event.EventHandler
import javafx.scene.control.SelectionMode
import javafx.stage.WindowEvent
import me.sargunvohra.lib.pokekotlin.client.{PokeApi, PokeApiClient}
import me.sargunvohra.lib.pokekotlin.model.PokemonSpecies
import scalafx.application.JFXApp
import scalafx.event.ActionEvent
import scalafx.geometry.{Insets, Orientation, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label, ListView, TextField}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{FlowPane, GridPane, Priority, TilePane}
import scalafx.scene.text.Text
import scalafx.stage.StageStyle

import scala.collection.JavaConverters._
import util.{Pokemon, PokemonMove, PokemonTypes}
import util.PokemonTypes._
import web.PokeAPI

import scala.collection

object Pokedex extends JFXApp {

  //val databaseAccessor: DatabaseAccessor = new DatabaseAccessor
  val logger = Logger.get
  val dataHandler = new DataHandler

  val pokemonSearchBox: TextField = new TextField(){
    promptText = "Search for a Pokemon..."
  }

  val pokemonSearchButton: Button = new Button("Search"){
    onAction = (e: event.ActionEvent) => {
      val poke = pokemonListView.getItems.asScala.find(
        savedPoke => savedPoke.id.toString == pokemonSearchBox.text.value || savedPoke.name == pokemonSearchBox.text.value
      )
      if (poke.isDefined)
        pokemonListView.selectionModel.value.select(poke.get)
      else
        logger.log(this, logger.LogLevel.WARN, "No Pokemon found in listview..")
    }
  }

  val spriteBox: ImageView = new ImageView(){
    image.value = new Image(new javafx.scene.image.Image(getClass.getResource("/images/Pokeball.png").toExternalForm))
    fitWidth.value = 100
    preserveRatio.value = true
    smooth = true
    styleClass = Seq("pane", "spriteBox")
    margin = Insets(5)
  }

  val typeBox: TilePane = new TilePane {
    orientation = Orientation.Horizontal
    prefWidth = 120
    minHeight = 25
    styleClass = Seq("pane", "typePane")
    alignment.value = Pos.Center
    margin = Insets(5)
  }
  setPokemonTypes(PokemonTypes.NONE)

  val evolutionChainBox: TilePane = new TilePane(){
    orientation = Orientation.Horizontal
    styleClass = Seq("pane", "evoChainBox")
    maxHeight = 30
    maxWidth = 100
    alignment.value = Pos.Center
    margin = Insets(5)
  }
  populateEvolutionBoxFromResources("/images/Pokeball.png", "/images/Pokeball.png", "/images/Pokeball.png")

  val movesListBox: ListView[PokemonMove] = new ListView[PokemonMove](Seq(PokemonMove(PokeAPI.pokeAPI.getMove(1)))){
    styleClass = Seq("pane", "evoChainBox")
    //minHeight = 400
    prefHeight = 400
    prefWidth = 200
    vgrow = Priority.Always
    selectionModel.value.setSelectionMode(SelectionMode.SINGLE)
    selectionModel.value.selectedItemProperty.addListener(new ChangeListener[PokemonMove] {
      override def changed(observable: ObservableValue[_ <: PokemonMove], oldValue: PokemonMove, newValue: PokemonMove): Unit = {
        movePane.children.remove(0, movePane.children.size())
        movePane.children.addAll(
          new Label(observable.getValue.move.getDamageClass.getName),
          new Label("Add the rest of the move info here")
        )
      }
    })
  }

  val movePane: FlowPane = new FlowPane(){
    styleClass = Seq("pane", "evoChainBox")
    orientation = Orientation.Vertical
  }

  val parentPane: GridPane = new GridPane(){
    padding = Insets(25)
    //hgap = 5
    //vgap = 5
    prefHeight = 500
    prefWidth = 800
    minHeight = 500
    minWidth = 800
    vgrow = Priority.Always
    hgrow = Priority.Always
  }
  parentPane.add(spriteBox,           0, 0, 1, 2)
  parentPane.add(typeBox,             0, 2, 1, 2)
  parentPane.add(evolutionChainBox,   1, 0, 1, 1)
  parentPane.add(movesListBox,        2, 0, 1, 9)
 // parentPane.add(movePane,            2, 10, 1, 1)
  parentPane.add(pokemonSearchBox,    7, 0, 1, 1)
  parentPane.add(pokemonSearchButton, 8, 0, 1, 1)
  parentPane.add(pokemonListView,     7, 1, 2, 10)

  stage = new JFXApp.PrimaryStage {
    title.value = "Pokédex - James Attfield"
    scene = new Scene(){
      resizable.value = false
      stylesheets.addAll(getClass.getResource("/styles/skin.css").toExternalForm, getClass.getResource("/styles/types.css").toExternalForm)
      root = parentPane
      initStyle(StageStyle.Decorated)
    }
  }

  def pokemonListView: ListView[Pokemon] = new ListView[Pokemon](dataHandler.allSavedPokemon){
    prefHeight = 700
    prefWidth = 200
    vgrow = Priority.Always
    selectionModel.value.setSelectionMode(SelectionMode.SINGLE)
    selectionModel.value.selectedItemProperty.addListener(new ChangeListener[Pokemon] {
      override def changed(observable: ObservableValue[_ <: Pokemon], oldValue: Pokemon, newValue: Pokemon): Unit = {
        pokemonSearchBox.text.value = observable.getValue.name
        logger.log(this, logger.LogLevel.INFO, s"Pokemon ${observable.getValue.id} / ${observable.getValue.name} Selected in ListView[Pokemon]")
      }
    })
  }

  def typeLabel(typ: PokemonType): Label = new Label(typ.toString){
      id.value = if (typ == PokemonTypes.NONE) "???" else typ.toString.toLowerCase()
      styleClass = Seq("typeLabel")
    }

  def setPokemonTypes(types: PokemonType*): Unit = {
    typeBox.children = types.map(typeLabel)
  }

  //May have to say from pokedexdata/<pokemon>.json
  def populateEvolutionBoxFromResources(imageLocations: String*): Unit = evolutionChainBox.children = imageLocations.map(loc => new ImageView(){image.value = new Image(new javafx.scene.image.Image(getClass.getResource(loc).toExternalForm)); fitWidth = 30; fitHeight = 30})

}
