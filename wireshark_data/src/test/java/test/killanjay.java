package test;

import java.io.IOException;

public class killanjay extends Thread {
    @Override
    public void run() {
        String path = "pkill anjay-bc6";
        Runtime runtime = Runtime.getRuntime();
        Process pro = null;
        try {
            pro = runtime.exec(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            int status = pro.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
