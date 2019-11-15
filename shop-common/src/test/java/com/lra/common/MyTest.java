package com.lra.common;

import lombok.Data;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

/**
 * @Description
 * @Author Anglar
 * @Date 2019/11/15 10:44
 * @Version V1.0
 **/
public class MyTest {

    @Test
    public void d(){
        HashMap<String,Person> ss = new HashMap<>();
        Person person = new Person();
        person.setName("哈哈");
        ss.put("s",person);
        ss.put("ss",person);

        Person person1 = ss.get("s");
        Person person2 = ss.get("ss");
        System.out.println(person1.equals(person2));

    }

}

@Data
class Person{
    private String name;
}
