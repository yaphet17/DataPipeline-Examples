/*
 * Copyright (c) 2006-2020 North Concepts Inc.  All rights reserved.
 * Proprietary and Confidential.  Use is subject to license terms.
 * 
 * https://northconcepts.com/data-pipeline/licensing/
 */
package com.northconcepts.datapipeline.foundations.examples.pipeline;

import com.northconcepts.datapipeline.foundations.file.LocalFile;
import com.northconcepts.datapipeline.foundations.pipeline.Pipeline;
import com.northconcepts.datapipeline.foundations.pipeline.action.convert.ConvertStringToNumberAction;
import com.northconcepts.datapipeline.foundations.pipeline.action.transform.AddFieldsAction;
import com.northconcepts.datapipeline.foundations.pipeline.action.transform.RenameFieldsAction;
import com.northconcepts.datapipeline.foundations.pipeline.input.CsvPipelineInput;
import com.northconcepts.datapipeline.foundations.pipeline.output.ExcelPipelineOutput;

public class SaveAndRestorePipelineFromJson {

    public static void main(String[] args) throws Throwable{

        CsvPipelineInput pipelineInput = new CsvPipelineInput()
                .setFileSource(new LocalFile().setPath("data/input/Listing.csv"))
                .setFieldNamesInFirstRow(true);

        ExcelPipelineOutput pipelineOutput = new ExcelPipelineOutput()
                .setFileSink(new LocalFile().setPath("data/output/output.xlsx"))
                .setFieldNamesInFirstRow(true);

        Pipeline pipeline = new Pipeline();
        pipeline.setInput(pipelineInput);
        pipeline.setOutput(pipelineOutput);

        pipeline.addAction(new RenameFieldsAction().add("Taxes", "Taxes_Renamed"));
        pipeline.addAction(new ConvertStringToNumberAction()
                .add("Sell", "List")
                .setType(ConvertStringToNumberAction.FieldType.DOUBLE)
                .setPattern("0.00"));
        pipeline.addAction(new AddFieldsAction().add("new_column", AddFieldsAction.FieldType.EXPRESSION, "List - Sell"));

        String json = pipeline.toJsonString();

        System.out.println(json);
        
        Pipeline pipeline2 = new Pipeline();
        pipeline2.fromJsonString(json);

        pipeline2.run();
    }
}
