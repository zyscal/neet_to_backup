package com.company;

import java.net.*;

public class Main {
    private static int port_LwM2M_server= 5683;
    private static int port_CoAP_file_server= 5700;
    public static DatagramSocket socket_leshan;
    public static DatagramSocket socket_anjay;
    public static InetAddress server_addr;
    public static int anjayport = -1;//if this port == 0,means there no anjay client connected,we should not to recv the leshan requests
    public static String anjayaddr = "0.0.0.0";

    public static int model = 1;
    /**
     * open USB
     * check UDP
     */



    public static void main(String[] args) throws SocketException, UnknownHostException {

        socket_leshan = new DatagramSocket(4414);
        socket_anjay = new DatagramSocket(port_LwM2M_server);
        server_addr = InetAddress.getByName("59.110.213.240");
//        server_addr = InetAddress.getByName("192.168.1.54");
        Anjaythread a = new Anjaythread(model);
//        Leshanthread b = new Leshanthread(model);
        a.start();
//        b.start();
    }
}
