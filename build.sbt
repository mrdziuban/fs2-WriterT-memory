lazy val proj = project.in(file(".")).settings(
  name := "fs2-WriterT-memory",
  organization := "com.example",
  scalaVersion := "2.13.8",
  version      := "0.1.0-SNAPSHOT",
  addCompilerPlugin("org.typelevel" % "kind-projector" % "0.13.2" cross CrossVersion.full),
  libraryDependencies ++= Seq(
    "co.fs2" %% "fs2-core" % "3.2.7",
  ),
  run / fork := true,
  run / javaOptions ++= Seq("-Xms128m", "-Xmx128m"),
)
