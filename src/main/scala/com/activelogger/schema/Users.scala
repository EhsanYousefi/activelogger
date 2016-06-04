package com.activelogger.schema

import java.util.UUID
import com.websudos.phantom.dsl._

case class User(
                id: UUID,
                apiKey: String,
                email: String
               )

sealed class Users extends CassandraTable[ConcreteUsers, User] {

  object id extends UUIDColumn(this) with PartitionKey[UUID]
  object apiKey extends StringColumn(this) with PrimaryKey[String]
  object email extends StringColumn(this)

  def fromRow(row: Row): User = {
    User(
      id(row),
      apiKey(row),
      email(row)
    )
  }

}

abstract class ConcreteUsers extends Users with RootConnector {

  override val tableName: String = "users"

  def store(user: User): concurrent.Future[ResultSet] = {
    insert
      .value(_.id, user.id)
      .value(_.apiKey, user.apiKey)
      .value(_.email, user.email)
      .future()
  }

  def getByID(id: UUID): concurrent.Future[Option[User]] = {
    select.where(_.id eqs id).one()
  }

}