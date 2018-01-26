name := "PhantomDSL2"

version := "0.1"

scalaVersion := "2.12.4"

libraryDependencies ++= Seq(
   "com.outworkers" %% "phantom-dsl" % "2.20.0",
   "com.typesafe" % "config" % "1.3.2",
   "com.outworkers" %% "phantom-streams" % "2.20.0",
   "com.outworkers"  %%  "util-testing" % "0.38.0" % Test,
   "org.scalatest" %% "scalatest" % "3.0.4" % Test
)