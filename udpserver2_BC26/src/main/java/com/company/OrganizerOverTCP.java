package com.company;

import javax.swing.*;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class OrganizerOverTCP extends Thread {
    private int model = -1;
    private boolean[] openStatus = {false, false};
    public OrganizerOverTCP(int model) {
        this.model = model;
    }

    @Override
    public void run() {
        try {
            // 先关后开
            String CloseTcp = "AT+QICLOSE=" + model;
            Main.NBiot_with_BC35.writes_by_String(CloseTcp);
            Thread.sleep(1000);

            // 打开NB-IoT连接
            boolean firstMessage = true;
            ServerSocket serverSocket = new ServerSocket(5701);
            if(model == Main.NB_TCPSocket_connectID) {
                Main.TCPSocket = serverSocket.accept();
            }

            String OpenTcp = "AT+QIOPEN=1," +  model + ",\"TCP\",\"39.107.84.110\",5800,0,1";
            Main.NBiot_with_BC35.writes_by_String(OpenTcp);
            while (true) {
                InputStream in = Main.TCPSocket.getInputStream();
                int LengthOfInputSream = in.available();
                if(LengthOfInputSream == 0) {
                    continue;
                } else if(firstMessage) {
                    Thread.sleep(10000);
                    firstMessage = false;
                }
                byte[] recv = new byte[LengthOfInputSream];
                in.read(recv);
                String sendByNB = "AT+QISENDEX=" + model + ",";
                sendByNB += recv.length + ",";
                for (int i = 0; i < recv.length; i++) {
                    int chang = recv[i] & 0xFF;
                    String hex_recv = Integer.toHexString(chang);
                    if(hex_recv.length() < 2) {
                        sendByNB += "0";
                    }
                    sendByNB += hex_recv;
                }
                System.out.println("sendByNB : " + sendByNB);
                Main.NBiot_with_BC35.writes_by_String(sendByNB);
                System.out.println("after bc35 write");
            }
        } catch (Exception e) {
        }
    }
}
