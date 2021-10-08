package test;

public class test {
    public static void main(String[] args)
    {
        String a = "\r\n123\r\n";
        String[]b = a.split("\r\n");
        System.out.println("size of b : " + b.length);
        for(int i = 0; i < b.length; i++)
        {
            System.out.println(b[i]);
        }
    }
}
