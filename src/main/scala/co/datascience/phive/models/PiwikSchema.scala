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

// Squeryl
import org.squeryl._
import PrimitiveTypeMode._

/**
 * Squeryl schema definition for the Piwik log files
 */
case class PiwikSchema(prefix: String) extends Schema {

  // Auto-translate Scala camelCase field names into lower_underscore field names
  override def columnNameFromPropertyName(n: String) =
    SquerylNamingConventionTransforms.camelCase2LowerUnderscore(n)

  // Map classes to actual table names
  val logAction = table[LogAction](this.prefix + "log_action")
  val logConversion = table[LogConversion](this.prefix + "log_conversion")
  val logConversionVisit = table[LogConversionVisit](this.prefix + "log_conversion_visit")
  // TODO: add other 4 tables in here
}
