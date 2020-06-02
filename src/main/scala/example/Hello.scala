package example

import cats.~>
import cats.free.Free

object Hello extends Greeting with App {
  println(greeting)
}

trait Greeting {
  lazy val greeting: String = "hello"
}

object connection {
  sealed trait ConnectionOp[A] {
    def visit[F[_]](v: ConnectionOp.Visitor[F]): F[A]
  }

  type ConnectionIO[A] = Free[ConnectionOp, A]

  object ConnectionOp {
    trait Visitor[F[_]] extends (ConnectionOp ~> F)
  }
}