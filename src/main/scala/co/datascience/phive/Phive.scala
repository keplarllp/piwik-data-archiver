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

// Squeryl
import org.squeryl._
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.adapters.MySQLAdapter

// opencsv
import au.com.bytecode.opencsv._

// Amazon S3
import org.jets3t.service.security.AWSCredentials
import org.jets3t.service.impl.rest.httpclient.RestS3Service

// Phive
import models.PiwikSchema
import csv._

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

    // From the export section
    private val export = phive.getConfig("export")
    val folder         = export.getString("folder")

    // From the upload section
    private val upload = phive.getConfig("upload")
    val key            = upload.getString("key")
    val secret         = upload.getString("secret")
    val bucket         = upload.getString("bucket")
  }

  // Instantiate our schema with the appropriate table prefix
  private val PrefixedSchema = PiwikSchema(PhiveConfig.prefix)

  // Let's initialize the db connection once
  Class.forName("com.mysql.jdbc.Driver")
    SessionFactory.concreteFactory = Some(() => Session.create(
      java.sql.DriverManager.getConnection(PhiveConfig.connection, PhiveConfig.username, PhiveConfig.password), new MySQLAdapter)
  )

  // Let's create our Amazon S3 client once
  private val creds = new AWSCredentials(PhiveConfig.key, PhiveConfig.secret)
  private val S3 = new RestS3Service(creds)

  /**
   * Executes the export
   */
  def run() {

    // First let's output the LogAction table. The simplest as there is no timestamping on this one
    LogAction.initCsv()
    inTransaction {
      from (PrefixedSchema.logAction)(r => select(r)).toList foreach(la => LogAction.writeRow(la.toArray))
    }
	  LogAction.finalizeCsv()

    // Now let's loop through and perform the same process with the other four tables...
    // TODO

    // Finally let's upload if required
    if (upload) {
      LogAction.uploadCsv(S3, PhiveConfig.bucket)
    }
  }
}