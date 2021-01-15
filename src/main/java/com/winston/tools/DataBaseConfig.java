
package com.winston.tools;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;
import lombok.Data;
import org.jetbrains.annotations.Nullable;

@State(name="DataBaseConfig", storages={@com.intellij.openapi.components.Storage("DataBaseConfigToolConfig.xml")})
@Data
public class DataBaseConfig implements PersistentStateComponent<DataBaseConfig> {
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
    public void loadState(DataBaseConfig mybatisEntity)
    {
        XmlSerializerUtil.copyBean(mybatisEntity, this);
    }

    @Nullable
    public static DataBaseConfig getInstance(Project project) {
        return ServiceManager.getService(project, DataBaseConfig.class);
    }
}