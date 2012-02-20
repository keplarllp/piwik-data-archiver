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
package co.orderly.dutch

// Java
import java.io.File
import java.util.concurrent.TimeUnit.NANOSECONDS

// Argot
import org.clapper.argot._

// Config
import com.typesafe.config.{Config, ConfigFactory}

/**
 * Our entrypoint object for Dutch.
 */
object DutchApp {

  // Argument specifications
  import ArgotConverters._

  // General bumf for Dutch
  val parser = new ArgotParser(
    programName = generated.Settings.name,
    compactUsage = true,
    preUsage = Some("%s: Version %s. Copyright (c) 2012, %s.".format(
      generated.Settings.name,
      generated.Settings.version,
      generated.Settings.organization)
    )
  )

  // Obligatory output file
  val output = parser.parameter[String]("outputfile",
                                        "Output CSV file to write",
                                        false)

  // Optional config argument
  val config = parser.option[Config](List("c", "config"),
                                     "filename",
                                     "Configuration file. Defaults to \"resources/example.conf\" (within .jar) if not set") {
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
  val noHeader = parser.flag[Boolean](List("n", "noheader"),
                                     "Flags that input CSV file(s) does not have a header row")

  // Optional custom CSV separator
  val separator = parser.option[Char](List("s", "separator"), "sep",
                                      "Separator character to use for CSVs (defaults to ,). Use \\t for tab")

  // Optional CSV character escaper
  val quoteChar = parser.option[Char](List("q", "quote"), "char",
                                      "Quote character to use for CSVs (defaults to \")")

  // Optional input file(s)
  val input = parser.multiParameter[File]("input",
                                          "Input CSV file(s) to read. If not specified, uses stdin",
                                          true) {
    (i, opt) =>

      val file = new File(i)
      if (!file.exists)
        parser.usage("Input file \"%s\" does not exist".format(i))

      file
  }

  /**
   * Main Dutch program
   */
  def main(args: Array[String]) {

    try {
      // Store start time
      val start = System.nanoTime()

      // Grab the command line arguments
      parser.parse(args)

      // Run the pricing module
      Pricer(config = config.value.getOrElse(ConfigFactory.load("merchant")), // Fall back to the /resources/merchant.conf
             input = input.value,
             separator = separator.value.getOrElse(','),
             quoteChar = quoteChar.value.getOrElse('\"'),
             header = !(noHeader.value.getOrElse(false)),
             output = output.value.get
      ).run()

      // Print how long this took
      Console.println("Completed in %s milliseconds".format(NANOSECONDS.toMillis(System.nanoTime() - start)))
    } catch {
      case e: ArgotUsageException => println(e.message)
    }
  }
}