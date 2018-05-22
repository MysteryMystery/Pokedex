name := "Pokédex"

version := "0.1"

scalaVersion := "2.12.5"

resolvers ++= Seq(
  "bintray" at "http://jcenter.bintray.com"
)

libraryDependencies ++= Seq(
  "org.scalafx" %% "scalafx" % "8.0.144-R12",
  "com.typesafe.slick" %% "slick" % "3.2.3",
  "com.h2database" % "h2" % "1.4.192",
  //"net.liftweb" %% "lift-json" % "3.2.0",
  "com.google.code.gson" % "gson" % "2.8.4",
  "me.sargunvohra.lib" % "pokekotlin" % "2.3.0",
  "org.yaml" % "snakeyaml" % "1.21"
)

assemblyJarName in assembly := "PokedexExplorer.jar"