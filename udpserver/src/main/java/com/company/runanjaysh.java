package com.company;
import test.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class runanjaysh {
    public static void main(String[] args) throws IOException, InterruptedException {
            regAnjay reg = new regAnjay();
            killanjay kil = new killanjay();

            for(int i = 0; i < 1000; i++)
            {
                reg.run();
                Thread.sleep(8000);
                kil.run();
                Thread.sleep(2000);
                System.out.println(i);
            }
    }

}

