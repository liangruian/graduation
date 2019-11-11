package com.lra.common;

/**
 * @Description
 * @Author Anglar
 * @Date 2019/10/30 9:34
 * @Version V1.0
 **/
public class HHH {
    public static void main(String[] args){
        String callBack = callBack("*");
        System.out.println(callBack);
    }

    public static String callBack(String input) {

        int[] array = {0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1,
                0, 0, 1, 1, 4, 5, 2, 3, 4, 1, 0, 1,

                0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0};

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < array.length; i++) {

            if (i % 7 == 0)

                sb.append("  \n");

            if (array[i] == 0)

                sb.append("   ");

            else if (array[i] == 4)

                sb.append("  ");

            else if (array[i] == 5)

                sb.append(" I ");

            else if (array[i] == 2)

                sb.append("Lvoe ");

            else if (array[i] == 3)

                sb.append("You");

            else

                sb.append("  " + input);

        }

        return sb.toString();
    }
}
