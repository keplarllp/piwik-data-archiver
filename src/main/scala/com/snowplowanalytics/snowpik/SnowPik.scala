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
package com.snowplowanalytics.snowpik

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
import models._
import models.ExtractablePimps._
import csv._

/**
 * SnowPik performs the Piwik data export and upload
 */
case class SnowPik(config: Config,
                 period: TimePeriod.Value,
                 upload: Boolean) {

  /**
   * Immediately construct a SnowPikConfig object which
   * holds all of our configuration variables.
   */
  object SnowPikConfig {

    // Get the top level
    private val snowpik = config.getConfig("snowpik")

    // From the db section
    private val db = snowpik.getConfig("db")
    val username   = db.getString("username")
    val password   = db.getString("password")
    val prefix     = db.getString("prefix")
    val connection = "jdbc:mysql://%s:%s/%s".format(db.getString("server"),
                                                    db.getString("port"),
                                                    db.getString("database"))

    // From the export section
    private val export = snowpik.getConfig("export")
    val folder         = export.getString("folder")

    // From the upload section
    private val upload = snowpik.getConfig("upload")
    val key            = upload.getString("key")
    val secret         = upload.getString("secret")
    val bucket         = upload.getString("bucket")
  }

  // Instantiate our schema with the appropriate table prefix
  private val PrefixedSchema = PiwikSchema(SnowPikConfig.prefix)

  // Let's initialize the db connection once
  Class.forName("com.mysql.jdbc.Driver")
    SessionFactory.concreteFactory = Some(() => Session.create(
      java.sql.DriverManager.getConnection(SnowPikConfig.connection, SnowPikConfig.username, SnowPikConfig.password), new MySQLAdapter)
  )

  // Let's create our Amazon S3 client once
  private val creds = new AWSCredentials(SnowPikConfig.key, SnowPikConfig.secret)
  implicit private val S3 = new RestS3Service(creds)

  /**
   * Executes the export
   */
  def run(siteId: Int) {

    // siteId and folder used implicitly by ~>
    implicit val id = siteId
    implicit val folder = SnowPikConfig.folder

    PrefixedSchema.logAction          ~> LogActionFile
    PrefixedSchema.logConversion      ~> LogConversionFile
    PrefixedSchema.logConversionItem  ~> LogConversionItemFile
    PrefixedSchema.logLinkVisitAction ~> LogLinkVisitActionFile
    PrefixedSchema.logVisit           ~> LogVisitFile

    // Upload if required
    if (upload) {
      LogActionFile          -> SnowPikConfig.bucket
      LogConversionFile      -> SnowPikConfig.bucket
      LogConversionItemFile  -> SnowPikConfig.bucket
      LogLinkVisitActionFile -> SnowPikConfig.bucket
      LogVisitFile           -> SnowPikConfig.bucket
    }
  }
}