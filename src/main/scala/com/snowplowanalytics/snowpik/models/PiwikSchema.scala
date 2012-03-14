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

/**
 * Squeryl schema definition for the Piwik log files
 */
case class PiwikSchema(prefix: String) extends Schema {

  // Auto-translate Scala camelCase field names into lower_underscore field names
  override def columnNameFromPropertyName(n: String) = camelCase2LowerUnderscore(n)

  // Map classes to actual table names
  val logAction = table[LogAction](this.prefix + "log_action")
  val logConversion = table[LogConversion](this.prefix + "log_conversion")
  val logConversionItem = table[LogConversionItem](this.prefix + "log_conversion_item")
  val logLinkVisitAction = table[LogLinkVisitAction](this.prefix + "log_link_visit_action")
  val logVisit = table[LogVisit](this.prefix + "log_visit")

  /**
   * Naming convention transform for Squeryl.
   * Changes a camelCasedField to a lowercased_field
   */
  private def camelCase2LowerUnderscore(name: String) =
    name.toList.map(c => if(c.isUpper) "_" + c.toLower else c).mkString
}

/**
 * Our pimps to add more functionality to our tables
 */
object ExtractablePimps {

  implicit def pimpLogAction(table: Table[LogAction]) = new ExtractableLogAction(table)
  implicit def pimpServerTimedModel[T <: ServerTimedModel](table: Table[T]) = new ExtractableServerTimedModel[T](table)
  implicit def pimpLogVisit(table: Table[LogVisit]) = new ExtractableLogVisit(table)
}