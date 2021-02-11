import cats.effect.{ExitCode, IO, IOApp}
import com.mongodb.reactivestreams.client.MongoCollection
import mongo.{Mongo, MongoCollectionEffect}
import org.bson.Document
import org.bson.codecs.configuration.CodecRegistry

object Main extends IOApp {

  override def run(args: List[String]): IO[ExitCode] = {
    Mongo
      .fromUrl[IO]("mongodb://root:example@0.0.0.0:27017") // mongodb://${username}:${password}@${domain}:${port}"
      .use { mongoClient =>
        val database = mongoClient.getDatabase("example")
        val collection: MongoCollection[Document] = database.getCollection("example")

        //todo: case classからマッピングできるようにする
        //https://github.com/mongodb/mongo-scala-driver/blob/9c07d219a3ad693e41edf17bd80d4b45ff530a0a/driver/src/main/scala/org/mongodb/scala/MongoCollection.scala#L100
        //case class -> circe -> BSON が現実的？
        val doc = new Document("name", "MongoDB")
          .append("type", "database")
          .append("count", 1)
          .append("info", new Document("x", 203).append("y", 102))

        val mongoCollectionEffect = new MongoCollectionEffect[IO, Document](collection) //todo:型パラメータを渡さなくても動くようにする

        val insertOne = mongoCollectionEffect.insertOne(doc).compile.drain
        //todo: lowLevelAPIと highLevelApiを用意する？
        val find = mongoCollectionEffect.find().compile.lastOrError.handleErrorWith(_ => IO{new Document("error", "をにぎりつぶしたよ。多分findの結果が0件だよ")})

        for {
          f1 <- find
          _ <- IO { println(f1) }
          _ <- insertOne
          f2 <- find
          _ <- IO { println(f2) }
        } yield ()
      }
      .as(ExitCode.Success)
  }
}
