package scalafix.internal.rule

import metaconfig.ConfDecoder
import metaconfig.ConfEncoder
import metaconfig.annotation.Description
import metaconfig.generic.Surface

case class RemoveUnusedConfig(
    @Description("Remove unused imports")
    imports: Boolean = true,
    @Description("Remove unused private members")
    privates: Boolean = true,
    @Description("Remove unused local definitions")
    locals: Boolean = true,
    @Description("Remove unused pattern match variables")
    patternvars: Boolean = true,
    @Description("Remove unused function parameters")
    params: Boolean = true
)

object RemoveUnusedConfig {
  val default: RemoveUnusedConfig = RemoveUnusedConfig()
  implicit val surface: Surface[RemoveUnusedConfig] =
    ConfigCompanionDerivation.removeUnusedSurface
  implicit val decoder: ConfDecoder[RemoveUnusedConfig] =
    ConfigCompanionDerivation.removeUnusedDecoder
  implicit val encoder: ConfEncoder[RemoveUnusedConfig] =
    ConfigCompanionDerivation.removeUnusedEncoder
}
