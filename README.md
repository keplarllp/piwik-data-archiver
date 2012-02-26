# SnowPlow-Piwik Connector

![logo] [logo]

## Overview

SnowPlow-Piwik Connector is a command-line tool which dumps your [Piwik] [piwik] log data to .csv files and then uploads these files to [Amazon S3] [s3] ready for you to analyze using [SnowPlow] [snowplow]. SnowPlow-Piwik Connector is written in Scala.

## Health Warning

For performance reasons, this tool talks directly to your [Piwik MySQL database] [schema] - if you want a version which uses the Piwik API, please vote for [this GitHub issue] [issue1].

While all of its database operations are non-destructive, we strongly recommend creating a dedicated user account in your Piwik MySQL database for SnowPlow, and giving this SnowPlow user only the bare-minimum permissions.

Note that you use this software at your own risk - Orderly Ltd takes no responsibility for any loss of data incurred through the use of it.

## Setup

### 1. Before you Start

To make full use of this tool, you will need:

* A running Piwik installation
* Access to your your Piwik MySQL database
* An Amazon S3 account
* A SnowPlow installation

### 2. Download

    // TODO

### 3. Setup MySQL Access

Setup a dedicated MySQL user account for this software, and grant the user `SELECT` (i.e. read-only) permissions on the five tables that it needs to read:

```mysql
-- Create the SnowPlow user
CREATE USER 'snowplow'@'localhost' IDENTIFIED BY '<password>';

-- Set the SnowPlow user's permissions
GRANT SELECT ON `piwik`.`piwik\_log\_action` TO 'snowplow'@'localhost' WITH GRANT OPTION;
GRANT SELECT ON `piwik`.`piwik\_log\_visit` TO 'snowplow'@'localhost' WITH GRANT OPTION;
GRANT SELECT ON `piwik`.`piwik\_log\_link\_visit\_action` TO 'snowplow'@'localhost' WITH GRANT OPTION;
GRANT SELECT ON `piwik`.`piwik\_log\_conversion` TO 'snowplow'@'localhost' WITH GRANT OPTION;
GRANT SELECT ON `piwik`.`piwik\_log\_conversion\_item` TO 'snowplow'@'localhost' WITH GRANT OPTION;
```

### 4. Setup your Configuration File

Next you need to create a configuration file, setting both your Piwik MySQL and Amazon S3 connection details. You can find a template configuration file within the .zip file.

Here is an example configuration:

```python
###########################
# Connector Configuration #
###########################

connector {
  db {
    username: ""
    password: ""
    prefix:   ""
    database: ""
    server:   ""
    port:     ""

  }
  s3 {
    key:      ""
    secret:   ""
    bucket:   ""
  }
}
```
### 5. Run against your Historic Piwik Data

You can run this tool against your Piwik data collected to-date using this command: 

```bash
./snowik --config snowik.conf --period historic
```

This command processes all of your Piwik data up until the end of **yesterday** (based on your host computer's clock). Today's data is ignored because the log files for today will be as yet incomplete.

### 6. Setup a daily job

Once you have uploaded your historical Piwik data to S3, you can setup a daily `cronjob` to export each day's new Piwik log file data to S3. To do this:

    // TODO

## Full Usage Instructions

The full capabilities of the tool are as per per the command-line usage:

    // TODO

## Copyright and License

SnowPlow-Piwik Connector is copyright 2012 Orderly Ltd.

Licensed under the [Apache License, Version 2.0] [license] (the "License");
you may not use this software except in compliance with the License.

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

[logo]: https://github.com/datascience/piwik-export-to-hive/raw/master/doc/phive.png
[snowplow]: http://www.keplarllp.com/blog/
[piwik]: http://piwik.org
[issue1]: https://github.com/datascience/piwik-export-to-hive/issues/1
[schema]: http://piwik.org/docs/plugins/database-schema/
[s3]: http://aws.amazon.com/s3/
[hive]: http://hive.apache.org/
[license]: http://www.apache.org/licenses/LICENSE-2.0
