import sbt._
import Keys._

object PlayFlowBuild extends Build {

  import uk.gov.hmrc.DefaultBuildSettings
  import DefaultBuildSettings._
  import uk.gov.hmrc.{SbtBuildInfo, ShellPrompt}

  val nameApp = "play-flow"
  val versionApp = "0.1.0"

  val appDependencies = {
    import Dependencies._

    Seq(
      Compile.play,
      Test.scalaTest,
      Test.pegdown
    )
  }

  lazy val playFlow = Project(nameApp, file("."))
    .settings(version := versionApp)
    .settings(scalaSettings : _*)
    .settings(defaultSettings() : _*)
    .settings(
      targetJvm := "jvm-1.7",
      shellPrompt := ShellPrompt(versionApp),
      libraryDependencies ++= appDependencies,
      resolvers := Seq(
        Opts.resolver.sonatypeReleases,
        Opts.resolver.sonatypeSnapshots,
        "typesafe-releases" at "http://repo.typesafe.com/typesafe/releases/",
        "typesafe-snapshots" at "http://repo.typesafe.com/typesafe/snapshots/"
      ),
      crossScalaVersions := Seq("2.11.4", "2.10.4"),
      publishArtifact := true,
      publishArtifact in Test := true
    )
    .settings(SbtBuildInfo(): _*)
    .settings(SonatypeBuild(): _*)

}

object Dependencies {

  object Compile {
    val play = "com.typesafe.play" %% "play-json" % "2.3.2" % "provided"
  }

  sealed abstract class Test(scope: String) {

    val scalaTest = "org.scalatest" %% "scalatest" % "2.2.0" % scope
    val scalaCheck = "org.scalacheck" %% "scalacheck" % "1.11.5" % scope
    val pegdown = "org.pegdown" % "pegdown" % "1.4.2" % scope
  }

  object Test extends Test("test")

  object IntegrationTest extends Test("it")

}


object SonatypeBuild {

  import xerial.sbt.Sonatype._

  def apply() = {
    sonatypeSettings ++ Seq(
      pomExtra := (<url>http://liquid-armour.co.uk</url>
        <licenses>
          <license>
            <name>Apache 2</name>
            <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
          </license>
        </licenses>
        <scm>
          <connection>scm:git@github.com:liquidarmour/play-flow.git</connection>
          <developerConnection>scm:git@github.com:liquidarmour/play-flow.git</developerConnection>
          <url>git@github.com:liquidarmour/play-flow.git</url>
        </scm>
        <developers>
          <developer>
            <id>liquidarmour</id>
            <name>James Williams</name>
            <url>http://liquid-armour.co.uk</url>
          </developer>
        </developers>)
    )
  }
}