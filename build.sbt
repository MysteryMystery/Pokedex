name := "Pokédex"

version := "0.1"

scalaVersion := "2.12.5"

resolvers ++= Seq(
  "bintray" at "http://jcenter.bintray.com"
)

libraryDependencies ++= Seq(
  "org.scalafx" %% "scalafx" % "8.0.144-R12",
  "com.typesafe.slick" %% "slick" % "3.2.3",
  "org.reflections" % "reflections" % "0.9.11"
)

assemblyJarName in assembly := "PokedexExplorer.jar"