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
package csv

object LogConversionItemFile extends CsvFile {

  val subDir = "conversion-items"

  val headerRow = Array(
    "idsite",
    "idvisitor",
    "server_time",
    "idvisit",
    "idorder",
    "idaction_sku",
    "idaction_name",
    "idaction_category",
    "idaction_category2",
    "idaction_category3",
    "idaction_category4",
    "idaction_category5",
    "price",
    "quantity",
    "deleted"
  )
}