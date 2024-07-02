ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.4.0"

lazy val root = (project in file("."))
  .settings(
    name := "actor-programming-challenge",
    scalacOptions ++= Seq(
      "-deprecation",
      "-feature",
      "-unchecked",
      "-Xlint",
      "-Ykind-projector"
    ),
    libraryDependencies ++= Seq(
      "org.wvlet.airframe" %% "airframe-ulid"             % "24.4.0",
      "org.apache.pekko"   %% "pekko-actor-typed"         % "1.0.2",
      "org.apache.pekko"   %% "pekko-slf4j"               % "1.0.2",
      "org.scalatest"      %% "scalatest"                 % "3.2.18" % Test,
      "org.apache.pekko"   %% "pekko-actor-testkit-typed" % "1.0.2"  % Test,
      "ch.qos.logback"      % "logback-classic"           % "1.5.5"  % Test
    ),
    semanticdbEnabled := true
  )

// --- Custom commands
addCommandAlias("lint", ";scalafmtCheck;test:scalafmtCheck;scalafmtSbtCheck;scalafixAll --check")
addCommandAlias("fmt", ";scalafixAll;scalafmtAll;scalafmtSbt;scalafixAll")
