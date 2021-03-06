/**
 * Licensed to Big Data Genomics (BDG) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The BDG licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.bdgenomics.adam.apis.java

import org.apache.spark.rdd.RDD
import org.apache.spark.api.java.JavaRDD._
import org.apache.spark.api.java.JavaRDD
import org.bdgenomics.adam.rdd.ADAMContext._
import org.bdgenomics.adam.util.ADAMFunSuite
import org.bdgenomics.formats.avro.AlignmentRecord

class JavaADAMContextSuite extends ADAMFunSuite {

  sparkTest("can read a small .SAM file") {
    val path = copyResource("small.sam")
    val ctx = new JavaADAMContext(sc)
    val reads = ctx.adamRecordLoad(path)
    assert(reads.jrdd.count() === 20)
  }

  sparkTest("can read a small .SAM file inside of java") {
    val path = copyResource("small.sam")
    val aRdd = sc.loadAlignments(path)

    val newReads = JavaADAMConduit.conduit(aRdd.rdd, aRdd.sequences, aRdd.recordGroups)

    assert(newReads.jrdd.count() === 20)
  }
}
