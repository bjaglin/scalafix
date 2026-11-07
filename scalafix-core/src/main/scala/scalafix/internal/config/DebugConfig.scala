package scalafix.internal.config

import metaconfig.ConfDecoder
import metaconfig.generic.Surface

case class DebugConfig(
    printSymbols: Boolean = false
)
object DebugConfig {
  implicit val surface: Surface[DebugConfig] =
    ConfigCompanionDerivation.debugSurface
  val default: DebugConfig = DebugConfig()
  implicit val decoder: ConfDecoder[DebugConfig] =
    ConfigCompanionDerivation.debugDecoder(default)
}
