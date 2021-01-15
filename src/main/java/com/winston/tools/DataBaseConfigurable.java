package com.winston.tools;


import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.project.Project;
import com.sun.istack.NotNull;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.JComponent;

public class DataBaseConfigurable implements SearchableConfigurable
{
    private DataBaseSettingsForm contentPanel;
    private final Project project;

    public DataBaseConfigurable(@NotNull Project project)
    {
        this.project = project;
    }

    @Nls
    public String getDisplayName()
    {
        return "GenerateCode";
    }

    @Nullable
    public String getHelpTopic()
    {
        return "GenerateCode";
    }

    @NotNull
    public String getId()
    {
        return "preference.DataBaseConfigurable";
    }

    @Nullable
    public Runnable enableSearch(String s)
    {
        return null;
    }

    @Nullable
    public JComponent createComponent()
    {
        this.contentPanel = new DataBaseSettingsForm();
        this.contentPanel.createUI(this.project);
        return this.contentPanel.getContentPane();
    }

    public boolean isModified()
    {
        return this.contentPanel.isModified();
    }

    public void apply() throws ConfigurationException
    {
        this.contentPanel.apply();
    }

    public void reset()
    {
        this.contentPanel.reset();
    }

    public void disposeUIResources()
    {
        this.contentPanel = null;
    }
}