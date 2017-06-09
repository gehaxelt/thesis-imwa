package it.neef.tub.dima.ba.imwa;
/*
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import it.neef.tub.dima.ba.imwa.examples.adlerscm.AdlerContributionMeasuresExample;

/**
 * Main Job class which is later executed by Apache Flink.
 */
public class Job {

    /**
     * The main method checks the argument list and starts the IMWA-Framework.
     *
     * @param args the arguments from the command line
     * @throws Exception If an error occurred during the processing of the data.
     */
    public static void main(String[] args) throws Exception {

        if (args.length < 1) {
            System.out.println("Usage: job.jar <dumppath> <pageviewpath> <shorttag>");
            System.exit(1);
        }

        //Run Adler's Contribution Measures
        AdlerContributionMeasuresExample example = new AdlerContributionMeasuresExample();
        example.run(args);

    }
}
