# Phive: Piwik Export to Hive

![phive] [phive]

## Overview

Phive (Piwik Export to Hive) is a command-line tool which dumps your [Piwik] [piwik] log data to .csv files and then uploads these files to [Amazon S3] [s3] ready for you to analyze using [Apache Hive] [hive]. Phive is written in Scala.

## Health Warning

For performance reasons, Phive talks directly to your [Piwik MySQL database] [schema] - if you want a version which uses the Piwik API, please vote for this GitHub issue: #1.

While all Phive database operations are non-destructive, we strongly recommend creating a dedicated user account in your Piwik MySQL database for Phive, and giving this Phive user only the bare-minimum permissions.

Note that you use Phive at your own risk - Orderly Ltd takes no responsibility for any loss of data incurred through the use of Phive.

## Setting up Phive

### 1. Before you Start

To make full use of Phive, you will need:

* A running Piwik installation
* Access to your your Piwik MySQL database
* An Amazon S3 account
* A working Hive installation (e.g. on Amazon Elastic MapReduce)

### 2. Download Phive

    // TODO

### 3. Setup MySQL Access

Setup a dedicated MySQL user account for Phive, and grant the user `SELECT` (i.e. read-only) permissions on the five tables that Phive needs to read:

```mysql
-- Create the Phive user
CREATE USER 'phive'@'localhost' IDENTIFIED BY '<password>';

-- Set the Phive user's permissions
GRANT SELECT ON `piwik`.`piwik\_log\_action` TO 'phive'@'localhost' WITH GRANT OPTION;
GRANT SELECT ON `piwik`.`piwik\_log\_visit` TO 'phive'@'localhost' WITH GRANT OPTION;
GRANT SELECT ON `piwik`.`piwik\_log\_link\_visit\_action` TO 'phive'@'localhost' WITH GRANT OPTION;
GRANT SELECT ON `piwik`.`piwik\_log\_conversion` TO 'phive'@'localhost' WITH GRANT OPTION;
GRANT SELECT ON `piwik`.`piwik\_log\_conversion\_item` TO 'phive'@'localhost' WITH GRANT OPTION;
```

### 4. Setup your Configuration File

Next you need to create a configuration file for Phive, setting both your Piwik MySQL and Amazon S3 connection details. You can find a template configuration file within the .zip file.

Here is an example configuration:

```python
#######################
# Phive Configuration #
#######################

phive {
  db {
    username: ""
    password: ""
    server:   ""
    port:     ""
    database: ""
  }
  s3 {
    key:      ""
    secret:   ""
    bucket:   ""
  }
}
```
### 5. Run Phive against your Historic Piwik Data

You can run Phive against your Piwik data collected to-date using this command: 

```bash
./phive --config phive.conf --period historic
```

This command processes all of your Piwik data up until the end of **yesterday** (based on your host computer's clock). Today's data is ignored because the log files for today will be as yet incomplete.

### 6. Setup a daily Phive job

Once you have uploaded your historical Piwik data to S3, you can setup a daily `cronjob` to export each day's new Piwik log file data to S3. To do this:

    // TODO

## Full Usage Instructions

The full capabilities of Phive are as per per the command-line usage:

    // TODO

## Copyright and License

Phive is copyright 2012 Orderly Ltd.

Licensed under the [Apache License, Version 2.0] [license] (the "License");
you may not use this software except in compliance with the License.

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

[phive]: https://github.com/datascience/piwik-export-to-hive/raw/master/doc/phive.png
[piwik]: http://piwik.org
[schema]: http://piwik.org/docs/plugins/database-schema/
[s3]: http://aws.amazon.com/s3/
[hive]: http://hive.apache.org/
[license]: http://www.apache.org/licenses/LICENSE-2.0
