package test

import com.softwaremill.macwire.wire

object MissingSymbol {
  val a = wire[Bar]
}

class Bar()