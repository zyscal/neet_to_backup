import Simple_class.CoAP_mes;
import Simple_class.CoAP_mes_TCP;
import com.sun.org.apache.xpath.internal.operations.Or;
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
    BufferedWriter out_Reg_TCP_fileName;
    BufferedWriter out_Reg_TCP_datetime_filename;
    BufferedWriter out_log;
    private long sumRTT = 0;
    // matchCount 上下行数据包匹配的对数
    private int matchCount = 0;
    NB_CoAP_REG_TCP() {

    }

    void RegRTTFromClient(int OrganizerPort, int ComPort,String PcapFileName, String RTTFileName, String RTTDateTimeFile, String out_log_file) {
        try {
            out_Reg_TCP_fileName = new BufferedWriter(new FileWriter(RTTFileName));
            out_Reg_TCP_datetime_filename = new BufferedWriter(new FileWriter(RTTDateTimeFile));
            out_log = new BufferedWriter(new FileWriter(out_log_file));
        } catch (Exception e) {
            System.out.println("something wrong");
            System.out.println(e);
        }

        try {
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
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void RegRTTFromServer(int OrganizerPort, int analyzerPort,String PcapFileName, String RTTFileName, String RTTDateTimeFile) {
        try {
            out_Reg_TCP_fileName = new BufferedWriter(new FileWriter(RTTFileName));
            out_Reg_TCP_datetime_filename = new BufferedWriter(new FileWriter(RTTDateTimeFile));
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            ArrayList<CoAP_mes_TCP> DownLinkMessage = new ArrayList<>();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy,MM,dd,H,m,s,S");
            Pcap pcap = Pcap.openStream(PcapFileName);
            pcap.loop(new PacketHandler() {
                @Override
                public boolean nextPacket(Packet packet) throws IOException {
                    TCPPacket tcpPacket = (TCPPacket) packet.getPacket(Protocol.TCP);
                    if (tcpPacket.getSourcePort() == analyzerPort) { // 下行消息 created
                        Buffer Coap = tcpPacket.getPacket(Protocol.TCP).getPayload();

                        // payload长度 = ipv4报文总长度 - ipv4报文头部 - TCP头部
                        IPv4Packet iPv4Packet = (IPv4Packet)packet.getPacket(Protocol.IPv4);
                        int TCPPayloadLength = iPv4Packet.getTotalIPLength() - iPv4Packet.getHeaderLength() - tcpPacket.getHeaderLength();
                        // 获取payload
                        byte[] Coapheader = new byte[TCPPayloadLength];
                        Coap.getBytes(Coapheader);
                        CoAP_mes_TCP coAP_mes_tcp = new CoAP_mes_TCP(tcpPacket.getSequenceNumber(), tcpPacket.getAcknowledgementNumber(), TCPPayloadLength, tcpPacket.getArrivalTime());
                        DownLinkMessage.add(coAP_mes_tcp);
                    } else if(tcpPacket.getDestinationPort() == analyzerPort) { // 上行ACK
                        for(int i = 0; i < DownLinkMessage.size(); i++) {
                            if(DownLinkMessage.get(i).seqNum + DownLinkMessage.get(i).payloadLength == tcpPacket.getAcknowledgementNumber()) {
                                out_Reg_TCP_fileName.write((tcpPacket.getArrivalTime() - DownLinkMessage.get(i).getArrive_date()) / 1000 + "\n");
                                out_Reg_TCP_datetime_filename.write(formatter.format(new Date(DownLinkMessage.get(i).getArrive_date() / 1000)) + "\n");
                                DownLinkMessage.remove(i);
                                break;
                            }
                        }
                    }

                    return true;
                }
            });
            out_Reg_TCP_fileName.close();
            out_Reg_TCP_datetime_filename.close();
            out_log.close();
        } catch (Exception e) {
            System.out.println(e);
        }



    }


}
