name := "notation"

version := "1.0"

scalaVersion := "2.12.3"

resolvers ++= Seq(
  Resolver.bintrayRepo("stanch", "maven"),
  Resolver.sonatypeRepo("releases")
)

libraryDependencies ++= Seq(
  "com.storm-enroute" %% "scalameter-core" % "0.8.2",
  "com.typesafe.akka" %% "akka-actor" % "2.5.3",
  "junit" % "junit" % "4.10" % "test",
  "org.apache.spark" % "spark-core_2.10" % "2.1.0",
  "org.apache.spark" % "spark-sql_2.10" % "2.1.0",
  "org.scala-js" %%% "scalajs-dom" % "0.9.1",
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.4",
  "org.scalatest" %% "scalatest" % "3.0.0"
)


testFrameworks += new TestFramework("org.scalameter.ScalaMeterFramework")

parallelExecution in Test := false

enablePlugins(ScalaJSPlugin)

scalaJSUseMainModuleInitializer := false

relativeSourceMaps := false

jsDependencies += "org.webjars.npm" % "paper" % "0.11.4" / "paper-full.min.js" commonJSName "paper"