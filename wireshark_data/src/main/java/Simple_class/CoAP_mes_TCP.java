package Simple_class;

import io.pkts.packet.IPv4Packet;
import io.pkts.packet.Packet;
import io.pkts.packet.TCPPacket;
import io.pkts.protocol.Protocol;

public class CoAP_mes_TCP extends CoAP_mes {
    public long seqNum;
    public long ackNum;
    public long payloadLength;
    public int count = 0;
    public CoAP_mes_TCP(long seqNum, long ackNum, long payloadLength, long arrive_date) {
        this.arrive_date = arrive_date;
        this.seqNum = seqNum;
        this.ackNum = ackNum;
        this.payloadLength = payloadLength;
    }
    // payload长度 = ipv4报文总长度 - ipv4报文头部 - TCP头部
    public static int GetTCPPayloadLength(Packet packet) {
        int length = 0;
        try {
            IPv4Packet iPv4Packet = (IPv4Packet)packet.getPacket(Protocol.IPv4);
            TCPPacket tcpPacket = (TCPPacket) packet.getPacket(Protocol.TCP);
            length = iPv4Packet.getTotalIPLength() - iPv4Packet.getHeaderLength() - tcpPacket.getHeaderLength();
        } catch (Exception e) {
            System.out.println(e);
        }
        return  length;
    }
}
