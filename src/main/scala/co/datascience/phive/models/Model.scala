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

trait Model {

  /**
   * A helper method, because opencsv expects to write an array to each line
   */
  def toArray: Array[String]

  implicit def int2CsvString(i: Int): String = i.toString
  implicit def optionString2CsvString(os: Option[String]) = os.getOrElse("")
  implicit def long2CsvString(l: Long): String = l.toString
}