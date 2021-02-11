import cats.effect.{ExitCode, IO, IOApp}
import com.mongodb.reactivestreams.client.MongoCollection
import mongo.{Mongo, MongoCollectionEffect}
import org.bson.Document


object Main extends IOApp {

  override def run(args: List[String]): IO[ExitCode] = {
    Mongo
      .fromUrl[IO]("mongodb://root:example@0.0.0.0:27017")  // mongodb://${username}:${password}@${domain}:${port}"
      .use { mongoClient =>
            val database = mongoClient.getDatabase("example")
            val collection: MongoCollection[Document] = database.getCollection("example")

           //todo: case classからマッピングできるようにする
            val doc = new Document("name", "MongoDB")
              .append("type", "database")
              .append("count", 1)
              .append("info", new Document("x", 203).append("y", 102))

            val mongoCollectionEffect = new MongoCollectionEffect[IO, Document](collection) //todo:型パラメータを渡さなくても動くようにする

            val insertOne = mongoCollectionEffect
              .insertOne(doc)
              .compile
              .drain
            insertOne
      }
      .as(ExitCode.Success)
  }
}
