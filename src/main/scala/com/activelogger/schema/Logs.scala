package com.activelogger.schema

import java.util.UUID

import com.websudos.phantom.dsl._

case class Log(
                id: UUID,
                eventID: String,
                fileName: String,
                severity: Int,
                createdAt: DateTime,
                message: String,
                val tags: Set[String] = Set(),
                val customData: Map[String, String] = Map()
              )

sealed class Logs extends CassandraTable[ConcreteLogs, Log] {

  object id extends UUIDColumn(this) with PartitionKey[UUID]
  object eventID extends StringColumn(this) with ClusteringOrder[String] with Descending
  object fileName extends StringColumn(this) with ClusteringOrder[String] with Descending
  object severity extends IntColumn(this) with ClusteringOrder[Int] with Descending
  object createdAt extends DateTimeColumn(this) with ClusteringOrder[DateTime] with Descending
  object message extends StringColumn(this)
  object tags extends SetColumn[ConcreteLogs, Log ,String](this)
  object customData extends MapColumn[ConcreteLogs, Log, String, String](this)

  def fromRow(row: Row): Log = {
    Log(
      id(row),
      eventID(row),
      fileName(row),
      severity(row),
      createdAt(row),
      message(row),
      tags(row),
      customData(row)
    )
  }

}

abstract class ConcreteLogs extends Logs with RootConnector {

  override val tableName: String = "logs"

  def store(log: Log): concurrent.Future[ResultSet] = {

    insert
      .value(_.id, log.id)
      .value(_.eventID, log.eventID)
      .value(_.fileName, log.fileName)
      .value(_.severity, log.severity)
      .value(_.createdAt, log.createdAt)
      .value(_.message, log.message)
      .value(_.tags, log.tags)
      .value(_.customData, log.customData)
      .future()

  }

  def getByID(id: UUID): concurrent.Future[Option[Log]] = {
    select.where(_.id eqs id).one()
  }

}
