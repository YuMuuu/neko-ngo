package example

import cats.effect.{Async, ConcurrentEffect, Resource, Sync}
import com.mongodb.MongoClientSettings
import com.mongodb.reactivestreams.client.{MongoClient, MongoClients, MongoCollection, MongoDatabase}
import org.bson.Document

// 参考にした: https://github.com/fiadliel/fs2-mongodb
object Mongo {
  def fromUrl[F[_]](url: String)(
      implicit F: Sync[F]): Resource[F, MongoClient] = {
    Resource.make(F.delay(MongoClients.create(url))) { client =>
      F.delay(client.close())
    }
  }

  def fromSettings[F[_]](settings: MongoClientSettings)(
      implicit F: Sync[F]): Resource[F, MongoClient] = {
    Resource.make(F.delay(MongoClients.create(settings)))(client =>
      F.delay(client.close()))
  }
}

import cats.effect.IO
import fs2.interop.reactivestreams._

class mongon[F[_]: ConcurrentEffect] {
  val mongoClient: MongoClient = MongoClients.create

  val database: MongoDatabase = mongoClient.getDatabase("mydb")

  val collection: MongoCollection[Document] = database.getCollection("test")

  val doc: Document = new Document("name", "MongoDB")
    .append("type", "database")
    .append("count", 1)
    .append("info", new Document("x", 203).append("y", 102));

  val stream = collection.insertOne(doc).toStream
}
