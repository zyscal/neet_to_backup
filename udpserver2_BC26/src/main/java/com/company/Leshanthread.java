package com.company;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Leshanthread extends Thread{
    public int model = -1;
    private int port = 5683;

    public Leshanthread(int model)
    {
        this.model = model;
    }

    @Override
    public void run() {
        System.out.println("------------Listening to leshan-------------");
        try {
            if(Main.anjayaddr.equals("0.0.0.0") == false  && Main.anjayport != -1)
            {
            DatagramPacket pack_from_leshan = new DatagramPacket(new byte[1024], 1024);
            Main.socket_leshan.receive(pack_from_leshan);
            System.out.println("leshan address : " + pack_from_leshan.getAddress());
            System.out.println("leshan length : " + pack_from_leshan.getLength());

            //if anjay address and port exists
                String anjayaddr = Main.anjayaddr.split("/")[1];
                System.out.println("anjay address : " + anjayaddr);
                DatagramPacket pack_to_anjay = new DatagramPacket(pack_from_leshan.getData(),
                        pack_from_leshan.getLength(),InetAddress.getByName(anjayaddr),Main.anjayport);

                Main.socket_anjay.send(pack_to_anjay);
                System.out.println("pack_to_anjay : success");

                this.run();
                System.out.println("------------Listening to leshan finish-------------");
            }
            else {
                Thread.sleep(1000);
                this.run();
            }
        } catch (SocketException e) {
            System.out.println("problem with socket_list_leshan");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("problem with socket_list_leshan recive");
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
