package test.explicitResultTypes

import test.explicitResultTypes.PackageObject.{ Foo, Nested }
import test.explicitResultTypes.pkg.Obj
// like https://github.com/tpolecat/doobie/blob/c2e044/modules/core/src/main/scala/doobie/free/Aliases.scala#L10
trait Trait {
  class Foo // like https://github.com/tpolecat/doobie/blob/c2e0445/modules/core/src/main/scala/doobie/free/Aliases.scala#L14
  object Nested {
    class Bar
  }
}

class Clazz {
  trait Qux
}

// like https://github.com/tpolecat/doobie/blob/c2e0445/modules/core/src/main/scala/doobie/hi/package.scala#L25
package object PackageObject extends Trait

package pkg {
  abstract class AbstractClazz {
    trait Baz
  }
  object Obj extends Clazz {
    object NestedObj extends AbstractClazz
  }
}

trait PathDependentTypes {
  // like https://github.com/tpolecat/doobie/blob/c2e0445/modules/core/src/main/scala/doobie/util/query.scala#L163
  def foo: PackageObject.Foo = ???
  val fooRef: Foo = foo

  def bar: PackageObject.Nested.Bar = ???
  val barRef: Nested.Bar = bar

  def qux: pkg.Obj.Qux = ???
  val quxRef: Obj.Qux = qux

  def baz: pkg.Obj.NestedObj.Baz = ???
  val bazRef: Obj.NestedObj.Baz = baz
}
