package scalafix.internal.rule

import metaconfig.*
import metaconfig.generic.Surface
import scalafix.internal.compat.deriveDecoder
import scalafix.internal.compat.deriveDecoderEx
import scalafix.internal.compat.deriveEncoder
import scalafix.internal.compat.deriveSurface
import scalafix.internal.config.ScalafixMetaconfigReaders.*

private[rule] object ConfigCompanionDerivation {
  def removeUnusedSurface: Surface[RemoveUnusedConfig] =
    deriveSurface[RemoveUnusedConfig]
  def removeUnusedDecoder: ConfDecoder[RemoveUnusedConfig] =
    deriveDecoder[RemoveUnusedConfig](RemoveUnusedConfig())
  def removeUnusedEncoder: ConfEncoder[RemoveUnusedConfig] =
    deriveEncoder[RemoveUnusedConfig]

  def organizeImportsSurface: Surface[OrganizeImportsConfig] =
    deriveSurface[OrganizeImportsConfig]
  def organizeImportsEncoder: ConfEncoder[OrganizeImportsConfig] =
    deriveEncoder[OrganizeImportsConfig]
  def organizeImportsDecoder
      : ConfDecoderExT[OrganizeImportsConfig, OrganizeImportsConfig] =
    deriveDecoderEx[OrganizeImportsConfig](OrganizeImportsConfig())

  def disableSyntaxSurface: Surface[DisableSyntaxConfig] =
    deriveSurface[DisableSyntaxConfig]
  def disableSyntaxDecoder: ConfDecoder[DisableSyntaxConfig] =
    deriveDecoder[DisableSyntaxConfig](DisableSyntaxConfig())

  def redundantSyntaxDecoder: ConfDecoder[RedundantSyntaxConfig] =
    deriveDecoder[RedundantSyntaxConfig](RedundantSyntaxConfig())
  def redundantSyntaxSurface: Surface[RedundantSyntaxConfig] =
    deriveSurface[RedundantSyntaxConfig]

  def explicitResultTypesDecoder: ConfDecoder[ExplicitResultTypesConfig] =
    deriveDecoder[ExplicitResultTypesConfig](ExplicitResultTypesConfig())
  def explicitResultTypesSurface: Surface[ExplicitResultTypesConfig] =
    deriveSurface[ExplicitResultTypesConfig]
}
