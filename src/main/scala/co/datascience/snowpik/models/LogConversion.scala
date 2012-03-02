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
import java.util.{Date => JDate}
import java.sql.{Timestamp => JTimestamp}

/*
-- ----------------------------
-- Table structure for `piwik_log_conversion`
-- ----------------------------
DROP TABLE IF EXISTS `piwik_log_conversion`;
CREATE TABLE `piwik_log_conversion` (
  `idvisit` int(10) unsigned NOT NULL,
  `idsite` int(10) unsigned NOT NULL,
  `idvisitor` binary(8) NOT NULL,
  `server_time` datetime NOT NULL,
  `idaction_url` int(11) DEFAULT NULL,
  `idlink_va` int(11) DEFAULT NULL,
  `referer_visit_server_date` date DEFAULT NULL,
  `referer_type` int(10) unsigned DEFAULT NULL,
  `referer_name` varchar(70) DEFAULT NULL,
  `referer_keyword` varchar(255) DEFAULT NULL,
  `visitor_returning` tinyint(1) NOT NULL,
  `visitor_count_visits` smallint(5) unsigned NOT NULL,
  `visitor_days_since_first` smallint(5) unsigned NOT NULL,
  `visitor_days_since_order` smallint(5) unsigned NOT NULL,
  `location_country` char(3) NOT NULL,
  `location_continent` char(3) NOT NULL,
  `url` text NOT NULL,
  `idgoal` int(10) NOT NULL,
  `buster` int(10) unsigned NOT NULL,
  `idorder` varchar(100) DEFAULT NULL,
  `items` smallint(5) unsigned DEFAULT NULL,
  `revenue` float DEFAULT NULL,
  `revenue_subtotal` float DEFAULT NULL,
  `revenue_tax` float DEFAULT NULL,
  `revenue_shipping` float DEFAULT NULL,
  `revenue_discount` float DEFAULT NULL,
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
  PRIMARY KEY (`idvisit`,`idgoal`,`buster`),
  UNIQUE KEY `unique_idsite_idorder` (`idsite`,`idorder`),
  KEY `index_idsite_datetime` (`idsite`,`server_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
*/
class LogConversion(
  val idvisit: Int,
  val idsite: Int,
  val idvisitor: Array[Byte],
  val serverTime: JTimestamp,
  val idactionUrl: Option[Int],
  val idlinkVa: Option[Int],
  val refererVisitServerDate: Option[JDate],
  val refererType: Option[Int],
  val refererName: Option[String],
  val refererKeyword: Option[String],
  val visitorReturning: Boolean,
  val visitorCountVisits: Int,
  val visitorDaysSinceFirst: Int,
  val visitorDaysSinceOrder: Int,
  val locationCountry: String,
  val locationContinent: String,
  val url: String,
  val idgoal: Int,
  val buster: Int,
  val idorder: Option[String],
  val items: Int,
  val revenue: Option[Float],
  val revenueTax: Option[Float],
  val revenueShipping: Option[Float],
  val revenueDiscount: Option[Float],
  val customVarK1: Option[String],
  val customVarV1: Option[String],
  val customVarK2: Option[String],
  val customVarV2: Option[String],
  val customVarK3: Option[String],
  val customVarV3: Option[String],
  val customVarK4: Option[String],
  val customVarV4: Option[String],
  val customVarK5: Option[String],
  val customVarV5: Option[String]
  ) extends ServerTimedModel(idsite, serverTime) {

  /**
  * Add all the fields to the array in the right order
  */
  def toArray: Array[String] = Array(
    idvisit,
    idsite,
    idvisitor,
    serverTime,
    idactionUrl,
    idlinkVa,
    refererVisitServerDate,
    refererType,
    refererName,
    refererKeyword,
    visitorReturning,
    visitorCountVisits,
    visitorDaysSinceFirst,
    visitorDaysSinceOrder,
    locationCountry,
    locationContinent,
    url,
    idgoal,
    buster,
    idorder,
    items,
    revenue,
    revenueTax,
    revenueShipping,
    revenueDiscount,
    customVarK1,
    customVarV1,
    customVarK2,
    customVarV2,
    customVarK3,
    customVarV3,
    customVarK4,
    customVarV4,
    customVarK5,
    customVarV5)
}
