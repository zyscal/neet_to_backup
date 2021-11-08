package com.company;
import test.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class runanjaysh {
    public static void main(String[] args) throws IOException, InterruptedException {
            regAnjay reg = new regAnjay();
            killanjay kil = new killanjay();

            int min = 60; //每轮跑50分钟
            for(int i = 10; i <= 30; i += 2) {
                reg.model = i;
                int count = min * 60 / i;
                for(int j = 0; j < count; j++) {
                    System.out.println("model is " + i + "count is " + j + "/" +count);
                    reg.count = j;
                    reg.run();
                    Thread.sleep((i - 2) * 1000);
                    kil.run();
                    Thread.sleep(2*1000);
                }
            }


    }

}

