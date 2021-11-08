import Simple_class.CoAP_mes;
import Simple_class.CoAP_mes_TCP;
//import com.sun.org.apache.xpath.internal.operations.Or;
import io.pkts.PacketHandler;
import io.pkts.Pcap;
import io.pkts.PcapOutputStream;
import io.pkts.buffer.Buffer;
import io.pkts.framer.TCPFramer;
import io.pkts.packet.*;
import io.pkts.packet.impl.TcpPacketImpl;
import io.pkts.protocol.Protocol;
import jdk.nashorn.internal.runtime.ECMAException;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class NB_CoAP_REG_TCP {
    private long sumRTT = 0;
    // matchCount 上下行数据包匹配的对数
    private int matchCount = 0;
    // 用于计算con的数量
    private int conCount = 0;

    // 用于计算ack的数量
    private int ackCount = 0;
    NB_CoAP_REG_TCP() {

    }

    void RegRTTFromClient(int OrganizerPort, int ComPort,String PcapFileName, String RTTFileName, String RTTDateTimeFile, String out_log_file) {
        try {
            BufferedWriter out_Reg_TCP_fileName = new BufferedWriter(new FileWriter(RTTFileName));
            BufferedWriter out_Reg_TCP_datetime_filename = new BufferedWriter(new FileWriter(RTTDateTimeFile));
            BufferedWriter out_log = new BufferedWriter(new FileWriter(out_log_file));

            sumRTT = 0;
            matchCount = 0;
            ArrayList<CoAP_mes> UpLinkMessage = new ArrayList<>();
            HashMap<String, Integer> UpLinkMessageHash = new HashMap<>();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy,MM,dd,H,m,s,S");
            Pcap pcap = Pcap.openStream(PcapFileName);
            pcap.loop(new PacketHandler() {
                @Override
                public boolean nextPacket(Packet packet) throws IOException {
                    TransportPacket transportPacket = (TransportPacket)packet.getPacket(Protocol.TCP);
                    if(transportPacket.hasProtocol(Protocol.TCP)) {
                    } else {
                        System.out.println("no tcp");
                        return false;
                    }
                    Buffer Coap = transportPacket.getPacket(Protocol.TCP).getPayload();
                    byte[] Coapheader = new byte[12];
                    Coap.getBytes(Coapheader);
                    byte[] token = CoAP_mes.GetTokenOverTCP(Coapheader);
                    CoAP_mes Mes = new CoAP_mes(transportPacket.getPacket(Protocol.TCP).getArrivalTime(), transportPacket.getDestinationPort(),
                            transportPacket.getSourcePort(), 0, token);
                    if(transportPacket.getDestinationPort() == ComPort) { // 上行的注册消息
                        UpLinkMessage.add(Mes);
                        byte[] mid = new byte[0];
                        String key = CoAP_mes.make_KEY(mid, token);
                        if(UpLinkMessageHash.containsKey(key)) {
                            int value = UpLinkMessageHash.get(key);
                            UpLinkMessageHash.put(key, value + 1);
                        } else {
                            UpLinkMessageHash.put(key, 1);
                        }
                    } else if(transportPacket.getDestinationPort() == OrganizerPort) { // 下行的响应消息
                        for(int i = 0; i < UpLinkMessage.size(); i++) {
                            if(CoAP_mes.tokenMatch(UpLinkMessage.get(i).getToken(), Mes.getToken())) {
                                // 处理RTT
                                long RTT = (Mes.getArrive_date() - UpLinkMessage.get(i).getArrive_date())/1000;
                                sumRTT += RTT;
                                String RTTToString = RTT + "\n";
                                out_Reg_TCP_fileName.write(RTTToString);

                                // 处理datetime
                                out_Reg_TCP_datetime_filename.write(formatter.format(new Date(UpLinkMessage.get(i).getArrive_date() / 1000)) + "\n");

                                // 处理链表
                                UpLinkMessage.remove(i);
                                matchCount++;
                                break;
                            }
                        }
                    }
                    return true;
                }
            });
            out_Reg_TCP_fileName.close();
            out_Reg_TCP_datetime_filename.close();
            out_log.write("size of UpLinkArray : " + UpLinkMessage.size() + "\n");
            out_log.write("AVG() : " + (double)sumRTT / (double) matchCount + "\n");
            int countOfRetransmission = 0;
            for(String key : UpLinkMessageHash.keySet()) {
                if(UpLinkMessageHash.get(key) > 1) {
                    countOfRetransmission++;
                }
            }
            out_log.write("count of Retransmission is : " + countOfRetransmission + "\n");
            out_log.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void RegRTTFromServer(int OrganizerPort, int analyzerPort,String PcapFileName, String RTTFileName,
                          String RTTDateTimeFile, String Server_log_file, String Packet_loss_file) {
        try {
            BufferedWriter out_Reg_TCP_fileName = new BufferedWriter(new FileWriter(RTTFileName));
            BufferedWriter out_Reg_TCP_datetime_filename = new BufferedWriter(new FileWriter(RTTDateTimeFile));
            BufferedWriter out_Server_Log = new BufferedWriter(new FileWriter(Server_log_file));
            BufferedWriter out_Packet_loss = new BufferedWriter(new FileWriter(Packet_loss_file));

            conCount = 0;
            ackCount = 0;
            ArrayList<CoAP_mes_TCP> DownLinkMessage = new ArrayList<>();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy,MM,dd,H,m,s,S");
            Pcap pcap = Pcap.openStream(PcapFileName);
            pcap.loop(new PacketHandler() {
                @Override
                public boolean nextPacket(Packet packet) throws IOException {
                    TCPPacket tcpPacket = (TCPPacket) packet.getPacket(Protocol.TCP);
                    if (tcpPacket.getSourcePort() == analyzerPort) { // 下行消息 created
                        Buffer Coap = tcpPacket.getPacket(Protocol.TCP).getPayload();
                        int TCPPayloadLength = CoAP_mes_TCP.GetTCPPayloadLength(packet);
                        // 获取payload
                        byte[] Coapheader = new byte[TCPPayloadLength];
                        Coap.getBytes(Coapheader);
                        CoAP_mes_TCP coAP_mes_tcp = new CoAP_mes_TCP(tcpPacket.getSequenceNumber(), tcpPacket.getAcknowledgementNumber(), TCPPayloadLength, tcpPacket.getArrivalTime());
                        DownLinkMessage.add(coAP_mes_tcp);
                        conCount++; // con 计数
                    } else if(tcpPacket.getDestinationPort() == analyzerPort) { // 上行ACK
                        for(int i = 0; i < DownLinkMessage.size(); i++) {
                            if(DownLinkMessage.get(i).seqNum + DownLinkMessage.get(i).payloadLength == tcpPacket.getAcknowledgementNumber()) {
                                out_Reg_TCP_fileName.write((tcpPacket.getArrivalTime() - DownLinkMessage.get(i).getArrive_date()) / 1000 + "\n");
                                out_Reg_TCP_datetime_filename.write(formatter.format(new Date(DownLinkMessage.get(i).getArrive_date() / 1000)) + "\n");
                                DownLinkMessage.remove(i);
                                break;
                            }
                        }
                        ackCount++;
                    }
                    System.out.println(conCount + "/" + ackCount);
                    return true;
                }
            });
            for(CoAP_mes_TCP i : DownLinkMessage) {
                out_Packet_loss.write(formatter.format(i.getArrive_date() / 1000) + "\n");
            }
            out_Server_Log.write("packet loss count : " + DownLinkMessage.size() + "\n");
            out_Server_Log.write("tcp downlink count : " + conCount + "\n");
            out_Server_Log.write("packet loss rate : " + (float)DownLinkMessage.size() / (float)conCount + "\n");
            out_Reg_TCP_fileName.close();
            out_Reg_TCP_datetime_filename.close();
            out_Server_Log.close();
            out_Packet_loss.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void FindClientRetransmission (String ServerRecv, int dstPort, String ClientSend, String RetransmissionDatetimeFileName) {
        BufferedWriter dateTime;
        try {
            dateTime = new BufferedWriter(new FileWriter(RetransmissionDatetimeFileName));
            ArrayList<CoAP_mes> ClientSendList = new ArrayList<>();
            Pcap pcap = Pcap.openStream(ClientSend);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy,MM,dd,H,m,s,S");
            pcap.loop(new PacketHandler() {
                @Override
                public boolean nextPacket(Packet packet) throws IOException {
                    TCPPacket tcpPacket = (TCPPacket)packet.getPacket(Protocol.TCP);
                    if(tcpPacket.getDestinationPort() == dstPort) { // 如果是上行数据则存在重传的可能
                        // 获取payload长度
                        int TCPPayloadLength = CoAP_mes_TCP.GetTCPPayloadLength(packet);
                        Buffer Coap = tcpPacket.getPacket(Protocol.TCP).getPayload();
                        byte[] CoAPHeader = new byte[TCPPayloadLength];
                        Coap.getBytes(CoAPHeader);
                        byte[] token = CoAP_mes.GetTokenOverTCP(CoAPHeader);
                        CoAP_mes coAP_mes = new CoAP_mes(tcpPacket.getArrivalTime(), tcpPacket.getDestinationPort(), tcpPacket.getSourcePort(),
                                0, token);
                        ClientSendList.add(coAP_mes);
                    }
                    return true;
                }
            });
            System.out.println("length of client send is ： " + ClientSend.length());

            pcap = Pcap.openStream(ServerRecv);
            pcap.loop(new PacketHandler() {
                @Override
                public boolean nextPacket(Packet packet) throws IOException {
                    TCPPacket tcpPacket = (TCPPacket) packet.getPacket(Protocol.TCP);
                    int TCPPayLoadLength = CoAP_mes_TCP.GetTCPPayloadLength(packet);
                    Buffer CoAP = tcpPacket.getPacket(Protocol.TCP).getPayload();
                    byte[] CoAPHeader = new byte[TCPPayLoadLength];
                    CoAP.getBytes(CoAPHeader);
                    byte[] token = CoAP_mes.GetTokenOverTCP(CoAPHeader);

                    for(CoAP_mes i : ClientSendList) {
                        if(CoAP_mes.tokenMatch(i.getToken(), token)) {
                            dateTime.write(formatter.format(new Date(i.getArrive_date() / 1000)) + "\n");
                            break;
                        }
                    }
                    return true;
                }
            });
            dateTime.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void FindServerRetransmission (String ServerSend, String RetransmissionDatetimeFileName) {
        try {
            BufferedWriter RetransmissionDatetime = new BufferedWriter(new FileWriter(RetransmissionDatetimeFileName));
            Pcap pcap = Pcap.openStream(ServerSend);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy,MM,dd,H,m,s,S");
            pcap.loop(new PacketHandler() {
                @Override
                public boolean nextPacket(Packet packet) throws IOException {
                    RetransmissionDatetime.write(formatter.format(packet.getArrivalTime() / 1000) + "\n");
                    return true;
                }
            });
            RetransmissionDatetime.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


}
