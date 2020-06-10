package example

import cats.effect.ConcurrentEffect
import com.mongodb.bulk.BulkWriteResult
import com.mongodb.client.model.WriteModel
import com.mongodb.client.result.UpdateResult
import com.mongodb.reactivestreams.client.{MongoCollection, Success}
import fs2.Stream
import fs2.interop.reactivestreams._
import org.bson.conversions.Bson
import collection.JavaConverters._

class MongoCollectionEffect[F[_]: ConcurrentEffect, A](
    val underlying: MongoCollection[A])
    extends AnyVal {
  //todo: com.mongodb.reactivestreams.client.MongoCollectionに実装されている関数をすべてラップする

  def bulkWrite(requests: List[WriteModel[A]]): Stream[F, BulkWriteResult] =
    underlying.bulkWrite(requests.asJava).toStream

  def count(): Stream[F, Long] =
    underlying.count().toStream.map(_.asInstanceOf[Long]) //convertの仕方あってる？

  def count(filter: Bson): Stream[F, Long] =
    underlying
      .count(filter)
      .toStream
      .map(_.asInstanceOf[Long]) //convertの仕方あってる？

  def insertOne(document: A): Stream[F, Unit] =
    underlying.insertOne(document).toStream.map(_ => ())

  def insertMany(document: Seq[A]): Stream[F, Unit] =
    underlying.insertMany(document.asJava).toStream.map(_ => ())

  def updateOne(filter: Bson, update: Bson): Stream[F, UpdateResult] =
    underlying.updateOne(filter, update).toStream

  def updateMany(filter: Bson, update: Bson): Stream[F, UpdateResult] =
    underlying.updateMany(filter, update).toStream
}
