import sbt._

class InflectorProject(info: ProjectInfo) extends DefaultProject(info) {
  
  
  val repo = "nexus" at "http://nexus-direct.scala-tools.org/content/repositories/releases"
  
  val specs = "org.scala-tools.testing" %% "specs" % "1.6.2" withSources
}