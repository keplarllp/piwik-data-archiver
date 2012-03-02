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

object LogVisitFile extends CsvFile {

  val filename = "visit.csv"

  val header = Array(
    "idvisit",
    "idsite",
    "idvisitor",
    "visitor_localtime",
    "visitor_returning",
    "visitor_count_visits",
    "visitor_days_since_last",
    "visitor_days_since_order",
    "visitor_days_since_first",
    "visit_first_action_time",
    "visit_last_action_time",
    "visit_exit_idaction_url",
    "visit_exit_idaction_name",
    "visit_entry_idaction_url",
    "visit_entry_idaction_name",
    "visit_total_actions",
    "visit_total_time",
    "visit_goal_converted",
    "visit_goal_buyer",
    "referer_type",
    "referer_name",
    "referer_url",
    "referer_keyword",
    "config_id",
    "config_os",
    "config_browser_name",
    "config_browser_version",
    "config_resolution",
    "config_pdf",
    "config_flash",
    "config_java",
    "config_director",
    "config_quicktime",
    "config_realplayer",
    "config_windowsmedia",
    "config_gears",
    "config_silverlight",
    "config_cookie",
    "location_ip",
    "location_browser_lang",
    "location_country",
    "location_continent",
    "custom_var_k1",
    "custom_var_v1",
    "custom_var_k2",
    "custom_var_v2",
    "custom_var_k3",
    "custom_var_v3",
    "custom_var_k4",
    "custom_var_v4",
    "custom_var_k5",
    "custom_var_v5",
    "location_provider"
  )

  val dateIndex = 10 // visit_last_action_time
}