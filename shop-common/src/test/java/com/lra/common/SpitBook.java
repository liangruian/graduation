package com.lra.common;

import cn.hutool.core.bean.BeanUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author Anglar
 * @Date 2019/10/23 12:16
 * @Version V1.0
 **/
public class SpitBook {

    private final static String LAGPATTEN = "\\|\\|\\|\\|\\|\\|\\|\\|[a-z]+\\|\\|";

    private final static String ISBNPATTERN = "[0-9]+(\\-*[0-9]*)*";

    private final static String AUTHOUPATTEN = "[a-z0-9A-Z]*(\\-|.)[a-z0-9A-Z]* [0-9]+";

    @Test
    public void ss() throws Exception {
        InputStream inputStream = new FileInputStream(new File("D:\\aa.txt"));
        int available = inputStream.available();
        byte bytes[] = new byte[available];
        inputStream.read(bytes);
        String allData = new String(bytes, "unicode");
        String[] split = allData.split("[0-9]{5}[a-zA-Z]{3}  ");
        List<String> strings = Arrays.asList(split);
        List<BookInfo> bookInfos = new ArrayList<>();
        for (String string : strings) {
            BookInfo bookInfo = new BookInfo();
            String[] is = string.split("¬a");
            //1 语言 -a:ISBN -b出版商
            String i = is[0];
            //语言
            Pattern laPatten = Pattern.compile(LAGPATTEN);
            Matcher lagMatcher = laPatten.matcher(i);
            if (lagMatcher.find()) {
                String lag = lagMatcher.group(0).replace("|", "");
                bookInfo.setLag(lag);
            }
            if (i.split("b").length > 1) {
                String bookHome = i.split("b")[1];
                if (!bookHome.contains("|")) {
                    bookInfo.setBookHome(bookHome);
                }
            }
            if (i.split("a").length > 1) {
                String isbn = i.split("a")[1];
                if (!isbn.contains("|")) {
                    bookInfo.setIsbn(isbn);
                }
            }

        }

        //数据测试
        Map<Integer, List<String>> collect = strings.stream().collect(Collectors.groupingBy(s -> s.split("¬a").length));
        Map<Integer, List<String>> listMap = new HashMap<>();
        collect.remove(1);
        collect.forEach((k, v) -> {
            v.forEach(s -> {
                String[] is = s.split("¬a");
                for (int i = 0; i < is.length; i++) {
                    if (listMap.containsKey(i)) {
                        List<String> stringList = listMap.get(i);
                        stringList.add(is[i] + "                                                                             @@@@@@@@-" + s.substring(s.indexOf("|")));
                        listMap.put(i, stringList);
                    } else {
                        List<String> stringList = new ArrayList<>();
                        stringList.add(is[i] + "                                                                             @@@@@@@@-" + s.substring(s.indexOf("|")));
                        listMap.put(i, stringList);
                    }
                }
            });
        });
        System.out.println(collect);
    }

    @Test
    public void getIndex_1() {
        String s = "7-88024-149-3        -aTP-H151 10";
        String[] split = s.split("-a");
        if (split.length > 1) {
            String one = split[0];
            String[] split1 = one.split("¬b");
            String two = split[1];
            String[] split2 = two.split("¬b");
            if (split1.length > 1) {
                //523 ¬bT 10  -aWorkbook- level2                ¬bRead it yourself with ladybird level2
                String cagNumber = split1[0];
                String authoe = split1[1];
                String[] nameAndVersion = split[1].split("¬b");
                String bookName = nameAndVersion[0];
                String bookVersion = nameAndVersion[1];
                System.out.println();
            } else if (split2.length > 1) {
                if (split2[1].contains(".")) {
                    //7-88024-149-3        -aTP-H151                        ¬b C.1 10
                    String isbn = split[0];
                    String cagNumber = split2[0];
                    String authoe = split2[1];
                } else {
                    //H319.9 10            -aRead-the-Greats指導手冊(粉紅)   ¬b 大師精選魔法書
                    String authoe = split[0];
                    String bookName = split2[0];
                    String bookVersion = split2[1];
                }
            } else {
                //7-88024-149-3        -aTP-H151 10
                String isbn = split[0];
                String authoe = split[1];
            }
        } else {
            String[] split1 = s.split("¬b");
            if (split.length > 1) {
                String addressOrAuthor = split1[1];
                Pattern pattern = Pattern.compile("[0-9]+");
                Matcher matcher = pattern.matcher(addressOrAuthor);
                if (matcher.find()) {
                    //857¬b5444 10
                    String cagNum = split1[0];
                    String authoe = split1[0];
                } else {
                    //962-12-2957-x   ¬b澳門
                    String isbn = split1[0];
                    String address = split1[0];
                }
            } else {
                Pattern pattern = Pattern.compile(AUTHOUPATTEN);
                Matcher matcher = pattern.matcher(s);
                if (matcher.find()) {
                    //TP-CD 10
                } else {
                    pattern = Pattern.compile(ISBNPATTERN);
                    matcher = pattern.matcher(s);
                    if (matcher.find()) {
                        //7-88024-148-5
                        String isbn = s;
                    } else {
                        //港孩
                        String bookName = s;
                    }
                }
            }
        }
    }

    @Test
    public void getIndex_2() {
        String s = "小萌芽3   -alibrary";
        String[] splitB = s.split("¬b");
        if(splitB.length == 2){
            //I89¬b30 10
            //N   ¬b20000598¬dN
            //春秋‧戰國‧秦   ¬b智能教育
            //折紙戰士2   ¬b吉林美術出版社¬c2002.9
            //八十天環遊地球   -aN   ¬b20009897¬dN   ¬n42
        }

    }

}

@Data
@AllArgsConstructor
@NoArgsConstructor
class BookInfo {

    String category;
    String isbn;
    String lag;
    String categoryNum;
    String author;
    String bookName;
    String bookVersion;
    String collBookName;
    String collBookNumber;
    String bookHome;

    public BookInfo(String categoryNum, String author, String bookName, String bookVersion, String collBookName, String collBookNumber) {
        this.categoryNum = categoryNum;
        this.author = author;
        this.bookName = bookName;
        this.bookVersion = bookVersion;
        this.collBookName = collBookName;
        this.collBookNumber = collBookNumber;
    }
}





