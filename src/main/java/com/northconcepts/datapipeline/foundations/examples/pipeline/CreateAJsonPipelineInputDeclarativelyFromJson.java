/*
 * Copyright (c) 2006-2022 North Concepts Inc.  All rights reserved.
 * Proprietary and Confidential.  Use is subject to license terms.
 * 
 * https://northconcepts.com/data-pipeline/licensing/
 */
package com.northconcepts.datapipeline.foundations.examples.pipeline;


import com.northconcepts.datapipeline.core.StreamWriter;
import com.northconcepts.datapipeline.foundations.pipeline.Pipeline;
import com.northconcepts.datapipeline.foundations.pipeline.input.JsonPipelineInput;
import com.northconcepts.datapipeline.foundations.pipeline.output.DataWriterPipelineOutput;

import java.io.FileInputStream;

public class CreateAJsonPipelineInputDeclarativelyFromJson {

    public static void main(String[] args) throws Throwable {
        Pipeline pipeline = new Pipeline();

        JsonPipelineInput input = (JsonPipelineInput) new JsonPipelineInput()
            .fromJson(new FileInputStream("example/data/input/pipeline/jsonpipelineinput.json"));

        pipeline.setInput(input);
        pipeline.setOutput(new DataWriterPipelineOutput(() -> StreamWriter.newSystemOutWriter()));

        pipeline.run();
    }

}
