package scalafix.internal.config

import metaconfig.ConfDecoder
import metaconfig.generic.Surface
import scalafix.internal.compat.deriveDecoder
import scalafix.internal.compat.deriveSurface
import scalafix.internal.config.ScalafixConfig.scalaVersionDecoder

private[config] object ConfigCompanionDerivation {
  def lintSurface: Surface[LintConfig] = deriveSurface[LintConfig]
  def lintDecoder(default: LintConfig): ConfDecoder[LintConfig] =
    deriveDecoder[LintConfig](default)

  def debugSurface: Surface[DebugConfig] = deriveSurface[DebugConfig]
  def debugDecoder(default: DebugConfig): ConfDecoder[DebugConfig] =
    deriveDecoder[DebugConfig](default)

  def configRulePatchesSurface: Surface[ConfigRulePatches] =
    deriveSurface[ConfigRulePatches]
  def configRulePatchesDecoder(
      default: ConfigRulePatches
  ): ConfDecoder[ConfigRulePatches] =
    deriveDecoder[ConfigRulePatches](default)

  def scalafixConfigDecoder(
      default: ScalafixConfig
  ): ConfDecoder[ScalafixConfig] =
    deriveDecoder[ScalafixConfig](default)
  def scalafixConfigSurface: Surface[ScalafixConfig] =
    deriveSurface[ScalafixConfig]
}
