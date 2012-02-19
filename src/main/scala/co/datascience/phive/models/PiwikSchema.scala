package co.datascience.phive
package models

// Squeryl
import org.squeryl._
import PrimitiveTypeMode._

// xxx
import SquerylNamingConventionTransforms

/**
 * Squeryl schema definition for Orderly MDM database
 */
object MdmSchema extends Schema {

  // Auto-translate Scala camelCase field names into lower_underscore field names
  override def columnNameFromPropertyName(n:String) =
    SquerylNamingConventionTransforms.camelCase2LowerUnderscore(n)

  // Map classes to actual table names
  val xxx         = table[xxx]("xxx")
}
