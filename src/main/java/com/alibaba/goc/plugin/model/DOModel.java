package com.alibaba.goc.plugin.model;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * DO类对应的模型
 */
public class DOModel {

    /**
     * DO文件的位置
     */
    private String filePath;
    /**
     * import list
     */
    private List<String> imports = Lists.newArrayList();

    /**
     * DO类的名称
     */
    private String name;

    /**
     * DO类的父类,(可能还需要父接口)
     */
    private List<String> superClassName = Lists.newArrayList();

    /**
     * DO类的字段
     */
    private List<FieldModel> fields = Lists.newArrayList();

    /**
     * DO类的方法
     */
    private List<MethodModel> methods = Lists.newArrayList();


    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public List<String> getImports() {
        return imports;
    }

    public void setImports(List<String> imports) {
        this.imports = imports;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getSuperClassName() {
        return superClassName;
    }

    public void setSuperClassName(List<String> superClassName) {
        this.superClassName = superClassName;
    }

    public List<FieldModel> getFields() {
        return fields;
    }

    public void setFields(List<FieldModel> fields) {
        this.fields = fields;
    }

    public List<MethodModel> getMethods() {
        return methods;
    }

    public void setMethods(List<MethodModel> methods) {
        this.methods = methods;
    }
}
