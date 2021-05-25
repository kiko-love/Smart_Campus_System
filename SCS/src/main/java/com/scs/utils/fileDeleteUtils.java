package com.scs.utils;

import java.io.File;

public class fileDeleteUtils {
    public static void deleteAll(File file) {

        if (file.isFile() || file.list().length == 0) {
            file.delete();
        } else {
            for (File f : file.listFiles()) {
                deleteAll(f); // 递归删除每一个文件

            }
            file.delete(); // 删除文件夹
        }
    }
    public static void deleteDir(File file) {
        if (file.isDirectory()) {
            for (File f : file.listFiles())
                deleteDir(f);
        }
        file.delete();
    }
    //判断该文件夹下的文件个数和文件夹个数
    public static int countFileNumber(File file){

        int fileCount = 0;
        File list[] = file.listFiles();
        if(list==null||list.length==0){
            return fileCount;
        }
        for(int i = 0; i < list.length; i++){
            if(list[i].isFile()){
                fileCount++;
            }
        }
        return fileCount;
    }
    //判断该文件夹下的文件夹个数
    public static int countFileDirNumber(File file){
        int folderCount = 0;
        File list[] = file.listFiles();
        if(list==null||list.length==0){
            return folderCount;
        }
        for(int i = 0; i < list.length; i++){
            if(!list[i].isFile()){
                folderCount++;
            }
        }
        return folderCount;
    }
}
