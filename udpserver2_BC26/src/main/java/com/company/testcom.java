package com.company;

import gnu.io.*;

import java.io.*;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.TooManyListenersException;



public class testcom implements SerialPortEventListener {
    static CommPortIdentifier portId;
    /** Enumeration 为枚举型类,在java.util中 */
    @SuppressWarnings("rawtypes")
    static Enumeration portList;
    InputStream inputStream;
    /** 声明RS-232串行端口的成员变量 */
    SerialPort serialPort;
    OutputStream outputStream;
    public ArrayList<Byte> recvBytes = new ArrayList<Byte>();
    public boolean hasr = false;
    public int socket_connectID = -1;
    public void actionPerformed(String ttyUSB)
    {
        /**获取系统中所有的通讯端口 */
        portList=CommPortIdentifier.getPortIdentifiers();
        /** 用循环结构找出串口 */
        while (portList.hasMoreElements()){
            /**强制转换为通讯端口类型*/
            portId=(CommPortIdentifier)portList.nextElement();
            if(portId.getPortType() == CommPortIdentifier.PORT_SERIAL){
                //USB串口1
                if (portId.getName().equals(ttyUSB)) {
                    try {
                        ////System.out.println("find : " + portId.getName());
                        serialPort = (SerialPort) portId.open("ReadComm", 2000);
                        /**设置串口监听器*/
                        serialPort.addEventListener(this);
                        /** 侦听到串口有数据,触发串口事件*/
                        serialPort.notifyOnDataAvailable(true);
                    }
                    catch (PortInUseException e) {
                        ////System.out.println(e);
                    }
                    catch (TooManyListenersException e) {
                        ////System.out.println(e);
                    }

                }
                //if end
            } //if end
        } //while end
    } //actionPerformed() end
    @Override
    public void serialEvent(SerialPortEvent event) {
        ////System.out.println("enter into serialEvent");
        /**设置串口通讯参数：波特率、数据位、停止位、奇偶校验*/
//        ////System.out.println("enter into serialEvent");
        try {
            serialPort.setSerialPortParams(9600,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
        }
        catch (UnsupportedCommOperationException e) {
            e.printStackTrace();
        }
        try {
            inputStream = serialPort.getInputStream();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        try {
            switch(event.getEventType())
            {
                case SerialPortEvent.BI:
                case SerialPortEvent.OE:
                case SerialPortEvent.FE:
                case SerialPortEvent.PE:
                case SerialPortEvent.CD:
                case SerialPortEvent.CTS:
                case SerialPortEvent.DSR:
                case SerialPortEvent.RI:
                case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
                    break;
                case SerialPortEvent.DATA_AVAILABLE:
                    int lenofinputstream = inputStream.available();
                        byte[] recv = new byte[lenofinputstream];
                        while (lenofinputstream != 0) {
                            inputStream.read(recv);
                            System.out.println("------------------------\nsome thing recv :" + recv.length);
                            for (int i = 0; i < recv.length; i++) {//
                                int chang = recv[i] & 0xFF;
                                String hex_recv = Integer.toHexString(chang);
                                System.out.print(hex_recv + " ");
                            }
                            System.out.println();
                            for (int i = 0; i < recv.length; i++) {
                                char ascll = (char) ((int) recv[i]);
                                if (ascll == 0x0a) {
                                    System.out.print("0a ");
                                } else if (ascll == 0x0d) {
                                    System.out.print("0d ");
                                } else {
                                    System.out.print(ascll + " ");
                                }
                            }
                            System.out.println();
                            for (int i = 0; i < recv.length; i++) {
                                byte temrecv = recv[i];
                                if (i == 0 && temrecv == 0x0a && hasr) {
                                    hasr = false;
                                    handle_every_bytes(recvBytes);
                                    continue;
                                } else if (hasr) {
                                    hasr = false;
                                    Byte tem = 0x0d;
                                    recvBytes.add(tem);
                                }


                                if (temrecv == 0x0d && i + 1 < recv.length && recv[i + 1] == 0x0a) {
                                    i++;
                                    handle_every_bytes(recvBytes);
                                } else if (temrecv == 0x0d && i + 1 == recv.length) {
                                    hasr = true;
                                    System.out.println("change hasr to true>>>>>>>>>>>>>>>");
                                } else {
                                    Byte tem = new Byte(temrecv);
                                    recvBytes.add(tem);
                                }
                            }
                            inputStream.close();
                            break;
                        }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handle_every_bytes(ArrayList<Byte> recv2)
    {
        ArrayList<Byte> recv = new ArrayList<Byte>(recv2);
        recvBytes.clear();
        System.out.println("length of handle_every_bytes is : " + recv.size());
        System.out.print("handle_every_bytes : ");
        for(int i = 0; i < recv.size(); i++)
        {
            System.out.print(recv.get(i) + " ");
        }
        System.out.println();

        byte[][] ignore ={{0x53, 0x45, 0x4e, 0x44, 0x20, 0x4f, 0x4b}/*send ok*/, {0x4f, 0x4b}/*OK*/, {0x41, 0x54}/*AT*/, {0x2b}/* + */};
        if(recv.size() == 0)
        {
            ////System.out.println("return from size == 0");
            return;
        }
        if(canIignore(ignore, recv))
        {
            //OK
            System.out.println("i can ignore : " + recv.size());
            return;
        }
        else
        {
            ////System.out.println("ready to send back to anjay");
            String hex_to_byte = "";
            for(int i = 0; i < recv.size(); i++)
            {
                char ascll = (char)((int)recv.get(i));
                hex_to_byte +=ascll;
            }
            System.out.println("hex_to_byte : " + hex_to_byte);
            byte[] send = this.hex2byte(hex_to_byte);

            String anjayaddr = Main.anjayaddr.split("/")[1];
            DatagramPacket pack_to_anjay;
            try {
                if(socket_connectID == Anjaythread.NB_Socket_connectID_LwM2Mserver)
                {
                    pack_to_anjay = new DatagramPacket(send,
                            send.length, InetAddress.getByName(anjayaddr),Main.anjayport);
                    Main.socket_anjay.send(pack_to_anjay);
                }
                else if(socket_connectID == Anjaythread.NB_Socket_connectID_CoAPfileserver)
                {
                    pack_to_anjay = new DatagramPacket(send,
                            send.length, InetAddress.getByName(anjayaddr),Main.anjay_coapfileclient_port);
                    Main.socket_coap_file.send(pack_to_anjay);
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    boolean canIignore(byte[][] ignore, ArrayList<Byte> recv)
    {
//        System.out.println("enter into canIignore");
        for(int i = 0; i < ignore.length; i++)
        {
            ////System.out.println("round " + i);
            boolean check = true;
            for(int j = 0; j < ignore[i].length; j++)
            {
                if(ignore[i][j] != recv.get(j))
                {
                    check = false;
                    break;
                }
            }
            if(check)
            {
                if(recv.get(0) == 0x2b)
                {
                    int the_next_msgID = getConnectID(recv);
                    System.out.println("#######connectID is : " + the_next_msgID);
                    socket_connectID = the_next_msgID;

                }


                return true;
            }
        }
        return false;
    }
    public int getConnectID(ArrayList<Byte> recv)
    {
        for(int i = 0; i < recv.size(); i++)
        {
            if (recv.get(i) == 0x2c)
            {
                return recv.get(i + 1) - 0x30;
            }
        }
        return 0;
    }
    public static byte[] hex2byte(String hex) {
        String digital = "0123456789ABCDEF";
        char[] hex2char = hex.toCharArray();
        byte[] bytes = new byte[hex.length() / 2];
        int temp;
        for (int i = 0; i < bytes.length; i++) {
            temp = digital.indexOf(hex2char[2 * i]) * 16;
            temp += digital.indexOf(hex2char[2 * i + 1]);
            bytes[i] = (byte) (temp & 0xff);
        }
        return bytes;
    }
    protected void writes_by_String(String things)
    {
        things += "\r\n";
        ////System.out.println("things.length:" +things.length());
        ////System.out.println("AT write : " + things);
        byte[] send = new byte[things.length()];
        send = things.getBytes();
        this.writes(send);
    }

    protected void writes(byte[] b){
        ////System.out.println("size of b : " + b.length);
        try {
            outputStream = serialPort.getOutputStream();
            outputStream.write(b);
            outputStream.flush();
            ////System.out.println("after write");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void sendComm(String data) {
        byte[] writerBuffer = null;
        try {
            writerBuffer = hexToByteArray(data);
        } catch (NumberFormatException e) {
            ////System.out.println(e);
        }
        try {
            outputStream = serialPort.getOutputStream();
            outputStream.write(writerBuffer);
            outputStream.flush();
        } catch (NullPointerException e) {
            ////System.out.println(e);
        } catch (IOException e) {
            ////System.out.println(e);
        }
    }
    public static byte[] hexToByteArray(String inHex) {
        int hexlen = inHex.length();
        byte[] result;
        if (hexlen % 2 == 1) {
            // 奇数
            hexlen++;
            result = new byte[(hexlen / 2)];
            inHex = "0" + inHex;
        } else {
            // 偶数
            result = new byte[(hexlen / 2)];
        }
        int j = 0;
        for (int i = 0; i < hexlen; i += 2) {
            result[j] = hexToByte(inHex.substring(i, i + 2));
            j++;
        }
        return result;
    }
    public static byte hexToByte(String inHex) {
        return (byte) Integer.parseInt(inHex, 16);
    }
    public String getAvailableSerialPortsName() {
        String ttyUSB = null;
        @SuppressWarnings("rawtypes")
        Enumeration thePorts = CommPortIdentifier.getPortIdentifiers();
        while (thePorts.hasMoreElements()) {
            CommPortIdentifier com = (CommPortIdentifier) thePorts.nextElement();
            switch (com.getPortType()) {
                case CommPortIdentifier.PORT_SERIAL:
                    CommPort thePort = null;
                    try {//过滤COM1-3
//                        thePort = com.open("CommUtil", 2000);
//                        if (!Pattern.compile("ttyUSB[0-3]\\b").matcher(com.getName()).matches()) {
//                            ttyUSB = com.getName();
//                        }
                        thePort = com.open("CommUtil", 2000);
                        if(com.getName().charAt(8) == 'U')
                        {
                            ttyUSB = com.getName();
                        }
                    } catch (PortInUseException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        ////System.out.println("Failed to open port " + com.getName());
                    } finally {
                        if (thePort != null) {
                            thePort.close();
                        }
                    }
            }
        }
        return ttyUSB;
    }

}
