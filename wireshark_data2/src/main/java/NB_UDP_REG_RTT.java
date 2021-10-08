import io.pkts.PacketHandler;
import io.pkts.Pcap;
import io.pkts.buffer.Buffer;
import io.pkts.packet.Packet;
import io.pkts.packet.TransportPacket;
import io.pkts.protocol.Protocol;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

public class NB_UDP_REG_RTT {
    public BufferedWriter out_RTT_lost;
    public BufferedWriter out_datetime;
    public BufferedWriter out_RTT;


    public NB_UDP_REG_RTT()
    {
        try
        {
            out_RTT = new BufferedWriter(new FileWriter("RTT.txt"));
            out_RTT_lost = new BufferedWriter(new FileWriter("RTT_lost.txt"));
            out_datetime = new BufferedWriter(new FileWriter("RTT_datetime.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void RTT_NB_1000_reg_UDP_CoAP() {
        try
        {
            Pcap pcap = Pcap.openStream("/home/zyscal/Documents/need_to_backup/wiresahrk_caught/1000nb/1000nb_displayed.pcap");
            pcap.loop(new PacketHandler() {
                int i = 1;
                Date last_date = null;
                Queue<CoAP_mes> CON_Que = new LinkedList<CoAP_mes>();
                @Override
                public boolean nextPacket(Packet packet) throws IOException {
                    System.out.println("------begin-----------");
                    System.out.println(i);
                    TransportPacket transportPacket = (TransportPacket)packet.getPacket(Protocol.UDP);
                    if(transportPacket.hasProtocol(Protocol.UDP))
                    {

                        Buffer Coap = transportPacket.getPacket(Protocol.UDP).getPayload();
                        byte[] Coapheader = new byte[4];
                        Coap.getBytes(Coapheader);
                        int mid = GetMID(Coapheader);
                        int type = GetType(Coapheader);

                        if(type == 0)//this is a CON
                        {
                            CoAP_mes con_mes = new CoAP_mes(transportPacket.getPacket(Protocol.UDP).getArrivalTime(),
                                    transportPacket.getDestinationPort(),transportPacket.getSourcePort(), mid);
                            CON_Que.offer(con_mes);
                        }
                        else if(type == 2)//this is an ACK message
                        {
                            CoAP_mes ack_mes = new CoAP_mes(transportPacket.getPacket(Protocol.UDP).getArrivalTime(),
                                    transportPacket.getDestinationPort(), transportPacket.getSourcePort(), mid);
                            while (!CON_Que.isEmpty())
                            {
                                CoAP_mes coap_mes = CON_Que.poll();
                                if(coap_mes.getMid() == ack_mes.getMid() )
                                {
                                    //match
                                    System.out.println("match !");
                                    int dif = Get_diff_ms(ack_mes.getArrive_date(), coap_mes.getArrive_date());
                                    System.out.println("time difference : " + dif);
                                    String RTT =  dif + ", ";
                                    out_RTT.write(RTT);

                                    Date newdata = new Date(coap_mes.getArrive_date() / 1000);
                                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy, MM, dd, H, m, s, S");
                                    System.out.println("datetime(" + formatter.format(newdata) + ")");
                                    System.out.println(newdata);
                                    String datatowrite = "datetime(" + formatter.format(newdata) + ")";
                                    out_datetime.write(datatowrite + ", ");
                                    i++;
                                    return true;
                                }
                                else
                                {
                                    //notmatch
                                    System.out.println("not match>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                                    System.out.println("CON MID : " + coap_mes.getMid());
                                    Date date = new Date(coap_mes.getArrive_date()/1000 );
                                    System.out.println("CON TIME : " + date);
                                    System.out.println("CON DesPort : " + coap_mes.getDes_port());
                                    System.out.println("CON SrcPort : " + coap_mes.getSrc_port());


                                    Date newdata = new Date(coap_mes.getArrive_date() / 1000);
                                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy, MM, dd, H, m, s, S");
                                    String datatowrite = "datetime(" + formatter.format(newdata) + ")";
                                    System.out.println("datatowrite" + datatowrite);
                                    out_RTT_lost.write(datatowrite + ", ");
                                }
                            }
                            System.out.println("queue is empty !>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                            System.out.println("ACK mid : " + ack_mes.getMid());
                            System.out.println("ACK time : " + new Date(ack_mes.getArrive_date()/1000));
                            System.out.println("ACK DesPort : " + ack_mes.getDes_port());
                            System.out.println("ACK SrcPort : " + ack_mes.getSrc_port());



                        }
                    }
                    i++;
                    return true;
                }
                public int Get_diff_ms(long ACK, long CON)
                {
                    Date CON_Date = new Date(CON/1000 );
                    Date ACK_Date = new Date(ACK/1000 );
                    long cha = ACK_Date.getTime() - CON_Date.getTime();
                    return (int)cha;
                }
                public int GetMID(byte[] CoAPheader) throws IOException {
                    int high = (int)((CoAPheader[2] & 0xff) << 8) & 0xffff;
                    int low = (int)(CoAPheader[3] & 0xff) & 0xffff;
                    int MID = (high + low) & 0xffff;
                    System.out.println("MID : " + MID);
                    return MID;
                }
                /**
                 *
                 * @return 0 means:CON
                 * @return 1 means:NON (I am not sure right now!)
                 * @return 2 means:ACK
                 */
                public int GetType(byte[] CoAPheader)
                {
                    byte Type = (byte) ((CoAPheader[0] & 0x30) >> 4);
                    return (int)Type;
                }

            });
            System.out.println();
        } catch (FileNotFoundException e) {
            System.out.println("filenotfound");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
        }
        try
        {
            out_datetime.close();
            out_RTT_lost.close();
            out_RTT.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
