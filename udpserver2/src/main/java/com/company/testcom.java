package com.company;

import gnu.io.*;
import sun.misc.IOUtils;

import java.io.*;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.TooManyListenersException;
import java.util.regex.Pattern;


public class testcom implements  SerialPortEventListener{
    static CommPortIdentifier portId;
    boolean CanIWrite = true;
    /** Enumeration 为枚举型类,在java.util中 */
    @SuppressWarnings("rawtypes")
    static Enumeration portList;
    InputStream inputStream;
    /** 声明RS-232串行端口的成员变量 */
    SerialPort serialPort;
    OutputStream outputStream;
    public String recvBuffer = "";
    public boolean writing = false;
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
                        System.out.println("portID.getName : " + portId.getName());
                        serialPort = (SerialPort) portId.open("ReadComm", 2000);
                        /**设置串口监听器*/
                        serialPort.addEventListener(this);
                        /** 侦听到串口有数据,触发串口事件*/
                        serialPort.notifyOnDataAvailable(true);
                    }
                    catch (PortInUseException e) {
                        System.out.println(e);
                    }
                    catch (TooManyListenersException e) {
                        System.out.println(e);
                    }

                }
                //if end
            } //if end
        } //while end
    } //actionPerformed() end
    @Override
    public void serialEvent(SerialPortEvent event) {
        /**设置串口通讯参数：波特率、数据位、停止位、奇偶校验*/
//        System.out.println("enter into serialEvent");
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
                    byte[] recv = new byte[50];
                    String handledrecv = "";
                    /** 从线路上读取数据流 */
                    while (inputStream.available() > 0) {
//                        System.out.println("enter into while");
                        inputStream.read(recv);

                    } //while end

                    for(int i = 0; i < recv.length; i++)
                    {
                        if(recv[i] <= 0)
                        {
                            break;
                        }
                        char ascll = (char)((int)recv[i]);
                        handledrecv +=ascll;
                    }
                    System.out.println("handledrecv : " + handledrecv);
                    recvBuffer += handledrecv;
                    if(handledrecv.length() < 32)
                    {

                        handle_every_String(recvBuffer);
                    }


            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println("end of serialEvent");
    }
    public void handle_every_String(String before) throws IOException {
        System.out.println("recvBuffer/before : " + before);
        String[] every = before.split("\r\n");
        this.recvBuffer = "";
        if (every.length < 2)
        {
            return;
        }
        for(int i = 1; i < every.length; i++)
        {
            String tem = every[i];
            System.out.println("every[" + i + "]" + " : " + tem);
            if(tem.equals("OK") || tem.equals("ERROR"))
            {
                return;
            }
            if(tem.charAt(0) == '+')//something response
            {
                tem = tem.split(":")[1];
                String ask_for_mas = "AT+NSORF=" + tem;
                this.writes_by_String(ask_for_mas);
            }
            if(tem.charAt(0) >= '0' && tem.charAt(0) <= '9')
            {
                //maybe a mas
                String[] findmsg = tem.split(",");
                if(findmsg.length < 6)
                {
                    return;
                }
                if(findmsg.length == 6)
                {
                    //this is a msg
//                    System.out.println(findmsg[4]);
//                    this.writes_by_String(findmsg[4]);
                    /**
                     * this msg need to be sent back to ANJAY
                     */
//                    byte[] send_back_to_anjay = new byte[findmsg[4].length() / 2];
//                    for(int j = 0; j < findmsg[4].length(); j += 2)
//                    {
//                        char handle_high = findmsg[4].charAt(j);
//                        char handle_low = findmsg[4].charAt(j + 1);
//                        byte high = (byte) ((handle_high & 0x0F) << 4);
//                        System.out.println("high : " + (int)high);
//                        byte low = (byte)(handle_low & 0x0F);
//                        System.out.println("low : " + (int)low);
//                        high |= low;
//                        System.out.println("he : " + (int)high);
//                        send_back_to_anjay[j / 2] = high;
//                    }
                    byte[] send = this.hex2byte(findmsg[4]);

                    String iwannaknow = "";
                    for(int k = 0; k < send.length; k++)
                    {
                        int chang = send[k] & 0xFF;
                        String temsend = Integer.toHexString(chang);
                        if (temsend.length() < 2)
                        {
                            iwannaknow += "0";
                        }
                        iwannaknow += temsend;
                    }
                    System.out.println("i wanna know : " + iwannaknow);


//                    String testsend = "";
//                    for(int k = 0; k < send_back_to_anjay.length; k++)
//                    {
//                        char ascll = (char)((int)send_back_to_anjay[i]);
//                        testsend +=ascll;
//                    }


                    String anjayaddr = Main.anjayaddr.split("/")[1];
                    DatagramPacket pack_to_anjay = new DatagramPacket(send,
                            send.length, InetAddress.getByName(anjayaddr),Main.anjayport);

                    Main.socket_anjay.send(pack_to_anjay);
                    System.out.println("after send back to anjay");
                }
            }
        }
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
        System.out.println("things.length:" +things.length());
        System.out.println("AT write : " + things);
        byte[] send = new byte[things.length()];
        send = things.getBytes();
        this.writes(send);
    }

    protected void writes(byte[] b){
        int i= 0;
        while(writing)
        {
            i++;
            if(i % 100 == 0)
                System.out.print(">>>>>>>");
        }
            writing = true;
        try {
            outputStream = serialPort.getOutputStream();
            outputStream.write(b);
            outputStream.flush();
            System.out.println("after write");
        } catch (IOException e) {
            e.printStackTrace();
        }
        writing = false;
    }
    public void sendComm(String data) {
        byte[] writerBuffer = null;
        try {
            writerBuffer = hexToByteArray(data);
        } catch (NumberFormatException e) {
            System.out.println(e);
        }
        try {
            outputStream = serialPort.getOutputStream();
            outputStream.write(writerBuffer);
            outputStream.flush();
        } catch (NullPointerException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
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
                        System.out.println("Failed to open port " + com.getName());
                    } finally {
                        if (thePort != null) {
                            thePort.close();
                        }
                    }
            }
        }
        return ttyUSB;
    }


    public static void main(String[] args) throws UnsupportedEncodingException, InterruptedException {
        testcom a = new testcom();
        System.out.println(a.getAvailableSerialPortsName());
//        a.actionPerformed("/dev/ttyUSB0");
//        System.out.println("begin to write");
//        Thread.sleep(2000);
//        System.out.println("\n\n");
////        a.sendComm("AT\r\n");
//        String AT = "ATTTT\r\n";
//        byte[] test = new byte[AT.length()];
//        test = AT.getBytes();
//
//
////        for(int i = 0; i < AT.length(); i++)
////        {
////            test[i] = AT.charAt(i);
////        }
////        byte[] test = new byte[]{0x41, 0x54, 0x0d, 0x0a};
//        a.writes(test);
//        System.out.println("after send");
    }
}
