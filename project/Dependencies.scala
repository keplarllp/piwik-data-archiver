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
import sbt._

object Dependencies {
  val resolutionRepos = Seq(
    ScalaToolsSnapshots,
    "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"
  )

  object V {
    // Libraries for Phive
    val config    = "0.2.1"
    val argot     = "0.3.5"
    val opencsv   = "2.0"
    val specs2    = "1.8"
  }

  object Libraries {
    // Used for Typesafe Config
    val config      = "com.typesafe.config"        %  "config"               % V.config
    // User for Argot
    val argot       = "org.clapper"                %% "argot"                % V.argot
    // Used for reading/writing CSV files
    val opencsv     = "net.sf.opencsv"             %  "opencsv"              % V.opencsv
    // Used for testing
    val specs2      = "org.specs2"                 %% "specs2"               % V.specs2      % "test"
  }
}
