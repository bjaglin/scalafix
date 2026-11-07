package scalafix.internal.v1

import java.net.URLClassLoader

import scala.meta.io.AbsolutePath
import scala.meta.io.Classpath

import metaconfig.ConfDecoder
import metaconfig.ConfEncoder
import metaconfig.generic.Surface
import scalafix.internal.compat.deriveDecoder
import scalafix.internal.compat.deriveEncoder
import scalafix.internal.compat.deriveSurface
import scalafix.internal.v1.Args.callbackDecoder
import scalafix.internal.v1.Args.callbackEncoder
import scalafix.internal.v1.Args.charsetDecoder
import scalafix.internal.v1.Args.charsetEncoder
import scalafix.internal.v1.Args.classLoaderEncoder
import scalafix.internal.v1.Args.classpathEncoder
import scalafix.internal.v1.Args.confEncoder
import scalafix.internal.v1.Args.pathEncoder
import scalafix.internal.v1.Args.pathMatcherDecoder
import scalafix.internal.v1.Args.pathMatcherEncoder
import scalafix.internal.v1.Args.printStreamDecoder
import scalafix.internal.v1.Args.printStreamEncoder
import scalafix.internal.v1.Args.scalaVersionDecoder
import scalafix.internal.v1.Args.scalaVersionEncoder
import scalafix.internal.v1.Args.toolboxDecoder
import scalafix.internal.v1.Args.toolboxEncoder

private[v1] object ArgsCompanionDerivation {
  def decoder(base: Args)(implicit
      classpathDecoder: ConfDecoder[Classpath],
      classLoaderDecoder: ConfDecoder[URLClassLoader],
      absolutePathDecoder: ConfDecoder[AbsolutePath]
  ): ConfDecoder[Args] = deriveDecoder[Args](base)

  val encoder: ConfEncoder[Args] = deriveEncoder[Args]
  val surface: Surface[Args] = deriveSurface[Args]
}
