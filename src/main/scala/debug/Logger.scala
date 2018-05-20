package debug

import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control._
import scalafx.scene.layout.{FlowPane, HBox, Priority, StackPane}
import scalafx.scene.text.Text
import scalafx.stage.Stage

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
      stylesheets.add(getClass.getResource("/skin.css").toExternalForm)
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
    //logSpace.text.value += s"[${new java.util.Date()}] (${obj.getClass.getName}) $str\n"
    /*val flow = new FlowPane()
    flow.children.add(new Text(s"[${new java.util.Date()}]"))
    flow.children.add(new Text(s"[${logLevel.toString}]") {style=s"-fx-text-fill: ${logLevel match {case LogLevel.DEBUG => "#432ec9" case LogLevel.WARN => "#ce412b" case _ => "black"}}"})
    flow.children.add(new Text(s"(${obj.getClass.getName}) $str"))*/

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
