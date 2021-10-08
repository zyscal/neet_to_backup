package test;

import java.util.Arrays;
import java.util.HashMap;

public class testcase {
    public static Integer aa = 0;

    public static void main(String args[])
    {
//        HashMap<Byte[], Integer> test = new HashMap<Byte[], Integer>();
//        Byte[] a = new Byte[]{0x1, 0x2};
//        Byte[] b = new Byte[]{0x3, 0x4};
//        test.put(a, 1);
//        test.put(b, 2);
//        System.out.println(test.containsKey(a));
//        test.put(a, 3);
//        System.out.println(test.get(a));
//        byte[] a = new byte[]{0x1, 0x2, 0x3};
//        byte[] d = new byte[]{0x1, 0x2, 0x3};
//        if(Arrays.equals(a, d))
//        {
//            System.out.println("yes");
//        }
//        else {
//            System.out.println("NO");
//        }
//    }
        testcase a = new testcase();
        a.test_fun(aa);
        System.out.println("aa : " + aa);
    }
    public void test_fun(Integer a) {
        a++;
        System.out.println("a : " + a);
    }
}
