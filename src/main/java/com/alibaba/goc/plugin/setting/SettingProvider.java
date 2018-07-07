package com.alibaba.goc.plugin.setting;

import com.intellij.openapi.components.*;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(name = "LeanpubSettingsProvider", storages = {
        @Storage(file = StoragePathMacros.PROJECT_FILE),
        @Storage(file = StoragePathMacros.PROJECT_CONFIG_DIR + "/leanpub.xml", scheme = StorageScheme.DIRECTORY_BASED)})
public class SettingProvider implements PersistentStateComponent<SettingProvider> {

    String apikey = "";
    String slug = "";

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public static SettingProvider create(Project project){
        return ServiceManager.getService(project, SettingProvider.class);
    }


    @Nullable
    @Override
    public SettingProvider getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull SettingProvider state) {
        this.apikey = state.apikey;
        this.slug = state.slug;
    }
}
