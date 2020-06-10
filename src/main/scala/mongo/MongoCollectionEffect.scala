package mongo

import java.util

import cats.effect.ConcurrentEffect
import com.mongodb.MongoNamespace
import com.mongodb.bulk.BulkWriteResult
import com.mongodb.client.model.{CountOptions, IndexModel, WriteModel}
import com.mongodb.client.result.{DeleteResult, UpdateResult}
import com.mongodb.reactivestreams.client.{ClientSession, MongoCollection}
import fs2.Stream
import fs2.interop.reactivestreams._
import org.bson.Document
import org.bson.conversions.Bson

import scala.collection.JavaConverters._

class MongoCollectionEffect[F[_]: ConcurrentEffect, A](
    val underlying: MongoCollection[A])
    extends AnyVal {
  //todo: com.mongodb.reactivestreams.client.MongoCollectionに実装されている関数をすべてラップする

// def getNamespace: MongoNamespace = ???
// def getDocumentClass: Class[Document] = ???
// def getCodecRegistry: CodecRegistry = ???
// def getReadPreference: ReadPreference = ???
// def getWriteConcern: WriteConcern = ???
// def getReadConcern: ReadConcern = ???
// def withDocumentClass[NewTDocument](clazz: Class[NewTDocument]): MongoCollection[NewTDocument] = ???

// def withReadPreference(readPreference: ReadPreference): MongoCollection[Document] = ???
// def withWriteConcern(writeConcern: WriteConcern): MongoCollection[Document] = ???
// def withReadConcern(readConcern: ReadConcern): MongoCollection[Document] = ???

  @Deprecated
  def count(): Stream[F, Long] =
    underlying.count().toStream.map(_.asInstanceOf[Long]) //convertの仕方あってる？

  @Deprecated
  def count(filter: Bson): Stream[F, Long] =
    underlying
      .count(filter)
      .toStream
      .map(_.asInstanceOf[Long]) //convertの仕方あってる？

  @Deprecated
  def count(filter: Bson, options: CountOptions): Stream[F, Long] =
    underlying.count(filter , options)
    .toStream()
    .map(_.asInstanceOf[Long])

  @Deprecated
  def count(clientSession: ClientSession): Stream[F, Unit] =
    underlying
      .count(clientSession)
      .toStream()
      .map(_.asInstanceOf[Long])

// def count(clientSession: ClientSession, filter: Bson): Publisher[lang.Long] = ???
  @Deprecated
  def count(clientSession: ClientSession, filter: Bson): Stream[F, Long] =
    underlying
      .count(clientSession, filter)
      .toStream()
      .map(_.asInstanceOf[Long])

// def count(clientSession: ClientSession, filter: Bson, options: CountOptions): Publisher[lang.Long] = ???

  def estimatedDocumentCount(): Stream[F, Long] =
    underlying
      .estimatedDocumentCount()
      .toStream()
      .map(_.asInstanceOf[Long])

// def estimatedDocumentCount(options: EstimatedDocumentCountOptions): Publisher[lang.Long] = ???

  def countDocuments(): Stream[F, Long] =
    underlying
      .countDocuments()
      .toStream()
      .map(_.asInstanceOf[Long])

// def countDocuments(filter: Bson): Publisher[lang.Long] = ???
  def countDocuments(filter: Bson): Stream[F, Long] =
    underlying
      .countDocuments(filter)
      .toStream()
      .map(_.asInstanceOf[Long])

// def countDocuments(filter: Bson, options: CountOptions): Publisher[lang.Long] = ???

  def countDocuments(clientSession: ClientSession): Stream[F, Long] =
    underlying
      .countDocuments(clientSession)
      .toStream()
      .map(_.asInstanceOf[Long])

  def countDocuments(clientSession: ClientSession,
                     filter: Bson): Stream[F, Long] =
    underlying
      .countDocuments(clientSession, filter)
      .toStream()
      .map(_.asInstanceOf[Long])
// def countDocuments(clientSession: ClientSession, filter: Bson, options: CountOptions): Publisher[lang.Long] = ???

// def distinct[TResult](fieldName: String, resultClass: Class[TResult]): DistinctPublisher[TResult] = ???
// def distinct[TResult](fieldName: String, filter: Bson, resultClass: Class[TResult]): DistinctPublisher[TResult] = ???
// def distinct[TResult](clientSession: ClientSession, fieldName: String, resultClass: Class[TResult]): DistinctPublisher[TResult] = ???
// def distinct[TResult](clientSession: ClientSession, fieldName: String, filter: Bson, resultClass: Class[TResult]): DistinctPublisher[TResult] = ???

// def find(): FindPublisher[Document] = ???
// def find[TResult](clazz: Class[TResult]): FindPublisher[TResult] = ???
// def find(filter: Bson): FindPublisher[Document] = ???
// def find[TResult](filter: Bson, clazz: Class[TResult]): FindPublisher[TResult] = ???
// def find(clientSession: ClientSession): FindPublisher[Document] = ???
// def find[TResult](clientSession: ClientSession, clazz: Class[TResult]): FindPublisher[TResult] = ???
// def find(clientSession: ClientSession, filter: Bson): FindPublisher[Document] = ???
// def find[TResult](clientSession: ClientSession, filter: Bson, clazz: Class[TResult]): FindPublisher[TResult] = ???

// def aggregate(pipeline: util.List[_ <: Bson]): AggregatePublisher[Document] = ???
// def aggregate[TResult](pipeline: util.List[_ <: Bson], clazz: Class[TResult]): AggregatePublisher[TResult] = ???
// def aggregate(clientSession: ClientSession, pipeline: util.List[_ <: Bson]): AggregatePublisher[Document] = ???
// def aggregate[TResult](clientSession: ClientSession, pipeline: util.List[_ <: Bson], clazz: Class[TResult]): AggregatePublisher[TResult] = ???

// def watch(): ChangeStreamPublisher[Document] = ???
// def watch[TResult](resultClass: Class[TResult]): ChangeStreamPublisher[TResult] = ???
// def watch(pipeline: util.List[_ <: Bson]): ChangeStreamPublisher[Document] = ???
// def watch[TResult](pipeline: util.List[_ <: Bson], resultClass: Class[TResult]): ChangeStreamPublisher[TResult] = ???
// def watch(clientSession: ClientSession): ChangeStreamPublisher[Document] = ???
// def watch[TResult](clientSession: ClientSession, resultClass: Class[TResult]): ChangeStreamPublisher[TResult] = ???
// def watch(clientSession: ClientSession, pipeline: util.List[_ <: Bson]): ChangeStreamPublisher[Document] = ???
// def watch[TResult](clientSession: ClientSession, pipeline: util.List[_ <: Bson], resultClass: Class[TResult]): ChangeStreamPublisher[TResult] = ???

// def mapReduce(mapFunction: String, reduceFunction: String): MapReducePublisher[Document] = ???
// def mapReduce[TResult](mapFunction: String, reduceFunction: String, clazz: Class[TResult]): MapReducePublisher[TResult] = ???
// def mapReduce(clientSession: ClientSession, mapFunction: String, reduceFunction: String): MapReducePublisher[Document] = ???
// def mapReduce[TResult](clientSession: ClientSession, mapFunction: String, reduceFunction: String, clazz: Class[TResult]): MapReducePublisher[TResult] = ???

  def bulkWrite(requests: List[WriteModel[A]]): Stream[F, BulkWriteResult] =
    underlying.bulkWrite(requests.asJava).toStream

// def bulkWrite(requests: util.List[_ <: WriteModel[_ <: Document]], options: BulkWriteOptions): Publisher[BulkWriteResult] = ???
// def bulkWrite(clientSession: ClientSession, requests: util.List[_ <: WriteModel[_ <: Document]]): Publisher[BulkWriteResult] = ???
  def bulkWrite(clientSession: ClientSession,
                requests: List[WriteModel[A]]): Stream[F, BulkWriteResult] =
    underlying.bulkWrite(clientSession, requests.asJava).toStream()

// def bulkWrite(clientSession: ClientSession, requests: util.List[_ <: WriteModel[_ <: Document]], options: BulkWriteOptions): Publisher[BulkWriteResult] = ???

  def insertOne(document: A): Stream[F, Unit] =
    underlying.insertOne(document).toStream.map(_ => ())

// def insertOne(document: Document, options: InsertOneOptions): Publisher[Success] = ???

// def insertOne(clientSession: ClientSession, document: Document): Publisher[Success] = ???
  def insertOne(clientSession: ClientSession,
                document: A): Stream[F, Unit] =
    underlying.insertOne(clientSession, document).toStream().map(_ => ())

// def insertOne(clientSession: ClientSession, document: Document, options: InsertOneOptions): Publisher[Success] = ???

  def insertMany(document: List[A]): Stream[F, Unit] =
    underlying.insertMany(document.asJava).toStream.map(_ => ())

// def insertMany(documents: util.List[_ <: Document], options: InsertManyOptions): Publisher[Success] = ???

  def insertMany(clientSession: ClientSession,
                 documents: List[A]): Stream[F, Unit] =
    underlying
      .insertMany(clientSession, documents.asJava)
      .toStream()
      .map(_ => ())

// def insertMany(clientSession: ClientSession, documents: util.List[_ <: Document], options: InsertManyOptions): Publisher[Success] = ???

  def deleteOne(filter: Bson): Stream[F, DeleteResult] =
    underlying.deleteOne(filter).toStream()

// def deleteOne(filter: Bson, options: DeleteOptions): Publisher[DeleteResult] = ???

  def deleteOne(clientSession: ClientSession, filter: Bson) =
    underlying.deleteOne(clientSession, filter).toStream()

// def deleteOne(clientSession: ClientSession, filter: Bson, options: DeleteOptions): Publisher[DeleteResult] = ???

  def deleteMany(filter: Bson): Stream[F, DeleteResult] =
    underlying.deleteMany(filter).toStream()

// def deleteMany(filter: Bson, options: DeleteOptions): Publisher[DeleteResult] = ???

  def deleteMany(clientSession: ClientSession,
                 filter: Bson): Stream[F, DeleteResult] =
    underlying.deleteMany(clientSession, filter).toStream()

// def deleteMany(clientSession: ClientSession, filter: Bson, options: DeleteOptions): Publisher[DeleteResult] = ???

// def replaceOne(filter: Bson, replacement: Document): Publisher[UpdateResult] = ???
  def replaceOne(filter: Bson, replacement: A): Stream[F, UpdateResult] =
    underlying.replaceOne(filter, replacement).toStream()

// def replaceOne(filter: Bson, replacement: Document, options: ReplaceOptions): Publisher[UpdateResult] = ???
// def replaceOne(filter: Bson, replacement: Document, options: UpdateOptions): Publisher[UpdateResult] = ???

  def replaceOne(clientSession: ClientSession,
                 filter: Bson,
                 replacement: A): Stream[F, UpdateResult] =
    underlying.replaceOne(clientSession, filter, replacement).toStream()

// def replaceOne(clientSession: ClientSession, filter: Bson, replacement: Document, options: ReplaceOptions): Publisher[UpdateResult] = ???
// def replaceOne(clientSession: ClientSession, filter: Bson, replacement: Document, options: UpdateOptions): Publisher[UpdateResult] = ???

  def updateOne(filter: Bson, update: Bson): Stream[F, UpdateResult] =
    underlying.updateOne(filter, update).toStream

// def updateOne(filter: Bson, update: Bson, options: UpdateOptions): Publisher[UpdateResult] = ???

  def updateOne(clientSession: ClientSession,
                filter: Bson,
                update: Bson): Stream[F, UpdateResult] =
    underlying.updateOne(clientSession, filter, update).toStream()

// def updateOne(clientSession: ClientSession, filter: Bson, update: Bson, options: UpdateOptions): Publisher[UpdateResult] = ???

  def updateOne(filter: Bson, update: List[Bson]): Stream[F, UpdateResult] =
    underlying.updateOne(filter, update.asJava).toStream()
// def updateOne(filter: Bson, update: util.List[_ <: Bson], options: UpdateOptions): Publisher[UpdateResult] = ???

  def updateOne(clientSession: ClientSession,
                filter: Bson,
                update: List[Bson]): Stream[F, UpdateResult] =
    underlying.updateOne(clientSession, filter, update.asJava).toStream()

// def updateOne(clientSession: ClientSession, filter: Bson, update: util.List[_ <: Bson], options: UpdateOptions): Publisher[UpdateResult] = ???

  def updateMany(filter: Bson, update: Bson): Stream[F, UpdateResult] =
    underlying.updateMany(filter, update).toStream

// def updateMany(filter: Bson, update: Bson, options: UpdateOptions): Publisher[UpdateResult] = ???

  def updateMany(clientSession: ClientSession,
                 filter: Bson,
                 update: Bson): Stream[F, UpdateResult] =
    underlying.updateMany(clientSession, filter, update).toStream()

// def updateMany(clientSession: ClientSession, filter: Bson, update: Bson, options: UpdateOptions): Publisher[UpdateResult] = ???

  def updateMany(filter: Bson, update: List[Bson]): Stream[F, UpdateResult] =
    underlying.updateMany(filter, update.asJava).toStream()

// def updateMany(filter: Bson, update: util.List[_ <: Bson], options: UpdateOptions): Publisher[UpdateResult] = ???

  def updateMany(clientSession: ClientSession,
                 filter: Bson,
                 update: List[Bson]): Stream[F, UpdateResult] =
    underlying.updateMany(clientSession, filter, update.asJava).toStream()

// def updateMany(clientSession: ClientSession, filter: Bson, update: util.List[_ <: Bson], options: UpdateOptions): Publisher[UpdateResult] = ???

  def findOneAndDelete(filter: Bson): Stream[F, Document] =
    underlying.find(filter).toStream()

// def findOneAndDelete(filter: Bson, options: FindOneAndDeleteOptions): Publisher[Document] = ???

  def findOneAndDelete(clientSession: ClientSession,
                       filter: Bson): Stream[F, Document] =
    underlying.findOneAndDelete(clientSession, filter).toStream()

// def findOneAndDelete(clientSession: ClientSession, filter: Bson, options: FindOneAndDeleteOptions): Publisher[Document] = ???

  def findOneAndReplace(filter: Bson, replacement: A): Stream[F, A] =
    underlying.findOneAndReplace(filter, replacement).toStream()

// def findOneAndReplace(filter: Bson, replacement: Document, options: FindOneAndReplaceOptions): Publisher[Document] = ???

  def findOneAndReplace(clientSession: ClientSession,
                        filter: Bson): Stream[F, A] =
    underlying.findOneAndDelete(clientSession, filter).toStream()

// def findOneAndReplace(clientSession: ClientSession, filter: Bson, replacement: Document, options: FindOneAndReplaceOptions): Publisher[Document] = ???

  def findOneAndUpdate(filter: Bson, update: Bson): Stream[F, A] =
    underlying.findOneAndUpdate(filter, update).toStream()

// def findOneAndUpdate(filter: Bson, update: Bson, options: FindOneAndUpdateOptions): Publisher[Document] = ???

  def findOneAndUpdate(clientSession: ClientSession,
                       filter: Bson,
                       update: Bson): Stream[F, A] =
    underlying.findOneAndUpdate(clientSession, filter, update).toStream()

// def findOneAndUpdate(clientSession: ClientSession, filter: Bson, update: Bson, options: FindOneAndUpdateOptions): Publisher[Document] = ???

  def findOneAndUpdate(filter: Bson, update: List[Bson]): Stream[F, A] =
    underlying.findOneAndUpdate(filter, update.asJava).toStream()

// def findOneAndUpdate(filter: Bson, update: util.List[_ <: Bson], options: FindOneAndUpdateOptions): Publisher[Document] = ???

  def findOneAndUpdate(clientSession: ClientSession,
                       filter: Bson,
                       update: List[Bson]): Stream[F, A] =
    underlying.findOneAndUpdate(clientSession, filter, update.asJava).toStream()

// def findOneAndUpdate(clientSession: ClientSession, filter: Bson, update: util.List[_ <: Bson], options: FindOneAndUpdateOptions): Publisher[Document] = ???

  def drop(): Stream[F, Unit] =
    underlying.drop().toStream().map(_ -> ())

  def drop(clientSession: ClientSession): Stream[F, Unit] =
    underlying.drop(clientSession).toStream().map(_ => ())

  def createIndex(key: Bson): Stream[F, String] =
    underlying.createIndex(key).toStream()

// def createIndex(key: Bson, options: IndexOptions): Publisher[String] = ???

  def createIndex(clientSession: ClientSession, key: Bson): Stream[F, String] =
    underlying.createIndex(clientSession, key).toStream()

// def createIndex(clientSession: ClientSession, key: Bson, options: IndexOptions): Publisher[String] = ???

  def createIndexes(indexes: List[IndexModel]): Stream[F, String] =
    underlying.createIndexes(indexes.asJava).toStream()

// def createIndexes(indexes: util.List[IndexModel], createIndexOptions: CreateIndexOptions): Publisher[String] = ???

// def createIndexes(clientSession: ClientSession, indexes: util.List[IndexModel]): Publisher[String] = ???
  def createIndexes(clientSession: ClientSession,
                    indexes: util.List[IndexModel]): Stream[F, String] =
    underlying.createIndexes(clientSession, indexes).toStream()

// def createIndexes(clientSession: ClientSession, indexes: util.List[IndexModel], createIndexOptions: CreateIndexOptions): Publisher[String] = ???

// def listIndexes(): ListIndexesPublisher[Document] = ???
// def listIndexes[TResult](clazz: Class[TResult]): ListIndexesPublisher[TResult] = ???
// def listIndexes(clientSession: ClientSession): ListIndexesPublisher[Document] = ???
// def listIndexes[TResult](clientSession: ClientSession, clazz: Class[TResult]): ListIndexesPublisher[TResult] = ???

  def dropIndex(indexName: String): Stream[F, Unit] =
    underlying.dropIndex(indexName).toStream().map(_ => ())

  def dropIndex(keys: Bson): Stream[F, Unit] =
    underlying.dropIndex(keys).toStream().map(_ => ())

// def dropIndex(indexName: String, dropIndexOptions: DropIndexOptions): Publisher[Success] = ???
//
// def dropIndex(keys: Bson, dropIndexOptions: DropIndexOptions): Publisher[Success] = ???
//
  def dropIndex(clientSession: ClientSession,
                indexName: String): Stream[F, Unit] =
    underlying.dropIndex(clientSession, indexName).toStream().map(_ => ())

  def dropIndex(clientSession: ClientSession, keys: Bson): Stream[F, Unit] =
    underlying.dropIndex(clientSession, keys).toStream().map(_ => ())

// def dropIndex(clientSession: ClientSession, indexName: String, dropIndexOptions: DropIndexOptions): Publisher[Success] = ???
// def dropIndex(clientSession: ClientSession, keys: Bson, dropIndexOptions: DropIndexOptions): Publisher[Success] = ???

  def dropIndexes(): Stream[F, Unit] =
    underlying.dropIndexes().toStream().map(_ => ())

// def dropIndexes(dropIndexOptions: DropIndexOptions): Publisher[Success] = ???

  def dropIndexes(clientSession: ClientSession): Stream[F, Unit] =
    underlying.dropIndexes(clientSession).toStream().map(_ => ())

// def dropIndexes(clientSession: ClientSession, dropIndexOptions: DropIndexOptions): Publisher[Success] = ???

  def renameCollection(
      newCollectionNamespace: MongoNamespace): Stream[F, Unit] =
    underlying.renameCollection(newCollectionNamespace).toStream().map(_ => ())

// def renameCollection(newCollectionNamespace: MongoNamespace, options: RenameCollectionOptions): Publisher[Success] = ???

  def renameCollection(
      clientSession: ClientSession,
      newCollectionNamespace: MongoNamespace): Stream[F, Unit] =
    underlying
      .renameCollection(clientSession, newCollectionNamespace)
      .toStream()
      .map(_ => ())

// def renameCollection(clientSession: ClientSession, newCollectionNamespace: MongoNamespace, options: RenameCollectionOptions): Publisher[Success] = ???

}
