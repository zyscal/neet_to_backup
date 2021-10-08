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
import java.util.ArrayList;
import java.util.Date;

public class NB_UDP_REG_UP_DOWN {
    public BufferedWriter out_UpLink_datetime;
    public BufferedWriter out_UpLink_value;
    public BufferedWriter out_DownLink_datetime;
    public BufferedWriter out_DownLink_value;
    public BufferedWriter out_UpLink_loss;
    public BufferedWriter out_DownLink_loss;

    public NB_UDP_REG_UP_DOWN()
    {
        try {
            out_UpLink_datetime = new BufferedWriter(new FileWriter("UpLink_datetime.txt"));
            out_UpLink_value = new BufferedWriter(new FileWriter("UpLink_value.txt"));
            out_DownLink_datetime = new BufferedWriter(new FileWriter("DownLink_datetime.txt"));
            out_DownLink_value = new BufferedWriter(new FileWriter("DownLink_value.txt"));
            out_UpLink_loss = new BufferedWriter(new FileWriter("UpLink_loss_time.txt"));
            out_DownLink_loss = new BufferedWriter(new FileWriter("DownLink_loss_time.txt"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Up_Down_delay()
    {
        ArrayList UpLink = new ArrayList();
        ArrayList DownLink = new ArrayList();
        handle_local(UpLink, DownLink);
        handle_huawei(UpLink, DownLink);
    }
    public void handle_local(ArrayList UpLink, ArrayList DownLink)
    {
        System.out.println("-----------begin to handle UpLink--------------");
        try {
            Pcap pcap = Pcap.openStream("/home/zyscal/Documents/need_to_backup/wiresahrk_caught/1000nb/1000nb_displayed.pcap");
            pcap.loop(new PacketHandler() {
                @Override
                public boolean nextPacket(Packet packet) throws IOException {
                    TransportPacket transportPacket = (TransportPacket)packet.getPacket(Protocol.UDP);
                    if(transportPacket.hasProtocol(Protocol.UDP))
                    {
                        Buffer Coap = transportPacket.getPacket(Protocol.UDP).getPayload();
                        byte[] Coapheader = new byte[4];
                        Coap.getBytes(Coapheader);
                        int mid = GetMID(Coapheader);
                        int type = GetType(Coapheader);
                        if(type == 0)//UpLink
                        {
                            CoAP_mes con_mes = new CoAP_mes(transportPacket.getPacket(Protocol.UDP).getArrivalTime(),
                                    transportPacket.getDestinationPort(),transportPacket.getSourcePort(), mid);
                            UpLink.add(con_mes);
                        }else if(type == 2)//DownLink
                        {
                            CoAP_mes con_mes = new CoAP_mes(transportPacket.getPacket(Protocol.UDP).getArrivalTime(),
                                    transportPacket.getDestinationPort(),transportPacket.getSourcePort(), mid);
                            DownLink.add(con_mes);
                        }
                    }
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

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void handle_huawei(ArrayList<CoAP_mes> UpLink, ArrayList<CoAP_mes> DownLink)
    {
        System.out.println("UpLink.size() : " + UpLink.size());
        System.out.println("Down.size() : " + UpLink.size());
        try
        {
            Pcap pcap = Pcap.openStream("/home/zyscal/Documents/need_to_backup/wiresahrk_caught/1000nb/20201211regudp.pcap");
            pcap.loop(new PacketHandler() {
                @Override
                public boolean nextPacket(Packet packet) throws IOException {
                    TransportPacket transportPacket = (TransportPacket)packet.getPacket(Protocol.UDP);
                    if(transportPacket.hasProtocol(Protocol.UDP))
                    {
                        Buffer Coap = transportPacket.getPacket(Protocol.UDP).getPayload();
                        byte[] Coapheader = new byte[4];
                        Coap.getBytes(Coapheader);
                        int mid = GetMID(Coapheader);
                        int type = GetType(Coapheader);
                        if(type == 0)//UpLink
                        {
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy, MM, dd, H, m, s, S");
                            for(int i = 0; i < UpLink.size(); i++)
                            {
                                CoAP_mes coap_mes = UpLink.get(i);
                                if(coap_mes.getMid() == mid)
                                {
                                    UpLink.remove(i);
                                    Date UpLinkSend = new Date(coap_mes.getArrive_date() / 1000);
                                    System.out.println("match !");
                                    System.out.println("UpLinkSend : " + formatter.format(UpLinkSend));
                                    String UpLink_time = "" + Get_diff_ms(transportPacket.getArrivalTime(), coap_mes.getArrive_date());

                                    System.out.println("UpLink time : " + UpLink_time);
                                    out_UpLink_value.write(UpLink_time + ", ");
                                    out_UpLink_datetime.write("datetime(" + formatter.format(UpLinkSend) + "), ");
                                    return true;
                                }
                                else
                                {
                                    System.out.println("################not match!");
                                    Date DownLinkSend= new Date(transportPacket.getArrivalTime() / 1000);
                                    System.out.println("DownLinkSend : " + formatter.format(DownLinkSend));
                                    System.out.println("mid : " + mid);
                                }
                            }
                            System.out.println(">>>>>>>>>>>>>>>>empty");
                            Date DownLinkSend= new Date(transportPacket.getArrivalTime() / 1000);
                            System.out.println("DownLinkSend : " + formatter.format(DownLinkSend));
                            System.out.println("mid : " + mid);
                        }
                        else if(type == 2)//DownLink
                        {
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy, MM, dd, H, m, s, S");
                            for(int i = 0; i < DownLink.size(); i++)
                            {
                                CoAP_mes coap_mes = DownLink.get(i);
                                if(coap_mes.getMid() == mid)
                                {
                                    DownLink.remove(i);
                                    Date DownLinkSend = new Date(transportPacket.getArrivalTime() / 1000);
                                    System.out.println("match !");
                                    System.out.println("DownLinkSend : " + formatter.format(DownLinkSend));
                                    String Down_time = "" + Get_diff_ms(coap_mes.getArrive_date(), transportPacket.getArrivalTime());
                                    System.out.println("Down_time : " + Down_time);
                                    out_DownLink_value.write(Down_time + ", ");
                                    out_DownLink_datetime.write("datetime(" + formatter.format(DownLinkSend) + "), ");
                                    return true;
                                }
                                else
                                {
                                    System.out.println("################not match!");
                                    Date UpLinkRecv = new Date(transportPacket.getArrivalTime() / 1000);
                                    System.out.println("UpLinkRecv : " + formatter.format(UpLinkRecv));
                                    System.out.println("mid : " + mid);
                                }
                            }
                            System.out.println(">>>>>>>>>>>>>>>>>>>is empty !");
                            Date UpLinkRecv = new Date(transportPacket.getArrivalTime() / 1000);
                            System.out.println("UpLinkRecv : " + formatter.format(UpLinkRecv));
                            System.out.println("mid : " + mid);
                        }
                    }
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("DownLink ArrayList size() : " + DownLink.size());
        System.out.println("UpLink ArrayList size() : " + UpLink.size());
        SimpleDateFormat format = new SimpleDateFormat("yyyy, MM, dd, H, m, s, S");
        for(CoAP_mes tempcaop_mes : UpLink)
        {
            Date date = new Date(tempcaop_mes.getArrive_date() / 1000);
            String formatdate = "datetime(" + format.format(date) + "), ";
            try
            {
                out_UpLink_loss.write(formatdate);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try
        {
            out_UpLink_datetime.close();
            out_UpLink_value.close();
            out_DownLink_datetime.close();
            out_DownLink_value.close();
            out_UpLink_loss.close();
            out_DownLink_loss.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
