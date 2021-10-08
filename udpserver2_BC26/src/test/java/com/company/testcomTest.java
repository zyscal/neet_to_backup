package com.company;

class testcomTest {
    public static void main(String[] args)
    {
        byte[][] ignore ={{0x53, 0x45, 0x4e, 0x44, 0x20, 0x4f, 0x4b}, {0x4f, 0x4b}};
        System.out.println(ignore.length);
        testcomTest a = new testcomTest();
        a.test(ignore);
    }

    public void test(byte[][] ignore)
    {
        System.out.println(ignore.length);
        for(int i = 0; i < ignore.length; i++)
        {
            for(int j = 0; j < ignore[i].length; j++)
            {
                System.out.print(ignore[i][j] + "");
            }
            System.out.println();
        }
    }


}