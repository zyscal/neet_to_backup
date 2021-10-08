package test;

import java.io.IOException;

public class regAnjay extends Thread{
    @Override
    public void run() {
        String path = "/home/zyscal/Documents/lwm2m/anjay/Anjay/output/bin/demo --endpoint-name testanjay --server-uri coap://59.110.213.240:5683";
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
