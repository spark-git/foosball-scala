import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "forms"
    val appVersion      = "1.0"

    val appDependencies = Seq(
      // Add your project dependencies here,
        "mysql" % "mysql-connector-java" % "5.1.18" 
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings( 
    		lessEntryPoints <<= baseDirectory(customLessEntryPoints)
    )

    
    def customLessEntryPoints(base: File): PathFinder = (
        (base / "app" / "assets" / "stylesheets" / "bootstrap" * "bootstrap.less") +++
        (base / "app" / "assets" / "stylesheets" * "*.less"))

}
