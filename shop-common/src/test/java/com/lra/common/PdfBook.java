package com.lra.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author Anglar
 * @Date 2019/10/22 9:54
 * @Version V1.0
 **/
public class PdfBook {

    private final static String LAGPATTEN = "\\|\\|\\|\\|\\|\\|\\|\\|[a-z]+\\|\\|";

    private final static String ISBN_Patten = "¬a[0-9]+[ ]*¬[ ]*b";

    private final static String CATEGORY_PATTEN = "¬([ ]*)b[0-9]*( |¬)?([0-9]*)([ ]*)¬a";

    private final static String BOOK_INFO_PATTEN = "(¬([ ]*)a([0-9]*(.)?[0-9]*[ ]*¬[ ]*b[0-9]*P)?(([\\u4E00-\\u9FA5]*[a-zA-Z]*(.)?[0-9]?[\\u4E00-\\u9FA5]*[a-zA-Z]*)[ ]*[0-9]*[ ]*¬([ ]*)a){1,})|(¬([ ]*)a([a-zA-Z0-9]*[ ]*)*[ ]*¬([ ]*)a)";

    private final static String BOOK_ADDRESS_PATTEN = "¬[ ]*a[ ]*[\\u4E00-\\u9FA5]*[a-zA-Z]*[ ]*¬[ ]*b";

    public static void main(String[] args) throws Exception {
        InputStream inputStream = new FileInputStream(new File("D:\\aa.txt"));
        int available = inputStream.available();
        byte bytes[] = new byte[available];
        inputStream.read(bytes);
        String allData = new String(bytes, "unicode");
        //00440nam  -----
        String[] split = allData.split("[0-9]{5}nam  ");
        for (String s : split) {
            String[] split1 = s.split("\\|{9}");
            if (split1.length > 2) {
                String data = split1[2];
                //语言
                Pattern laPatten = Pattern.compile(LAGPATTEN);
                Matcher lagMatcher = laPatten.matcher(data);
                if (lagMatcher.find()) {
                    String lag = lagMatcher.group(0).replace("|", "");
                }
                //ISBN
                Pattern ISBNPatten = Pattern.compile(ISBN_Patten);
                Matcher ISBNMatcher = ISBNPatten.matcher(s);
                if (ISBNMatcher.find()) {
                    String ISBN = ISBNMatcher.group(0)
                            .replace("¬", "")
                            .replace(" ", "")
                            .replace("a", "")
                            .replace("b", "");
                }
                //分类号?出版社？
                Pattern cagPatten = Pattern.compile(CATEGORY_PATTEN);
                Matcher cagMatcher = cagPatten.matcher(s);
                if (cagMatcher.find()) {
                    String category = cagMatcher.group(0);
                    category.substring(2, category.length() - 2).trim().replace(" ", "-");
                }
                //书名，版本，作者丛书号/名
                Book info = getBookInfo(s);
                //出版地
                Pattern addressPattern = Pattern.compile(BOOK_ADDRESS_PATTEN);
                Matcher matcher = addressPattern.matcher(s);
                if (matcher.find()) {
                    String address = matcher.group();
                    address = address.substring(2, address.length() - 2);
                }
                System.out.println();
            }
        }

    }

    @Test
    public void ss() throws Exception {
        InputStream inputStream = new FileInputStream(new File("D:\\aa.txt"));
        int available = inputStream.available();
        byte bytes[] = new byte[available];
        inputStream.read(bytes);
        String allData = new String(bytes, "unicode");
        //00440nam  -----

        String[] split = allData.split("[0-9]{5}[a-zA-Z]{3}  ");
        List<String> strings = Arrays.asList(split);
        Map<Integer, List<String>> collect = strings.stream().collect(Collectors.groupingBy(s -> s.split("¬a").length));


        Map<Integer,List<String[]>> data = new HashMap<>();
        collect.forEach((k, v) -> {
            v.forEach(s->{
                String[] split1 = s.split("¬a");
                if(data.containsKey(k)){
                    List<String[]> strings1 = data.get(k);
                    strings1.add(split1);
                    data.put(k,strings1);
                }else{
                    List<String[]> strings1 = new ArrayList<>();
                    strings1.add(split1);
                    data.put(k,strings1);
                }
            });
        });

        System.out.println(collect);
    }

    public static Book getBookInfo(String data) {
        Pattern cagPatten = Pattern.compile(BOOK_INFO_PATTEN);
        Matcher cagMatcher = cagPatten.matcher(data);
        int group = 0;
        String categoryNum = null;
        String author = null;
        String bookName = null;
        String bookVersion = null;
        String collBookName = null;
        String collBookNumber = null;
        while (cagMatcher.find()) {
            if (group == 0) {
                String category = cagMatcher.group();
                if (category.contains("¬b")) {
                    //  ¬a999.99¬b9870P 10¬a測試abc   ¬a測試版   ¬a
                    String[] split = category.split("¬b");
                    categoryNum = split[0].replace("¬", "")
                            .replace(" ", "")
                            .replace("a", "");
                    String[] bookInfo = split[1].split("¬a");
                    author = bookInfo[0].split("P")[0];
                    bookName = bookInfo[1];
                    bookVersion = bookInfo[2];
                } else {
                    // ¬a荷莉.布萊克 10¬a魔法學園1鋼鐵試煉   ¬a初版   ¬a
                    // ¬a小喬的樹不見了   ¬a第一版   ¬a
                    String[] split = category.split("¬a");
                    if (split.length == 3) {
                        bookName = split[1];
                        bookVersion = split[2];
                    } else if (split.length == 4) {
                        author = split[1];
                        bookName = split[2];
                        bookVersion = split[3];
                    }
                }
            } else {
                // ¬a皇冠叢書       ¬a
                // ¬a親愛關係¬v11   ¬alib   ¬a
                // ¬a親愛關係¬v11   ¬a
                //集叢書名
                //集叢書號
                String collBook = cagMatcher.group();
                String[] split = collBook.split("¬a");
                if (!collBook.contains("¬v")) {
                    collBookName = split[1];
                } else {
                    String[] split1 = split[1].split("¬v");
                    collBookName = split1[0];
                    collBookNumber = split1[1];
                }
            }
            group++;
        }
        return new Book(categoryNum, author, bookName, bookVersion, collBookName, collBookNumber);
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Book {
    String category;
    String isbn;
    String lag;

    String categoryNum;
    String author;
    String bookName;
    String bookVersion;
    String collBookName;
    String collBookNumber;

    public Book(String categoryNum, String author, String bookName, String bookVersion, String collBookName, String collBookNumber) {
        this.categoryNum = categoryNum;
        this.author = author;
        this.bookName = bookName;
        this.bookVersion = bookVersion;
        this.collBookName = collBookName;
        this.collBookNumber = collBookNumber;
    }
}
