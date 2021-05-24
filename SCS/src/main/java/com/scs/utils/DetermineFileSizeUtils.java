package com.scs.utils;

import org.springframework.web.multipart.MultipartFile;

import java.text.DecimalFormat;

public class DetermineFileSizeUtils {
    /**
     * 获取文件的大小(返回到达的最高单位)
     * 比如：1024Byte就不再用Byte
     * 直接返回1KB
     * 返回值精确到小数点后3位
     *
     * @param file 文件
     * @return 文件的大小 若文件不存在或者不是文件就返回null
     */
    public static String getSize(MultipartFile file) {
        long size = file.getSize();
        double s = (double) size;
        String unit;
        if (size != -1L) {
            int l;
            if (size < 1024L) {
                l = 0;
            } else if (size < 1024L * 1024L) {
                l = 1;
                s = (double) size / 1024L;
            } else {
                for (l = 2; size >= 1024L * 1024L; l++) {
                    size = size / 1024L;
                    if ((size / 1024L) < 1024L) {
                        s = (double) size / 1024L;
                        break;
                    }
                }
            }

            switch (l) {
                case 0:
                    unit = "Byte";
                    break;
                case 1:
                    unit = "KB";
                    break;
                case 2:
                    unit = "MB";
                    break;
                case 3:
                    unit = "GB";
                    break;
                case 4:
                    //不可能也不该达到的值
                    unit = "TB";
                    break;
                default:
                    //ER代表错误
                    unit = "ER";
            }

            String format = String.format("%.2f", s);
            return format + unit;
        }
        return null;
    }
}



