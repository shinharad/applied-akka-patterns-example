name := "applied-akka-patterns-example"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.12.1"

lazy val root = (project in file("."))
  .settings(
    libraryDependencies ++= {
      val akkaV = "2.5.4"
      Seq(
        "com.typesafe.akka" %% "akka-actor" % akkaV
//        "com.typesafe.akka" %% "akka-remote" % akkaV,
//        "com.typesafe.akka" %% "akka-cluster" % akkaV,
//        "com.typesafe.akka" %% "akka-cluster-tools" % akkaV,
//        "com.typesafe.akka" %% "akka-cluster-sharding" % akkaV,
//        "com.typesafe.akka" %% "akka-cluster-metrics" % akkaV,
//        "com.typesafe.akka" %% "akka-persistence" % akkaV,
//        "com.typesafe.akka" %% "akka-contrib" % akkaV,
//        "org.iq80.leveldb" % "leveldb" % "0.7",
//        "org.fusesource.leveldbjni" % "leveldbjni-all" % "1.8"
      )
    }
  )
