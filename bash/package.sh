#!/bin/bash
#
# Simple bash script to package up the zip file for upload to GitHub
#
# Copyright (c) 2012 Orderly Ltd. All rights reserved.
#
# This program is licensed to you under the Apache License Version 2.0,
# and you may not use this file except in compliance with the Apache License Version 2.0.
# You may obtain a copy of the Apache License Version 2.0 at http://www.apache.org/licenses/LICENSE-2.0.
#
# Unless required by applicable law or agreed to in writing,
# software distributed under the Apache License Version 2.0 is distributed on an
# "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the Apache License Version 2.0 for the specific language governing permissions and limitations there under.

# Figure out where this script lives
SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
SNOWPIK_DIR=${SCRIPT_DIR}/..

# "Constants"
VERSION=0.1
MIN_JAR=${SNOWPIK_DIR}/target/scala-2.9.1/snowpik_2.9.1-${VERSION}.min.jar
BUILD_DIR=/tmp/snowpik
ZIP_FILE=${SNOWPIK_DIR}/snowpik-${VERSION}.zip

# First let's package and Proguard it
sbt package
sbt proguard

# Now let's put all the files into  
if [ -d ${BUILD_DIR} ];then
	rm -rf ${BUILD_DIR}
fi
mkdir ${BUILD_DIR}
cp ${MIN_JAR} ${BUILD_DIR}/snowpik-${VERSION}.jar
cp ${SNOWPIK_DIR}/README.md ${BUILD_DIR}
cp ${SNOWPIK_DIR}/LICENSE-2.0.txt ${BUILD_DIR}
cp ${SCRIPT_DIR}/snowpik.sh ${BUILD_DIR}
cp ${SCRIPT_DIR}/cronic ${BUILD_DIR}

if [ -f ${ZIP_FILE} ];then
	rm ${ZIP_FILE}
fi
zip -rj ${ZIP_FILE} ${BUILD_DIR}/

