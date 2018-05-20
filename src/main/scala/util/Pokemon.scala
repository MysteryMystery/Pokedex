package util

case class Pokemon (id: Int, name: String){
  override def toString: String = s"($id) $name"
}
