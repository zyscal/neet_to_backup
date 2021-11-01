package Simple_class;

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
//    public static boolean check(CoAP_mes_TCP mes, CoAP_mes_TCP ack) {
//
//    }
}
