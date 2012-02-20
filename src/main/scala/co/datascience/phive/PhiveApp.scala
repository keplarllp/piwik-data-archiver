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
package co.datascience.phive

// Java
import java.io.File
import java.util.NoSuchElementException

// Argot
import org.clapper.argot._

// Config
import com.typesafe.config.{Config, ConfigFactory}

/**
 * Enum holding the different possible time periods
 * to export Piwik data for
 */
object TimePeriod extends Enumeration {
 val HISTORIC = Value("historic")
 val YESTERDAY = Value("yesterday")
}

/**
 * Our entrypoint object for Phive.
 */
object PhiveApp {

  // Argument specifications
  import ArgotConverters._

  // General bumf for Phive
  val parser = new ArgotParser(
    programName = generated.Settings.name,
    compactUsage = true,
    preUsage = Some("%s: Version %s. Copyright (c) 2012, %s.".format(
      generated.Settings.name,
      generated.Settings.version,
      generated.Settings.organization)
    )
  )

  // Optional config argument
  val config = parser.option[Config](List("c", "config"),
                                          "filename",
                                          "Configuration file. Defaults to \"resources/default.conf\" (within .jar) if not set") {
    (c, opt) =>

      val file = new File(c)
      if (file.exists) {
        ConfigFactory.parseFile(file)
      } else {
        parser.usage("Configuration file \"%s\" does not exist".format(c))
        ConfigFactory.empty()
      }
  }

  // Optional no-header flag
  val noUpload = parser.flag[Boolean](List("n", "noupload"),
                                           "Flags that the generated .csv files should not be uploaded to S3")

  // Optional time period
  // TODO: make this return an enum instead
  val period = parser.option[TimePeriod.Value](List("p", "period"),
                                                    "time",
                                                    "Time period of data to extract. Either \"yesterday\" or \"historic\"") {
    (p, opt) =>

      try {
        TimePeriod.withName(p)
      } catch {
        case nsee: NoSuchElementException => parser.usage("Time period \"%s\" invalid. Must be either \"yesterday\" or \"historic\"".format(p))
        case e => throw e // Rethrow any other error we get
      }
  }

  /**
   * Main Phive program
   */
  def main(args: Array[String]) {

    try {
      // Grab the command line arguments
      parser.parse(args)

      // Run the Piwik export and upload
      Phive(config = config.value.getOrElse(ConfigFactory.load("default")),    // Fall back to the /resources/default.conf
           period = period.value.getOrElse(TimePeriod.withName("yesterday")), // Default to yesterday's data
           upload = !(noUpload.value.getOrElse(false))                        // Default to upload = true
      ).run()
    } catch {
      case e: ArgotUsageException => println(e.message)
    }
  }
}