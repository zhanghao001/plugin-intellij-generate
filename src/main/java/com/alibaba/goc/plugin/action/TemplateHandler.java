package com.alibaba.goc.plugin.action;

import com.alibaba.goc.plugin.model.DOModel;
import com.google.common.collect.ImmutableBiMap;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.Map;

public class TemplateHandler {

    public static void generateDO(DOModel doModel) {
        Configuration conf = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        conf.setDefaultEncoding("UTF-8");
        conf.setClassForTemplateLoading(TemplateHandler.class, "/template");
        try {
            Template template = conf.getTemplate("do.template");
            File file = new File(doModel.getFilePath() + doModel.getName() + ".java");
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
            template.process(doModel, out);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Configuration conf = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        conf.setClassForTemplateLoading(TemplateHandler.class, "/template");

        try {

            Map<String, String> param = ImmutableBiMap.of("name", "id", "type", "Long");
            Template template = conf.getTemplate("test.template");
            StringWriter stringWriter = new StringWriter();
            template.process(param, stringWriter);
            System.out.println(stringWriter);

            String sourcePath = TemplateHandler.class.getResource("/").getPath();
            Writer out = new FileWriter(new File(sourcePath + "test.java"));
            System.out.println(sourcePath);
            template.process(param, out);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}
