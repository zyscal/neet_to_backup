package test;

import java.io.IOException;

public class regAnjay extends Thread{
    @Override
    public void run() {
        String path = "/home/zyscal/Documents/need_to_backup/need_to_backup/Anjay_2021/Anjay/examples/tutorial/BC6/anjay-bc6 device1-1 coap://192.168.3.24:6000 ";
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
