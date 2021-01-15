package com.winston.tools;

import com.intellij.openapi.project.Project;
import com.winston.code.generator.core.util.ConfigUtil;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;

public class DataBaseSettingsForm extends JDialog
{
    private JPanel contentPane;
    private JComboBox<String> dbTypeComboBox;
    private JPasswordField passwordJPasswordField;
    private JTextField dbUrlTextField;
    private JTextField userNameTextField;
    private DataBaseConfig config;

    public DataBaseSettingsForm()
    {
         //setupUI();
        //contentPane = new JPanel();
        setContentPane(this.contentPane);
    }

    public void createUI(Project project) {
        this.config = DataBaseConfig.getInstance(project);
        String dbUrl = this.config.getDbUrl();
        String userName = this.config.getUserName();
        String password = this.config.getPassword();
        if(StringUtils.isEmpty(dbUrl)){
            dbUrl = ConfigUtil.getConfigByKey(ConfigUtil.DBURL);
        }
        if(StringUtils.isEmpty(userName)){
            userName = ConfigUtil.getConfigByKey(ConfigUtil.USERNAME);
        }
        if(StringUtils.isEmpty(password)){
            password = ConfigUtil.getConfigByKey(ConfigUtil.PASSWORD);
        }
        this.dbUrlTextField.setText(dbUrl);
        this.userNameTextField.setText(userName);
        this.passwordJPasswordField.setText(password);
        this.dbTypeComboBox.setSelectedItem("mysql");
    }

    public boolean isModified() {
        boolean modified = false;
        modified |= !this.dbUrlTextField.getText().equals(this.config.getDbUrl());
        modified |= !this.userNameTextField.getText().equals(this.config.getUserName());
        modified |= !new String(this.passwordJPasswordField.getPassword()).equals(this.config.getPassword());
        return modified;
    }

    public void apply() {
        this.config.setDbUrl(this.dbUrlTextField.getText());
        this.config.setUserName(this.userNameTextField.getText());
        this.config.setPassword(new String(this.passwordJPasswordField.getPassword()));
    }

    public void reset() {
        this.dbUrlTextField.setText(this.config.getDbUrl());
        this.userNameTextField.setText(this.config.getUserName());
        this.passwordJPasswordField.setText(this.config.getPassword());
    }

    public JPanel getContentPane() {
        return this.contentPane;
    }



}