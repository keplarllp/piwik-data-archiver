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
package models

// Java
import java.sql.{Timestamp => JTimestamp}

// Squeryl
import org.squeryl._
import org.squeryl.PrimitiveTypeMode._

// SnowPlow
import csv.CsvFile

/*
-- ----------------------------
-- Table structure for `piwik_log_visit`
-- ----------------------------
DROP TABLE IF EXISTS `piwik_log_visit`;
CREATE TABLE `piwik_log_visit` (
  `idvisit` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `idsite` int(10) unsigned NOT NULL,
  `idvisitor` binary(8) NOT NULL,
  `visitor_localtime` time NOT NULL,
  `visitor_returning` tinyint(1) NOT NULL,
  `visitor_count_visits` smallint(5) unsigned NOT NULL,
  `visitor_days_since_last` smallint(5) unsigned NOT NULL,
  `visitor_days_since_order` smallint(5) unsigned NOT NULL,
  `visitor_days_since_first` smallint(5) unsigned NOT NULL,
  `visit_first_action_time` datetime NOT NULL,
  `visit_last_action_time` datetime NOT NULL,
  `visit_exit_idaction_url` int(11) unsigned NOT NULL,
  `visit_exit_idaction_name` int(11) unsigned NOT NULL,
  `visit_entry_idaction_url` int(11) unsigned NOT NULL,
  `visit_entry_idaction_name` int(11) unsigned NOT NULL,
  `visit_total_actions` smallint(5) unsigned NOT NULL,
  `visit_total_time` smallint(5) unsigned NOT NULL,
  `visit_goal_converted` tinyint(1) NOT NULL,
  `visit_goal_buyer` tinyint(1) NOT NULL,
  `referer_type` tinyint(1) unsigned DEFAULT NULL,
  `referer_name` varchar(70) DEFAULT NULL,
  `referer_url` text NOT NULL,
  `referer_keyword` varchar(255) DEFAULT NULL,
  `config_id` binary(8) NOT NULL,
  `config_os` char(3) NOT NULL,
  `config_browser_name` varchar(10) NOT NULL,
  `config_browser_version` varchar(20) NOT NULL,
  `config_resolution` varchar(9) NOT NULL,
  `config_pdf` tinyint(1) NOT NULL,
  `config_flash` tinyint(1) NOT NULL,
  `config_java` tinyint(1) NOT NULL,
  `config_director` tinyint(1) NOT NULL,
  `config_quicktime` tinyint(1) NOT NULL,
  `config_realplayer` tinyint(1) NOT NULL,
  `config_windowsmedia` tinyint(1) NOT NULL,
  `config_gears` tinyint(1) NOT NULL,
  `config_silverlight` tinyint(1) NOT NULL,
  `config_cookie` tinyint(1) NOT NULL,
  `location_ip` varbinary(16) NOT NULL,
  `location_browser_lang` varchar(20) NOT NULL,
  `location_country` char(3) NOT NULL,
  `location_continent` char(3) NOT NULL,
  `custom_var_k1` varchar(200) DEFAULT NULL,
  `custom_var_v1` varchar(200) DEFAULT NULL,
  `custom_var_k2` varchar(200) DEFAULT NULL,
  `custom_var_v2` varchar(200) DEFAULT NULL,
  `custom_var_k3` varchar(200) DEFAULT NULL,
  `custom_var_v3` varchar(200) DEFAULT NULL,
  `custom_var_k4` varchar(200) DEFAULT NULL,
  `custom_var_v4` varchar(200) DEFAULT NULL,
  `custom_var_k5` varchar(200) DEFAULT NULL,
  `custom_var_v5` varchar(200) DEFAULT NULL,
  `location_provider` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idvisit`),
  KEY `index_idsite_config_datetime` (`idsite`,`config_id`,`visit_last_action_time`),
  KEY `index_idsite_datetime` (`idsite`,`visit_last_action_time`),
  KEY `index_idsite_idvisitor` (`idsite`,`idvisitor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
*/
class LogVisit(
  val idvisit: Int,
  val idsite: Int,
  val idvisitor: Array[Byte],
  val visitorLocaltime: String, // TODO: check this works
  val visitorReturning: Boolean,
  val visitorCountVisits: Int,
  val visitorDaysSinceLast: Int,
  val visitorDaysSinceOrder: Int,
  val visitorDaysSinceFirst: Int,
  val visitFirstActionTime: JTimestamp,
  val visitLastActionTime: JTimestamp,
  val visitExitIdactionUrl: Int,
  val visitExitIdactionName: Int,
  val visitEntryIdactionUrl: Int,
  val visitEntryIdactionName: Int,
  val visitTotalActions: Int,
  val visitTotalTime: Int,
  val visitGoalConverted: Boolean,
  val visitGoalBuyer: Boolean,
  val refererType: Option[Boolean],
  val refererName: Option[String],
  val refererUrl: String,
  val refererKeyword: Option[String],
  val configId: Array[Byte],
  val configOs: String,
  val configBrowserName: String,
  val configBrowserVersion: String,
  val configResolution: String,
  val configPdf: Boolean,
  val configFlash: Boolean,
  val configJava: Boolean,
  val configDirector: Boolean,
  val configQuicktime: Boolean,
  val configRealplayer: Boolean,
  val configWindowsmedia: Boolean,
  val configGears: Boolean,
  val configSilverlight: Boolean,
  val configCookie: Boolean,
  val locationIp: Array[Byte],
  val locationBrowserLang: String,
  val locationCountry: String,
  val locationContinent: String,
  val customVarK1: Option[String],
  val customVarV1: Option[String],
  val customVarK2: Option[String],
  val customVarV2: Option[String],
  val customVarK3: Option[String],
  val customVarV3: Option[String],
  val customVarK4: Option[String],
  val customVarV4: Option[String],
  val customVarK5: Option[String],
  val customVarV5: Option[String],
  val locationProvider: Option[String]
  ) extends Model {
  
  
  /**
   * Add all the fields to the array in the right order
   */
  def toArray: Array[String] = Array(
    idvisit,
    idsite,
    idvisitor,
    visitorLocaltime,
    visitorReturning,
    visitorCountVisits,
    visitorDaysSinceLast,
    visitorDaysSinceOrder,
    visitorDaysSinceFirst,
    visitFirstActionTime,
    visitLastActionTime,
    visitExitIdactionUrl,
    visitExitIdactionName,
    visitEntryIdactionUrl,
    visitEntryIdactionName,
    visitTotalActions,
    visitTotalTime,
    visitGoalConverted,
    visitGoalBuyer,
    refererType,
    refererName,
    refererUrl,
    refererKeyword,
    configId,
    configOs,
    configBrowserName,
    configBrowserVersion,
    configResolution,
    configPdf,
    configFlash,
    configJava,
    configDirector,
    configQuicktime,
    configRealplayer,
    configWindowsmedia,
    configGears,
    configSilverlight,
    configCookie,
    locationIp,
    locationBrowserLang,
    locationCountry,
    locationContinent,
    customVarK1,
    customVarV1,
    customVarK2,
    customVarV2,
    customVarK3,
    customVarV3,
    customVarK4,
    customVarV4,
    customVarK5,
    customVarV5,
    locationProvider
  )

  /**
   * Exports this table to .csv
   */
  def ~>(logFile: CsvFile) {

    inTransaction {
      from (this)(t =>
        where(t.idsite === siteId)
        select(t)
        orderBy(t.visitLastActionTime)
      ).toList foreach(logFile.writeRow(_.toArray))
    }
  }
}