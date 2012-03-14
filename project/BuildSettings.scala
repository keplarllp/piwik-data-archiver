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
import Keys._

object BuildSettings {

  // Basic settings for our app
  lazy val basicSettings = Seq[Setting[_]](
    organization  := "Orderly Ltd",
    version       := "0.1",
    description   := "A command-line tool for exporting the Piwik log tables to .csv and uploading to S3 for analysis using SnowPlow",
    scalaVersion  := "2.9.1",
    scalacOptions := Seq("-deprecation", "-encoding", "utf8"),
    resolvers     ++= Dependencies.resolutionRepos
  )

  // Makes our SBT app settings available from within the app
  lazy val scalifySettings = Seq(sourceGenerators in Compile <+= (sourceManaged in Compile, version, name, organization) map { (d, v, n, o) =>
    val file = d / "settings.scala"
    IO.write(file, """package com.snowplowanalytics.snowpik.generated
      |object Settings {
      |  val organization = "%s"
      |  val version = "%s"
      |  val name = "%s"
      |}
      |""".stripMargin.format(o, v, n))
    Seq(file)
  })

  // Proguard settings for packaging
  import ProguardPlugin._
  lazy val proguard = proguardSettings ++ Seq(
    proguardOptions := Seq(
      "-keepattributes *Annotation*,EnclosingMethod",
      "-dontskipnonpubliclibraryclassmembers",
      "-dontoptimize",
      "-dontshrink"
    )
  )

  lazy val snowpikSettings = basicSettings ++ scalifySettings ++ proguard
}
