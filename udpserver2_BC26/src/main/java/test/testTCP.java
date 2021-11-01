package test;

import com.company.Main;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class testTCP {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(5701);

            while (true) {
                Socket s = serverSocket.accept();
                InputStream in = s.getInputStream();
                int LengthOfInputSream = in.available();
                System.out.println(LengthOfInputSream);
                byte[] recv = new byte[LengthOfInputSream];
                in.read(recv);
                for (int i = 0; i < recv.length; i++) {
                    int chang = recv[i] & 0xFF;
                    String hex_recv = Integer.toHexString(chang);
                    System.out.print(hex_recv + " ");
                }
            }




//            System.out.println("in.available() ： " + in.available());


//            int num = 0;
//            byte[] by = new byte[1024];
//            in.read(by);
//            System.out.println("by 的大小是 ： " + );
//                for(int i = 0; i ; i++)
//                {
//                    int chang = pack_to_byte[i] & 0xFF;
//                    String temsend = Integer.toHexString(chang);
//                    if (temsend.length() < 2)
//                    {
//                        send_by_NB += "0";
//                    }
//                    send_by_NB += temsend;
//                }


        } catch (Exception e) {}
    }
}
