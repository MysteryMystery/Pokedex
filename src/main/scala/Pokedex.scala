import Pokedex.pokemonListView
import database.PokeAPI.PokemonTypes.PokemonType
import database.{Cache, PokeAPI}
import debug.Logger
import javafx.beans.value.{ChangeListener, ObservableValue}
import javafx.event
import javafx.event.EventHandler
import javafx.scene.control.SelectionMode
import javafx.scene.image
import javafx.stage.WindowEvent
import scalafx.application.JFXApp
import scalafx.event.ActionEvent
import scalafx.geometry.{Insets, Orientation, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label, ListView, TextField}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout._
import scalafx.scene.text.Text
import scalafx.stage.StageStyle

import scala.collection.JavaConverters._
import database.PokeAPI._

import scala.collection

object Pokedex extends JFXApp {

  //val databaseAccessor: DatabaseAccessor = new DatabaseAccessor
  val logger = Logger.get
  val cache = new Cache

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
    fitWidth.value = 150
    minWidth(150)
    preserveRatio.value = true
    smooth = true
    styleClass = Seq("pane", "spriteBox")
   // margin = Insets(5)
    alignmentInParent = Pos.Center
  }

  val spriteWrapper: Pane = new Pane(){
    children = spriteBox
    minHeight = 150
    minWidth = 150
    styleClass = Seq("pane", "spriteBox")
   // margin = Insets(5)
  }

  val typeBox: TilePane = new TilePane {
    orientation = Orientation.Horizontal
    prefWidth = 150
    minHeight = 25
    maxHeight = 25

    styleClass = Seq("pane", "typePane")
    alignment.value = Pos.Center
   // margin = Insets(5)
   // padding = Insets(5)
  }

  val evolutionChainBox: TilePane = new TilePane(){
    orientation = Orientation.Horizontal
    styleClass = Seq("pane", "evoChainBox")
    maxHeight = 40
    maxWidth = 130

    minHeight = 40
    minWidth = 130

    alignment.value = Pos.Center
   // margin = Insets(5)
  }
  populateEvolutionBoxSprites(EvolutionChain(PokeAPI.noPokemon))

  val formsBox: TilePane = new TilePane(){
    orientation = Orientation.Horizontal
    styleClass = Seq("pane", "evoChainBox")
    prefHeight = 40
    prefWidth = 130
    alignment.value = Pos.Center
   // margin = Insets(5)
  }

  val movesListBox: ListView[PokemonMove] = new ListView[PokemonMove](Seq()){
    styleClass = Seq("pane", "evoChainBox")
    //minHeight = 400
    //prefHeight = 400
    prefWidth = 200
    vgrow = Priority.Always
    selectionModel.value.setSelectionMode(SelectionMode.SINGLE)
    selectionModel.value.selectedItemProperty.addListener(new ChangeListener[PokemonMove] {
      override def changed(observable: ObservableValue[_ <: PokemonMove], oldValue: PokemonMove, newValue: PokemonMove): Unit = {
        movePane.children.remove(0, movePane.children.size())
        movePane.children.addAll(
          new Label(observable.getValue.name),
          new Label("Add the rest of the move info here")
        )
      }
    })
  }

  val movePane: FlowPane = new FlowPane(){
    styleClass = Seq("pane", "movePane")
    orientation = Orientation.Vertical
    prefHeight = 200
    prefWidth = 200
  }

  val baseStatsPane: TilePane = new TilePane {
    orientation = Orientation.Vertical
    styleClass = Seq("pane", "movePane")
    minHeight = 100
    minWidth = 100
    alignment.value = Pos.Center
    //margin = Insets(5)
  }

  val parentPane: GridPane = new GridPane(){
    //gridLinesVisible = true
    padding = Insets(25)
    //hgap = 5
    //vgap = 5
    prefHeight = 700
    //prefWidth = 850
    //minHeight = 500
    //minWidth = 800
    vgrow = Priority.Always
    hgrow = Priority.Always

    hgap = 10
    vgap = 10
  }
  parentPane.add(spriteWrapper,       0, 0, 1, 3)
  parentPane.add(typeBox,             0, 3, 1, 1)
  parentPane.add(evolutionChainBox,   1, 0, 1, 1)
  parentPane.add(formsBox,            1, 1, 1, 1)
  parentPane.add(baseStatsPane,       0, 4, 2, 2)
  parentPane.add(movesListBox,        3, 0, 1, 5)
  parentPane.add(movePane,            3, 5, 1, 1)
  parentPane.add(pokemonSearchBox,    4, 0, 1, 1)
  parentPane.add(pokemonSearchButton, 5, 0, 1, 1)
  parentPane.add(pokemonListView,     4, 1, 2, 5)

  /*val parentPane: BorderPane = new BorderPane() {
    vgrow = Priority.Always
    hgrow = Priority.Always
    prefHeight = 1000
    prefWidth = 1200
    left = new VBox(){
      children = Seq()
      children = Seq(new HBox(){
        children = Seq(new VBox(){
          top = spriteWrapper
          bottom = typeBox
        }, new VBox(){
          top = evolutionChainBox
          bottom = formsBox
        })
      }, baseStatsPane)
    }
    /*right = new HBox() {
      left = new VBox() {
        top = movesListBox
        bottom = movePane
      }
      right = new VBox() {
          top = new HBox() {
            children = Seq(pokemonSearchBox, pokemonSearchButton)
          }
      }
    }*/
  }*/

  stage = new JFXApp.PrimaryStage {
    title.value = "Pokédex - James Attfield"
    icons.add(new Image(new image.Image(getClass.getResource("/images/Pokeball.png").toExternalForm)))
    scene = new Scene(){
      resizable.value = false
      stylesheets.addAll(getClass.getResource("/styles/skin.css").toExternalForm, getClass.getResource("/styles/types.css").toExternalForm)
      root = parentPane
      initStyle(StageStyle.Decorated)
    }
  }

  def pokemonListView: ListView[Pokemon] = new ListView[Pokemon](cache.loadPokemon){
    styleClass = Seq("pane", "listbox", "list-view")
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
      id.value = typ.toString.toLowerCase()
      styleClass = Seq("typeLabel")
    }

  def setPokemonTypes(pokemon: Pokemon): Unit = {
    typeBox.children = pokemon.types.map(typeLabel)
  }

  def populateEvolutionBoxSprites(evoChain: EvolutionChain): Unit = evolutionChainBox.children = evoChain.map(p => new ImageView(){image.value = p.sprite; fitWidth = 40; fitHeight = 40;})
  def populateFormsBoxSprites(forms: Seq[Image]): Unit = formsBox.children = forms.map(i => new ImageView(){image = i; fitHeight = 40; fitWidth = 40;})
  def populateMainSpriteBox(pokemon: Pokemon): Unit = spriteBox.image = pokemon.sprite

  def populateWindow(pokemon: Pokemon): Unit = {
    setPokemonTypes(pokemon)
    populateMainSpriteBox(pokemon)
    populateEvolutionBoxSprites(pokemon.evoChain)
    populateFormsBoxSprites(pokemon.forms)
  }

  populateWindow(PokeAPI.noPokemon)
}
