organization  := "com.activelogger"

version       := "0.1"

scalaVersion  := "2.11.7"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

resolvers ++= Seq(
  "Typesafe repository snapshots"    at "http://repo.typesafe.com/typesafe/snapshots/",
  "Typesafe repository releases"     at "http://repo.typesafe.com/typesafe/releases/",
  "Sonatype repo"                    at "https://oss.sonatype.org/content/groups/scala-tools/",
  "Sonatype releases"                at "https://oss.sonatype.org/content/repositories/releases",
  "Sonatype snapshots"               at "https://oss.sonatype.org/content/repositories/snapshots",
  "Sonatype staging"                 at "http://oss.sonatype.org/content/repositories/staging",
  "Java.net Maven2 Repository"       at "http://download.java.net/maven/2/",
  "Twitter Repository"               at "http://maven.twttr.com",
  "Websudos releases"                at "https://dl.bintray.com/websudos/oss-releases/"
)

libraryDependencies ++= {

  val akkaV = "2.3.9"
  val sprayV = "1.3.3"
  val phantomVersion = "1.25.4"

  Seq(
    "io.spray"            %%  "spray-can"                 % sprayV,
    "io.spray"            %%  "spray-testkit"             % sprayV  % "test",
    "io.spray"            %% "spray-routing-shapeless2"   % sprayV,
    "com.typesafe.akka"   %%  "akka-actor"                % akkaV,
    "com.typesafe.akka"   %%  "akka-testkit"              % akkaV   % "test",
    "org.specs2"          %%  "specs2-core"               % "2.3.11" % "test",
    "com.websudos"        %% "phantom-dsl"                % phantomVersion
  )

}

Revolver.settings
