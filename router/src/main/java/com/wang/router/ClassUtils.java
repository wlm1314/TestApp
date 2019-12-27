package com.wang.router;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dalvik.system.DexFile;

public class ClassUtils {

    private static List<String> getSourcePaths(Context context) throws PackageManager.NameNotFoundException {
        ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0);
        List<String> sourcePaths = new ArrayList<>();

        //当前应用的apk文件
        sourcePaths.add(applicationInfo.sourceDir);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            if (null != applicationInfo.splitSourceDirs){
                sourcePaths.addAll(Arrays.asList(applicationInfo.splitSourceDirs));
            }
        }
        return sourcePaths;
    }

    public static Set<String> getFileNameByPackageName(Context context, final String packageName) throws PackageManager.NameNotFoundException {
        Set<String> classNames = new HashSet<>();
        List<String> paths = getSourcePaths(context);
        DexFile dexFile = null;
        for (String path: paths){
            try {
                //加载apk中的dex 并遍历 获得所有的packageName的类
                dexFile = new DexFile(path);
                Enumeration<String> dexEntries = dexFile.entries();
                while (dexEntries.hasMoreElements()){
                    String className = dexEntries.nextElement();
                    if (className.startsWith(packageName)){
                        classNames.add(className);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (null != dexFile){
                    try {
                        dexFile.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return classNames;
    }

}
