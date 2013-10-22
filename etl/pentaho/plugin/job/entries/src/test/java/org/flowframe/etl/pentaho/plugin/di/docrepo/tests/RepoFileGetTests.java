/*******************************************************************************
 *
 * Pentaho Data Integration
 *
 * Copyright (C) 2002-2012 by Pentaho : http://www.pentaho.com
 *
 *******************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************************/

package org.flowframe.etl.pentaho.plugin.di.docrepo.tests;

import junit.framework.TestCase;
import org.flowframe.etl.pentaho.plugin.di.docrepo.fileget.RepoFileGet;
import org.pentaho.di.TestUtilities;
import org.pentaho.di.core.Result;
import org.pentaho.di.job.Job;

public class RepoFileGetTests
extends TestCase {
    
    /**
     * Creates a Result and logs that fact.
     * @return
     */
    private static Result createStartJobEntryResult() {
        
        Result startResult = new Result();
        startResult.setLogText(TestUtilities.now() +" - START - Starting job entry\r\n ") ;
        return startResult;
        
    }    
    
    /**
     * Tests copying a folder contents.  The folders used are created in 
     * the Java's temp location using unique folder and file names.
     * 
     * @throws Exception
     */
    public void testLocalFileCopy() throws Exception {
        //  the parent job 
        Job parentJob = new Job();
        
        //  Set up the job entry to do wildcard copy
        RepoFileGet jobEntry = new RepoFileGet("10180",
                "10154",
                "17603",
                "test@liferay.com",
                "test",
                "localhost",
                "7080",
                "10180",
                "28352");

        jobEntry.setParentJob(parentJob);
        
        //  Check the result for errors.
        Result result = jobEntry.execute(createStartJobEntryResult(), 1);
        if(result.getNrErrors()!=0) {
            fail(result.getLogText());
        }
    }
}

