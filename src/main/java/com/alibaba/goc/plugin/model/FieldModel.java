package com.alibaba.goc.plugin.model;


public class FieldModel {
    private String typeName;
    private String fieldName;
    private String comment;
    private Boolean typeIsBoolean;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getTypeIsBoolean() {
        return typeIsBoolean;
    }

    public void setTypeIsBoolean(Boolean typeIsBoolean) {
        this.typeIsBoolean = typeIsBoolean;
    }
}
