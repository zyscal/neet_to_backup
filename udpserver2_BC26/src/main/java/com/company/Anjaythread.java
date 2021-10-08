package com.company;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Anjaythread extends Thread {

    public int port = 5683;
    public static int NB_Socket_connectID_LwM2Mserver = 0;
    public static int NB_Socket_connectID_CoAPfileserver = 1;
    public int model = -1;
    public boolean firstrun = true;
    public static testcom NBiot_with_BC35;
    public static boolean allready_open = false;
    public DatagramSocket socket;
    String com = "/dev/ttyUSB0";
    /**
     * @param model
     * -1 not chose
     * 1 wifi + UDP/COAP/LwM2M
     * 2 NB-iot + UPD/COAP/LwM2M
     */

    public Anjaythread(int model, DatagramSocket socket)
    {
        this.model = model;
        this.socket = socket;
    }
    public void preBC35_G()
    {
        if(Anjaythread.allready_open == false)
        {
            NBiot_with_BC35 = new testcom();
            NBiot_with_BC35.actionPerformed(com);
            Anjaythread.allready_open = true;
        }

//        NBiot_with_BC35.actionPerformed(NBiot_with_BC35.getAvailableSerialPortsName());
//        System.out.println(NBiot_with_BC35.getAvailableSerialPortsName());

//        System.out.println("after open USB");
//        openSocekt();
    }
    public void openSocekt()
    {
        try {
            Thread.sleep(2000);//a little break between open UDP and open port;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String AT = "AT+NSOCR=DGRAM,17,52616,1\r\n";
        byte[] openUDP = new byte[AT.length()];
        openUDP = AT.getBytes();
        NBiot_with_BC35.writes(openUDP);
    }

    public void firstrun()
    {
        firstrun = false;
        this.preBC35_G();
    }

    @Override
    public void run() {
        //System.out.println("------------Listening to Anjay-------------");
        if(this.model == 2)
        {
            this.preBC35_G();
//            if(firstrun)
//            {
//                System.out.println("this first run()");
//                this.firstrun();
//            }else
//            {
//                System.out.println("this is not first run()");
//            }
        }
        while (true)
        {
                try{
                    DatagramPacket pack_from_anjay = new DatagramPacket(new byte[1024], 1024);
                    socket.receive(pack_from_anjay);
                    System.out.println("enter into anjay : " + socket.getLocalPort());

                    if(socket.getLocalPort() == Main.port_LwM2M_server)
                    {
                        Main.anjayport = pack_from_anjay.getPort();
                    }
                    else if(socket.getLocalPort() == Main.port_CoAP_file_server)
                    {
                        Main.anjay_coapfileclient_port = pack_from_anjay.getPort();
                    }

                    Main.anjayaddr = pack_from_anjay.getAddress().toString();
                    switch (this.model)
                    {
                        case 1:
                            this.run_with_model_1(pack_from_anjay);
                            break;
                        case 2:
                            this.run_with_model_2(pack_from_anjay);
                            break;
                        default:
                    }
                    System.out.println("out of switch");

                    //System.out.println("------------Listening to Anjay finish-------------");
                    //this.run();
                } catch (SocketException e) {
                    //System.out.println("problem with socket_list_anjay");
                    e.printStackTrace();
                } catch (IOException e) {
                    //System.out.println("problem with receive from anjay");
                    e.printStackTrace();
                }
                System.out.println("end thread");
            }
        }


    public void run_with_model_1(DatagramPacket pack_from_anjay) throws IOException {
        DatagramPacket pack_to_leshan = new DatagramPacket(pack_from_anjay.getData(),
                pack_from_anjay.getLength(),Main.server_addr, port);
        Main.socket_leshan.send(pack_to_leshan);
    }
    public void run_with_model_2(DatagramPacket pack_from_anjay) {
        //System.out.println("enter into run_with_model_2");
        int length = pack_from_anjay.getLength();
        byte[] pack_to_byte = pack_from_anjay.getData();
        String send_by_NB = "";
        if(socket.getLocalPort() == Main.port_LwM2M_server)
        {

            send_by_NB = "AT+QISENDEX=" + NB_Socket_connectID_LwM2Mserver + "," + length + ",";
        }
        else if(socket.getLocalPort() == Main.port_CoAP_file_server)
        {
            send_by_NB = "AT+QISENDEX=" + NB_Socket_connectID_CoAPfileserver + "," + length + ",";
            System.out.println("**************************5700*********************");
        }

        for(int i = 0; i < pack_from_anjay.getLength(); i++)
        {

              int chang = pack_to_byte[i] & 0xFF;
//              //System.out.println("change : " + chang);
              String temsend = Integer.toHexString(chang);
              if (temsend.length() < 2)
              {
                  send_by_NB += "0";
              }
              send_by_NB += temsend;
        }

        NBiot_with_BC35.writes_by_String(send_by_NB);

    }


}
