package com.alibaba.goc.plugin.action;

import com.alibaba.goc.plugin.convert.DOConvert;
import com.alibaba.goc.plugin.model.DOModel;
import com.alibaba.goc.plugin.setting.SettingProvider;
import com.alibaba.goc.plugin.util.NotificationUtils;
import com.intellij.codeInsight.generation.OverrideImplementUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;

import java.io.File;

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

        System.out.print(1123);

        System.out.println(SettingProvider.create(e.getProject()).getApikey());
        System.out.print(5678);

        Project project = e.getProject();
        PsiFile file = e.getData(LangDataKeys.PSI_FILE);
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        if (file == null || editor == null) {
            e.getPresentation().setEnabled(false);
        }
        //获取当前的java类
        PsiClass psiClass = OverrideImplementUtil.getContextClass(e.getProject(), editor, file, false);
        String dalPath = project.getBasePath() + File.separator + "dal" + File.separator;

        dalPath += "src" + File.separator + "main" + File.separator + "java" + File.separator;
        NotificationUtils.showEventLog("code-generate", "do generate", "file path = " + dalPath, project);
        //转化为do模型
        DOModel model = DOConvert.convertClassToModel(psiClass, project);
        model.setFilePath(dalPath);
        TemplateHandler.generateDO(model);
        System.out.println(model);
        VirtualFileManager.getInstance().syncRefresh();
        project.getWorkspaceFile().refresh(false, true);
        project.getProjectFile().refresh(false, true);
        project.getBaseDir().refresh(false, true);


    }


}
