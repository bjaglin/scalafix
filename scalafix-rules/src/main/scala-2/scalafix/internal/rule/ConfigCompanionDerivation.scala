package scalafix.internal.rule

import metaconfig._
import metaconfig.generic
import metaconfig.generic.Surface
import scalafix.internal.config.ScalafixMetaconfigReaders._

private[rule] object ConfigCompanionDerivation {
  def removeUnusedSurface: Surface[RemoveUnusedConfig] =
    generic.deriveSurface[RemoveUnusedConfig]
  def removeUnusedDecoder: ConfDecoder[RemoveUnusedConfig] =
    generic.deriveDecoder[RemoveUnusedConfig](RemoveUnusedConfig())
  def removeUnusedEncoder: ConfEncoder[RemoveUnusedConfig] =
    generic.deriveEncoder[RemoveUnusedConfig]

  def organizeImportsSurface: Surface[OrganizeImportsConfig] =
    generic.deriveSurface[OrganizeImportsConfig]
  def organizeImportsEncoder: ConfEncoder[OrganizeImportsConfig] =
    generic.deriveEncoder[OrganizeImportsConfig]
  def organizeImportsDecoder
      : ConfDecoderExT[OrganizeImportsConfig, OrganizeImportsConfig] =
    generic.deriveDecoderEx[OrganizeImportsConfig](OrganizeImportsConfig())

  def disableSyntaxSurface: Surface[DisableSyntaxConfig] =
    generic.deriveSurface[DisableSyntaxConfig]
  def disableSyntaxDecoder: ConfDecoder[DisableSyntaxConfig] =
    generic.deriveDecoder[DisableSyntaxConfig](DisableSyntaxConfig())

  def redundantSyntaxDecoder: ConfDecoder[RedundantSyntaxConfig] =
    generic.deriveDecoder[RedundantSyntaxConfig](RedundantSyntaxConfig())
  def redundantSyntaxSurface: Surface[RedundantSyntaxConfig] =
    generic.deriveSurface[RedundantSyntaxConfig]

  def explicitResultTypesDecoder: ConfDecoder[ExplicitResultTypesConfig] =
    generic.deriveDecoder[ExplicitResultTypesConfig](
      ExplicitResultTypesConfig()
    )
  def explicitResultTypesSurface: Surface[ExplicitResultTypesConfig] =
    generic.deriveSurface[ExplicitResultTypesConfig]
}
