package com.winston.tools;

import com.intellij.openapi.project.Project;
import com.winston.code.generator.core.common.enums.DBTypeEnum;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class DataBaseSettingsForm extends JDialog implements ActionListener {
    private JPanel contentPane;
    private JComboBox<String> dbTypeComboBox;
    private JTextField dbUrlTextField;
    private JTextField userNameTextField;
    private JPasswordField passwordJPasswordField;
    private JTextField dataBaseTextField;
    private JLabel dataBaseLabel;
    private DataBaseConfig config;

    public DataBaseSettingsForm() {
        setContentPane(this.contentPane);
    }

    public void createUI(Project project) {
        this.config = DataBaseConfig.getInstance(project);
        reset();
        dbTypeComboBox.addActionListener(this);
        dbUrlTextField.addActionListener(this);
        userNameTextField.addActionListener(this);
        passwordJPasswordField.addActionListener(this);
        dataBaseTextField.addActionListener(this);
    }

    public boolean isModified() {
        boolean modified;
        modified = !Optional.ofNullable(this.dbTypeComboBox.getSelectedItem()).orElse("").equals(this.config.getDbType());
        modified |= !this.dbUrlTextField.getText().equals(this.config.getDbUrl());
        modified |= !this.userNameTextField.getText().equals(this.config.getUserName());
        modified |= !new String(this.passwordJPasswordField.getPassword()).equals(this.config.getPassword());
        modified |= !this.dataBaseTextField.getText().equals(this.config.getDataBase());

        return modified;
    }

    public void apply() {
        this.config
          .setDbType(Optional.ofNullable(dbTypeComboBox.getSelectedItem()).map(Object::toString).orElse(DBTypeEnum.SQLSERVER
            .getDbType()));
        this.config.setDbUrl(this.dbUrlTextField.getText());
        this.config.setUserName(this.userNameTextField.getText());
        this.config.setPassword(new String(this.passwordJPasswordField.getPassword()));
        this.config.setDataBase(this.dataBaseTextField.getText());
    }

    public void reset() {
        DBTypeEnum defaultDbType = DBTypeEnum.SQLSERVER;
        dbTypeComboBox.setSelectedItem(Optional.ofNullable(config)
          .map(DataBaseConfig::getDbType)
          .orElse(defaultDbType.getDbType()));
        dbUrlTextField.setText(Optional.ofNullable(config)
          .map(DataBaseConfig::getDbUrl)
          .orElse(defaultDbType.getDefaultUrl()));
        userNameTextField.setText(Optional.ofNullable(config)
          .map(DataBaseConfig::getUserName)
          .orElse(""));
        passwordJPasswordField.setText(Optional.ofNullable(config)
          .map(DataBaseConfig::getPassword)
          .orElse(""));
        dataBaseTextField.setText(Optional.ofNullable(config)
          .map(DataBaseConfig::getDataBase)
          .orElse(""));
    }

    public JPanel getContentPane() {
        return this.contentPane;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source instanceof JComboBox) {
            String dbType =
              Optional.ofNullable(dbTypeComboBox.getSelectedItem()).map(Object::toString).orElse(DBTypeEnum.SQLSERVER
                .getDbType());
            dataBaseTextField.setVisible(dbType.equals(DBTypeEnum.MYSQL.getDbType()));
            dataBaseLabel.setVisible(dbType.equals(DBTypeEnum.MYSQL.getDbType()));
        }
    }
}
