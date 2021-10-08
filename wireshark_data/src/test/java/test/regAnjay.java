package test;

import java.io.IOException;

public class regAnjay extends Thread{
    @Override
    public void run() {
//        String path = "/home/zyscal/Documents/Anjay/Anjay/output/bin/demo --endpoint-name BC26badcard --server-uri coap://192.168.24.129:3324";
        String path = "/home/zyscal/Documents/Anjay/Anjay/examples/tutorial/BC6/anjay-bc6 test" + test.times;
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
