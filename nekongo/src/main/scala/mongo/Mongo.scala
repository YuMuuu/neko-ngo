package mongo

import cats.effect.{Async, IO, Resource, Sync}
import com.mongodb.MongoClientSettings
import com.mongodb.reactivestreams.client.{
  MongoClient,
  MongoClients,
  MongoCollection
}
import org.bson.Document
import org.bson.codecs.configuration.CodecRegistry

import    com.mongodb.reactivestreams.client.MongoClients

// 参考にした: https://github.com/fiadliel/fs2-mongodb
object Mongo {
  //todo: urlの値をrefinedで制限する
  def fromUrl[F[_]](url: String)(
      implicit F: Sync[F]): Resource[F, MongoClient] = {
    Resource.make {
      F.delay(MongoClients.create(url)) //<- ぬるぽになる
    } { client =>
      F.delay(client.close())
    }
  }

  def fromSettings[F[_]](settings: MongoClientSettings)(
      implicit F: Sync[F]): Resource[F, MongoClient] = {
    Resource.make(F.delay(MongoClients.create(settings)))(client =>
      F.delay(client.close()))
  }
}

//具体的な型での実装
object MongoIO {
  def fromUrl(url: String): Resource[IO, MongoClient] = {
    Resource.make {
      IO(MongoClients.create(url))
    } { client =>
      IO(client.close()).handleErrorWith(_ => IO.unit) //力強くエラーを握りつぶす
    }
  }
}
