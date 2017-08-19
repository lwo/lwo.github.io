name := "notation"

version := "1.0"

scalaVersion := "2.12.2"

libraryDependencies += "junit" % "junit" % "4.10" % "test"

libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.4"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.0"

libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.1"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.5.3"

libraryDependencies += "com.storm-enroute" %% "scalameter-core" % "0.8.2"

resolvers ++= Seq(
  Resolver.bintrayRepo("stanch", "maven")
)

testFrameworks += new TestFramework("org.scalameter.ScalaMeterFramework")

parallelExecution in Test := false

enablePlugins(ScalaJSPlugin)

scalaJSUseMainModuleInitializer := false

relativeSourceMaps := false

jsDependencies += "org.webjars.npm" % "paper" % "0.11.4" / "paper-full.min.js" commonJSName "paper"