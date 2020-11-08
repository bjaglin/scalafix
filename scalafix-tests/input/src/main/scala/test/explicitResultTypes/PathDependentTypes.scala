/*
rules = ExplicitResultTypes
ExplicitResultTypes.skipSimpleDefinitions = ["Lit"]
*/
package test.explicitResultTypes

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

package object PackageObject2 extends Trait

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
  val fooRef = foo

  def foo2: PackageObject2.Foo = ???
  val foo2Ref = foo2

  def bar: PackageObject.Nested.Bar = ???
  val barRef = bar

  def qux: pkg.Obj.Qux = ???
  val quxRef = qux

  def baz: pkg.Obj.NestedObj.Baz = ???
  val bazRef = baz
}
