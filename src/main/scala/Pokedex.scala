import Pokedex.pokemonListView
import database.DataHandler
import debug.Logger
import javafx.beans.value.{ChangeListener, ObservableValue}
import javafx.event
import javafx.event.EventHandler
import javafx.scene.control.SelectionMode
import javafx.stage.WindowEvent
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
import util.Pokemon

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

  val typeBox: FlowPane = new FlowPane(){
    prefWidth = 100
    maxHeight = 25
    minHeight = 25
    styleClass = Seq("pane", "typePane")
    alignment.value = Pos.Center
    margin = Insets(5)
  }

  val evolutionChainBox: TilePane = new TilePane(){
    orientation = Orientation.Horizontal
    styleClass = Seq("pane", "evoChainBox")
    maxHeight = 30
    maxWidth = 100
    alignment.value = Pos.Center
    margin = Insets(5)
  }
  populateEvolutionBoxFromResources("/images/Pokeball.png", "/images/Pokeball.png", "/images/Pokeball.png")

  def populateEvolutionBoxFromResources(imageLocations: String*): Unit = evolutionChainBox.children = imageLocations.map(loc => new ImageView(){image.value = new Image(new javafx.scene.image.Image(getClass.getResource(loc).toExternalForm)); fitWidth = 30; fitHeight = 30})

  val parentPane: GridPane = new GridPane(){
    padding = Insets(25)
    //hgap = 5
    //vgap = 5
    prefHeight = 500
    prefWidth = 800
    vgrow = Priority.Always
    hgrow = Priority.Always
  }
  parentPane.add(spriteBox,           0, 0, 1, 2)
  parentPane.add(typeBox,             0, 2, 1, 2)
  parentPane.add(evolutionChainBox,   1, 0, 1, 1)
  parentPane.add(pokemonSearchBox,    7, 0, 1, 1)
  parentPane.add(pokemonSearchButton, 8, 0, 1, 1)
  parentPane.add(pokemonListView,     7, 1, 2, 10)

  stage = new JFXApp.PrimaryStage {
    title.value = "Pokédex - James Attfield"
    scene = new Scene(){
      resizable.value = false
      stylesheets.add(getClass.getResource("/styles/skin.css").toExternalForm)
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

  def typeLabel(typ: String): Label = {
    new Label(typ){
      style = "-fx-background-color: #333;"
      //Style according to colour types
    }
  }
}
