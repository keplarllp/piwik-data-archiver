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

/*
-- ----------------------------
-- Table structure for `piwik_log_conversion_item`
-- ----------------------------
DROP TABLE IF EXISTS `piwik_log_conversion_item`;
CREATE TABLE `piwik_log_conversion_item` (
  `idsite` int(10) unsigned NOT NULL,
  `idvisitor` binary(8) NOT NULL,
  `server_time` datetime NOT NULL,
  `idvisit` int(10) unsigned NOT NULL,
  `idorder` varchar(100) NOT NULL,
  `idaction_sku` int(10) unsigned NOT NULL,
  `idaction_name` int(10) unsigned NOT NULL,
  `idaction_category` int(10) unsigned NOT NULL,
  `idaction_category2` int(10) unsigned NOT NULL,
  `idaction_category3` int(10) unsigned NOT NULL,
  `idaction_category4` int(10) unsigned NOT NULL,
  `idaction_category5` int(10) unsigned NOT NULL,
  `price` float NOT NULL,
  `quantity` int(10) unsigned NOT NULL,
  `deleted` tinyint(1) unsigned NOT NULL,
  PRIMARY KEY (`idvisit`,`idorder`,`idaction_sku`),
  KEY `index_idsite_servertime` (`idsite`,`server_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
*/
class LogConversionItem(
  idsite: Int,
  idvisitor: Array[Byte],
  serverTime: JTimestamp,
  idvisit: Int,
  idorder: String,
  idactionSku: Int,
  idactionName: Int,
  idactionCategory: Int,
  idactionCategory2: Int,
  idactionCategory3: Int,
  idactionCategory4: Int,
  idactionCategory5: Int,
  price: Float, 
  quantity: Int, 
  deleted: Boolean) extends Model {

  /**
   * Add all the fields to the array in the right order
   */
  def toArray: Array[String] = Array(
    idsite,
    idvisitor,
    serverTime,
    idvisit,
    idorder,
    idactionSku,
    idactionName,
    idactionCategory,
    idactionCategory2,
    idactionCategory3,
    idactionCategory4,
    idactionCategory5,
    price,
    quantity,
    deleted)
}
