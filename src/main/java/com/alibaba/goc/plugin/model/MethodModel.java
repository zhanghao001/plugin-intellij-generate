package com.alibaba.goc.plugin.model;

import java.util.List;

/**
 * 渲染方法的model
 */
public class MethodModel {
    /**
     * 注释
     */
    private String comment;

    private String returnTypeName;
    private String methodName;
    private List<FieldModel> args;
    private String methodContent;

    public String getReturnTypeName() {
        return returnTypeName;
    }

    public void setReturnTypeName(String returnTypeName) {
        this.returnTypeName = returnTypeName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public List<FieldModel> getArgs() {
        return args;
    }

    public void setArgs(List<FieldModel> args) {
        this.args = args;
    }

    public String getMethodContent() {
        return methodContent;
    }

    public void setMethodContent(String methodContent) {
        this.methodContent = methodContent;
    }
}
