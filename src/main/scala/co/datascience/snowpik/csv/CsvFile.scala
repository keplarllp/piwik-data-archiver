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
package co.datascience.snowpik
package csv

// Java
import java.io.{File, FileWriter}
import java.sql.{Timestamp => JTimestamp}

// opencsv
import au.com.bytecode.opencsv._

// Amazon S3
import org.jets3t.service.model.S3Object
import org.jets3t.service.acl.{Permission, GroupGrantee, AccessControlList}
import org.jets3t.service.impl.rest.httpclient.RestS3Service

/**
 * Abstract class for defining one of our five CSV flatfiles
 */
abstract class CsvFile {

  val filename: String

  val header: Array[String]

  // Which field in the index is the dateIndex we use to partition our files?
  val dateIndex: Int

  protected var writer: Option[CSVWriter] = None
  protected var lastTimestamp: Option[JTimestamp] = None

  /**
   * Initializes our CSVWriter object, writes out the
   * appropriate header row and returns it.
   */
  protected def initCsv(timestamp: JTimestamp): CSVWriter = {

    // Check if a folder with the year-month exists. If not, create it.
    // TODO

    writer = new CSVWriter(new FileWriter(filename), CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER)
    writer.writeNext(header)
    writer
  }

  /**
   * Writes out a row to our CSV file.
   */
  def writeRow(row: Array[String], timestamp: JTimestamp) {

    // If timestamp doesn't match last row's, time to open a new file
    if (!lastTimestamp.isDefined || lastTimestamp.get != timestamp) {
      if (writer.isDefined) finalizeCsv()
      writer = initCsv(timestamp)
    }

    if (row.length != header.length)
      throw new IllegalArgumentException("Fields in row (%s) do not match fields in header (%s)".format(row.length, header.length))

    writer.writeNext(row)
  }

  /**
   * Finalise our CSV.
   */
  def finalizeCsv() {
    writer.close()
  }

  /**
   * Upload our CSV file.
   */
  def uploadCsv(s3: RestS3Service, bucket: String) {

    // Create the S3 object from the file
    val file = new File(filename)
    val s3Object = new S3Object(file)

    // Give the S3 object public ACL based on the owning bucket's ACL
    val publicAcl: AccessControlList = s3.getBucketAcl(bucket)
    publicAcl.grantPermission(GroupGrantee.ALL_USERS, Permission.PERMISSION_READ)
    s3Object.setAcl(publicAcl)

    // Upload the file to the bucket
    s3.putObject(bucket, s3Object)
  }
}