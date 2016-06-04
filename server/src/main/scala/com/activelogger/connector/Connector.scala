package com.activelogger.connector

import com.typesafe.config.ConfigFactory
import com.websudos.phantom.connectors.ContactPoints

import scala.collection.JavaConversions._
import scala.util.Try

trait BaseConnector {

  def config = ConfigFactory.load("activelogger.conf")
  def keyspaces: List[String] = Try(config.getStringList("database.keyspaces").toList).getOrElse(List("default"))
  def hosts: List[String] = Try(config.getStringList("database.hosts").toList).getOrElse(List("localhost"))
  def port: Option[Int] = Try(Some(config.getInt("database.port"))).getOrElse(None)
  def userName: String  = Try(config.getString("database.userName")).getOrElse("cassandra")
  def password: String  = Try(config.getString("database.password")).getOrElse("cassandra")

  lazy val connector = port match {

    case Some(v) => keyspaces.map { key =>
      ( key, ContactPoints(hosts, v).withClusterBuilder(_.withCredentials(userName, password)).keySpace(key) )
    }.toMap

    case None => keyspaces.map { key =>
      ( key, ContactPoints(hosts).withClusterBuilder(_.withCredentials(userName, password)).keySpace(key) )
    }.toMap

  }

}

object DefaultConnector extends BaseConnector