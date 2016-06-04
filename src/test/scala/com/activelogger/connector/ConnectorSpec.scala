package com.activelogger.connector

import com.activelogger.test.helper.newConnector
import org.specs2.mutable.Specification


class DefaultConnectorSpec extends Specification {

  "Default Connector" should {

    "Load Hosts, Kespaces, Port, Username, Password" in {

      val keyspace = newConnector(
        """
          database {
            keyspaces = ["test1", "test2"]
            hosts = ["192.168.1.1"]
            port = 21
            userName = "user1"
            password = "password1"
          }
        """)

      keyspace.hosts must beEqualTo(List("192.168.1.1"))
      keyspace.keyspaces must beEqualTo(List("test1", "test2"))
      keyspace.port must beEqualTo(Some(21))
      keyspace.userName must beEqualTo("user1")
      keyspace.password must beEqualTo("password1")

    }

    "Load defaults" in {

      val keyspace = newConnector(
        """
          database { }
        """)

      keyspace.hosts must beEqualTo(List("localhost"))
      keyspace.keyspaces must beEqualTo(List("default"))
      keyspace.port must beEqualTo(None)
      keyspace.userName must beEqualTo("cassandra")
      keyspace.password must beEqualTo("cassandra")

    }

    // Cassandra should be instantiate on localhost for this test to be pass
    "Instantiate successfully with default configuration" in {

      val keyspace = newConnector(
        """
          database { }
        """)

      keyspace.connector.size must beEqualTo(1)

    }
  }
}

