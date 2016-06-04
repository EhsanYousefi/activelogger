package com.activelogger.database

import java.util.UUID

import com.activelogger.schema.User
import com.activelogger.test.helper._
import com.websudos.phantom.dsl.ResultSet
import org.specs2.matcher.ResultMatchers
import org.specs2.mutable.Specification


class DBSpec extends Specification with ResultMatchers {

  val connector = newConnector(
      """
          database {
            keyspaces = ["test1", "test2"]
            hosts = ["127.0.0.1"]
          }
      """)

  val db = new BaseDB {
    override lazy val defaultConnector = connector.connector
  }

  "Database" should {

    "Instantiate successfully" in {

      db.instances.size must beEqualTo(2)

    }

    "Insert record in itself" in {

      val user = User(UUID.randomUUID, UUID.randomUUID.toString, "ehsan.yousefi@live.com")
      val g = db.instances("test1").users.store(user)
      Thread.sleep(2000)
      db.instances("test1").users.getByID(user.id) must beEqualTo(Some(user)).await

    }

    "Create itself schema successfully" in {

      var a: List[ResultSet] = List()

      db.init.map { x =>
        x.map { y =>
          y.map { z =>
            a = z :: a
          }
        }
      }

      a must haveClass[::[ResultSet]]

    }

  }
}