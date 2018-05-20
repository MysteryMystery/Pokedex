import Pokedex.pokemonListView
import database.{DataHandler, DatabaseAccessor}
import debug.Logger
import javafx.beans.value.{ChangeListener, ObservableValue}
import javafx.event
import javafx.event.EventHandler
import javafx.scene.control.SelectionMode
import javafx.stage.WindowEvent
import scalafx.application.JFXApp
import scalafx.event.ActionEvent
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control.{Button, ListView, TextField}
import scalafx.scene.layout.{GridPane, Priority}

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

  val parentPane: GridPane = new GridPane(){
    padding = Insets(25)
    prefHeight = 500
    prefWidth = 800
    vgrow = Priority.Always
    hgrow = Priority.Always
  }
  parentPane.add(pokemonSearchBox,    7, 1, 1, 1)
  parentPane.add(pokemonSearchButton, 8, 1, 1, 1)
  parentPane.add(pokemonListView,     7, 2, 2, 10)

  stage = new JFXApp.PrimaryStage {
    title.value = "Pokédex - James Attfield"
    scene = new Scene(){
      resizable.value = false
      stylesheets.add(getClass.getResource("/skin.css").toExternalForm)
      root = parentPane
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
}
