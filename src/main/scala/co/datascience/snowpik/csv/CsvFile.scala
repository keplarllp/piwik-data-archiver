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
import java.text.SimpleDateFormat

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

  val subDir: String
  val headerRow: Array[String]

  protected var writer: Option[CSVWriter] = None
  protected var lastDate: Option[String] = None

  /**
   * Writes out a row to our CSV file.
   */
  def writeRow(row: Array[String], timestamp: JTimestamp) {

    val date = new SimpleDateFormat("yyyy-MM-dd").format(timestamp)

    // If timestamp doesn't match last row's, time to open a new file
    if (lastDate.isEmpty || lastDate.get != date) {
      if (writer.isDefined) finalizeCsv()
      writer = Some(initCsv(date))
    }

    if (row.length != headerRow.length)
      throw new IllegalArgumentException("Fields in row (%s) do not match fields in header (%s)".format(row.length, headerRow.length))

    writer.get.writeNext(row)
    lastDate = Some(date)
  }

  /**
   * Initializes our CSVWriter object, writes out the
   * appropriate header row and returns it.
   */
  protected def initCsv(date: String): CSVWriter = {

    // First setup the folder
    val dir = "tables/%s/dt=%s".format(subDir, date)
    new File(dir).mkdir() // TODO: add error handling

    // Now set the full file path
    val filePath = "%s/%s.log".format(dir, date)

    // Now initialize the CSVWriter, write the header and return it
    val w = new CSVWriter(new FileWriter(filePath), CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER)
    w.writeNext(headerRow)
    w
  }

  /**
   * Finalise our CSV.
   */
  def finalizeCsv() {
    writer.get.close()
  }

  /**
   * Upload our CSV file.
   */
  def uploadCsv(s3: RestS3Service, bucket: String) {

    // Create the S3 object from the file
    val file = new File(subDir) // TODO: update this
    val s3Object = new S3Object(file)

    // Give the S3 object public ACL based on the owning bucket's ACL
    val publicAcl: AccessControlList = s3.getBucketAcl(bucket)
    publicAcl.grantPermission(GroupGrantee.ALL_USERS, Permission.PERMISSION_READ)
    s3Object.setAcl(publicAcl)

    // Upload the file to the bucket
    s3.putObject(bucket, s3Object)
  }
}