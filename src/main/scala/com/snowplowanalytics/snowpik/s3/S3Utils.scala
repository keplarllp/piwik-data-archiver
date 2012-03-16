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
package com.snowplowanalytics.snowpik
package s3

// Java
import java.io.File

// Amazon S3
import org.jets3t.service.model.S3Object
import org.jets3t.service.acl.{Permission, GroupGrantee, AccessControlList}
import org.jets3t.service.impl.rest.httpclient.RestS3Service

object S3Utils {

  /**
   * General-purpose procedure to upload a file to
   * an S3 bucket
   */
  def uploadFile(file: File, bucket: String, s3: RestS3Service, key: Option[String] = None) {

    if (!file.exists) throw new IllegalArgumentException("File with path %s does not exist".format(file.getPath))
    if (file.isDirectory) throw new IllegalArgumentException("%s is a directory not a file".format(file.getName))

    val s3Object = new S3Object(file)
    if (key.isDefined) s3Object.setKey(key.get)

    // Give the S3 object public ACL based on the owning bucket's ACL
    val bucketAcl: AccessControlList = s3.getBucketAcl(bucket)
    s3Object.setAcl(bucketAcl)

    // Upload the file to the bucket
    s3.putObject(bucket, s3Object)
  }
}