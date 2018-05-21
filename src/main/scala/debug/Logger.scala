package debug

import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control._
import scalafx.scene.layout.{FlowPane, HBox, Priority, StackPane}
import scalafx.scene.text.Text
import scalafx.stage.{Stage, StageStyle}

object Logger {
  private val v = new Logger
  def get: Logger = v
}

class Logger {
  private[this] val logSpace: TextArea = new TextArea(){
    //padding = Insets(25)
    prefHeight = 300
    prefWidth = 500
    vgrow = Priority.Always
    hgrow = Priority.Always
    //alignment = Pos.TopLeft
    wrapText = true
    editable = false
  }

  private[this] val loggerWindow = new Stage(){
    title.value = "Logger - Pokedex"
    scene = new Scene(){
      stylesheets.add(getClass.getResource("/styles/skin.css").toExternalForm)
      content = new StackPane(){
        //children = logSpace
        styleClass = Seq("logger")
        children.add(logSpace)
        prefHeight = 300
        prefWidth = 500
        resizable.value = false
      }
    }
    show
  }

  def log(obj: Any, logLevel: LogLevel.LogLevel,str: String): Unit = {
    logSpace.text.value += s"[${new java.util.Date()}][${logLevel.toString}](${obj.getClass.getName}) $str\n"
  }

  object LogLevel extends Enumeration {
    type LogLevel = Value
    val
      INFO,
      WARN,
      DEBUG
            = Value
  }
}
