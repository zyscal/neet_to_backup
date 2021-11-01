package com.company;

import javax.naming.ldap.SortKey;
import java.net.*;

public class Main {
    public static int port_LwM2M_server = 5683;
    public static int port_CoAP_file_server = 5700;
    // 通讯串口
    public static testcom NBiot_with_BC35;
    public static String com = "/dev/ttyUSB1";
    // wifi UDP
    public static DatagramSocket socket_leshan;

    // NB-IoT UDP
    public static DatagramSocket socket_anjay;//connectID = 0
    public static int NB_Socket_connectID_LwM2Mserver = 0;

    // NB-IoT UDP 文件
    public static DatagramSocket socket_coap_file;//connectID = 2
    public static int NB_Socket_connectID_CoAPfileserver = 2;

    // NB-IoT TCP
    public static ServerSocket socket_tcp_analyzer; // connectID = 1
    public static int NB_TCPSocket_connectID = 1;
    public static Socket TCPSocket;
    // NB-IoT TCP 文件
    public static ServerSocket socket_tcp_fileserver; // connectID = 3

    public static boolean isanjayend = true;
    public static InetAddress server_addr;
    public static int anjayport = -1;//if this port == 0,means there no anjay client connected,we should not to recv the leshan requests
    public static int anjay_coapfileclient_port = -1;//the port used to receive the file from coap server
    public static String anjayaddr = "0.0.0.0";
    public static int model = 2;
    /**
     * open USB
     * check UDP
     */

    public static void main(String[] args) throws SocketException, UnknownHostException, InterruptedException {
        server_addr = InetAddress.getByName("39.107.84.110");
        // 打开串口
        NBiot_with_BC35 = new testcom();
        NBiot_with_BC35.actionPerformed(com);
//        socket_leshan = new DatagramSocket(4417);
//        Leshanthread b = new Leshanthread( model );
//        b.start();

//        // 基于UDP
        socket_anjay = new DatagramSocket(port_LwM2M_server);
        Anjaythread thread_handle_LwM2M_client = new Anjaythread(model, socket_anjay);
        thread_handle_LwM2M_client.start();
        Thread.sleep(1000);
//
//        // 基于UPD文件传输
//        socket_coap_file = new DatagramSocket(port_CoAP_file_server);
//        Anjaythread thread_handle_CoAP_file_client = new Anjaythread(model, socket_coap_file);
//        thread_handle_CoAP_file_client.start();

        // 基于TCP
        OrganizerOverTCP organizerOverTCP = new OrganizerOverTCP(NB_TCPSocket_connectID);
        organizerOverTCP.start();
        System.out.println("a.start finish");

    }
//    public static void main(String[] args) {
//        System.out.println("test udp server");
//        try(DatagramSocket fromanjay = new DatagramSocket(port))
//        {
//            while (true)
//            {
//                System.out.println("----------recv from anjay-------------");
//                DatagramPacket request_from_anjay = new DatagramPacket(new byte[1024], 1024);
//                DatagramPacket fromserver_pack = new DatagramPacket(new byte[1024],1024);
//                fromanjay.receive(request_from_anjay);
//                System.out.println("getaddress : " + request_from_anjay.getAddress());
//                System.out.println("getlength : " + request_from_anjay.getLength());
////                byte[] recvs = request_from_anjay.getData();
////                int gao = recvs[2]>>4;
////                int di = (recvs[2]) & 0x0F;
////                System.out.println(gao);
////                System.out.println(Integer.parseInt(di,16));
//                DatagramSocket toserver = new DatagramSocket();
//                InetAddress server_address = InetAddress.getByName("59.110.213.240");
//                DatagramPacket toserver_pack = new DatagramPacket(request_from_anjay.getData()
//                        , request_from_anjay.getLength(),server_address ,port);
//                toserver.send(toserver_pack);
//                toserver.receive(fromserver_pack);
//                System.out.println("----------recv from server-------------");
//                System.out.println("getaddress : " + fromserver_pack.getAddress());
//                System.out.println("getlength : " + fromserver_pack.getLength());
//                System.out.println("\n\n\n");
//                DatagramPacket toanjay_pack = new DatagramPacket(fromserver_pack.getData()
//                , fromserver_pack.getLength(),request_from_anjay.getAddress(),request_from_anjay.getPort());
//                fromanjay.send(toanjay_pack);
//                toserver.close();
//            }
//        }
//        catch (Exception e)
//        {
//            System.out.println("find a mistake !");
//            System.out.println("e:" + e);
//        }
//    }
}
