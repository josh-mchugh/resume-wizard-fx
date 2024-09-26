import mill._, scalalib._

object app extends RootModule with ScalaModule {
  def scalaVersion = "3.3.3"

  def resolutionCustomizer = T.task {
     Some( (r: coursier.core.Resolution) =>
       r.withOsInfo(coursier.core.Activation.Os.fromProperties(sys.props.toMap))
     )
   }

  def ivyDeps = {
    Agg(
      ivy"org.scalafx::scalafx:22.0.0-R33",
      ivy"org.controlsfx:controlsfx:11.2.1",
      ivy"org.openjfx:javafx-base:22.0.2",
      ivy"org.openjfx:javafx-controls:22.0.2",
      ivy"org.openjfx:javafx-web:22.0.2",
      ivy"org.greenrobot:eventbus-java:3.3.1",
      ivy"org.slf4j:slf4j-api:2.0.16",
      ivy"org.slf4j:slf4j-log4j12:2.0.16"
    )
  }
}
