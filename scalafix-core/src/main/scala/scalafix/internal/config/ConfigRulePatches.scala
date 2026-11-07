package scalafix.internal.config

import metaconfig.ConfDecoder
import metaconfig.generic.Surface
import scalafix.patch.Patch
import scalafix.patch.Patch.internal._

case class ConfigRulePatches(
    replaceSymbols: List[ReplaceSymbol] = Nil,
    addGlobalImports: List[AddGlobalImport] = Nil,
    removeGlobalImports: List[RemoveGlobalImport] = Nil
) {
  def all: List[Patch] =
    replaceSymbols ++ addGlobalImports ++ removeGlobalImports
}

object ConfigRulePatches {
  implicit val surface: Surface[ConfigRulePatches] =
    ConfigCompanionDerivation.configRulePatchesSurface
  val default: ConfigRulePatches = ConfigRulePatches()
  implicit val configRuleDecoder: ConfDecoder[ConfigRulePatches] =
    ConfigCompanionDerivation.configRulePatchesDecoder(default)
}
