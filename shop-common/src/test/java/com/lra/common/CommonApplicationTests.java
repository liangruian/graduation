package com.lra.common;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

//@SpringBootTest
class CommonApplicationTests {

    @Test
    void contextLoads() {
    }

    public static void main(String args[]) throws Exception {

        String s= "01475nam0 2200325   450 001001300000005001700013010003200030049003100062100004100093101001800134102001500152105001800167106000600185200023600191210004000427215001700467300005300484305003800537330015500575510006700730517005100797606006800848690001200916701004900928701005100977702003501028702003801063801002501101801002301126!012003000006!20181018200552.9!  !a978-7-5047-3466-2!dCNY58.00!  !aA210000LPL!bUCS01004438268!  !a20181018d2010    em y0chiy0110    ea!2 !achi!aeng!ceng!  !aCN!b110000!  !ay   z   000yy!  !ar!1 !a过程管理!9guo cheng guan li!b专著!e供应链的增值!dProcess management!ecreating value along the supply chain!f乔尔·D. 威斯纳(Joel D.Wisner), 琳达·L. 斯坦利(Linda L. Stanley)著!g纪寿文, 房圆晨译注!zeng!  !a北京!c中国物资出版社!d2010!  !a357页!d29cm!  !a高等院校物流与供应链双语实验教材!  !a由圣智学习出版公司授权!  !a本书着重介绍了以客户为导向的过程战略、制造和服务流程、精益生产系统、质量和过程执行、未来发展趋势等。!1 !aProcess management!ecreating value along the supply chain!zeng!1 !a供应链的增值!9gong ying lian de zeng zhi!0 !a企业管理!9qi ye guan li!x供销管理!x高等教育!j教材!  !aF274!v4! 0!a威斯纳!9wei si na!c(Wisner, Joel D.)！4著！ 0！a斯坦利！9si tan li！c(Stanley, Linda L.)！4著！ 0！a纪寿文！9ji shou wen！4译注！ 0！a房圆晨！9fang yuan chen！4译注！ 0！aCN！b110017！c20101012！ 2！aCN！b0625！c20181018！！";
        InputStream fin = new ByteArrayInputStream(s.getBytes());
        //String file = "data.ISO";
        //FileInputStream fin = new FileInputStream(file);
        // 数组bytes用于存放读取的所有字节
        int fileSize = fin.available();
        byte bytes[] = new byte[fileSize];
        fin.read(bytes);
        // 读取此条数据的总长度
        byte marcB[] = new byte[5];
        for (int i = 0; i < 5; i++) {
            marcB[i] = bytes[i];
        }
        String marcS = new String(marcB);
        int marcLen = Integer.parseInt(marcS);
        // System.out.println(marcLen);
        // 读取数据基地址
        byte marcB2[] = new byte[5];
        for (int i = 0; i < 5; i++) {
            marcB2[i] = bytes[i + 12];
        }
        String marcS2 = new String(marcB2);
        int dataStart = Integer.parseInt(marcS2);
        System.out.println(s.substring(dataStart));
        // System.out.println(dataStart);
        // 读取次目录区数据
        int cmLength = dataStart - 24 - 1;
        byte marcB3[] = new byte[cmLength];
        for (int i = 0; i < cmLength; i++) {
            marcB3[i] = bytes[i + 24];
        }
        // 读取记录控制信息
        String marcS3 = new String(marcB3);
        System.out.println(marcS3);
        int n = cmLength / 12;
        String controls[] = new String[n];
        for (int i = 0; i < n; i++) {
            controls[i] = marcS3.substring(i * 12, (i + 1) * 12);
            // System.out.println(controls[i]);
        }
        // 读取数据区信息
        int dataLength = marcLen - dataStart - 1;
        byte data[] = new byte[dataLength];
        for (int i = 0; i < dataLength; i++) {
            data[i] = bytes[i + dataStart];
        }
        // System.out.println(dataLength);
        String OKData[][] = new String[n][2];
        for (int i = 0; i < n; i++) {
            OKData[i][0] = controls[i].substring(0, 3);
            int length = Integer.parseInt(controls[i].substring(3, 7));
            int start = Integer.parseInt(controls[i].substring(7));
            byte temp[] = new byte[length];
            for (int j = start; j < start + length; j++) {
                temp[j - start] = data[j];
            }
            OKData[i][1] = new String(temp);
            System.out.print(OKData[i][0] + "  ");
            System.out.print(controls[i].substring(7) + " " + controls[i].substring(3, 7) + " ");
            System.out.print(OKData[i][1] + "  ");
            System.out.println();
        }
        System.out.println(new String(data));
        fin.close();
    }
}
