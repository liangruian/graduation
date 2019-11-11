package com.lra.common;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @Description
 * @Author Anglar
 * @Date 2019/10/23 10:03
 * @Version V1.0
 **/
public class MarBook {
    public static void main(String args[]) throws Exception {

        InputStream inputStream = new FileInputStream(new File("D:\\aa.txt"));
        int available = inputStream.available();
        byte bytes[] = new byte[available];
        inputStream.read(bytes);
        String allData = new String(bytes, "unicode");
        //String file = "data.ISO";
        // 读取此条数据的总长度
        String marcS = new String(allData.substring(0, 5));
        int marcLen = Integer.parseInt(marcS);
        // System.out.println(marcLen);
        // 读取数据基地址
        String marcS2 = new String(allData.substring(12, 17));
        int dataStart = Integer.parseInt(marcS2);
        // System.out.println(dataStart);
        // 读取次目录区数据
        int cmLength = dataStart - 24 - 1;
        // 读取记录控制信息
        String marcS3 = new String(allData.substring(24, cmLength + 24));
        int n = cmLength / 12;
        String controls[] = new String[n];
        for (int i = 0; i < n; i++) {
            controls[i] = marcS3.substring(i * 12, (i + 1) * 12);
            // System.out.println(controls[i]);
        }
        // 读取数据区信息 008 00410 0000 020001800041090
        int dataLength = marcLen - dataStart - 1;
        String bookData = allData.substring(dataStart,marcLen);
        // System.out.println(dataLength);
        String OKData[][] = new String[n][2];
        for (int i = 0; i < n; i++) {
            OKData[i][0] = controls[i].substring(0, 3);
            int length = Integer.parseInt(controls[i].substring(3, 7));
            int start = Integer.parseInt(controls[i].substring(7));

            String temp = bookData.substring(start,start+length);

            OKData[i][1] = new String(temp);
            System.out.print(OKData[i][0] + "  ");
            System.out.print(controls[i].substring(7) + " " + controls[i].substring(3, 7) + " ");
            System.out.print(OKData[i][1] + "  ");
            System.out.println();
        }
        System.out.println();
    }
}
