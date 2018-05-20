name := "Pokédex"

version := "0.1"

scalaVersion := "2.12.5"

libraryDependencies ++= Seq(
  "org.scalafx" %% "scalafx" % "8.0.144-R12",
  "com.typesafe.slick" %% "slick" % "3.2.3",
  "com.h2database" % "h2" % "1.4.192",
  "net.liftweb" %% "lift-json" % "3.2.0"
)

assemblyJarName in assembly := "PokedexExplorer.jar"