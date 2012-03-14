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

// Scalaz
import scalaz._
import Scalaz._

/*
-- ----------------------------
-- Table structure for `piwik_log_link_visit_action`
-- ----------------------------
DROP TABLE IF EXISTS `piwik_log_link_visit_action`;
CREATE TABLE `piwik_log_link_visit_action` (
  `idlink_va` int(11) NOT NULL AUTO_INCREMENT,
  `idsite` int(10) unsigned NOT NULL,
  `idvisitor` binary(8) NOT NULL,
  `server_time` datetime NOT NULL,
  `idvisit` int(10) unsigned NOT NULL,
  `idaction_url` int(10) unsigned NOT NULL,
  `idaction_url_ref` int(10) unsigned NOT NULL,
  `idaction_name` int(10) unsigned DEFAULT NULL,
  `idaction_name_ref` int(10) unsigned NOT NULL,
  `time_spent_ref_action` int(10) unsigned NOT NULL,
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
  PRIMARY KEY (`idlink_va`),
  KEY `index_idvisit` (`idvisit`),
  KEY `index_idsite_servertime` (`idsite`,`server_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
*/
class LogLinkVisitAction(
  val idlinkVa: Long,
  override val idsite: Long,
  val idvisitor: Array[Byte],
  override val serverTime: JTimestamp,
  val idvisit: Long,
  val idactionUrl: Long,
  val idactionUrlRef: Long,
  val idactionName: Long,
  val idactionNameRef: Long,
  val timeSpentRefAction: Long,
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
    idlinkVa,
    idsite,
    idvisitor,
    serverTime,
    idvisit,
    idactionUrl,
    idactionUrlRef,
    idactionName,
    idactionNameRef,
    timeSpentRefAction,
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

  /**
   * Zero-argument constructor that Longializes each Option field to a default Some() value using Scalaz
   */
  def this() = this(
    ∅[Long],
    ∅[Long],
    ∅[Array[Byte]],
    new JTimestamp(System.currentTimeMillis),
    ∅[Long],
    ∅[Long],
    ∅[Long],
    ∅[Long],
    ∅[Long],
    ∅[Long],
    Some(∅[String]),
    Some(∅[String]),
    Some(∅[String]),
    Some(∅[String]),
    Some(∅[String]),
    Some(∅[String]),
    Some(∅[String]),
    Some(∅[String]),
    Some(∅[String]),
    Some(∅[String]) 
  )
}