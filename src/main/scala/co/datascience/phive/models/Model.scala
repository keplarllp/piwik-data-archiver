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
package models

// Java
import java.util.{Date => JDate}
import java.sql.{Timestamp => JTimestamp}

trait Model {

  /**
   * A helper method, because opencsv expects to write an array to each line
   */
  def toArray: Array[String]

  // Simple conversions
  implicit def int2CsvString(i: Int): String = i.toString
  implicit def boolean2CsvString(b: Boolean): String = b.toString
  implicit def jtimestamp2CsvString(jt: JTimestamp): String = jt.toString

  // Array conversions
  implicit def byteArray2CsvString(ba: Array[Byte]): String = String.valueOf(ba)

  // Option conversions
  implicit def optionString2CsvString(os: Option[String]) = os.getOrElse("")
  implicit def optionJDate2CsvString(ojd: Option[JDate]) = ojd.map(_.toString).getOrElse("")
  implicit def optionFloat2CsvString(of: Option[Float]) = of.map(_.toString).getOrElse("")
  implicit def optionInt2CsvString(oi: Option[Int]) = oi.map(_.toString).getOrElse("")
}