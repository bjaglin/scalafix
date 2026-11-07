package scalafix.internal.config

import metaconfig.ConfDecoder
import metaconfig.generic
import metaconfig.generic.Surface
import scalafix.internal.config.ScalafixConfig.scalaVersionDecoder

private[config] object ConfigCompanionDerivation {
  def lintSurface: Surface[LintConfig] = generic.deriveSurface[LintConfig]
  def lintDecoder(default: LintConfig): ConfDecoder[LintConfig] =
    generic.deriveDecoder[LintConfig](default)

  def debugSurface: Surface[DebugConfig] = generic.deriveSurface[DebugConfig]
  def debugDecoder(default: DebugConfig): ConfDecoder[DebugConfig] =
    generic.deriveDecoder[DebugConfig](default)

  def configRulePatchesSurface: Surface[ConfigRulePatches] =
    generic.deriveSurface[ConfigRulePatches]
  def configRulePatchesDecoder(
      default: ConfigRulePatches
  ): ConfDecoder[ConfigRulePatches] =
    generic.deriveDecoder[ConfigRulePatches](default)

  def scalafixConfigDecoder(
      default: ScalafixConfig
  ): ConfDecoder[ScalafixConfig] =
    generic.deriveDecoder[ScalafixConfig](default)
  def scalafixConfigSurface: Surface[ScalafixConfig] =
    generic.deriveSurface[ScalafixConfig]
}
