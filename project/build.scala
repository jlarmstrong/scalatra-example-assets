import sbt._
import Keys._
import org.scalatra.sbt._
import org.scalatra.sbt.PluginKeys._
import com.mojolly.scalate.ScalatePlugin._
import ScalateKeys._

object MyExampleAssetsBuild extends Build {
  val Organization = "com.omnispear.example.assets"
  val Name = "My Example Assets"
  val Version = "0.1.0-SNAPSHOT"
  val ScalaVersion = "2.10.3"
  val ScalatraVersion = "2.2.2"

  def writeVersion(sourceManaged: sbt.SettingKey[java.io.File], name: String, vgp: String, buildVersion: String){
    println("test")
  }

  lazy val project = Project (
    "my-example-assets",
    file("."),
    settings = Defaults.defaultSettings ++ ScalatraPlugin.scalatraWithJRebel ++ scalateSettings ++ Seq(
      organization := Organization,
      name := Name,
      version := Version,
      scalaVersion := ScalaVersion,
      resolvers += Classpaths.typesafeReleases,
      libraryDependencies ++= Seq(
        "ro.isdc.wro4j" % "wro4j-core" % "1.4.0",
        "org.scalatra" %% "scalatra" % ScalatraVersion,
        "org.scalatra" %% "scalatra-scalate" % ScalatraVersion,
        "org.scalatra" %% "scalatra-specs2" % ScalatraVersion % "test",
        "ch.qos.logback" % "logback-classic" % "1.0.6" % "runtime",
        "org.eclipse.jetty" % "jetty-webapp" % "8.1.8.v20121106" % "container",
        "org.eclipse.jetty.orbit" % "javax.servlet" % "3.0.0.v201112011016" % "container;provided;test" artifacts (Artifact("javax.servlet", "jar", "jar"))
      ),

      scalateTemplateConfig in Compile <<= (sourceDirectory in Compile){ base =>
        Seq(
          TemplateConfig(
            base / "webapp" / "WEB-INF" / "templates",
            Seq("import "+Organization+".Version"), //Seq.empty,  /* default imports should be added here */
            Seq(
              Binding("context", "_root_.org.scalatra.scalate.ScalatraRenderContext", importMembers = true, isImplicit = true)
            ),  /* add extra bindings here */
            Some("templates")
          )
        )
      },
            // put this in your build.sbt, this will create a Version.scala file that reflects current build state
      sourceGenerators in Compile <+= (sourceManaged in Compile, name, organization, version) map {
        (sourceManaged: File, name: String, vgp: String, buildVersion) =>
          import java.util.Date
          val file = sourceManaged / vgp.replace(".", "/") / "Version.scala"
          val code =
            (
              if (vgp != null && vgp.nonEmpty) "package " + vgp + "\n"
              else ""
              ) +
              "object Version {\n" +
              "  val name\t= \"" + name + "\"\n" +
              "  val version\t= \"" + buildVersion + "\"\n" +
              "  val datetime\t= \"" + new Date().toString() + "\"\n" +
              "  val buildNumber\t=\""+System.getProperty("BUILD_NUMBER", "MANUAL_BUILD")+"\"\n" +
              "}\n"
          IO write(file, code)
          Seq(file)
      }
    )
)

// end build
}
