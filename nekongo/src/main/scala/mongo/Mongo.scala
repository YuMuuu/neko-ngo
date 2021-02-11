package mongo

import cats.effect.{Resource, Sync}
import com.mongodb.MongoClientSettings
import com.mongodb.reactivestreams.client.{MongoClient, MongoClients, MongoCollection}
import org.bson.Document
import org.bson.codecs.configuration.CodecRegistry

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
