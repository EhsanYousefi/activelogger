package com.activelogger.test

import com.activelogger.connector.BaseConnector
import com.typesafe.config.ConfigFactory

package object helper {

  def configParser(config: String) = {
    ConfigFactory.parseString(config)
  }

  def newConnector(config: String) = {
    val newConfig = configParser(config)

    new BaseConnector {
      override val config = newConfig
    }
  }

}