/*
 * Copyright (c) 2006-2021 North Concepts Inc.  All rights reserved.
 * Proprietary and Confidential.  Use is subject to license terms.
 * 
 * https://northconcepts.com/data-pipeline/licensing/
 */
package com.northconcepts.datapipeline.foundations.examples.flatfile2;

import static com.northconcepts.datapipeline.core.XmlSerializable.getAttribute;
import static com.northconcepts.datapipeline.core.XmlSerializable.setAttribute;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.northconcepts.datapipeline.core.Parser;
import com.northconcepts.datapipeline.core.Record;
import com.northconcepts.datapipeline.core.RecordSerializable;
import com.northconcepts.datapipeline.core.XmlSerializable;
import com.northconcepts.datapipeline.foundations.core.Bean;
import com.northconcepts.datapipeline.foundations.schema.FieldDef;

public abstract class FlatFileFieldDef extends Bean implements RecordSerializable, XmlSerializable {

    private String name;
    private boolean skip;
    private FieldDef fieldDef;

    public FlatFileFieldDef() {
    }

    protected abstract void parseNextField(FlatFileReader reader, Parser parser, Record record);

    public String getName() {
        return name;
    }

    public FlatFileFieldDef setName(String name) {
        this.name = name;
        return this;
    }

    public boolean isSkip() {
        return skip;
    }

    public FlatFileFieldDef setSkip(boolean skip) {
        this.skip = skip;
        return this;
    }

    public FieldDef getFieldDef() {
        return fieldDef;
    }

    public FlatFileFieldDef setFieldDef(FieldDef fieldDef) {
        this.fieldDef = fieldDef;
        return this;
    }

    @Override
    public Record toRecord() {
        return fieldDef.toRecord()
                .setField(SERIALIZED_CLASS_NAME, getClass().getName())
                .setField("name", name)
                .setField("skip", skip);
    }

    @Override
    public FlatFileFieldDef fromRecord(Record source) {
        this.fieldDef = FieldDef.newInstanceFromRecord(source);
        this.name = source.getFieldValueAsString("name", null);
        this.skip = source.getFieldValueAsBoolean("skip", false);
        return this;
    }

    @Override
    public Element toXmlElement(Document document) {
        Element element = fieldDef.toXmlElement(document);
        setAttribute(element, SERIALIZED_CLASS_NAME, getClass().getName());
        setAttribute(element, "name", name);
        setAttribute(element, "skip", skip);
        return element;
    }

    @Override
    public FlatFileFieldDef fromXmlElement(Element element) {
        this.fieldDef = FieldDef.newInstanceFromXml(element);
        this.name = getAttribute(element, "name", (String)null);
        this.skip = getAttribute(element, "skip", false);
        return this;
    }

}
