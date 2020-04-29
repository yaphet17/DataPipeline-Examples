package com.northconcepts.datapipeline.foundations.examples.pipeline;

import com.northconcepts.datapipeline.core.Record;
import com.northconcepts.datapipeline.examples.cookbook.SelectAndArrangeFields;
import com.northconcepts.datapipeline.excel.ExcelDocument;
import com.northconcepts.datapipeline.foundations.file.LocalFile;
import com.northconcepts.datapipeline.foundations.pipeline.Pipeline;
import com.northconcepts.datapipeline.foundations.pipeline.action.transform.SelectArrangeFieldsCommand;
import com.northconcepts.datapipeline.foundations.pipeline.dataset.Column;
import com.northconcepts.datapipeline.foundations.pipeline.dataset.Dataset;
import com.northconcepts.datapipeline.foundations.pipeline.input.CsvPipelineInput;
import com.northconcepts.datapipeline.foundations.pipeline.input.ExcelPipelineInput;
import com.northconcepts.datapipeline.foundations.pipeline.output.ExcelPipelineOutput;
import com.northconcepts.datapipeline.job.Job;

import java.io.File;

public class FilterColumnsWithAllNullValues {

    public static void main(String[] args) {

        Pipeline pipeline = new Pipeline();

        LocalFile inputFile = new LocalFile()
                .setName("Input File")
                .setPath("data/input/Listing.csv")
                .detectFileType();

        LocalFile outputFile = new LocalFile()
                .setName("Output File")
                .setPath("data/output/NoNull.xlsx");

        CsvPipelineInput pipelineInput = new CsvPipelineInput()
                .setFileSource(inputFile)
                .setFieldNamesInFirstRow(true);

        ExcelPipelineOutput pipelineOutput = new ExcelPipelineOutput()
                .setFileSink(outputFile)
                .setFieldNamesInFirstRow(true);

        pipeline.setInput(pipelineInput);
        pipeline.setOutput(pipelineOutput);

        Dataset dataset = pipeline.getDataset();
        dataset.load().waitForRecordsToLoad();

        SelectArrangeFieldsCommand action = new SelectArrangeFieldsCommand();

        for(Column column : dataset.getColumns()) {
            if(column.getValueCount() != column.getNullCount()) {
                action.add(column.getName());
            }
        }

        pipeline.addAction(action);

        Job.run(pipeline.createDataReader(true), pipeline.createDataWriter());
    }
}
