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
package csv

// Java
import java.io.{File, FileWriter}
import java.sql.{Timestamp => JTimestamp}
import java.text.SimpleDateFormat

// opencsv
import au.com.bytecode.opencsv._

// Amazon S3
import org.jets3t.service.impl.rest.httpclient.RestS3Service

// SnowPik
import s3.S3Utils

/**
 * Abstract class for defining one of our five CSV flatfiles
 */
abstract class CsvFile {

  protected val subDir: String
  protected val headerRow: Array[String]

  protected var writer: Option[CSVWriter] = None
  private var lastDate: Option[String] = None

  // Based on subDir. Lazy so we don't initialize it before this abstract class has been made concrete
  private lazy val dir = "tables/%s".format(subDir) // TODO: should make this OS-agnostic

  /**
   * Writes out a row to our CSV file.
   */
  def writeRow(row: Array[String], timestamp: JTimestamp)(implicit folder: String) {

    val date = new SimpleDateFormat("yyyy-MM-dd").format(timestamp)

    // If timestamp doesn't match last row's, time to open a new file
    if (lastDate.isEmpty || lastDate.get != date) {
      if (writer.isDefined) this.close()
      writer = Some(this.open(folder, date))
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
  protected def open(folder: String, date: String): CSVWriter = {

    // Create the host folder (and parents) if they don't already exist TODO: should make this OS-agnostic
    val f = new File("%s%s".format(folder, dir))
    f.mkdirs()

    // Define the full file path TODO: should make this OS-agnostic
    val file = "%s/day=%s".format(f.getPath, date) // folder has trailing slash

    // Now initialize the CSVWriter, write the header and return it
    val w = new CSVWriter(new FileWriter(file), CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER)
    w.writeNext(headerRow)
    w
  }

  /**
   * Finalise our CSV.
   */
  def close() {
    writer.get.close()
  }

  /**
   * Upload our CSV files.
   */
  def ->(bucket: String)(implicit s3: RestS3Service, folder: String) {

    // TODO: need to join folder onto dir

    Option(new File(folder + dir).listFiles) match { // TODO: should make this OS-agnostic
      case Some(logs) => logs.map(f => S3Utils.uploadFile(f, bucket, s3))
      case None => println("No files to upload in %s folder, skipping".format(subDir))
    }
  }
}

abstract class CsvFileNoTimestamp extends CsvFile {

  /**
   * Writes out a row to our CSV file.
   * Overridden because this is much
   * simpler than the standard behaviour.
   */
  def writeRow(row: Array[String])(implicit folder: String) {

    if (writer.isEmpty) writer = Some(this.open(folder, "all"))

    if (row.length != headerRow.length)
      throw new IllegalArgumentException("Fields in row (%s) do not match fields in header (%s)".format(row.length, headerRow.length))

    writer.get.writeNext(row)
  }
}