
package com.winston.tools;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

@State(name="DataBaseConfig", storages={@com.intellij.openapi.components.Storage("DataBaseConfigToolConfig.xml")})
@Data
public class DataBaseConfig implements PersistentStateComponent<DataBaseConfig>, Serializable {
    private String dbType;
    private String dbUrl;
    private String userName;
    private String password;
    private String dataBase;

    @Nullable
    @Override
    public DataBaseConfig getState()
    {
        return this;
    }

    @Override
    public void loadState(@NotNull DataBaseConfig dataBaseConfig)
    {
        XmlSerializerUtil.copyBean(dataBaseConfig, this);
    }

    @Nullable
    public static DataBaseConfig getInstance(Project project) {
        return ServiceManager.getService(project, DataBaseConfig.class);
    }
}