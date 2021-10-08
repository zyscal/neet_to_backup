import Simple_class.CoAP_mes;
import io.pkts.PacketHandler;
import io.pkts.Pcap;
import io.pkts.buffer.Buffer;
import io.pkts.packet.IPPacket;
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

public class TWONB_UDP_REG_UP_DOWN {
    public BufferedWriter out_3323_UpLink_datetime;
    public BufferedWriter out_3323_UpLink_value;
    public BufferedWriter out_3323_DownLink_datetime;
    public BufferedWriter out_3323_DownLink_value;
    public BufferedWriter out_3323_UpLink_loss;
    public BufferedWriter out_3323_DownLink_loss;
    public BufferedWriter out_3323_IPchange_time;
    public BufferedWriter out_3323_server_Recv_time;

    public BufferedWriter out_3322_UpLink_datetime;
    public BufferedWriter out_3322_UpLink_value;
    public BufferedWriter out_3322_DownLink_datetime;
    public BufferedWriter out_3322_DownLink_value;
    public BufferedWriter out_3322_UpLink_loss;
    public BufferedWriter out_3322_DownLink_loss;
    public BufferedWriter out_3322_IPchange_time;
    public BufferedWriter out_3322_server_Recv_time;

    public String last_3322_ip = "";
    public String last_3323_ip = "";
    public TWONB_UDP_REG_UP_DOWN()
    {
        try {
            out_3323_UpLink_datetime = new BufferedWriter(new FileWriter("port_3323_UpLink_datetime.txt"));
            out_3323_UpLink_value = new BufferedWriter(new FileWriter("port_3323_UpLink_value.txt"));
            out_3323_DownLink_datetime = new BufferedWriter(new FileWriter("port_3323_DownLink_datetime.txt"));
            out_3323_DownLink_value = new BufferedWriter(new FileWriter("port_3323_DownLink_value.txt"));
            out_3323_UpLink_loss = new BufferedWriter(new FileWriter("port_3323_UpLink_loss_time.txt"));
            out_3323_DownLink_loss = new BufferedWriter(new FileWriter("port_3323_DownLink_loss_time.txt"));
            out_3323_IPchange_time = new BufferedWriter(new FileWriter("port_3323_IPchange_time.txt"));
            out_3323_server_Recv_time = new BufferedWriter(new FileWriter("port_3323_server_Recv_time.txt"));

            out_3322_UpLink_datetime = new BufferedWriter(new FileWriter("port_3322_UpLink_datetime.txt"));
            out_3322_UpLink_value = new BufferedWriter(new FileWriter("port_3322_UpLink_value.txt"));
            out_3322_DownLink_datetime = new BufferedWriter(new FileWriter("port_3322_DownLink_datetime.txt"));
            out_3322_DownLink_value = new BufferedWriter(new FileWriter("port_3322_DownLink_value.txt"));
            out_3322_UpLink_loss = new BufferedWriter(new FileWriter("port_3322_UpLink_loss_time.txt"));
            out_3322_DownLink_loss = new BufferedWriter(new FileWriter("port_3322_DownLink_loss_time.txt"));
            out_3322_IPchange_time = new BufferedWriter(new FileWriter("port_3322_IPchange_time.txt"));
            out_3322_server_Recv_time = new BufferedWriter(new FileWriter("port_3322_server_Recv_time.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Up_Down_delay()
    {
        ArrayList UpLink = new ArrayList();
        ArrayList DownLink = new ArrayList();
        handle_local(UpLink, DownLink);
        System.out.println(UpLink.size());
        System.out.println(DownLink.size());
        handle_huawei(UpLink, DownLink);
    }
    public void handle_local(ArrayList UpLink, ArrayList DownLink)
    {
        System.out.println("-----------begin to handle UpLink--------------");
        try {
            Pcap pcap = Pcap.openStream("/home/zyscal/Documents/need_to_backup/wiresahrk_caught/two_NB_24h/handle_20201221_8_15_20201221_16_30/displayed_both3323_3322.pcap");
            pcap.loop(new PacketHandler() {
                @Override
                public boolean nextPacket(Packet packet) throws IOException {
                    TransportPacket transportPacket = (TransportPacket)packet.getPacket(Protocol.UDP);
                    if(transportPacket.hasProtocol(Protocol.UDP))
                    {
                        Buffer Coap = transportPacket.getPacket(Protocol.UDP).getPayload();
                        byte[] Coapheader = new byte[12];
                        Coap.getBytes(Coapheader);
                        int mid = GetMID(Coapheader);
                        int type = GetType(Coapheader);
                        byte[] Token = GetToken(Coapheader);
                        if(type == 0)//UpLink
                        {
                            CoAP_mes con_mes = new CoAP_mes(transportPacket.getPacket(Protocol.UDP).getArrivalTime(),
                                    transportPacket.getDestinationPort(),transportPacket.getSourcePort(), mid, Token);
                            UpLink.add(con_mes);
                        }else if(type == 2)//DownLink
                        {
                            CoAP_mes con_mes = new CoAP_mes(transportPacket.getPacket(Protocol.UDP).getArrivalTime(),
                                    transportPacket.getDestinationPort(),transportPacket.getSourcePort(), mid, Token);
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
                public byte[] GetToken(byte[] CoAPheader)
                {
                    byte[] Token = new byte[8];
                    for(int i = 4,j = 0; i <= 11; i++,j++)
                    {
                        Token[j] = CoAPheader[i];
                    }
                    return Token;
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
            Pcap pcap = Pcap.openStream("/home/zyscal/Documents/need_to_backup/wiresahrk_caught/two_NB_24h/handle_20201221_8_15_20201221_16_30/server.pcap");
            pcap.loop(new PacketHandler() {
                @Override
                public boolean nextPacket(Packet packet) throws IOException {
                    TransportPacket transportPacket = (TransportPacket)packet.getPacket(Protocol.UDP);
                    IPPacket ipPacket = (IPPacket)packet.getPacket(Protocol.IPv4);
                    if(transportPacket.hasProtocol(Protocol.UDP))
                    {
                        Buffer Coap = transportPacket.getPacket(Protocol.UDP).getPayload();
                        byte[] Coapheader = new byte[12];
                        Coap.getBytes(Coapheader);
                        int mid = GetMID(Coapheader);
                        int type = GetType(Coapheader);
                        byte[] Token = GetToken(Coapheader);
                        if(type == 0)//UpLink
                        {
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy, MM, dd, H, m, s, S");
                            for(int i = 0; i < UpLink.size(); i++)
                            {
                                CoAP_mes coap_mes = UpLink.get(i);
                                if(coap_mes.getMid() == mid && Token_same(Token, coap_mes.getToken()))
                                {
                                    UpLink.remove(i);
                                    Date UpLinkSend = new Date(coap_mes.getArrive_date() / 1000);
                                    Date UpLinkRecv = new Date(transportPacket.getArrivalTime() / 1000);
                                    System.out.println("match !");
//                                    System.out.println("UpLinkSend : " + formatter.format(UpLinkSend));
                                    String UpLink_time = "" + Get_diff_ms(transportPacket.getArrivalTime(), coap_mes.getArrive_date());

//                                    System.out.println("UpLink time : " + UpLink_time);
                                    if(coap_mes.des_port == 3323)
                                    {
                                        out_3323_UpLink_value.write(UpLink_time + ", ");
                                        out_3323_UpLink_datetime.write("datetime(" + formatter.format(UpLinkSend) + "), ");
                                        out_3323_server_Recv_time.write("datetime(" + formatter.format(UpLinkRecv) + "), ");
                                        if(!ipPacket.getSourceIP().equals(last_3323_ip))
                                        {
                                            out_3323_IPchange_time.write("datetime(" + formatter.format(UpLinkSend) + "), ");
                                            last_3323_ip = ipPacket.getSourceIP();
                                        }
                                    }
                                    else if(coap_mes.des_port == 3322)
                                    {
                                        out_3322_UpLink_value.write(UpLink_time + ", ");
                                        out_3322_UpLink_datetime.write("datetime(" + formatter.format(UpLinkSend) + "), ");
                                        if(!ipPacket.getSourceIP().equals(last_3322_ip))
                                        {
                                            out_3322_IPchange_time.write("datetime(" + formatter.format(UpLinkSend) + "), ");
                                            last_3322_ip = ipPacket.getSourceIP();
                                        }
                                    }
                                    return true;
                                }
                                else
                                {
//                                    System.out.println("################not match!");
                                    Date DownLinkSend= new Date(transportPacket.getArrivalTime() / 1000);
//                                    System.out.println("DownLinkSend : " + formatter.format(DownLinkSend));
//                                    System.out.println("mid : " + mid);
                                }
                            }
                            System.out.println(">>>>>>>>>>>>>>>>empty");
                            Date DownLinkSend= new Date(transportPacket.getArrivalTime() / 1000);
//                            System.out.println("DownLinkSend : " + formatter.format(DownLinkSend));
//                            System.out.println("mid : " + mid);
                        }
                        else if(type == 2)//DownLink
                        {
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy, MM, dd, H, m, s, S");
                            for(int i = 0; i < DownLink.size(); i++)
                            {
                                CoAP_mes coap_mes = DownLink.get(i);
                                if(coap_mes.getMid() == mid && Token_same(Token, coap_mes.getToken()))
                                {
                                    DownLink.remove(i);
                                    Date DownLinkRecv = new Date(coap_mes.getArrive_date() / 1000);
                                    System.out.println("match !");
//                                    System.out.println("DownLinkRecv : " + formatter.format(DownLinkRecv));
                                    String Down_time = "" + Get_diff_ms(coap_mes.getArrive_date(), transportPacket.getArrivalTime());
//                                    System.out.println("Down_time : " + Down_time);

                                    if(coap_mes.src_port == 3323)
                                    {
                                        out_3323_DownLink_value.write(Down_time + ", ");
                                        out_3323_DownLink_datetime.write("datetime(" + formatter.format(DownLinkRecv) + "), ");
                                    }
                                    else if(coap_mes.src_port == 3322)
                                    {
                                        out_3322_DownLink_value.write(Down_time + ", ");
                                        out_3322_DownLink_datetime.write("datetime(" + formatter.format(DownLinkRecv) + "), ");
                                    }
                                    return true;
                                }
                                else
                                {
//                                    System.out.println("################not match!");
                                    Date UpLinkRecv = new Date(transportPacket.getArrivalTime() / 1000);
//                                    System.out.println("UpLinkRecv : " + formatter.format(UpLinkRecv));
//                                    System.out.println("mid : " + mid);
                                }
                            }
                            System.out.println(">>>>>>>>>>>>>>>>>>>is empty !");
                            Date UpLinkRecv = new Date(transportPacket.getArrivalTime() / 1000);
//                            System.out.println("UpLinkRecv : " + formatter.format(UpLinkRecv));
//                            System.out.println("mid : " + mid);
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
                public byte[] GetToken(byte[] CoAPheader)
                {
                    byte[] Token = new byte[8];
                    for(int i = 4,j = 0; i <= 11; i++,j++)
                    {
                        Token[j] = CoAPheader[i];
                    }
                    return Token;
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
                public boolean Token_same(byte[] a, byte[] b)
                {
                    for(int i = 0; i < 8; i++)
                    {
                        if(a[i] != b[i])
                        {
                            return false;
                        }
                    }
                    return true;
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
        for(CoAP_mes tempcoap_mes : UpLink)
        {
            Date date = new Date(tempcoap_mes.getArrive_date() / 1000);
            String formatdate = "datetime(" + format.format(date) + "), ";
            try
            {
                if(tempcoap_mes.des_port == 3323)
                {
                    out_3323_UpLink_loss.write(formatdate);
                }
                else if(tempcoap_mes.des_port == 3322)
                {
                    out_3322_UpLink_loss.write(formatdate);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for(CoAP_mes tempcoap_mes : DownLink)
        {
            Date date = new Date(tempcoap_mes.getArrive_date() / 1000);
            String formatdate = "datetime(" + format.format(date) + "), ";
            try
            {
                if(tempcoap_mes.src_port == 3323)
                {
                    out_3323_DownLink_loss.write(formatdate);
                }
                else if(tempcoap_mes.src_port == 3322)
                {
                    out_3322_DownLink_loss.write(formatdate);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try
        {
            out_3323_UpLink_datetime.close();
            out_3323_UpLink_value.close();
            out_3323_DownLink_datetime.close();
            out_3323_DownLink_value.close();
            out_3323_UpLink_loss.close();
            out_3323_DownLink_loss.close();
            out_3323_IPchange_time.close();
            out_3323_server_Recv_time.close();

            out_3322_UpLink_datetime.close();
            out_3322_UpLink_value.close();
            out_3322_DownLink_datetime.close();
            out_3322_DownLink_value.close();
            out_3322_UpLink_loss.close();
            out_3322_DownLink_loss.close();
            out_3322_IPchange_time.close();
            out_3322_server_Recv_time.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
