package com.alibaba.goc.plugin.action;

import com.alibaba.goc.plugin.util.NotificationUtils;
import com.intellij.codeInsight.generation.OverrideImplementUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiFile;
import com.intellij.psi.impl.source.javadoc.PsiDocCommentImpl;
import com.intellij.psi.impl.source.tree.PsiCommentImpl;
import org.apache.commons.lang3.StringUtils;
import org.fest.util.Lists;

/**
 * 程序入口.<br/>
 * 代码流程:
 * <ul>
 * <li>在VO中点击Generate的popups,选择该插件,然后获取当前的class(VO类)</li>
 * <li>分解出所有的field,然后初始化到各个model中</li>
 * <li>使用freemarker渲染模版文件,然后生成代码,刷新文件系统</li>
 * </ul>
 * 生成文件种类:
 * <ul>
 * <li>DO</li>
 * <li>DAO</li>
 * <li>SQL Mapper</li>
 * <li>Service</li>
 * </ul>
 * <a herf='https://www.jetbrains.org/intellij/sdk/docs/user_interface_components/popups.html'>popups官方文档</a>
 */
public class GenerateAction extends AnAction {


    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        Project project = e.getProject();
        PsiFile file = e.getData(LangDataKeys.PSI_FILE);
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        if (file == null || editor == null) {
            e.getPresentation().setEnabled(false);
        }
        //获取当前的java类,以及他的所有字段
        PsiClass psiClass = OverrideImplementUtil.getContextClass(e.getProject(), editor, file, false);
        String classComment = parseComment(psiClass);
        PsiField[] allFields = psiClass.getAllFields();
        Lists.newArrayList(allFields).forEach(item -> {
            NotificationUtils.showEventLog(classComment, item.getName(), formatField(item), project);
        });
    }

    private String formatField(PsiField field) {
        String template = "类型:%s 名称:%s 注释:%s";
        return String.format(template, field.getName(), field.getType().getCanonicalText(), parseComment(field));
    }

    private String parseComment(PsiElement element) {
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

    private String formatText(String text) {
        text = text.replace("/*", "");
        text = text.replace("*/", "");
        text = text.replace("//", "");
        text = text.replace("\n", "");
        text = text.replace("*", "");
        text = text.trim();
        return text;
    }
}
