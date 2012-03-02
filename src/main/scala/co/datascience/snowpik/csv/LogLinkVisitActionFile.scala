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

object LogLinkVisitActionFile extends CsvFile {

  val filename = "link-visit-action.csv"

  val header = Array(
    "idlink_va",
    "idsite",
    "idvisitor",
    "server_time",
    "idvisit",
    "idaction_url",
    "idaction_url_ref",
    "idaction_name",
    "idaction_name_ref",
    "time_spent_ref_action",
    "custom_var_k1",
    "custom_var_v1",
    "custom_var_k2",
    "custom_var_v2",
    "custom_var_k3",
    "custom_var_v3",
    "custom_var_k4",
    "custom_var_v4",
    "custom_var_k5",
    "custom_var_v5"
  )

  val dateIndex = 3 // server_time
}