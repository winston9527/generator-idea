package com.winston.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.ui.messages.MessageDialog;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFileSystemItem;
import com.winston.code.generator.core.common.enums.DBTypeEnum;
import com.winston.code.generator.core.util.SqlSessionFactoryUtil;
import com.winston.source.ui.TableNameFrame;
import com.winston.tools.DataBaseConfig;
import com.winston.tools.RefreshDictionary;
import org.apache.commons.lang.StringUtils;

/**
 *
 * generate code button
 */
public class GenerateAction extends AnAction {

    public void actionPerformed(AnActionEvent e) {

        Project project = e.getRequiredData(CommonDataKeys.PROJECT);
        String filePath="";
        String packagePath = "";

        Object dataObj = e.getDataContext().getData("psi.Element");
        System.err.println(dataObj);
        if(null!=dataObj){
            //Messages.showWarningDialog("||"+dataObj.toString(),"");
            if(dataObj instanceof PsiFileSystemItem){

                //Messages.showWarningDialog("类型1","");
                PsiFileSystemItem psiFileSystemItem = (PsiFileSystemItem) dataObj;
                filePath = psiFileSystemItem.getVirtualFile().getPath();
                RefreshDictionary.psiFileSystemItem = psiFileSystemItem;
                PsiElement[] children = psiFileSystemItem.getChildren();
                for (PsiElement child : children) {
                    //判断目录下是否存在java文件、方便获取package
                    System.out.println(child);
//                    if(child instanceof PsiJavaFile){
//                        PsiJavaFile psiJavaFile = (PsiJavaFile) child;
//                        packagePath = psiJavaFile.getPackageName();
//                        break;
//                    }
                }
                if(StringUtils.isEmpty(packagePath)){
                    //此处获取package方式不合理
                    //Messages.showErrorDialog("base 地址"+project.getBasePath(),"Error");
                    String basePath = project.getBasePath();
                    packagePath = filePath.replace(basePath,"");
                    packagePath = packagePath.replaceFirst("/","");
                    //针对maven项目
                    packagePath = packagePath.replace("src/main/java/","");
                    packagePath = packagePath.replace("/",".");
                }
            }else{

                //Messages.showWarningDialog("类型2","");
//                PsiClass psiClass = (PsiClass) dataObj;
//                packagePath = psiClass.getQualifiedName();
//                packagePath = packagePath.replace("."+psiClass.getName(),"");
//                //Messages.showWarningDialog(psiClass.getContainingFile().getContainingDirectory().getVirtualFile().getPath(),"");
//                filePath = psiClass.getContainingFile().getContainingDirectory().getVirtualFile().getPath();
//                RefreshDictionary.psiFileSystemItem = psiClass.getContainingFile().getContainingDirectory();
            }
        }else{
            Messages.showErrorDialog("Please choose a package to generate code!","Error");
        }
        if(StringUtils.isEmpty(filePath)){
            filePath = project.getBasePath();
        }
        //Messages.showWarningDialog("filePath:"+filePath+"\npackagePath:"+packagePath,"地址偶偶");
        DataBaseConfig config = DataBaseConfig.getInstance(project);

        //检查配置参数
        if (!validateConfig(project, config)) {
            return;
        }

        //Messages.showWarningDialog(project.getPresentableUrl(),String.format("jdbc:mysql://%s:%s/%s",
        //new Object[] { config.getDbUrl(), config.getUserName(), config.getPassword() }));
        DBTypeEnum dBTypeEnum= DBTypeEnum.find(config.getDbType());
        if(dBTypeEnum==null) {
            //MessageDialog.openInformation(window.getShell(), "Error", "获取数据库类型失败");
            return;
        }
        //创建数据库连接
        try {
            SqlSessionFactoryUtil.createSqlSessionFactoryInstance(dBTypeEnum,config.getDbUrl(), config.getUserName(), config.getPassword());
        }catch (Exception ep){

            Messages.showErrorDialog("Database connection failed:"+ep.getCause().getMessage(), "Error");
            return;
        }

        new TableNameFrame(filePath,packagePath);
    }

    private boolean validateConfig(Project project, DataBaseConfig config)
    {
        if (StringUtils.isBlank(config.getDbUrl())) {
            Messages.showWarningDialog(project, "The  database's dbUrl is null.", "Warning");
            return false;
        }


        if (StringUtils.isBlank(config.getUserName())) {
            Messages.showWarningDialog(project, "The database's userName is null.", "Warning");
            return false;
        }

        if (StringUtils.isBlank(config.getPassword())) {
            Messages.showWarningDialog(project, "The database's password is null.", "Warning");
            return false;
        }

        return true;
    }


    public static void main(String[] args) {

        String uu = "/src/main/java/com/winston/generator";
        System.out.println(uu.replaceFirst("/",""));
        System.out.println(uu.replaceFirst("src/main/java/",""));

    }
}
