package com.winston.tools;

import com.intellij.psi.PsiFileSystemItem;

/**
 * @Description:
 * @Author Winston
 * @Version 1.0 2017/4/17 19:19
 */
public class RefreshDictionary {

    public static PsiFileSystemItem psiFileSystemItem = null;

    public static void refreshDictionary(){
        if(psiFileSystemItem!=null){
            psiFileSystemItem.getVirtualFile().refresh(true,true);
        }
    }


}
