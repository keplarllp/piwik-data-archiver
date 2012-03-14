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
package models

// Java
import java.sql.{Timestamp => JTimestamp}

// Squeryl
import org.squeryl._
import org.squeryl.PrimitiveTypeMode._

// SnowPik
import csv.CsvFile

abstract class ServerTimedModel(
  val idsite: Long,
  val serverTime: JTimestamp) extends Model

class ExtractableServerTimedModel[T <: ServerTimedModel](table: Table[T]) extends Extractor[CsvFile] {

  /**
   * Exports this table to .csv
   */
  def ~>(logFile: CsvFile)(implicit siteId: Long, folder: String) {

    inTransaction {
      from (table)(t =>
        where(t.idsite === siteId)
        select(t)
        orderBy(t.serverTime)
      ).toList foreach(st => logFile.writeRow(st.toArray, st.serverTime))
    }
    logFile.close() // Close the last file
  }
}