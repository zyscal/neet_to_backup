package test;

import java.io.IOException;

public class regAnjay extends Thread{
    @Override
    public void run() {
        String path = "/home/zyscal/Documents/Anjay/Anjay/output/bin/demo2 --endpoint-name BC35_2 --server-uri coap://192.168.24.129:3323";
        Runtime runtime = Runtime.getRuntime();
        Process pro = null;
        try {
            pro = runtime.exec(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //            int status = pro.waitFor();
        System.out.println("after wait");
    }
}
