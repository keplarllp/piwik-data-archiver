# SnowPik: SnowPlow-Piwik Connector

![logo] [logo]

## Overview

SnowPik, the SnowPlow-Piwik Connector is a command-line tool which dumps your [Piwik] [piwik] log data to .csv files and then uploads these files to [Amazon S3] [s3] ready for you to analyze using [SnowPlow] [snowplow]. SnowPik is written in Scala.

**Current status: SnowPik is written, but currently the . **

## Health Warning

For performance reasons, SnowPik talks directly to your [Piwik MySQL database] [schema] - if you want a version which uses the Piwik API, please vote for [this GitHub issue] [issue1].

While all of its database operations are non-destructive, we strongly recommend creating a dedicated user account in your Piwik MySQL database for SnowPik, and giving this SnowPik user only the bare-minimum permissions.

Note that you use this software at your own risk - Orderly Ltd takes no responsibility for any loss of data incurred through the use of it.

## Setup

### 1. Before you start

To make full use of SnowPik, you will need:

* A running Piwik installation
* Access to your your Piwik MySQL database from a computer with the Java runtime
* An Amazon S3 account

### 2. Download and install

You can download the latest build of SnowPik from this GitHub repository. Right-click on this [download link] [download] and click **Save As...**, or alternatively:

    $ sudo mkdir /opt/snowpik
    $ cd /opt/snowpik
    $ wget xxx


### 3. Setup MySQL access

Setup a dedicated MySQL user account for SnowPik, and grant the user `SELECT` (i.e. read-only) permissions on the five tables that it needs to read:

```mysql
-- Create the SnowPik user
CREATE USER 'snowpik'@'localhost' IDENTIFIED BY '<password>';

-- Set the SnowPik user's permissions
GRANT SELECT ON `piwik`.`piwik\_log\_action` TO 'snowpik'@'localhost' WITH GRANT OPTION;
GRANT SELECT ON `piwik`.`piwik\_log\_visit` TO 'snowpik'@'localhost' WITH GRANT OPTION;
GRANT SELECT ON `piwik`.`piwik\_log\_link\_visit\_action` TO 'snowpik'@'localhost' WITH GRANT OPTION;
GRANT SELECT ON `piwik`.`piwik\_log\_conversion` TO 'snowpik'@'localhost' WITH GRANT OPTION;
GRANT SELECT ON `piwik`.`piwik\_log\_conversion\_item` TO 'snowpik'@'localhost' WITH GRANT OPTION;
```

### 4. Setup your Configuration File

Next you need to create a configuration file, setting both your Piwik MySQL and Amazon S3 connection details. You can find a template configuration file within the .zip file.

Here is a configuration file template, please update this to your requirements:

```python
#################################
# SnowPik Default Configuration #
#################################

snowpik {
  db {
    username: "PIWIK DB USERNAME"
    password: "PIWIK DB PASSWORD"
    prefix:   "PIWIK DB TABLE PREFIX EG piwik_"
    database: "PIWIK DB NAME"
    server:   "PIWIK DB SERVER EG localhost"
    port:     "PIWIK DB PORT EG 3306"
  }
  export {
    folder:   "LOCAL FOLDER TO STORE LOGS"
  }
  upload {
    key:      "AWS KEY ID"
    secret:   "AWS SECRET ACCESS KEY"
    bucket:   "AWS S3 BUCKET NAME"
  }
}
```

### 5. Run against your historic Piwik data

You can run SnowPik against your Piwik data collected to-date using this command: 

```bash
./snowpik --config snowpik.conf --period historic
```

This command processes all of your Piwik data up until the end of **yesterday** (based on your host computer's clock). Today's data is ignored because the log files for today will be as yet incomplete.

### 6. Setup a daily job

Once you have uploaded your historical Piwik data to S3, you can setup a daily `cronjob` to export each day's new Piwik log file data to S3. To do this:

    $ sudo vi /etc/crontab

And add the line:

    0 5 * * *   root  /opt/snowpik/cronic /opt/snowpik/snowpik --period YESTERDAY

Assuming that you have SnowPik installed in `/opt/snowpik`, this will run SnowPik every day at 5am, transferring the data Piwik collected yesterday into Amazon S3. `cronic` is a third-party wrapper script to improve `cron`'s email handling; it's bundled with SnowPik.  

## Full Usage Instructions

The full capabilities of SnowPik are as per per the command-line usage:

    OPTIONS
    -c filename
    --config filename  Configuration file. Defaults to "resources/default.conf"
                       (within .jar) if not set
    -n
    --noupload         Flags that the generated .csv files should not be uploaded
                       to S3
    -p time
    --period time      Time period of data to extract. Either "yesterday" or
                       "historic". NOT YET IMPLEMENTED
    -s id
    --site id          Piwik site id to extract data for. Defaults to 1    

Please note that the `period` argument is yet to be implemented. SnowPik currently extracts **all** Piwik log data right up to the present moment.

## Copyright and License

SnowPik, the SnowPlow-Piwik Connector is copyright 2012 Orderly Ltd.

Licensed under the [Apache License, Version 2.0] [license] (the "License");
you may not use this software except in compliance with the License.

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

[logo]: https://github.com/datascience/piwik-export-to-hive/raw/master/doc/snowpik.png
[snowplow]: http://www.keplarllp.com/blog/
[piwik]: http://piwik.org
[download]: http://TODO
[issue1]: https://github.com/datascience/piwik-export-to-hive/issues/1
[schema]: http://piwik.org/docs/plugins/database-schema/
[s3]: http://aws.amazon.com/s3/
[hive]: http://hive.apache.org/
[license]: http://www.apache.org/licenses/LICENSE-2.0
