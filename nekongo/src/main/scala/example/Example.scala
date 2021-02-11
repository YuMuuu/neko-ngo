package example

import cats.effect.{ConcurrentEffect, ExitCode, IO, IOApp}
import mongo.Mongo._
import mongo.MongoCollectionEffect
import org.bson.Document


//todo: intergration=testに移す
//note: integration-testと example packageに分ける？

class Example extends IOApp {
  implicit val concurrentEffect: ConcurrentEffect[IO] = ConcurrentEffect[IO]

  override def run(args: List[String]): IO[ExitCode] = {
    fromUrl("mongodb://localhost:27017").use { mongoClient =>
      val database = mongoClient.getDatabase("mydb")
      val collection = database.getCollection("test")

      val doc = new Document("name", "MongoDB")
        .append("type", "database")
        .append("count", 1)
        .append("info", new Document("x", 203).append("y", 102))


      case class _Document(name: String, `type`: String, count: Int, info: Point)
      case class Point(x: Int, y: Int)
      val doc2 = _Document("name", "MongoDB", 1, Point(203, 102))
      // これを↑のような DocumentTypeにしたい

      new MongoCollectionEffect(collection)
        .insertOne(doc)
        .compile
        .drain
        .as(ExitCode.Success)
    }
  }
}

