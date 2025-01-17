/*
 * Copyright (c) 2006-2022 North Concepts Inc.  All rights reserved.
 * Proprietary and Confidential.  Use is subject to license terms.
 * 
 * https://northconcepts.com/data-pipeline/licensing/
 */
package com.northconcepts.datapipeline.examples.cookbook;

import com.northconcepts.datapipeline.core.DataReader;
import com.northconcepts.datapipeline.core.DataWriter;
import com.northconcepts.datapipeline.core.StreamWriter;
import com.northconcepts.datapipeline.email.EmailReader;
import com.northconcepts.datapipeline.email.MailStore;
import com.northconcepts.datapipeline.job.Job;

public class ReadFromGmail {
    
   /*
     * Google Account->Security->2-Step Verification->Follow the steps
     *
     * Google Account->Security->App Passwords->Enter your password->Select app->
     * Choose Other(Custom name)->Enter NorthConcepts-DataPipeline->Click Generate->
     * Copy generated password
     *
     * Gmail -> Settings -> Forwarding and POP/IMAP -> Enable POP or Enable IMAP
     * 
     */

    public static void main(String... args) {
        DataReader reader = new EmailReader(MailStore.IMAP_OVER_SSL, "imap.gmail.com", "username", "password");
        DataWriter writer = new StreamWriter(System.out);
        
        Job.run(reader, writer);
    }
}
