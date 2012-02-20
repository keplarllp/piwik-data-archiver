/*
 * Copyright (c) 2012 Orderly Ltd. All rights reserved.
 *
 * This program is licensed to you under the Apache License Version 2.0,
 * and you may not use this file except in compliance with the Apache License Version 2.0.
 * You may obtain a copy of the Apache License Version 2.0 at http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the Apache License Version 2.0 is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Apache License Version 2.0 for the specific language governing permissions and limitations there under.
 */
package co.datascience.phive

// Config
import com.typesafe.config.Config

// Phive
import co.datascience.phive.models._

/**
 * Phive performs the Piwik data export and upload
 */
case class Phive(config: Config,
                 period: TimePeriod.Value,
                 upload: Boolean) {

  /**
   * Immediately construct a PhiveConfig object which
   * holds all of our configuration variables.
   */
  object PhiveConfig {

    // Get the top level
    private val phive = config.getConfig("phive")

    // From the db section
    private val db = phive.getConfig("db")
    val username   = db.getString("username")
    val password   = db.getString("password")
    val prefix     = db.getString("prefix")
    val connection = "jdbc:mysql://%s:%s/%s".format(db.getString("server"),
                                                    db.getString("port"),
                                                    db.getString("database"))

    // From the s3 section
    private val s3 = phive.getConfig("s3")
    val key        = s3.getString("key")
    val secret     = s3.getString("secret")
    val bucket     = s3.getString("bucket")
  }

  // Instantiate our schema with the appropriate table prefix
  private val PrefixedSchema = PiwikSchema(PhiveConfig.prefix)

  /**
   * Executes the export
   */
  def run() {
    Console.println("running!")
  }
}