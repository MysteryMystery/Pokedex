package web

object WebHandler {
  @throws(classOf[java.io.IOException])
  def get(url: String): String = scala.io.Source.fromURL(url).mkString
}
