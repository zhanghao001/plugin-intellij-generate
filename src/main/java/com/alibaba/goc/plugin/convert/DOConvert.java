package com.alibaba.goc.plugin.convert;

import com.alibaba.goc.plugin.model.DOModel;
import com.alibaba.goc.plugin.model.FieldModel;
import com.alibaba.goc.plugin.model.MethodModel;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiField;
import com.intellij.psi.impl.source.javadoc.PsiDocCommentImpl;
import com.intellij.psi.impl.source.tree.PsiCommentImpl;
import org.apache.commons.lang3.StringUtils;
import org.fest.util.Lists;

public class DOConvert {

    /**
     * 生成DO文件需要的Model
     *
     * @param psiClass
     * @param project
     * @return
     */
    public static DOModel convertClassToModel(PsiClass psiClass, Project project) {
        DOModel model = new DOModel();
        String classComment = parseComment(psiClass);
        String name = psiClass.getName();
        if (psiClass.getName().endsWith("VO")) {
            name = name.substring(0, name.length() - 2) + "DO";
        }
        model.setName(name);
        PsiField[] allFields = psiClass.getAllFields();
        Lists.newArrayList(allFields).forEach(item -> {
            String shortTypeName = item.getType().getPresentableText();
            String fullTypeName = item.getType().getCanonicalText();
            String fieldComment = parseComment(item);
            //如果不是基本的包装器类型,String等,需要import class.
            //生成field
            FieldModel field = new FieldModel();
            field.setComment(fieldComment);
            field.setFieldName(item.getName());
            field.setTypeName(shortTypeName);
            field.setTypeIsBoolean(false);
            model.getFields().add(field);
            //生成getter,setter
            MethodModel getter = new MethodModel();
            getter.setReturnTypeName(shortTypeName);
            getter.setMethodName("get" + firstCharToUpper(item.getName()));
            getter.setMethodContent("return " + item.getName() + ";");
            getter.setArgs(Lists.newArrayList());
            model.getMethods().add(getter);
            MethodModel setter = new MethodModel();
            setter.setReturnTypeName("void");
            setter.setMethodName("set" + firstCharToUpper(item.getName()));
            setter.setMethodContent("this." + item.getName() + " = " + item.getName() + ";");
            setter.setArgs(Lists.newArrayList(field));
            model.getMethods().add(setter);
        });
        return model;
    }

    private static String firstCharToUpper(String name) {
        char c = name.charAt(0);
        String s = String.valueOf(c).toUpperCase();
        return s + name.substring(1);
    }

    private static String parseComment(PsiElement element) {
        if (element == null) {
            return StringUtils.EMPTY;
        }
        PsiElement[] children = element.getChildren();
        for (PsiElement child : children) {
            String text = child.getText();
            if (child instanceof PsiDocCommentImpl || child instanceof PsiCommentImpl) {
                return formatText(text);
            }
        }
        return StringUtils.EMPTY;
    }

    private static String formatText(String text) {
        text = text.replace("/*", "");
        text = text.replace("*/", "");
        text = text.replace("//", "");
        text = text.replace("\n", "");
        text = text.replace("*", "");
        text = text.trim();
        return text;
    }

    private static String formatField(PsiField field) {
        String template = "类型:%s 名称:%s 注释:%s";
        return String.format(template, field.getType().getCanonicalText(), field.getName(), parseComment(field));
    }
}
