package com.alibaba.goc.plugin.setting;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class SettingConfig implements SearchableConfigurable {


    public SettingConfig(Project project) {
        this.project = project;
    }

    Project project;

    SettingForm settingForm;


    @NotNull
    @Override
    public String getId() {
        return "Leanpub";
    }

    @Nls
    @Override
    public String getDisplayName() {
        return "Leanpub";
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return "Leanpub";
    }

    @Nullable
    @Override
    public Runnable enableSearch(String option) {
        return null;
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        SettingProvider settingProvider = SettingProvider.create(project);
        settingForm = new SettingForm(project);
        return settingForm.createPanel(settingProvider);
    }

    @Override
    public boolean isModified() {
        return settingForm.isModified();
    }

    @Override
    public void apply() {
        settingForm.apply();
    }

    @Override
    public void reset() {
        settingForm.reset();
    }

    @Override
    public void disposeUIResources() {

    }


}

