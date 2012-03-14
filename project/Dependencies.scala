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
    // Libraries for SnowPik
    val config    = "0.2.1"
    val argot     = "0.3.5"
    val opencsv   = "2.0"
    val specs2    = "1.8"
    val jets3t	  = "0.8.1"
    val scalaz    = "6.0.3"

    val squeryl   = "0.9.5-RC1"
    val mysql     = "5.1.15"
    val jtds      = "1.2.4"
  }

  object Libraries {
    // Used by command-line app
    val argot       = "org.clapper"                %% "argot"                % V.argot
    val config      = "com.typesafe.config"        %  "config"               % V.config

    // Used for reading/writing CSV files
    val opencsv     = "net.sf.opencsv"             %  "opencsv"              % V.opencsv

    // Added for MySQL access
    val squeryl     = "org.squeryl"                %% "squeryl"              % V.squeryl
    val mysql       = "mysql"                      %  "mysql-connector-java" % V.mysql
    val jtds        = "net.sourceforge.jtds"       %  "jtds"                 % V.jtds

    // Used for S3 upload
    val jets3t      = "net.java.dev.jets3t"        %  "jets3t"               % V.jets3t

    // Used for testing
    val specs2      = "org.specs2"                 %% "specs2"               % V.specs2      % "test"

    // Added for (Ø) in Squeryl model default constructors
    val scalaz      = "org.scalaz"                 %% "scalaz-core"          % V.scalaz
  }
}
