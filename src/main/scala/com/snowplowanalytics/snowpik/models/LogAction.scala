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

// Squeryl
import org.squeryl._
import PrimitiveTypeMode._

// Scalaz
import scalaz._
import Scalaz._

// SnowPik
import csv.CsvFileNoTimestamp

/*
-- ----------------------------
-- Table structure for `piwik_log_action`
-- ----------------------------
DROP TABLE IF EXISTS `piwik_log_action`;
CREATE TABLE `piwik_log_action` (
  `idaction` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` text,
  `hash` int(10) unsigned NOT NULL,
  `type` tinyint(3) unsigned DEFAULT NULL,
  PRIMARY KEY (`idaction`),
  KEY `index_type_hash` (`type`,`hash`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 */
case class LogAction(
  idaction: Long,
  name:     Option[String],
  hash:     Long,
  `type`:   Int
  ) extends Model {

  /**
   * Add all the fields to the array in the right order
   */
  def toArray: Array[String] =
    Array(this.idaction, this.name, this.hash, this.`type`)

  /**
   * Zero-argument constructor that intializes each Option field to a default Some() value using Scalaz
   */
  def this() = this(∅[Long], Some(∅[String]), ∅[Long], ∅[Int])
}

class ExtractableLogAction(table: Table[LogAction]) extends Extractor[CsvFileNoTimestamp] {

  /**
   * Exports this table to .csv
   */
  def ~>(logFile: CsvFileNoTimestamp)(implicit siteId: Long, folder: String) {

    inTransaction {
      from (table)(t =>
        select(t)
      ).toList foreach(a => logFile.writeRow(a.toArray))
    }
    logFile.close() // Close the last file
  }
}
