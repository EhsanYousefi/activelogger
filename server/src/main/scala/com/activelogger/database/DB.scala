package com.activelogger.database

import com.activelogger.connector.DefaultConnector
import com.activelogger.schema.{ConcreteLogs, ConcreteUsers}
import com.websudos.phantom.dsl._

trait BaseDB {

  class BaseDatabase(override val connector: KeySpaceDef) extends Database(connector)

  lazy val defaultConnector = DefaultConnector.connector

  lazy val instances = defaultConnector.map { tuple =>
    (tuple._1, createInstance(tuple._2))
  }

  def init = {
    instances.map { m =>
      m._2.init
    }
  }

  def createInstance(cr: KeySpaceDef) = {
    new BaseDatabase(cr) {

      def init = {
        autocreate().future()
      }

      val logs = new {} with ConcreteLogs with connector.Connector
      val users = new {} with ConcreteUsers with connector.Connector
    }
  }

}

object DB extends BaseDB