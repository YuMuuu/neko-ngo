package example

import cats.effect.{ConcurrentEffect, ExitCode, IO, IOApp}
import example.Mongo._
import org.bson.Document

class Example extends IOApp {
  implicit val concurrentEffect: ConcurrentEffect[IO] = ConcurrentEffect[IO]

  override def run(args: List[String]): IO[ExitCode] = {
    fromUrl("mongodb://localhost:27017").use { mongoClient =>
      val database = mongoClient.getDatabase("mydb")
      val collection = database.getCollection("test")

      val doc = new Document("name", "MongoDB")
        .append("type", "database")
        .append("count", 1)
        .append("info", new Document("x", 203).append("y", 102));

      new MongoCollectionEffect(collection)
        .insertOne(doc)
        //なんか処理する
        .compile
        .drain
        .as(ExitCode.Success)
    }
  }
}
